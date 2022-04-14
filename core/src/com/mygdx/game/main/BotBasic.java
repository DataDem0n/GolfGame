package com.mygdx.game.main;

import javax.swing.*;
import java.util.ArrayList;

public class BotBasic {
    double[] holeLocation= new double[2];
    double[] myLocation = new double[2];
    ArrayList<WeightedVector> wVectors;

    BotBasic(){
        holeLocation[0] = DataField.targetRXY[1];
        holeLocation[1] = DataField.targetRXY[2];
        myLocation[0] = DataField.x;
        myLocation[1] = DataField.y;
        wVectors = new ArrayList<>();
    }
    int index;
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

        DataField.GUI = false;

        Timer t = new Timer(100, e1 -> {
            DataField.GUI = true;
        });

        return false;
    }

    private WeightedVector calcV(){
        double xt = Distance(myLocation[0],holeLocation[0],myLocation[1],myLocation[1]);
        double yt = Distance(holeLocation[0],holeLocation[0],holeLocation[1],myLocation[1]);

        double scale = Math.sqrt(Math.pow(xt,2)+Math.pow(yt,2));
        xt = xt/scale;
        yt= yt/scale;
        xt = xt*5;
        yt = yt*5;

        if(myLocation[0]>holeLocation[0])
            xt = -xt;
        if(myLocation[1]>holeLocation[1])
            yt = -yt;
        return new WeightedVector(xt,yt);
    }

    private double Distance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow((x2-x1) ,2)+Math.pow((y2-y1),2));
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

/*
 if((int)Math.signum(TargetX)==1&&(int)Math.signum(mylocation)==1){
         return holeLocation[0] + myLocation[0];
         }

         else if((int)Math.signum(TargetX)==-1&&(int)Math.signum(mylocation)==-1){
         return holeLocation[0] + myLocation[0];
         }

         else if((int)Math.signum(TargetX)==-1&&(int)Math.signum(mylocation)==1){
         return holeLocation[0] - myLocation[0];
         }

         else if((int)Math.signum(TargetX)==1&&(int)Math.signum(mylocation)==-1){
         return (holeLocation[0]*-1) - myLocation[0];
         }

         else if((int)Math.signum(TargetX)==1&&(int)Math.signum(mylocation)==0){
         return holeLocation[0];
         }

         else if((int)Math.signum(TargetX)==-1&&(int)Math.signum(mylocation)==0){
         return holeLocation[0];
         }*/
