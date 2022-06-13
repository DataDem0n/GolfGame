package com.mygdx.game.main;

import obstacles.Forest;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class DataField 
{
    public static boolean aiRunning;
    public static boolean GUI;
    public static boolean usingGui;
    public static BiFunction<Double, Double, Double> terrain = (x,y) -> Math.exp(-Math.pow(Math.pow(x-4,2)+Math.pow(y-5,2),2)/1000)+Math.exp(-Math.pow(Math.pow(x+5,2)+Math.pow(y+4,2),2)/1000)-0.1+Math.exp(-Math.pow(Math.pow(x+10,2)-Math.pow(y+10,2),2))+0.1+Math.exp(-Math.pow(Math.pow(x-10,2)+Math.pow(y-10,2),2));;//0.4*(0.9-Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/8));//-0.1+(x*x+y*y)/1000.0 ; //(1.0/10.0)*Math.cos(0.2*x); //0.4*(0.9-Math.exp(-((x*x+y*y)/8.0)));//((2.0/3.0)*Math.sin(x/6.0)+0.7);
    public static ArrayList<Double> velocityX;
    public static ArrayList<Double> velocityY;
    public static double sFriction = 0.2;
    public static double kFriction = 0.08;
    public static double[] targetRXY = {0.15, 20.0, 20.0};
    public static double[] coordinatesandVelocity;
    public static double[] sandPit;
    public static double x;
    public static double y;
    public static Forest gameForest;

}
