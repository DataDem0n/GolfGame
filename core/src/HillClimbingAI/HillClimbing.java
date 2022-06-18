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
    private boolean wentThroughWater;

    private final double stepSize = 0.01;

    /**
     * Constructor for class HillClimbing
     * @param solver solver that will be used by the algorithm
     */
    public HillClimbing(Solver solver){

        this.solver = solver ;
        testShot = new TestShot(solver);
    }

    /**
     * Method that gets the best direction that gives the shortest euclidean distance from the ball to the hole
     * @param coordsAndVel state vector of the ball holding initial position and velocity
     * @return two velocities in array that are the best direction of all for a given simulation
     */
    public double[] getInitialDirection(double [] coordsAndVel) {

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

            allVelocities.add(new double[]{nextVel[0], nextVel[1], currentShot});
            if (currentShot < tempFit) {
                tempFit = currentShot;
                bestVel = nextVel;
            }
        }

        testShot.testshot(0.001, bestVel, DataField.terrain, new double[]{coordsAndVel[0], coordsAndVel[1]}, DataField.kFriction, DataField.sFriction);
        wentThroughWater=testShot.getDidGoThroughWater();
        return bestVel;
    }


    /**TODO: LIWIA CAN YOU CHECK THIS
     *
     * @param angle double that is an angle in degrees that the vector will be rotated by
     * @param array array that holds two compose vectors that will be rotated
     * @return array of doubles representing a vector (array) thta is rotated by an angle
     */
    public double[] rotateVector(double angle, double[] array){
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        double[] result = new double [2];
        result[0] = array[0] * cos - array[1] * sin;
        result[1] = array[0] * sin + array[1] * cos;
        return result;
    }

    /** TODO LIWIA PLS CHECK
     *
     * @param array array that holds two compose vectors
     * @return returns a magnitude vector of the vector (array)
     */
    public double magnitude(double[] array){
        return Math.sqrt(Math.pow(array[0], 2) + Math.pow(array[1], 2));
    }

    /**
     *
     * @param array
     * @return
     */
    public double[] convertRandomUnit(double[] array){
        double magnitude = magnitude(array);
        if (magnitude == 0 ){
            return bestVelocity;
        }
        return new double[]{ Math.random()*5.0 * array[0] / magnitude , Math.random()*5.0 * array[1] / magnitude};
    }

    /**
     *
     * @param coordsAndVel
     * @param kFriction
     * @param sFriction
     * @return
     */
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

    /**
     * Method that checks if the velocity is higher than 5
     * @param velocity array of compose vectors in x and y direction
     * @return a vector in the same direction that length is at most 5
     */
    public boolean fasterThan5(double[] velocity){
        return Math.sqrt(Math.pow(velocity[0], 2) + Math.pow(velocity[1], 2)) > 5;
    }

    /**
     * Method that performs a shot
     * @param step double step size used in a sovler
     * @param vel array of doubles nitial velocity
     * @param terrain two parameters function - terrain of the simulation
     * @param coordinates array holding initial coordinates of the ball
     * @param kFriction double - kinetic friction
     * @param sFriction double - static friction
     * @return new position and velocity of the ball after the shot
     */
    public double[] performShot(double step,double[] vel , BiFunction<Double,Double,Double> terrain, double []coordinates, double kFriction, double sFriction){
        return testShot.performShot(step,vel,terrain,coordinates,kFriction,sFriction);
    }

    /**
     * Method that returns if the ball went through water during the shot
     * @return true if the ball went through water and false otherwise
     */
    public  boolean getWentThroguhWater(){
        return wentThroughWater;
    }

}