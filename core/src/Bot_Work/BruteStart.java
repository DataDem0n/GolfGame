package Bot_Work;

import com.mygdx.game.main.DataField;
import engine.GameEngine;
import solvers.RungeKutta4;
import Noise.RandomNoise;

import javax.swing.*;
import java.util.ArrayList;
import java.time.*;

public class BruteStart {

    ArrayList<Double> xVel;
    ArrayList<Double> yVel;
    CosineFinder FranklinTheSecond = new CosineFinder();
    ArrayList<WeightedVector> v;

    double initX;
    double initY;

    private RandomNoise random;
    private ArrayList<Double> seed;
    private static int counter =0;

        public BruteStart(double x, double y){

            initX = x;
            initY = y;
            DataField.velocityX = new ArrayList<>();
            DataField.velocityY = new ArrayList<>();
            xVel = new ArrayList<>();
            yVel = new ArrayList<>();
        }

//    public static void main(String[] args) {
//        BruteStart b = new BruteStart(0,0);
//        b.simulate(0,0);
//    }

        public ArrayList<WeightedVector> variance(WeightedVector mainshot, double step, double bound){

            double x = mainshot.getX();
            double y = mainshot.getY();

            ArrayList<WeightedVector> output = new ArrayList<>();

            for (double i = -bound; i <= bound; i+=step) {
                output.add(new WeightedVector(x+i,y+i));
                output.add(new WeightedVector(x+i,y-i));
                output.add(new WeightedVector(x-i,y+i));
            }

            return output;
        }

        public void start(){

            v = FranklinTheSecond.vectorFind(DataField.x,DataField.y,DataField.targetRXY[1],DataField.targetRXY[2],FranklinTheSecond.vectorToPoint(DataField.x,DataField.y,DataField.targetRXY[1],DataField.targetRXY[2]),1,true,5);

                ArrayList<WeightedVector> allshots = simulate(DataField.x, DataField.y, v);
                ArrayList<WeightedVector> best10 = sort(allshots);

                if (best10.get(0).getWeight()<1.0) {
                    best10.get(0).setX(best10.get(0).getX()*1.1);
                    best10.get(0).setY(best10.get(0).getY()*1.1);
                }

            System.out.println("XY:" + best10.get(0).getX() + " " + best10.get(0).getY());
                TakeShot(best10);

//                ArrayList<WeightedVector> t1 = sort(simulate(DataField.x, DataField.y, variance(best10.get(0), 0.1, 2)));
//                ArrayList<WeightedVector> t2 = sort(simulate(DataField.x, DataField.y, variance(t1.get(0), 0.01, 1)));
//                ArrayList<WeightedVector> t3 = sort(simulate(DataField.x, DataField.y, variance(t2.get(0), 0.001, .1)));
//                ArrayList<WeightedVector> t4 = sort(simulate(DataField.x, DataField.y, variance(t3.get(0), 0.0001, .25)));
//                ArrayList<WeightedVector> t5 = sort(simulate(DataField.x, DataField.y, variance(best10.get(1), 0.1, 2)));
//                ArrayList<WeightedVector> t6 = sort(simulate(DataField.x, DataField.y, variance(t5.get(0), 0.01, 1)));
//                ArrayList<WeightedVector> t7 = sort(simulate(DataField.x, DataField.y, variance(t6.get(0), 0.001, .1)));
//                ArrayList<WeightedVector> t8 = sort(simulate(DataField.x, DataField.y, variance(t7.get(0), 0.0001, .01)));
//                System.out.println(t4);
//
//                if (t8.get(0).getWeight() > t4.get(0).getWeight()) {
//                    System.out.println("ya dis tru man");
//                    TakeShot(t8);
//                } else {
//                    TakeShot(t4);
//                }



                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //shot 2
//                System.out.println("XY:" + DataField.x + " " + DataField.y);
//                allshots = simulate(DataField.x, DataField.y, v);
//                best10 = sort(allshots);


        }

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

        public ArrayList<WeightedVector> simulate(double ballInitX,double ballInitY, ArrayList<WeightedVector> v) {
            for (WeightedVector wv:v) {
                RungeKutta4 rk4 = new RungeKutta4(DataField.terrain,new double[]{ballInitX,ballInitY,wv.getX(),wv.getY()},DataField.sFriction,DataField.kFriction,DataField.targetRXY);
//
                double[] out = rk4.coordinatesAndVelocityUntilStop(0.0001,false);
                wv.setWeight(rk4.getBestFinalDistance());

                if(rk4.getDidGoThroughWater()){
                    wv.setWeight(100);
                }
            }
            return v;
        }

        private void TakeShot(ArrayList<WeightedVector> v1){

            this.random = new RandomNoise(1.0,1.1);
            this.seed = random.generateSeed();

            xVel.clear();
            yVel.clear();

            DataField.velocityX.clear();
            DataField.velocityY.clear();

            for (WeightedVector wv:v1) {
                xVel.add(wv.getX());
                yVel.add(wv.getY());
            }

            DataField.velocityX.add(xVel.get(0)*seed.get(counter));
            DataField.velocityY.add(yVel.get(0)*seed.get(counter));

            System.out.println("wagwan dis da velocity X " + DataField.velocityX.get(0));
            System.out.println("wagwan dis da velocity Y " + DataField.velocityY.get(0));

            counter++;

            xVel.remove(0);
            yVel.remove(0);

            DataField.GUI = false;

            Timer t = new Timer(100, e1 -> {
                DataField.GUI = true;
            });

        }

    public double increaseBasis(double t){
        double multiplier = 1.0;
        if(t<5&&t>=4){
            t = t*1.1;
        }
        else if(t<4&&t>=3){
            t = t*1.1;
        }
        else if(t<3&&t>=2){
            t = t*1.3;
        }
        else if(t<2&&t>=1){
            t = t*1.4;
        }
        else if(t<1&&t>=0){
            t = t*1.1;
        }

        return t;
    }
}