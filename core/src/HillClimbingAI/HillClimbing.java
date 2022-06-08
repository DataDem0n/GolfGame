package HillClimbingAI;

import com.mygdx.game.main.DataField;
import solvers.RungeKutta4;
import solvers.Solver;

import java.util.Arrays;
import java.util.function.BiFunction;

public class HillClimbing {

    private TestShot testShot;
    private double bestFitness;
    private double[] bestVelocity;
    private Solver solver;

    public HillClimbing(Solver solver){

        this.solver = solver ;
        testShot = new TestShot(solver);
    }

    public double [] getInitialDirection(BiFunction<Double,Double,Double> terrain, double [] coordsAndVel, double kFriction, double sFriction) {

        double[][] velocities = {
                {1, 0}, {0, 1}, {-1, 0},{0, -1},
                {2, 0}, {0, 2}, {-2, 0},{0, -2},
                {4, 0}, {0, 4}, {-4, 0},{0, -4},
                {2,2}, {-2,2},{2,-2},{-2,-2},
                {1,1}, {-1,1},{1,-1},{-1,-1}};
        //more different values like 1 2 3 4
        testShot.testshot(0.001, new double[]{velocities[0][0], velocities[0][1]}, terrain, new double[]{coordsAndVel[0], coordsAndVel[1]}, kFriction, sFriction);
        double tempFit = testShot.getFitness();
        double[] bestVel = {2, 0};

        for (int i = 1; i < velocities.length; i++) {
            testShot.testshot(0.001, new double[]{velocities[i][0], velocities[i][1]}, terrain, new double[]{coordsAndVel[0], coordsAndVel[1]}, kFriction, sFriction);
            if (testShot.getFitness() < tempFit) {

                tempFit = testShot.getFitness();
                bestVel = new double[]{velocities[i][0], velocities[i][1]};

            }
        }
        return bestVel;
    }

    double stepSize = 0.1;
    double [][] directions= { {0,stepSize}, {stepSize,0}, {0,-stepSize}, {-stepSize,0}};

//    public double magnitude(double[] array){
//        return Math.sqrt(Math.pow(array[0], 2) + Math.pow(array[1], 2));
//    }
//    public double[] convertUnit(double[] array){
//        double magnitude = magnitude(array);
//        if (magnitude == 0 ){
//            return bestVelocity;
//        }
//        return new double[]{ array[0] / magnitude , array[1] / magnitude};
//    }


    public double[] hillClimbing(double [] bestVel ,BiFunction<Double,Double,Double> terrain, double [] coords, double kFriction, double sFriction) {
        testShot = new TestShot(solver);

        double [] bestDirect = getInitialDirection(terrain, new double[]{coords[0],coords[1],bestVel[0],bestVel[1]}, kFriction, sFriction);
        bestVelocity = bestDirect;
        testShot.testshot(0.001, bestDirect, terrain, coords, kFriction, sFriction);

        bestFitness = testShot.getFinalFitness();

        double bestFinalFitness = testShot.getFinalFitness();

        boolean isRunning = true;
        if(bestFinalFitness == 0){
            isRunning = false;
        }
        outer:
        while (isRunning) {
            double[] currFitness = new double[4];
            double[] currFinalFitness = new double[4];
            double[][] currVelocities = new double[4][2];
            double closestPoint = 999;
            double closestFinalPoint = 999;
            double [][]step = {{0.01,0},{0,0.01},{-0.01,0},{0,-0.01}};

            for (int i = 0; i < 4; i++) {
                double[] testVelocity = new double[2];
                testVelocity[0] = bestVelocity[0] + step[i][0];
                testVelocity[1] = bestVelocity[1] + step[i][1];
                if(fasterThan5(testVelocity)){
                    testVelocity = bestVelocity;
                }
                testShot.testshot(0.001, testVelocity, terrain, coords, kFriction, sFriction);
                closestPoint = testShot.getFinalFitness();
                closestFinalPoint = testShot.getFinalFitness();
                currVelocities[i] = testVelocity;
                currFitness[i] = closestPoint;
                currFinalFitness[i] = closestFinalPoint;

                if (testShot.getFinalFitness() < DataField.targetRXY[0]) {
                    bestVelocity = testVelocity;
                    break outer;
                  }
                }

            isRunning = false;
            for (int i = 0; i < currFinalFitness.length ; i++) {

                if( currFitness[i] < bestFitness ){
                    bestVelocity = currVelocities[i];
                    bestFitness = currFinalFitness[i];
                    isRunning = true ;
                }
            }
    }
      return bestVelocity;
    }

    public boolean fasterThan5(double[] velocity){
        return Math.sqrt(Math.pow(velocity[0], 2) + Math.pow(velocity[1], 2)) > 5;
    }


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        double [] coordsAndVel = {1,1,0,0};
        double[] coords = {5.0 , 5.0 , Math.random()*10 - 5, Math.random()*10 - 5};
        BiFunction<Double, Double, Double> terrain = (x,y) -> (1.0);
        Solver solver1 = new RungeKutta4(terrain, coords, 0.8,0.2,  new double[]{0.5, 10.0, 10.0});
        //TestShot test = new TestShot(solver1, 5.0,5.0);
        solver1.coordinatesAndVelocityUntilStop(0.001,false);
        double[] coor= {10.0,10.0};
        HillClimbing h = new HillClimbing(solver1);
        double [] vel = h.getInitialDirection(terrain, coordsAndVel, 0.1,0.2);
        System.out.println("Initial Direction: " + Arrays.toString(vel));
        double[] best_velocity = h.hillClimbing(vel,terrain, coor, 0.1,0.2);
        System.out.println("Hill Climbing: " + Arrays.toString(best_velocity));
        solver1.coordinatesAndVelocityUntilStop(0.1, false);
        long stopTime = System.nanoTime();
        long timeElapsed = stopTime - startTime;
        System.out.println("Time:  "+ timeElapsed);
    }

}
