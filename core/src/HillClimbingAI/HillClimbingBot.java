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

    public ArrayList<ArrayList<Double>> hillClimbingBot(){

        ArrayList<Double> xVelocities = new ArrayList<>();
        ArrayList<Double> yVelocities = new ArrayList<>();

        int counter = 0;

        while (!(Math.pow(targetRXY[0],2)>(Math.pow((coordsAndVel[0]-targetRXY[1]), 2 )+Math.pow((coordsAndVel[1]-targetRXY[2]), 2 )))){


            double[] best_velocity = hc.hillClimbing(coordsAndVel, kFriction, sFriction);
            coordsAndVel = new double[] {coordsAndVel[0],coordsAndVel[1], best_velocity[0],best_velocity[1]};
            double[]  coords = {coordsAndVel[0],coordsAndVel[1]};
            double [] newCoor = hc.performShot(0.0001, best_velocity,terrain,coords,kFriction,sFriction);

            if(hc.getWentThroguhWater()){
                hillClimbingBot();
            }

            xVelocities.add(best_velocity[0]);
            yVelocities.add(best_velocity[1]);

            coordsAndVel[0] = newCoor[0];
            coordsAndVel[1] = newCoor[1];

            coordsAndVel[2]=0.001;
            coordsAndVel[3]=0.001;
            counter++;
        }
        System.out.println("counter of bot    "+counter);
        System.out.println("Hill Climbing xVel: " + xVelocities);
        System.out.println("Hill Climbing yVel: " + yVelocities);
        ArrayList<ArrayList<Double>> velocitiesOutput = new ArrayList<>();
        velocitiesOutput.add(xVelocities);
        velocitiesOutput.add(yVelocities);
        return velocitiesOutput;
    }

    public static void main(String[] args) {
        double [] coordsAndVel = {0.0, 0.0,0.1,0.1};
        Solver solver = new RungeKutta4(DataField.terrain, coordsAndVel, DataField.kFriction,DataField.sFriction,  DataField.targetRXY);
        HillClimbing h = new HillClimbing(solver);
        HillClimbingBot hcb = new HillClimbingBot(h,new double []{coordsAndVel[0],coordsAndVel[1]}, solver);
        hcb.hillClimbingBot();
    }
}