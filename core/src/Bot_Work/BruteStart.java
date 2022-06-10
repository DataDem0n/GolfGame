package Bot_Work;

import com.mygdx.game.main.DataField;
import engine.GameEngine;
import solvers.RungeKutta4;

import javax.swing.*;
import java.util.ArrayList;

public class BruteStart {

    ArrayList<Double> xVel;
    ArrayList<Double> yVel;
    CosineFinder FranklinTheSecond = new CosineFinder();
    ArrayList<WeightedVector> v;

    double initX;
    double initY;

        public BruteStart(double x, double y){
            initX = x;
            initY = y;
            DataField.velocityX = new ArrayList<>();
            DataField.velocityY = new ArrayList<>();
            v = FranklinTheSecond.vectorFind(DataField.x,DataField.y,DataField.targetRXY[1],DataField.targetRXY[2]);
            xVel = new ArrayList<>();
            yVel = new ArrayList<>();
        }

        public void start(){
            ArrayList<WeightedVector> allshots = simulate(DataField.x,DataField.y);
            System.out.println(allshots);
            ArrayList<WeightedVector> best10 = sort(allshots);
            System.out.println(best10);
            TakeShot(best10);
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

            for (int k = 0; k <= 10; k++) {
                best10.add(allshots.get(k));
            }

            return best10;
        }

        public ArrayList<WeightedVector> simulate(double ballInitX,double ballInitY) {

            for (WeightedVector wv:v) {
                RungeKutta4 rk4 = new RungeKutta4(DataField.terrain,new double[]{ballInitX,ballInitY,wv.getX(),wv.getY()},DataField.sFriction,DataField.kFriction,DataField.targetRXY);
//                GameEngine gm = new GameEngine(rk4);
                double[] out = rk4.coordinatesAndVelocityUntilStop(0.0001,false);
                wv.setWeight(rk4.getBestFinalDistance());

                System.out.println("------------ wee woo wee woo weight incoming ------------" + wv.getWeight());
            }
            return v;
        }

        private void TakeShot(ArrayList<WeightedVector> v1){

            for (WeightedVector wv:v1) {
                xVel.add(wv.getX());
                yVel.add(wv.getY());
            }

            DataField.x = initX;
            DataField.y = initY;

            DataField.velocityX.add(xVel.get(0));
            DataField.velocityY.add(yVel.get(0));

            xVel.remove(0);
            yVel.remove(0);

            DataField.GUI = false;

            Timer t = new Timer(100, e1 -> {
                DataField.GUI = true;
            });
        }
}
