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

    /**
     * constructor
     * @param adjacency: the adjacency field
     * @param slope: the slope field
     * @param ballCoorX: x-coordinate of the ball
     * @param ballCoorY: y-coordinate of the ball
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

    /**
     * This method converts the x-coordinate and y-coordinate of the ball to position on the adjacency field
     * @return: an array containing the position of the ball on the adjacency field
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



    /**
     *  This method implements the A* pathfinding algorithm
     * @param xPos: the starting x-postion for the A* pathfinding algorithm (ball's x-position)
     * @param yPos: the starting y-postion for the A* pathfinding algorithm (ball's y-position)
     * @return: a list containing the best path to the hole
     */

    public List<List> pathCalculatorX(int xPos, int yPos)
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



            xPos = minX;
            yPos = minY;
           
        }


        //System.out.println("PATH FOUND"); 
        getPath.add(pathXX);
        getPath.add(pathXY);
        return getPath;
   
    }





















    /**
     *  This method implements the A* pathfinding algorithm, (finds an alternative best path, if there is one)
     * @param xPos: the starting x-postion for the A* pathfinding algorithm (ball's x-position)
     * @param yPos: the starting y-postion for the A* pathfinding algorithm (ball's y-position)
     * @return: a list containing an alternative best path to the hole (if there is one)
     */
    public List<List> pathCalculatorY(int xPos, int yPos)
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

            xPos = minX;
            yPos = minY;
        }



        getPath.add(pathYX);
        getPath.add(pathYY);
        return getPath;
    }


    
    
    
    
    
    
    
    
    
    
    
    
    public List<List> pathCalculatorMixed(int xPos, int yPos)       //delete
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

            xPos = minX;
            yPos = minY;



            counter++;
        }
        getPath.add(pathMX);
        getPath.add(pathMY);
        return getPath;
    }










    /**
     *  This method decreases the list containing the best path
     * @param path1: a list containing the x-directions of the path
     * @param path2: a list containing the y-directions of the path
     * @return: a decreased list. (used for backtracking)
     */
    public List decreasePath(List path1, List path2)
    {
        ArrayList getPath = new ArrayList<List>();
        path1.remove(path1.size()-1);
        path2.remove(path2.size()-1);
        getPath.add(path1);
        getPath.add(path2);
        return getPath;
    }

}