package pathfindingbot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

public class PathCalculator {
    
    double ballCoorX;
    double ballCoorY;
    AdjacencyField adjacency;
    SlopeField slope;
    List<Integer> pathXX;
    List<Integer> pathXY;
    List<Integer> pathYX;
    List<Integer> pathYY;
    List<Integer> pathMX;
    List<Integer> pathMY;

    /**TODO
     *
     * @param adjacency
     * @param slope
     * @param ballCoorX
     * @param ballCoorY
     */

    public PathCalculator(AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY)
    {
        this.adjacency = adjacency;
        this.slope = slope;
        this.ballCoorX = ballCoorX;
        this.ballCoorY = ballCoorY;
        pathXX = new LinkedList<Integer>();
        pathXY = new LinkedList<Integer>();
        pathYX = new LinkedList<Integer>();
        pathYY = new LinkedList<Integer>();
        pathMX = new LinkedList<Integer>();
        pathMY = new LinkedList<Integer>();
    }

    /**TODO
     *
     * @return
     */

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
        
        pathXX.add(arrayPosition[0]);
        pathXY.add(arrayPosition[1]);
        pathYX.add(arrayPosition[0]);
        pathYY.add(arrayPosition[1]);
        pathMX.add(arrayPosition[0]);
        pathMY.add(arrayPosition[1]);
        
        return arrayPosition;
    }



    /**TODO
     *
     * @param xPos
     * @param yPos
     * @return
     */

    public List<List> pathCalculatorX(int xPos, int yPos)                    //pathcalculator x-path priority
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
            pathXX.add(minX);
            pathXY.add(minY);
            // System.out.println("x: "+minX);
            // System.out.println("y: "+minY);
            //  System.out.println(pathXX.toString());
            //  System.out.println(pathXY.toString());


            xPos = minX;
            yPos = minY;
           
        }


        //System.out.println("PATH FOUND"); 
        getPath.add(pathXX);
        getPath.add(pathXY);
        return getPath;
   
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public List<List> pathCalculatorY(int xPos, int yPos)                    //pathcalculator y-path priority
    {
        int min = Integer.MAX_VALUE;
        int minX = 0;
        int minY = 0;
        ArrayList getPath = new ArrayList<List>();
        int[][] field = adjacency.floodFillUpdateSandpits();
        while(field[xPos][yPos] != 0)
        {
            if(yPos + 1 < field[0].length)
            {
                if(min >= field[xPos][yPos+1] && field[xPos][yPos+1] >= 0)
                {
                    min = field[xPos][yPos+1];
                    minX = xPos;
                    minY = yPos+1;
                }
                  
            }

            if(yPos - 1 >= 0)
            {
                if(min >= field[xPos][yPos-1] && field[xPos][yPos-1] >= 0)
                {
                    min = field[xPos][yPos-1];
                    minX = xPos;
                    minY = yPos-1;
                }
                  
            }

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

 
            pathYX.add(minX);
            pathYY.add(minY);
            // System.out.println("x: "+minX);
            // System.out.println("y: "+minY);
            //  System.out.println(pathYX.toString());
            //  System.out.println(pathYY.toString());
            xPos = minX;
            yPos = minY;
        }


        //System.out.println("PATH FOUND"); 
        getPath.add(pathYX);
        getPath.add(pathYY);
        return getPath;
    }


    
    
    
    
    
    
    
    
    
    
    
    
    public List<List> pathCalculatorMixed(int xPos, int yPos)                    //pathcalculator y-path priority
    {
        int counter = 0;
        int min = Integer.MAX_VALUE;
        int minX = 0;
        int minY = 0;
        ArrayList getPath = new ArrayList<List>();
        int[][] field = adjacency.floodFillUpdateSandpits();
        while(field[xPos][yPos] != 0)
        {
            if(counter % 2 == 0)
            {
                if(yPos + 1 < field[0].length)
                {
                    if(min >= field[xPos][yPos+1] && field[xPos][yPos+1] >= 0)
                    {
                        min = field[xPos][yPos+1];
                        minX = xPos;
                        minY = yPos+1;
                    }

                }

                if(yPos - 1 >= 0)
                {
                    if(min >= field[xPos][yPos-1] && field[xPos][yPos-1] >= 0)
                    {
                        min = field[xPos][yPos-1];
                        minX = xPos;
                        minY = yPos-1;
                    }

                }

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
            }
            else
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
            }



            pathMX.add(minX);
            pathMY.add(minY);
            // System.out.println("x: "+minX);
            // System.out.println("y: "+minY);
            //  System.out.println(pathMX.toString());
            //  System.out.println(pathMY.toString());
            xPos = minX;
            yPos = minY;



            counter++;
        }


        //System.out.println("PATH FOUND");
        getPath.add(pathMX);
        getPath.add(pathMY);
        return getPath;
    }









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
        path1.remove(path1.size()-1);
        path2.remove(path2.size()-1);
        getPath.add(path1);
        getPath.add(path2);
        return getPath;
    }





    
    
    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)-> (double)0.4*(0.9-Math.exp(-1*(x*x+y*y)/8.0));            //the terrain (so the ai detects water)     (double)-0.1+(x*x+y*y)/1000.0
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double ballCoorX = -3;
        double ballCoorY = 0;
        double interval = 1;
        double holeCoorx = 4;
        double holeCoory = 1;
        double radius = 3;                                  //radius of all trees
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 0;                         //the higher this value, the less likely the ai takes a route through a sandpit
        SlopeField slope = new SlopeField(interval, terrain);

        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY, slope);
        SlopeField b = new SlopeField(interval,terrain);   
        PathCalculator path = new PathCalculator(a, b, ballCoorX, ballCoorY);


        int[] test = path.getBallPosition();
        System.out.println(test[0]); 
        System.out.println(test[1]); 

        List X =  path.pathCalculatorX(test[0], test[1]);
        List Y = path.pathCalculatorY(test[0], test[1]);
        List MIX = path.pathCalculatorMixed(test[0], test[1]);
        List pathMX = (List) MIX.get(0);
        List pathMY = (List) MIX.get(1);
        List pathXX = (List) X.get(0);
        List pathXY = (List) X.get(1);
        List pathYX = (List) Y.get(0);
        List pathYY = (List) Y.get(1);

        System.out.println(pathXX.toString());
        System.out.println(pathXY.toString());
System.out.println();
System.out.println();
        System.out.println(pathYX.toString());
        System.out.println(pathYY.toString());
System.out.println();
System.out.println();
        System.out.println(pathMX.toString());
        System.out.println(pathMY.toString());




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