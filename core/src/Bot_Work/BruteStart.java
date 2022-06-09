package Bot_Work;

import com.mygdx.game.main.DataField;

import javax.swing.*;
import java.util.ArrayList;

public class BruteStart {

    CosineFinder FranklinTheSecond = new CosineFinder();

        public boolean start() {
            ArrayList<WeightedVector> v = FranklinTheSecond.vectorFind(DataField.x,DataField.y,DataField.targetRXY[1],DataField.targetRXY[2]);
            ArrayList<Double> xVel = new ArrayList<>();
            ArrayList<Double> yVel = new ArrayList<>();
            for (WeightedVector wv:v) {
                xVel.add(wv.getX());
                yVel.add(wv.getY());
            }
            DataField.velocityX = new ArrayList<>();
            DataField.velocityY = new ArrayList<>();
            DataField.x = 0;
            DataField.y = 0;
            DataField.velocityX.add(xVel.get(0));
            DataField.velocityY.add(yVel.get(0));
            xVel.remove(0);yVel.remove(0);
            DataField.GUI = false;

            Timer t = new Timer(100, e1 -> {
                DataField.GUI = true;
            });
            return false;
        }
}
