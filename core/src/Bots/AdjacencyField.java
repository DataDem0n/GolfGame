package Bots;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.function.BiFunction;

/**
 * AdjacencyField
 */
public class AdjacencyField 
{
    public double interval;
    public double[] holeCoordinates = new double[2];
    public int queueSize = 0;                                                   //testing purposes
    int sandpitResentment;
    BiFunction<Double,Double,Double> terrain;
    double[] coorTX,coorTY,beginX,endX,beginY,endY;
    double radius;
    
    
    
    public AdjacencyField(double interval, double holeCoorx, double holeCoory, int sandpitResentment, BiFunction<Double,Double,Double> terrain, double[] coorTX, double[] coorTY, double radius, double[] beginX, double[] endX, double[] beginY, double[] endY)     //interval = trade-off between accuracy and speed.
    {
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


    public int[][] floodFillUpdateWater()                                                   //convergence to box fucks up, if there are 2 lakes (2 places with water)
    {
        int[][] field = Field();
        int maxHeight = 0;
        int maxWidth  = 0;
        int minHeight = Integer.MAX_VALUE;
        int minWidth  = Integer.MAX_VALUE;
        
        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[0].length; j++)
            {
                if(terrain.apply(i/interval-25, j/interval-25) < 0 || terrain.apply(i/interval-24, j/interval-24) < 0  ||  terrain.apply(i/interval-26, j/interval-26) < 0)                 //made water bigger, so the error doesnt land into it
                {
                    field[i][j] = -1;           //10000
                    if(i > maxHeight)
                    {
                     maxHeight = i;
                    }
                    if(i < minHeight)
                    {
                        minHeight = i;
                    }
                    if(j > maxWidth)
                    {
                        maxWidth = j;
                    }
                    if(j < minWidth)
                    {
                        minWidth = j;
                    }
                }
            }
        }



        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[0].length; j++)
            {
                if(i<=maxHeight && i>=minHeight && j<=maxWidth && j>=minWidth)                 //made water bigger, so the error doesnt land into it
                {
                    field[i][j] = -1;           //10000
                }
            }
        }
        
        
        
        
        return field;
    }

   
   
   
   
   
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




    public int[][] floodFill()
    {
        Queue<int[]> queue = new ArrayDeque<int[]>();
        int[][] field = floodFillUpdateTree();
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
        if(coordinateY >= 0)
        {
        coordinateY += 25;
        }
        

        arrayPosition[0] = (int)(coordinateX/interval);
        arrayPosition[1] = (int)(coordinateY/interval);
        field[arrayPosition[0]][arrayPosition[1]] = 1000;
        
        return field;
    }

   



  
    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)1;            //the terrain (so the ai detects water)
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 20;
        double holeCoory = 20;
        double radius = 3;                                  //radius of all trees
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 1;                         //the higher this value, the less likely the ai takes a route through a sandpit

        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);           
        SlopeField b = new SlopeField(interval,terrain);                                              
        int[][] testing = a.floodFillUpdateSandpits();
        System.out.println(a.queueSize);
    

        for(int i = 0; i<testing.length;i++)
        {
            for(int j = 0;j<testing[0].length; j++)
            {
                System.out.print(testing[i][j] + " ");
            }
            System.out.println();
        }
        //PathCalculator path = new PathCalculator(a, b, 1, 1);
        // path.pathCalculator(path.getBallPosition()[0], path.getBallPosition()[1]);
        
        // for(int j = 0;j<path.pathX.size()-1; j++)
        // {
        //     System.out.print("x: "+path.pathX.get(j) + "  y: "+path.pathY.get(j));
        //     System.out.println();
        // }
       
    }

    

}