package HillClimbingAI;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.main.DataField;
import solvers.RungeKutta4;
import solvers.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;

public class HillClimbingBot {

    private HillClimbing hc;
    private double [] coordsAndVel;



    BiFunction<Double, Double, Double> terrain;
    private double sFriction;
    private double kFriction;
    private double [] targetRXY;


    public HillClimbingBot(HillClimbing hc, double [] coords, Solver solver){
        this.coordsAndVel=new double[]{coords[0],coords[1], 0,0};
        this.terrain=DataField.terrain;
        this.kFriction=DataField.kFriction;
        this.sFriction=DataField.sFriction;
        this.targetRXY=DataField.targetRXY;
        this.hc = new HillClimbing(solver);
    }

    public void hillClimbingBot(){

        //maamy   coordsAndVel
        // coordsAndVel what is it?
        Solver solver = new RungeKutta4(terrain, coordsAndVel, kFriction,sFriction,  targetRXY);
        ArrayList<Double> xVelocities = new ArrayList<>();
        ArrayList<Double> yVelocities = new ArrayList<>();


        while (!(Math.pow(targetRXY[0],2)>(Math.pow((coordsAndVel[0]-targetRXY[1]), 2 )+Math.pow((coordsAndVel[1]-targetRXY[2]), 2 )))){

            double [] vel = hc.getInitialDirection(terrain, coordsAndVel, kFriction,sFriction);
           // System.out.println(Arrays.toString(vel));
            double[] best_velocity = hc.hillClimbing(vel,terrain, coordsAndVel, kFriction,sFriction);
            //System.out.println("WE ARE HERE" +Arrays.toString(coordsAndVel) + "best vel : " + Arrays.toString(best_velocity));
            coordsAndVel = new double[] {coordsAndVel[0],coordsAndVel[1], best_velocity[0],best_velocity[1]};
             double[]  coords = {coordsAndVel[0],coordsAndVel[1]};
            //solver = new RungeKutta4(terrain, coordsAndVel, kFriction,sFriction, targetRXY);
            System.out.println(Arrays.toString(coordsAndVel));
            double [] newCoor = hc.performShot(0.0001, best_velocity,terrain,coords,kFriction,sFriction);
             // System.out.println("after");
            xVelocities.add(best_velocity[0]);
            yVelocities.add(best_velocity[1]);

           // solver.setVelocity(best_velocity[0],best_velocity[1]);
            coordsAndVel[0] = newCoor[0];//solver.coordinatesAndVelocityUntilStop(0.0000001,false)[0];
            coordsAndVel[1] = newCoor[1];//solver.coordinatesAndVelocityUntilStop(0.0000001,false)[1];
           // System.out.println(Arrays.toString(coordsAndVel));
            coordsAndVel[2]=0.001;
            coordsAndVel[3]=0.001;

            //System.out.println(Arrays.toString(coordsAndVel));
            //System.out.println(Arrays.toString(coordsAndVel));
        }
        //System.out.println("Hill Climbing xVel: " + xVelocities);
        //System.out.println("Hill Climbing yVel: " + yVelocities);
    }

    public static void main(String[] args) {
        double [] coordsAndVel = {-3.0,0.0,0.1,0.1};
        BiFunction<Double, Double, Double> terrain = (x,y) -> 2.0;//0.4*(0.9-Math.exp(-((x*x+y*y)/8.0)));
        Solver solver = new RungeKutta4(terrain, coordsAndVel, DataField.kFriction,DataField.sFriction,  DataField.targetRXY);

        HillClimbing h = new HillClimbing(solver);
        HillClimbingBot hcb = new HillClimbingBot(h,new double []{coordsAndVel[0],coordsAndVel[1]}, solver);
        hcb.hillClimbingBot();
    }

}
