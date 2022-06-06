package HillClimbingAI;

import com.mygdx.game.main.DataField;
import solvers.RungeKutta2;
import solvers.RungeKutta4;
import solvers.Solver;

import java.io.PrintStream;
import java.util.function.BiFunction;


public class TestShot {

    private Solver solver;
    private double velocityX;
    private double velocityY;
    double targetX = DataField.targetRXY[1];
    double targetY = DataField.targetRXY[2];
    private double bestDistance;



   public TestShot(Solver solver){
       this.solver = solver;
       this.velocityX = velocityX;
       this.velocityY = velocityY;
   }
   public TestShot(Solver solver , double velocityX , double velocityY){
       this.solver = solver;
       this.velocityX = velocityX;
       this.velocityY = velocityY;
   }

   public void testshot(double step,double[] vel , BiFunction<Double,Double,Double> terrain, double []coordinates, double kFriction, double sFriction){
       double velocityX = vel[0] ;
       double velocityY = vel[1] ;
       double [] coordsAndVel = {coordinates[0], coordinates[1], velocityX, velocityY};
       solver=new RungeKutta4(terrain, coordsAndVel, kFriction, sFriction, DataField.targetRXY);
       double [] result = solver.coordinatesAndVelocityUntilStop(step,false);
       bestDistance=solver.getBestDistance();
   }

   public double getFitness(){
       return bestDistance;
   }


    public static void main(String[] args) {
        double[] coords = {10.0,10.0,1.0,1.0};
        BiFunction<Double, Double, Double> terrain = (x,y) -> (1.0);
        Solver solver1 = new RungeKutta4(terrain, coords, 0.8,0.2,  new double[]{10.0, 10.0, 10.0});
        //TestShot test = new TestShot(solver1, 5.0,5.0);
        solver1.coordinatesAndVelocityUntilStop(0.001,false);
        System.out.println( solver1.getBestDistance());




    }

}
