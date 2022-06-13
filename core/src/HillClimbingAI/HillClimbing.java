package HillClimbingAI;
import com.mygdx.game.main.DataField;
import solvers.RungeKutta4;
import solvers.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;

public class HillClimbing {

    private TestShot testShot;
    private double bestFitness;
    private double[] bestVelocity;
    private Solver solver;

    private final double stepSize = 0.01;

    public HillClimbing(Solver solver){

        this.solver = solver ;
        testShot = new TestShot(solver);
    }

    public double [] getInitialDirection(double [] coordsAndVel) {

        // czy to nie dziala tylko jak initial position jest 0 0?
        double[] initVel = {-1.0*Math.abs(DataField.targetRXY[1]-coordsAndVel[0]), -1.0*Math.abs(DataField.targetRXY[2]-coordsAndVel[1])};
        double[] oppositeVel = convertRandomUnit(initVel);
        ArrayList<double[]> allVelocities = new ArrayList<>();

        testShot.testshot(0.001, oppositeVel, DataField.terrain, new double[]{coordsAndVel[0], coordsAndVel[1]}, DataField.kFriction, DataField.sFriction);
        double tempFit = testShot.getFinalFitness();
        allVelocities.add(new double[]{oppositeVel[0], oppositeVel[1], tempFit});
        double[] bestVel = oppositeVel;
        double amount = 360;

        for (int i = 1; i < amount; i++) {

            double[] nextVel = convertRandomUnit(rotateVector(1.0,oppositeVel));
            oppositeVel = nextVel;
            testShot.testshot(0.001, nextVel, DataField.terrain, new double[]{coordsAndVel[0], coordsAndVel[1]}, DataField.kFriction, DataField.sFriction);
            double currentShot = testShot.getFinalFitness();


            if(testShot.getDidGoThroughWater()){
                currentShot +=100;
                amount += 1;
            }

            if (currentShot < tempFit) {
                tempFit = currentShot;
                bestVel = nextVel;
            }
        }
        //System.out.println(Arrays.toString(bestVel));

        testShot.testshot(0.001, bestVel, DataField.terrain, new double[]{coordsAndVel[0], coordsAndVel[1]}, DataField.kFriction, DataField.sFriction);
        wentThroughWater=testShot.getDidGoThroughWater();
        return bestVel;
    }

    public double[] rotateVector(double angle, double[] array){
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        double[] result = new double [2];
        result[0] = array[0] * cos - array[1] * sin;
        result[1] = array[0] * sin + array[1] * cos;
        return result;
    }

    public double magnitude(double[] array){
        return Math.sqrt(Math.pow(array[0], 2) + Math.pow(array[1], 2));
    }

    public double[] convertRandomUnit(double[] array){
        double magnitude = magnitude(array);
        if (magnitude == 0 ){
            return bestVelocity;
        }
        return new double[]{ Math.random()*5.0 * array[0] / magnitude , Math.random()*5.0 * array[1] / magnitude};
    }

    public double[] hillClimbing(double [] coordsAndVel, double kFriction, double sFriction) {
        int counter = 0;
        testShot = new TestShot(solver);

        double [] bestDirect = getInitialDirection(coordsAndVel);
        System.out.println("BEST DIRECT: "+Arrays.toString(bestDirect));
        bestVelocity = bestDirect;
        testShot.testshot(0.001, bestDirect, DataField.terrain, coordsAndVel, kFriction, sFriction);
        bestFitness = testShot.getFinalFitness();
        if(bestFitness <= DataField.targetRXY[0]){
            return bestVelocity;
        }

        double bestFinalFitness = testShot.getFinalFitness();

        boolean isRunning = true;
        if(bestFinalFitness == 0){
            isRunning = false;
        }
        outer:
        while (isRunning) {
            counter++;
            double[] currFitness = new double[4];
            double[][] currVelocities = new double[4][2];
            double closestPoint;
            double [][]step = { {0,stepSize}, {stepSize,0}, {0,-stepSize}, {-stepSize,0}};

            for (int i = 0; i < 4; i++) {
                double[] testVelocity = new double[2];
                testVelocity[0] = bestVelocity[0] + step[i][0];
                testVelocity[1] = bestVelocity[1] + step[i][1];
                if(fasterThan5(testVelocity)){
                    testVelocity = bestVelocity;
                }
                testShot.testshot(0.001, testVelocity, DataField.terrain, coordsAndVel, kFriction, sFriction);
                closestPoint = testShot.getFinalFitness();
                currVelocities[i] = testVelocity;
                currFitness[i] = closestPoint;
                if(testShot.getDidGoThroughWater()){
                    currFitness[i]+=500;
                    System.out.println("in waterrr");
                }

                if (testShot.getFinalFitness() <= DataField.targetRXY[0] && !testShot.getDidGoThroughWater()) {
                    System.out.println("win");
                    bestVelocity = testVelocity;
                    break outer;
                }
            }
            isRunning = false;
            for (int i = 0; i < currFitness.length ; i++) {
                if( currFitness[i] < bestFitness ){
                    bestVelocity = currVelocities[i];
                    bestFitness = currFitness[i];
                    isRunning = true ;
                }
                if(testShot.getDidGoThroughWater()){
                    isRunning = true;
                }
            }
            if(counter > 1000){
               bestVelocity = getInitialDirection(coordsAndVel);
            }
        }
        System.out.println(counter);
        return bestVelocity;
    }


    public boolean fasterThan5(double[] velocity){
        return Math.sqrt(Math.pow(velocity[0], 2) + Math.pow(velocity[1], 2)) > 5;
    }

    public double[] performShot(double step,double[] vel , BiFunction<Double,Double,Double> terrain, double []coordinates, double kFriction, double sFriction){
        return testShot.performShot(step,vel,terrain,coordinates,kFriction,sFriction);
    }


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        double [] coordsAndVel = {-3.0,0.0,0.1,0.1};
        //double[] coords = {0.50165 , 0.47448 , 0.1, 0.1};
        BiFunction<Double, Double, Double> terrain = (x,y) -> y*y-x*x;
        Solver solver1 = new RungeKutta4(terrain, coordsAndVel, 0.8,0.2,  DataField.targetRXY);
        //TestShot test = new TestShot(solver1, 5.0,5.0);
        solver1.coordinatesAndVelocityUntilStop(0.001,false);
        //double[] coor= {0.50165 , 0.47448};
        HillClimbing h = new HillClimbing(solver1);
        double [] vel = h.getInitialDirection( coordsAndVel);

        // System.out.println("Initial Direction: " + Arrays.toString(vel));
        double[] best_velocity = h.hillClimbing(new double[]{coordsAndVel[0],coordsAndVel[1]}, 0.1,0.2);
        // System.out.println("Hill Climbing: " + Arrays.toString(best_velocity));
        solver1.coordinatesAndVelocityUntilStop(0.1, false);
        long stopTime = System.nanoTime();
        long timeElapsed = stopTime - startTime;
        // System.out.println("Time:  "+ timeElapsed);
    }

}