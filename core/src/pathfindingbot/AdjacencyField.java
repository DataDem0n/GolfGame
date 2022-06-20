package pathfindingbot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.function.BiFunction;

/**
 * AdjacencyField
 */
public class AdjacencyField
{
    public double interval;
    public double[] holeCoordinates = new double[2];
    public int queueSize = 0;
    int sandpitResentment;
    BiFunction<Double,Double,Double> terrain;
    double[] coorTX,coorTY,beginX,endX,beginY,endY;
    double radius;
    double[][] slopeFieldX;
    double[][] slopeFieldY;

    /**
     * constructor
     * @param interval: this determines how big each tile is in for the adjacency field
     * @param holeCoorx: this is the x-coordinate of the hole
     * @param holeCoory: this is the y-coordinate of the hole
     * @param sandpitResentment: this sets how big the chance is that sandpits are avoided by the bot
     * @param terrain: this is the function that the adjacency field is build from
     * @param coorTX: an array containing the x-coordinates of all the trees in the field
     * @param coorTY: an array containing the y-coordinates of all the trees in the field
     * @param radius: the radius of all trees
     * @param beginX: an array containing all the begin x-coordinates of the sandpits
     * @param endX: an array containing all the end x-coordinates of the sandpits
     * @param beginY: an array containing all the begin y-coordinates of the sandpits
     * @param endY: an array containing all the end y-coordinates of the sandpits
     * @param slope: a field describing the slope of an interval
     */
    public AdjacencyField(double interval, double holeCoorx, double holeCoory, int sandpitResentment, BiFunction<Double,Double,Double> terrain, double[] coorTX, double[] coorTY, double radius, double[] beginX, double[] endX, double[] beginY, double[] endY, SlopeField slope)
    {
        this.slopeFieldX = slope.slopeXCalculator();
        this.slopeFieldY = slope.slopeYCalculator();
        this.terrain = terrain;
        this.interval = interval;
        holeCoordinates[0] = holeCoorx;
        holeCoordinates[1] = holeCoory;
        this.sandpitResentment = sandpitResentment;
        this.coorTX = coorTX;
        this.coorTY = coorTY;
        this.radius = radius;
        this.beginX = beginX;
        this.endX = endX;
        this.beginY = beginY;
        this.endY = endY;
    }

    /**
     *  This method converts the hole-coordinates on the terrain to coordinates on the adjacency field
     *
     * @return: returns an int array containing the hole-coordinates on the adjacency field
     */
    public int[] getHolePosition()
    {
        int[] arrayPosition = new int[2];
        double coordinateX = holeCoordinates[0];
        double coordinateY = holeCoordinates[1];

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
        
        arrayPosition[0] = (int)( coordinateX/interval);
        arrayPosition[1] = (int)( coordinateY/interval);
        return arrayPosition;
    }


    /**
     * This method constructs the field
     * @return: a Field without the floodfill implementation
     */
    public int[][] Field()
    {
        int[][] field = new int[(int)(50/interval)][(int)(50/interval)];
        for(int i = 0; i<field.length;i++)
        {
            for(int j = 0;j<field[0].length; j++)
            {
               field[i][j] = 10000;
            }
            
        }
        return field;
    }


    /**
     * This method checks if the neighbor has already been visited
     * @param neighbor: an array containing the coordinates of the neighbor that is being checked
     * @param container: a list containing all the previously visited neighbors (coordinates)
     * @return: a boolean value
     */
    public boolean Contain(int[] neighbor, ArrayList<int[]> container)
    {
        for(int i = 0; i < container.size(); i++)
        {
           
            if(neighbor[0] == container.get(i)[0] && neighbor[1] == container.get(i)[1])
            {
                
                return true;
            }
        }
        return false; 
    }


    // public int[][] floodFillUpdateWater()
    // {
    //     int[][] field = Field();
    //     int maxHeight = 0;
    //     int maxWidth  = 0;
    //     int minHeight = Integer.MAX_VALUE;
    //     int minWidth  = Integer.MAX_VALUE;
        
    //     for(int i = 0; i < field.length; i++)
    //     {
    //         for(int j = 0; j < field[0].length; j++)
    //         {
    //             if(terrain.apply(i/interval-25, j/interval-25) < 0 || terrain.apply(i/interval-24, j/interval-24) < 0  ||  terrain.apply(i/interval-26, j/interval-26) < 0)
    //             {
    //                 field[i][j] = -1;           //10000
    //                 if(i > maxHeight)
    //                 {
    //                  maxHeight = i;
    //                 }
    //                 if(i < minHeight)
    //                 {
    //                     minHeight = i;
    //                 }
    //                 if(j > maxWidth)
    //                 {
    //                     maxWidth = j;
    //                 }
    //                 if(j < minWidth)
    //                 {
    //                     minWidth = j;
    //                 }
    //             }
    //         }
    //     }

    /**
     * This method updates the field with negative values for the tiles that are in water.
     * @return: the field array, containing negative values where there is water
     */
        public int[][] floodFillUpdateWater()
        {
            int[][] field = Field();
            
            for(int i = 0; i < field.length; i++)
            {
                for(int j = 0; j < field[0].length; j++)
                {
                    if(terrain.apply(i/interval-25, j/interval-25) < 0 || terrain.apply(i/interval-24, j/interval-24) < 0  ||  terrain.apply(i/interval-26, j/interval-26) < 0)
                    {
                        field[i][j] = -1;
                    }
                }
            }

        return field;
    }






    /**
     * This method updates the field with negative values for the tiles that contain trees.
     * @return: the field array, containing negative values where there are trees
     */
    public int[][] floodFillUpdateTree()
    {
        int[][] field = floodFillUpdateWater();

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[0].length; j++)
            {
                for(int k = 0; k < coorTX.length; k++)
                {
                        if(((i/interval-25 <= coorTX[k]+radius) && (i/interval-25 >= coorTX[k]-radius)) && ((j/interval-25 <= coorTY[k]+radius) && (j/interval-25 >= coorTY[k]-radius)) && field[i][j] != -1)
                        {
                            field[i][j] = -2;           //20000
                        }
                }
            }
        }
        return field;
    }


    /**
     * This method updates the field with negative values for the tiles where the slope is considered extreme (higher than 1.5).
     * @return: the field array, containing negative values where the slopes are higher than 1.5
     */
    public int[][] floodFillUpdateSlope()
    {
        int[][] field = floodFillUpdateTree();

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[0].length; j++)
            {

                        if(slopeFieldX[i][j] > 1.5 || slopeFieldY[i][j] > 1.5)
                        {
                            field[i][j] = -3;           //20000
                        }
                
            }
        }
        return field;
    }




    /**
     * this method is the implementation of the flood fill algorithm
     * @return: the field with the flood fill algorithm implementation
     */
    public int[][] floodFill()
    {
        Queue<int[]> queue = new ArrayDeque<int[]>();
        int[][] field = floodFillUpdateSlope();
        int[] start = getHolePosition();
        field[start[0]][start[1]] = 0;
        queue.add(start);
        ArrayList<int[]> container = new ArrayList<int[]>();
        

       
        while(queue.peek() != null)                                                                   
        {
            int[] storeNeighborValues = new int[4];
            
            for(int i = 0; i < storeNeighborValues.length; i++)
            {
                storeNeighborValues[i] = -1;
            }
            
            if(queue.peek()[0]+1 < field.length)
            {

                int[] neighbor1 = {queue.peek()[0]+1, queue.peek()[1]};
                
                if(field[neighbor1[0]][neighbor1[1]] > 0)
                {
                    storeNeighborValues[0] = field[queue.peek()[0]+1][queue.peek()[1]];                                               
                    if(field[queue.peek()[0]+1][queue.peek()[1]]>field[queue.peek()[0]][queue.peek()[1]]+1 || field[queue.peek()[0]+1][queue.peek()[1]]<0)
                    {
                        field[queue.peek()[0]+1][queue.peek()[1]] = field[queue.peek()[0]][queue.peek()[1]]+1;
                    }
                    if(Contain(neighbor1, container) == false)                                      
                    {
                        queue.add(neighbor1);
                        queueSize++;                                                                                                   
                        container.add(neighbor1);
                    }
                }
               
              
            }
            if(queue.peek()[0]-1 >= 0)
            {
                int[] neighbor2 = {queue.peek()[0]-1, queue.peek()[1]};

                if(field[neighbor2[0]][neighbor2[1]] > 0)
                {

                storeNeighborValues[1] = field[queue.peek()[0]-1][queue.peek()[1]];
                if(field[queue.peek()[0]-1][queue.peek()[1]]>field[queue.peek()[0]][queue.peek()[1]]+1 || field[queue.peek()[0]-1][queue.peek()[1]]<0)
                {
                    field[queue.peek()[0]-1][queue.peek()[1]] = field[queue.peek()[0]][queue.peek()[1]]+1;
                }
                if(Contain(neighbor2, container) == false)                                      
                {
                    queue.add(neighbor2);
                    queueSize++;                                                                                                       
                    container.add(neighbor2); 
                }

                }
                
            }
            if(queue.peek()[1]+1 < field[0].length)
            {
                int[] neighbor3 = {queue.peek()[0], queue.peek()[1]+1};

                if(field[neighbor3[0]][neighbor3[1]] > 0)
                {

                storeNeighborValues[2] = field[queue.peek()[0]][queue.peek()[1]+1];
                if(field[queue.peek()[0]][queue.peek()[1]+1]>field[queue.peek()[0]][queue.peek()[1]]+1 || field[queue.peek()[0]][queue.peek()[1]+1]<0)
                {
                    field[queue.peek()[0]][queue.peek()[1]+1] = field[queue.peek()[0]][queue.peek()[1]]+1;
                }
                if(Contain(neighbor3, container) == false)                                   
                {
                    queue.add(neighbor3);
                    queueSize++;                                                                                                        
                    container.add(neighbor3);
                }

                }
                
            }
            if(queue.peek()[1]-1 >= 0)
            {
                int[] neighbor4 = {queue.peek()[0], queue.peek()[1]-1};

                if(field[neighbor4[0]][neighbor4[1]] > 0)
                {

                storeNeighborValues[3] = field[queue.peek()[0]][queue.peek()[1]-1];
                if(field[queue.peek()[0]][queue.peek()[1]-1]>field[queue.peek()[0]][queue.peek()[1]]+1 || field[queue.peek()[0]][queue.peek()[1]-1]<0)
                {
                    field[queue.peek()[0]][queue.peek()[1]-1] = field[queue.peek()[0]][queue.peek()[1]]+1;
                }
                if(Contain(neighbor4, container) == false)                                      
                {
                    queue.add(neighbor4);   
                    queueSize++;                                                                                                     
                    container.add(neighbor4);
                }

                }
                
            }
          
            
        
            queue.poll(); 
            queueSize--;                                                                                                                   
            
        }
        return field;
    }




    /**
     * this method adds the sandpitResentment to the field tiles (as penalty), that contain snadpits.
     * @return: an array with the updated field values.
     */
    public int[][] floodFillUpdateSandpits()
    {
        int[][] field = floodFill();

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[0].length; j++)
            {
                for(int k = 0; k < beginX.length; k++)
                {
                        if(((i/interval-25 <= endX[k]) && (i/interval-25 >= beginX[k])) && ((j/interval-25 <= endY[k]) && (j/interval-25 >= beginY[k])) && field[i][j] != -1 && field[i][j] != -2)
                        {
                            field[i][j] += sandpitResentment;
                        }
                }
            }
        }
        return field;
    }


    /**TODO
     *  Testing purposes delete later
     * @param ballCoorX
     * @param ballCoorY
     * @return
     */
    public int[][] floodFillUpdateBall(double ballCoorX, double ballCoorY)
    {
        int[][] field = floodFillUpdateSandpits();
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
        

        arrayPosition[0] = (int)(coordinateX/interval);
        arrayPosition[1] = (int)(coordinateY/interval);
        field[arrayPosition[0]][arrayPosition[1]] = 1000;
        field[43][35] = 10000;
        field[35][43] = 20000;
        field[36][43] = 30000;
        field[36][45] = 40000;
        
        return field;
    }


}