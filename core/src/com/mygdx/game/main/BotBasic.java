package com.mygdx.game.main;

import javax.swing.*;
import java.util.ArrayList;

public class BotBasic {
    double radius;
    double[] holeLocation= new double[2];
    double[] myLocation = new double[2];
    ArrayList<WeightedVector> wVectors;

    BotBasic(){
        radius = DataField.targetRXY[0];
        holeLocation[0] = DataField.targetRXY[1];
        holeLocation[1] = DataField.targetRXY[2];
        myLocation[0] = DataField.x;
        myLocation[1] = DataField.y;
        wVectors = new ArrayList<>();
    }

    /**
     * This method starts the bot upon GUI button press
     * @return false if the bot succeeds
     */
    public boolean start(){

        myLocation[0] = DataField.x;
        myLocation[1] = DataField.y;
        ArrayList<Double> xc = new ArrayList<>();
        ArrayList<Double> yc = new ArrayList<>();

        //update weights.
        WeightedVector temp = calcV();
        xc.add(temp.x);
        DataField.velocityX = xc;
        yc.add(temp.y);
        DataField.velocityY = yc;
        System.out.println("x-vel:"+xc);
        System.out.println("y-vel:"+yc);
        DataField.GUI = false;

        Timer t = new Timer(100, e1 -> {
            DataField.GUI = true;
        });

        return false;
    }

    /**
     * @return a weighted vector based on the ball's position and the hole's position
     */
    private WeightedVector calcV(){
        double xt = Distance(myLocation[0],holeLocation[0]);
        double yt = Distance(holeLocation[1],myLocation[1]);

        if(myLocation[0]>holeLocation[0])
            xt = -xt;
        if(myLocation[1]>holeLocation[1])
            yt = -yt;

        return new WeightedVector(xt,yt);
    }

    /**
     * @param x1 represents coordinate point x1
     * @param x2 represents coordinate point x2
     * @return the distance from point x1 to point x2
     */
    private double Distance(double x1, double x2){
        return Math.sqrt(Math.pow((x2-x1) ,2));
    }

    class WeightedVector{
        double x;
        double y;

        WeightedVector(double x, double y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString(){
            return "x: "+x+" y: "+y;
        }

    }

}
