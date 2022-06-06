package HillClimbingAI;

import com.mygdx.game.main.DataField;
import solvers.RungeKutta4;
import solvers.Solver;

import java.util.Arrays;
import java.util.function.BiFunction;

public class HillClimbing {

    private TestShot testShot;
    private double bestFitness ;
    private double[] bestVelocity ;
    private Solver solver ;

    public HillClimbing(Solver solver){
        this.solver = solver ;

    }

    //perfom 4 shots
    double stepSize = 0.1;
    double [][] directions= { {0,stepSize}, {stepSize,0}, {0,-stepSize}, {-stepSize,0}};

    public double magnitude(double[] array){
        return Math.sqrt(Math.pow(array[0], 2) + Math.pow(array[1], 2));
    }
    public double[] convertUnit(double[] array){
        double magnitude = magnitude(array);
        if (magnitude == 0 ){
            return bestVelocity;
        }
        return new double[]{ array[0] / magnitude , array[1] / magnitude};
    }
    public double[] testDirection(BiFunction<Double,Double,Double> terrain, double [] coords, double kFriction, double sFriction) {
        testShot = new TestShot(solver);

        double[] bestDirect = new double[]{ -(DataField.targetRXY[1] - coords[0]), -(DataField.targetRXY[2] - coords[1])};
//        if(magnitude(bestDirect) != 0){
//            bestDirect = convertUnit(bestDirect);
//        }
        bestVelocity = bestDirect;
        testShot.testshot(0.004, bestDirect, terrain, coords, kFriction, sFriction);
        bestFitness = testShot.getFitness();

        boolean isRunning = true ;
        if(bestFitness == 0 ){
            isRunning = false;
        }
        outer:
        while (isRunning) {
            double[] prevFitness = new double[4];
            double[][] prevVelocities = new double[4][2];
            double closestPoint = Integer.MAX_VALUE;

            for (int i = 0; i < 4; i++) {
                double[] testVelocity = new double[]{directions[i][0] + bestDirect[0], directions[i][1] + bestDirect[1]};
                testShot.testshot(0.001, testVelocity, terrain, coords, kFriction, sFriction);
                closestPoint = testShot.getFitness();
                prevVelocities[i] = testVelocity;

                prevFitness[i] = closestPoint;

                if (closestPoint < DataField.targetRXY[0]) {
                    bestVelocity = testVelocity;
                    break outer;
                }

            }
            isRunning = false;
            for (int i = 0; i < prevFitness.length ; i++) {
                if( prevFitness[i] < bestFitness ){
                    bestVelocity = prevVelocities[i];
                    bestFitness = prevFitness[i];
                    isRunning = true ;
                }
            }
    }
      return bestVelocity;
    }

    public static void main(String[] args) {

        double[] coords = {5.0 , 5.0 , Math.random()*10 - 5, Math.random()*10 - 5};
        BiFunction<Double, Double, Double> terrain = (x,y) -> (1.0);
        Solver solver1 = new RungeKutta4(terrain, coords, 0.8,0.2,  new double[]{0.5, 10.0, 10.0});
        //TestShot test = new TestShot(solver1, 5.0,5.0);
        solver1.coordinatesAndVelocityUntilStop(0.001,false);
        double[] coor= {10.0,10.0};
        HillClimbing h = new HillClimbing(solver1);
        double[] one_velocity = h.testDirection(terrain, coor, 0.1,0.2);
        System.out.println(Arrays.toString(one_velocity));
    }


}
