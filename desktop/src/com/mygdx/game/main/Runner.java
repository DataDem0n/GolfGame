package com.mygdx.game.main;

 import engine.GameEngineAM;
 import engine.GameEngineRK2;
 import engine.GameEngineRK4;
 import org.mariuszgromada.math.mxparser.Argument;
 import org.mariuszgromada.math.mxparser.Expression;
 import engine.GameEngineEuler;

 import java.util.Arrays;
 import java.util.function.BiFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner extends Thread
{

    //A variable, that stores the coordinates of the ball and velocity of the ball in the x-direction and y-direction.
    static double[] coordinatesAndVelocity;
    //gets String from read.txt and puts it to double for use as variable
    public static double getStringValueGame(String s)
    {
      String sub;

      String value;
      for(int i=0; i < s.length(); i++)
      {
          sub = s.substring(i,i+1);
          if(sub.equals("="))
          {
            for(int j = i+2; j < s.length(); j++)
            {
                if(s.substring(j,j+1).equals(" ") || s.substring(j,j+1).equals("") || j==s.length()-1)
                {
                    value = s.substring(i+2, j+1);
                    return  Double.parseDouble(value);
                }

            }
          }
      }
      return 0.000;

    }

    //gets String from read.txt, to put it in the write.txt
    public static String getStringValueStore(String s)
    {
      String sub;
      String value;
      for(int i=0; i < s.length(); i++)
      {
          sub = s.substring(i,i+1);
          if(sub.equals("="))
          {
            for(int j = i+2; j < s.length(); j++)
            {
                if(s.substring(j,j+1).equals(" ") || s.substring(j,j+1).equals("") || j==s.length()-1)
                {
                    value = s.substring(i+2, j+1);

                    return  value;
                }

            }
          }
      }
      return "";

    }

    //gets the inputs of the Radius, x-coordinate, y-coordinate from a string and puts them in a double array
    public static double[] getRadius(String s)
    {
        double[] radius = new double[3];
        String sub;
        double value;
        for(int i=0; i < s.length(); i++)
        {
            sub = s.substring(i,i+1);
            if(sub.equals("="))
            {
                for(int j = i+2; j < s.length(); j++)
                {
                    if(s.substring(j,j+1).equals(","))
                    {
                        value = Double.parseDouble(s.substring(i+2, j));
                        radius[0] = value;

                        for(int p = j+2; p < s.length(); p++)
                        {
                            if(s.substring(p,p+1).equals(","))
                            {
                                value = Double.parseDouble(s.substring(j+2, p));
                                radius[1] = value;

                                for(int k = p+2; k <s.length(); k++)
                                 {
                                   if(s.substring(k,k+1).equals(" ") || k==s.length()-1)
                                   {
                                        value = Double.parseDouble(s.substring(p+2, k));
                                        radius[2] = value;
                                        return radius;
                                   }
                                }
                            }
                        }
                    }
                }
            }
        }
    return radius;
}

    //gets the height profile in string form
    public static String get_function(){
        File read3 = new File("read.txt");
        Scanner scan3;
        try {
            scan3 = new Scanner(read3);
            for(int i = 0; i < 7; i++){
                scan3.nextLine();
            }
            String line = scan3.nextLine();
            return line.substring(line.indexOf('=') + 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    static FileWriter fr;
    public static void main(String[] args) throws FileNotFoundException {


    Scanner s = new Scanner(System.in);
        System.out.println("Would you like to use GUI instead of a file to play? y/n");
    String choice = s.next();

    //variables used by the GUI and PhysicsEngine
    double[] coordinatesAndVelocity = new double[4];
    double staticFriction;
    double kineticFriction;
    double[] targetRXY = new double[3];
    BiFunction<Double, Double, Double> terrain; //= (x,y)->(double)(0);
    ArrayList<String> end = new ArrayList<String>();
    ArrayList<Double> velx = new ArrayList<Double>();
    ArrayList<Double> vely = new ArrayList<Double>();

    //reads all the necessary files
    File read1 = new File("write.txt");
    Scanner scan1 = new Scanner(read1);
    while(scan1.hasNextLine())
    {
        end.add(scan1.nextLine());
    }

    File read2 = new File("read.txt");

    Scanner scan2 = new Scanner(read2);
    while(scan2.hasNextLine())
    {
        end.add(getStringValueStore(scan2.nextLine()) + " , " + getStringValueStore(scan2.nextLine()));
    }

    File read3 = new File("read.txt");

    Scanner scan3 = new Scanner(read3);

    //extracts the needed information from the read.txt, using the methods
    coordinatesAndVelocity[0] = getStringValueGame(scan3.nextLine());
    coordinatesAndVelocity[1] = getStringValueGame(scan3.nextLine());
    staticFriction = getStringValueGame(scan3.nextLine());
    kineticFriction = getStringValueGame(scan3.nextLine());
    targetRXY = getRadius(scan3.nextLine());
    scan3.nextLine();

    while(scan3.hasNextLine()) {
        velx.add(getStringValueGame(scan3.nextLine()));
        vely.add(getStringValueGame(scan3.nextLine()));
    }

        String heightProfile = get_function();

        //this sets terrain to the heightprofile given in the read.txt


        /*
        sets all the static fields from the DataFieldclass,
        so it can be accesed by the GameEngine thread
         */

    try(FileWriter fw = new FileWriter("write.txt");
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw))
    {
    for(int i = 0; i < end.size(); i++)
    {
        int p = i+1;
        if(!(end.get(i).substring(0,1).equals("e")))
        {
            out.print("end Coordinates " + p + "= ");
        }
        out.println(end.get(i));

    }

    }
    catch (IOException e)
    {
        System.out.println("file writing failed");
    }

        //testing
//        System.out.println("x-coor: " + coordinatesAndVelocity[0]);
//        System.out.println("y-coor: " + coordinatesAndVelocity[1]);
//        System.out.println("x-vel: " + coordinatesAndVelocity[2]);
//        System.out.println("y-velr: " + coordinatesAndVelocity[3]);
//        System.out.println("sfriction: " + staticFriction);
//        System.out.println("kfriction: " + staticFriction);
//    	System.out.println(targetRXY[0]);
//        System.out.println(targetRXY[1]);
//        System.out.println(targetRXY[2]);
//
//        for(int i = 0; i < velx.size(); i++)
//        {
//            System.out.println(velx.get(i));
//            System.out.println(vely.get(i));
//
//        }

        DataField.targetRXY = targetRXY;
        DataField.sFriction = staticFriction;
        DataField.kFriction = kineticFriction;
        DataField.GUI = true;
        DataField.usingGui = true;

        DataField.terrain = (x,y)->(double)1;

        if(!choice.equals("y")){
            DataField.x = coordinatesAndVelocity[0];
            DataField.y = coordinatesAndVelocity[1];
            DataField.coordinatesandVelocity = coordinatesAndVelocity;
            DataField.velocityX = velx;
            DataField.velocityY = vely;
            DataField.GUI = false;
            DataField.usingGui = false;//TODO 2d array for trees
        }

        settingsMenu pregame = new settingsMenu();

        while(settingsMenu.finished){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //starts the GUI and Physics thread
         DesktopLauncher t = new DesktopLauncher();
         t.start();

         switch(pregame.chooseSolvers.getSelectedIndex()) {
             case 1:
                 GameEngineEuler g = new GameEngineEuler(DataField.terrain, coordinatesAndVelocity, DataField.kFriction, DataField.sFriction, targetRXY);
                 g.start();
                 break;
             case 2:
                 GameEngineRK2 r2 = new GameEngineRK2(DataField.terrain, coordinatesAndVelocity, DataField.kFriction, DataField.sFriction, targetRXY);
                 r2.start();
                 break;
             case 3:
                 GameEngineRK4 r4 = new GameEngineRK4(DataField.terrain, coordinatesAndVelocity, DataField.kFriction, DataField.sFriction, targetRXY);
                 r4.start();
                 break;
             case 4:
                 GameEngineAM am = new GameEngineAM(DataField.terrain, coordinatesAndVelocity, DataField.kFriction, DataField.sFriction, targetRXY);
                 am.start();
                 break;
         }
    }
}