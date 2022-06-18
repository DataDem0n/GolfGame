package HillClimbingAI;

import Noise.RandomNoise;
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

    private RandomNoise random;
    private ArrayList<Double> seed;

    /**
     * Constructor for a class HillClimbingBot
     * @param hc HillClimbing object holding  the solver used
     * @param coords initial coordinates of the ball
     * @param solver Solver that will be used by the bot
     */
    public HillClimbingBot(HillClimbing hc, double [] coords, Solver solver){
        this.coordsAndVel=new double[]{coords[0],coords[1], 0,0};
        this.terrain=DataField.terrain;
        this.kFriction=DataField.kFriction;
        this.sFriction=DataField.sFriction;
        this.targetRXY=DataField.targetRXY;
        this.hc = new HillClimbing(solver);
        this.random = new RandomNoise(0.60,1.4);
        this.seed = random.generateSeed();
    }

    /**
     * Algorithm that simulates all the shots until the game is over (win)
     * @return array list of all shots needed to win the game
     */
    public ArrayList<ArrayList<Double>> hillClimbingBot(){
        ArrayList<Double> xVelocities = new ArrayList<>();
        ArrayList<Double> yVelocities = new ArrayList<>();

        int counter = 0;

        while (!(Math.pow(targetRXY[0],2)>(Math.pow((coordsAndVel[0]-targetRXY[1]), 2 )+Math.pow((coordsAndVel[1]-targetRXY[2]), 2 )))){

            double[] best_velocity = hc.hillClimbing(coordsAndVel, kFriction, sFriction);
            coordsAndVel = new double[] {coordsAndVel[0],coordsAndVel[1], best_velocity[0],best_velocity[1]};
            double[]  coords = {coordsAndVel[0],coordsAndVel[1]};
            best_velocity = new double[]{best_velocity[0]*seed.get(counter), best_velocity[1]*seed.get(counter)};
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
        //System.out.println("counter of bot    "+counter);
        //System.out.println("Hill Climbing xVel: " + xVelocities);
        //System.out.println("Hill Climbing yVel: " + yVelocities);
        ArrayList<ArrayList<Double>> velocitiesOutput = new ArrayList<>();
        velocitiesOutput.add(xVelocities);
        velocitiesOutput.add(yVelocities);
        return velocitiesOutput;
    }
}