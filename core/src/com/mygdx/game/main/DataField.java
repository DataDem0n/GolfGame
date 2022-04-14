package com.mygdx.game.main;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class DataField 
{
    public static boolean GUI;
    public static boolean usingGui;
    public static BiFunction<Double, Double, Double> terrain;
    public static ArrayList<Double> velocityX;
    public static ArrayList<Double> velocityY;
    public static double sFriction;
    public static double kFriction;
    public static double[] targetRXY;
    public static double[] coordinatesandVelocity;
    public static double[] sandPit = {3.0,5.0,0,3.0};
    public static float x;
    public static float y;
}
