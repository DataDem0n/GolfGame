package com.mygdx.game.main;

import obstacles.Forest;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class DataField 
{
    public static boolean aiRunning;
    public static boolean GUI;
    public static boolean usingGui;
    public static BiFunction<Double, Double, Double> terrain = (x,y) -> 0.4*(0.9-Math.exp(-((x*x+y*y)/8.0)));
    public static ArrayList<Double> velocityX;
    public static ArrayList<Double> velocityY;
    public static double sFriction = 0.2;
    public static double kFriction = 0.08;
    public static double[] targetRXY = {0.15,4.0,1.0};
    public static double[] coordinatesandVelocity;
    public static double[] sandPit;
    public static double x;
    public static double y;
    public static Forest gameForest;

}
