package Bots;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

import javax.naming.ldap.PagedResultsControl;

public class PathCalculator {
    
    double ballCoorX;
    double ballCoorY;
    AdjacencyField adjacency;
    SlopeField slope;
    List<Integer> pathX;
    List<Integer> pathY;


    public PathCalculator(AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY)
    {
        this.adjacency = adjacency;
        this.slope = slope;
        this.ballCoorX = ballCoorX;
        this.ballCoorY = ballCoorY;
        pathX = new LinkedList<Integer>();
        pathY = new LinkedList<Integer>();
    }

    public int[] getBallPosition()
    {
        int[] arrayPosition = new int[2];
        double coordinateX = ballCoorX;
        double coordinateY = ballCoorY;

        if(coordinateX >= 0)
        {
        coordinateX += 25;
        }
        else
        {
        double temp = 25 - (coordinateX*-1.0);
        coordinateX = temp;
        }
        if(coordinateY >= 0)
        {
        coordinateY += 25;
        }
        else
        {
            double temp = 25 - (coordinateY*-1.0);
            coordinateY = temp;
        }
        
        arrayPosition[0] = (int)(coordinateX/adjacency.interval);
        arrayPosition[1] = (int)(coordinateY/adjacency.interval);
        
        pathX.add(arrayPosition[0]);
        pathY.add(arrayPosition[1]);
        
        return arrayPosition;
    }



    public List<List> pathCalculator(int xPos, int yPos)                    //pathcalculator x-path priority
    {
        int min = Integer.MAX_VALUE;
        int minX = 0;
        int minY = 0;
        ArrayList getPath = new ArrayList<List>();
        int[][] field = adjacency.floodFillUpdateSandpits();
        while(field[xPos][yPos] != 0)
        {
            if(xPos + 1 < field.length)
            {
                  if(min > field[xPos+1][yPos] && field[xPos+1][yPos] >= 0)
                  {
                      min = field[xPos+1][yPos];
                      minX = xPos+1;
                      minY = yPos;
                  }
                  
            }
            if(xPos - 1 >= 0)
            {
                if(min > field[xPos-1][yPos] && field[xPos-1][yPos] >= 0)
                {
                    min = field[xPos-1][yPos];
                    minX = xPos-1;
                    minY = yPos;
                }
                  
            }
            if(yPos + 1 < field[0].length)
            {
                if(min > field[xPos][yPos+1] && field[xPos][yPos+1] >= 0)
                {
                    min = field[xPos][yPos+1];
                    minX = xPos;
                    minY = yPos+1;
                }
                  
            }
            if(yPos - 1 >= 0)
            {
                if(min > field[xPos][yPos-1] && field[xPos][yPos-1] >= 0)
                {
                    min = field[xPos][yPos-1];
                    minX = xPos;
                    minY = yPos-1;
                }
                  
            }
            pathX.add(minX);
            pathY.add(minY);

            xPos = minX;
            yPos = minY;
        }
        getPath.add(pathX);
        getPath.add(pathY);
        return getPath;
    }

    // public List<List> pathCalculator(int xPos, int yPos)                    //pathcalculator y-path priority
    // {
    //     int min = Integer.MAX_VALUE;
    //     int minX = 0;
    //     int minY = 0;
    //     ArrayList getPath = new ArrayList<List>();
    //     int[][] field = adjacency.floodFillUpdateSandpits();
    //     while(field[xPos][yPos] != 0)
    //     {
    //         if(xPos + 1 < field.length)
    //         {
    //               if(min > field[xPos+1][yPos] && field[xPos+1][yPos] >= 0)
    //               {
    //                   min = field[xPos+1][yPos];
    //                   minX = xPos+1;
    //                   minY = yPos;
    //               }
                  
    //         }
    //         if(xPos - 1 >= 0)
    //         {
    //             if(min > field[xPos-1][yPos] && field[xPos-1][yPos] >= 0)
    //             {
    //                 min = field[xPos-1][yPos];
    //                 minX = xPos-1;
    //                 minY = yPos;
    //             }
                  
    //         }
    //         if(yPos + 1 < field[0].length)
    //         {
    //             if(min >= field[xPos][yPos+1] && field[xPos][yPos+1] >= 0)
    //             {
    //                 min = field[xPos][yPos+1];
    //                 minX = xPos;
    //                 minY = yPos+1;
    //             }
                  
    //         }
    //         if(yPos - 1 >= 0)
    //         {
    //             if(min >= field[xPos][yPos-1] && field[xPos][yPos-1] >= 0)
    //             {
    //                 min = field[xPos][yPos-1];
    //                 minX = xPos;
    //                 minY = yPos-1;
    //             }
                  
    //         }
    //         pathX.add(minX);
    //         pathY.add(minY);
    //         // System.out.println("x: "+minX);
    //         // System.out.println("y: "+minY);
    //         //System.out.println(pathX.toString());
    //         xPos = minX;
    //         yPos = minY;
    //     }


    //     //System.out.println("PATH FOUND"); 
    //     getPath.add(pathX);
    //     getPath.add(pathY);
    //     return getPath;
    // }




    // public List decreasePath(List pathX, List pathY)
    // {
    //     ArrayList getPath = new ArrayList<List>();
    //     pathX.remove(pathX.size()-1);
    //     pathY.remove(pathY.size()-1);
    //     getPath.add(pathX);
    //     getPath.add(pathY);
    //     return getPath;
    // }

    public List decreasePath(List path1, List path2)
    {
        ArrayList getPath = new ArrayList<List>();
        pathX.remove(path1.size()-1);
        pathY.remove(path2.size()-1);
        getPath.add(path1);
        getPath.add(path2);
        return getPath;
    }





    
    
    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)-0.1+(x*x+y*y)/1000.0;            //the terrain (so the ai detects water)
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double ballCoorX = -10;
        double ballCoorY = 0;
        double interval = 1;
        double holeCoorx = 10;
        double holeCoory = 0;
        double radius = 3;                                  //radius of all trees
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 1;                         //the higher this value, the less likely the ai takes a route through a sandpit

        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);        
        SlopeField b = new SlopeField(interval,terrain);   
        PathCalculator path = new PathCalculator(a, b, ballCoorX, ballCoorY);


        int[] test = path.getBallPosition();
        System.out.println(test[0]); 
        System.out.println(test[1]); 

        path.pathCalculator(test[0], test[1]);
    }


    }



















































   // public List<List> pathCalculator(int xPos, int yPos, List pathX, List pathY)
    // {
    //     int min = Integer.MAX_VALUE;
    //     int minX = 0;
    //     int minY = 0;
    //     ArrayList getPath = new ArrayList<List>();
    //     if(adjacency.floodFillUpdateSandpits()[xPos][yPos] == 0)
    //     {
    //         System.out.println("PATH FOUND"); 
    //         getPath.add(new LinkedList<Double>(pathX));
    //         getPath.add(new LinkedList<Double>(pathY));
    //         return getPath;
    //     }
    //     else
    //     {
    //         if(xPos + 1 < adjacency.floodFillUpdateSandpits().length)
    //         {
    //               if(min > adjacency.floodFillUpdateSandpits()[xPos+1][yPos] && adjacency.floodFillUpdateSandpits()[xPos+1][yPos] >= 0)
    //               {
    //                   min = adjacency.floodFillUpdateSandpits()[xPos+1][yPos];
    //                   minX = xPos+1;
    //                   minY = yPos;
    //               }
                  
    //         }
    //         if(xPos - 1 >= 0)
    //         {
    //             if(min > adjacency.floodFillUpdateSandpits()[xPos-1][yPos] && adjacency.floodFillUpdateSandpits()[xPos-1][yPos] >= 0)
    //             {
    //                 min = adjacency.floodFillUpdateSandpits()[xPos-1][yPos];
    //                 minX = xPos-1;
    //                 minY = yPos;
    //             }
                  
    //         }
    //         if(yPos + 1 < adjacency.floodFillUpdateSandpits()[0].length)
    //         {
    //             if(min > adjacency.floodFillUpdateSandpits()[xPos][yPos+1] && adjacency.floodFillUpdateSandpits()[xPos][yPos+1] >= 0)
    //             {
    //                 min = adjacency.floodFillUpdateSandpits()[xPos][yPos+1];
    //                 minX = xPos;
    //                 minY = yPos+1;
    //             }
                  
    //         }
    //         if(yPos - 1 >= 0)
    //         {
    //             if(min > adjacency.floodFillUpdateSandpits()[xPos][yPos-1] && adjacency.floodFillUpdateSandpits()[xPos][yPos-1] >= 0)
    //             {
    //                 min = adjacency.floodFillUpdateSandpits()[xPos][yPos-1];
    //                 minX = xPos;
    //                 minY = yPos-1;
    //             }
                  
    //         }
    //         pathX.add(minX);
    //         pathY.add(minY);
    //         // System.out.println("x: "+minX);
    //         // System.out.println("y: "+minY);
    //         System.out.println(pathX.toString());
            

    //         pathCalculator(minX,minY, new LinkedList<Double>(pathX), new LinkedList<Double>(pathY));
    //     }
    //     return null;
    // }