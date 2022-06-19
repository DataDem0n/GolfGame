package bruteforcebot;

import com.mygdx.game.main.DataField;
import solvers.RungeKutta4;
import noise.RandomNoise;
import javax.swing.*;
import java.util.ArrayList;

public class BruteStart {

    ArrayList<Double> xVel;
    ArrayList<Double> yVel;
    VectorFinder FranklinTheSecond = new VectorFinder();
    ArrayList<WeightedVector> v;

    private ArrayList<Double> seed;
    private RandomNoise noise;
    private static int counter =0;

    /**
     * This is the constructor for this class.
     */
    public BruteStart(){
        DataField.velocityX = new ArrayList<>();
        DataField.velocityY = new ArrayList<>();
        xVel = new ArrayList<>();
        yVel = new ArrayList<>();
    }

    /**
     * This is the method that starts the bot.
     */
    public void start(){

        v = FranklinTheSecond.vectorFind(DataField.x,DataField.y,DataField.targetRXY[1],DataField.targetRXY[2],FranklinTheSecond.vectorToPoint(DataField.x,DataField.y,DataField.targetRXY[1],DataField.targetRXY[2]),1,true,5);

        ArrayList<WeightedVector> allshots = simulate(DataField.x, DataField.y, v);
        ArrayList<WeightedVector> best10 = sort(allshots);

        System.out.println(best10);

        if (best10.get(0).getWeight()<1.0) {
            best10.get(0).setX(best10.get(0).getX()*1.1);
            best10.get(0).setY(best10.get(0).getY()*1.1);
        }

        System.out.println("XY:" + best10.get(0).getX() + " " + best10.get(0).getY());
        TakeShot(best10);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param allshots is a weighted vector ArrayList containing all the shots performed in the first iteration.
     * @return a new weighted vector ArrayList containing the best 10 vectors based on how low the weights are.
     */
    public ArrayList<WeightedVector> sort(ArrayList<WeightedVector> allshots){

        for (int j = 0;j < allshots.size(); j++) {
            for (int i = 0; i < allshots.size(); i++) {
                if(i+1<allshots.size()&&allshots.get(i).getWeight()>allshots.get(i+1).getWeight()){ //godlike sorting almost O(1) ;)
                    WeightedVector temp = allshots.get(i);
                    allshots.set(i,allshots.get(i+1));
                    allshots.set(i+1,temp);
                }
            }
        }

        ArrayList<WeightedVector> best10 = new ArrayList<>();

        for (int k = 0; k < 10; k++) {
            best10.add(allshots.get(k));
        }

        return best10;
    }

    /**
     * Simulates every shot in ArrayList v and gives a weight to that specific shot
     * @param ballInitX is the ball's X position at the moment of the simulation
     * @param ballInitY is the ball's Y position at the moment of the simulation
     * @param v is a weighted vector ArrayList containing all the shots to be simulated
     * @return the same ArrayList with weights added to every vector entry
     */
    public ArrayList<WeightedVector> simulate(double ballInitX,double ballInitY, ArrayList<WeightedVector> v) {
        for (WeightedVector wv:v) {
            counter++;
            RungeKutta4 rk4 = new RungeKutta4(DataField.terrain,new double[]{ballInitX,ballInitY,wv.getX(),wv.getY()},DataField.sFriction,DataField.kFriction,DataField.targetRXY);
            double[] out = rk4.coordinatesAndVelocityUntilStop(0.001,false);
            wv.setWeight(rk4.getBestFinalDistance());

            if(rk4.getDidGoThroughWater()){
                wv.setWeight(100);
            }
        }
        System.out.println("iterations: " +counter);
        return v;
    }

    /**
     * @param v1 contains the X and Y velocities used to take the shot.
     * This is also where the noise object from the noise package is created and used.
     */
    private void TakeShot(ArrayList<WeightedVector> v1){

        this.noise = new RandomNoise(1.0,1.1);
        this.seed = noise.generateSeed();

        xVel.clear();
        yVel.clear();

        DataField.velocityX.clear();
        DataField.velocityY.clear();

        for (WeightedVector wv:v1) {
            xVel.add(wv.getX());
            yVel.add(wv.getY());
        }

        DataField.velocityX.add(xVel.get(0));
        DataField.velocityY.add(yVel.get(0));

//            DataField.velocityX.add(xVel.get(0)*seed.get(counter));
//            DataField.velocityY.add(yVel.get(0)*seed.get(counter));

        System.out.println("X velocity: " + DataField.velocityX.get(0));
        System.out.println("Y velocity: " + DataField.velocityY.get(0));

        counter++;

        xVel.remove(0);
        yVel.remove(0);

        DataField.GUI = false;

        Timer t = new Timer(100, e1 -> {
            DataField.GUI = true;
        });

    }

}