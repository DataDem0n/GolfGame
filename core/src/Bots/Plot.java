package Bots;

import java.util.function.BiFunction;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Plot extends PathCalculator
{
    BiFunction<Double,Double,Double> terrain;
    double interval;
    double step;
    AdjacencyField adjacency;
    SlopeField slope;
    double ballCoorX, ballCoorY;
    int[][] plain;


    public Plot(BiFunction<Double,Double,Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY)
    {
        super(adjacency, slope, ballCoorX, ballCoorY);
        this.terrain = terrain;
        this.interval = interval;
        step = 0.5*interval;
        this.adjacency = adjacency;
        plain = adjacency.floodFillUpdateSandpits();
        int[] ballPos = getBallPosition();
        List<List> getpath = pathCalculator(ballPos[0], ballPos[1]);
        pathX = getpath.get(0);
        pathY = getpath.get(1);
        
        //System.out.println(pathX.toString());
        

       
    }

    public double[] differenceCalc(double ballCoorX1, double ballCoorY1)                                                        //calculates the dx and dy of the shot
    {
        //pathCalculator(getBallPosition()[0], getBallPosition()[1]);
        double[] difference = new double[2];
        double x0 = ballCoorX1;
        double x1 = pathConverter(pathX.get(pathX.size()-1));
        double y0 = ballCoorY1;
        double y1 = pathConverter(pathY.get(pathY.size()-1));

        difference[0] = Math.abs(x1 - x0);
        difference[1] = Math.abs(y1-  y0);
        return difference;

    }

    public double unitCalc(double ballCoorX1, double ballCoorY1)                                                                    //doesnt work if x == 0, add another argument to shotplottable.
    {
        double[] difference = differenceCalc(ballCoorX1,ballCoorY1);
        double differenceX = difference[0];
        //System.out.println(differenceX);
        double differenceY = difference[1];
        if(differenceX <= 0.1)
        {
            return -666;
        }
        System.out.println("differenceX: "+differenceX);
        System.out.println("differenceY: "+differenceY);
        double unit = (differenceY/differenceX);                            //fucks up if X is very small
        //System.out.println(unit);
        return unit;
    }

    public boolean checkWaterOrTree(double currentX, double currentY)
    {
        double vX = currentX;
        double vY = currentY;
        

        if(vX >= 0)
        {
            vX += 25;
        }
        else
        {
         double temp = 25 - (vX*-1.0);
         vX = temp;
        }
        if(vY >= 0)
        {
            vY += 25;
        }
        else
        {
         double temp = 25 - (vY*-1.0);
         vY = temp;
        }
        vX /= interval;
        vY /= interval;
        // System.out.println("vX: " + (int)vX);
        // System.out.println("vY: " + (int)vY);
        

       
        if(plain[(int) vX][(int) vY] < 0)            
        {
            return true;
        }
        return false;
        
        
    }



    public boolean shotPlottable(double ballCoorX1, double ballCoorY1)                                                                                             
    {
        double currentX = ballCoorX1;
        double currentY = ballCoorY1;
        System.out.println();
        System.out.println();
        System.out.println("currentX: "+currentX);
        System.out.println("currentY: "+currentY);

        double xcoor = pathConverter(pathX.get(pathX.size()-1));
        double ycoor = pathConverter(pathY.get(pathY.size()-1));
        System.out.println("endX: "+xcoor);
        System.out.println("endY: "+ycoor);

    
        
        double unitVec = unitCalc(ballCoorX1, ballCoorY1);
        System.out.println("unitcalc: "+unitVec);
        
        
        
        
        
        if(unitVec == -666 && currentY < ycoor)                                        //valid
        {

            System.out.println("col1");
            while(currentY < ycoor)
            {

                    if(checkWaterOrTree(currentX, currentY))
                    {
                        return false;
                    }
                    System.out.println("trackX: "+currentX);
                    System.out.println("trackY: "+currentY);
                    currentX += 0;
                    currentY += step;
                    // System.out.println("currentX: "+currentX);
                    // System.out.println("currentY: "+currentY);

            }
            return true;
        }








        if(unitVec == -666 && currentY > ycoor)                                         //valid
        {
            System.out.println("col2");
            while(currentY > ycoor)
            {
                            
                    if(checkWaterOrTree(currentX, currentY))
                    {
                        return false;
                    }
                    System.out.println("trackX: "+currentX);
                    System.out.println("trackY: "+currentY);
                    currentX += 0;
                    currentY -= step;
                    // System.out.println("currentX: "+currentX);
                    // System.out.println("currentY: "+currentY);

            }
            return true;
        }
        
        
        
        
        
        
        
        //coor = endcoor
        if(currentX < xcoor && currentY <= ycoor)
        {
            System.out.println("col3");
            // System.out.println("pathX: " + pathX.get(pathX.size()-1));
            // System.out.println("pathY: " + pathY.get(pathY.size()-1));
            while(currentX < xcoor || currentY < ycoor)
            {
                            
                    if(checkWaterOrTree(currentX, currentY))
                    {
                        return false;
                    }
                    System.out.println("trackX: "+currentX);
                    System.out.println("trackY: "+currentY);
                    currentX += step;
                    currentY += step*unitVec;
                    // System.out.println("currentX: "+currentX);
                    // System.out.println("currentY: "+currentY);
                    System.out.println("EXX: "+xcoor);

            }
            return true;
        }

        if(currentX < xcoor && currentY > ycoor)
        {
            System.out.println("col4");
            while(currentX < xcoor || currentY > ycoor)
            {
                if(checkWaterOrTree(currentX, currentY))
                {
                    return false;
                }
                System.out.println("trackX: "+currentX);
                System.out.println("trackY: "+currentY);
                currentX += step;
                currentY -= step*unitVec;
            }
            return true;
        }
        
        
        
        
        
        
        
        
        
        
        
        if(currentX > xcoor && currentY > ycoor)
        {
            System.out.println("col5");
            while(currentX > xcoor || currentY > ycoor)
            {
                if(checkWaterOrTree(currentX, currentY))
                {
                    return false;
                }
                currentX -= step;
                currentY -= step*unitVec;
            }
            return true;
        }
        
        
        
        
        
        
        
        
        
        
        
        if(currentX > xcoor && currentY <= ycoor)
        {
            System.out.println("col6");
            while(currentX > xcoor || currentY < ycoor)
            {
                if(checkWaterOrTree(currentX, currentY))
                {
                    return false;
                }
                currentX -= step;
                currentY += step*unitVec;
            }
            return true;
        }
        
        
        
        
        
        
        
        

        
       return true;
    }

    
    public double[] getCorrectShot(List pathX, List pathY, double ballCoorX1, double ballCoorY1)
    {
        double[] correctPos = new double[2];

        while(!shotPlottable(ballCoorX1,ballCoorY1))
        {
            System.out.println("not plotted");                                                              //________________________________not reached
            pathX = (List) decreasePath(pathX, pathY).get(0);
            pathY = (List) decreasePath(pathX, pathY).get(1);
        }

            correctPos[0] = pathConverter((int) pathX.get(pathX.size()-1));
            correctPos[1] = pathConverter((int) pathY.get(pathY.size()-1));
          
        // System.out.println("correctX: "+ correctPos[0]);
        // System.out.println("correctY: "+ correctPos[1]);
        return correctPos;
        
    }
    public double pathConverter(int path)
    {
        if(path*1.0*interval > 25)
        {
            return path*1.0*interval-25;
        }
        else
        {
            return (-1.0*25) + path*1.0*interval;
        }
    }





    // public double getCorrectVelocity()
    // {
    //     int ballCoordinatesX = path.getBallPosition()[0];
    //     int ballCoordinatesY = path.getBallPosition()[1];
    //     List listX = path.pathCalculator(ballCoordinatesX, ballCoordinatesY).get(0);
    //     List listY = path.pathCalculator(ballCoordinatesX, ballCoordinatesY).get(1);
    //     double endX = getCorrectShot(listX, listY)[0];
    //     double endY = getCorrectShot(listX, listY)[1];
    //     double totAccX;
    //     double totAccY;

    //     while(ballCoordinatesX < endX && ballCoordinatesY < endY)
    //     {
    //         accelerationrungeX(ballCoordinatesX, ballCoordinatesY, velx, vely, terrain, friction)
    //         ballCoordinatesX += step;
    //         ballCoordinatesX += step*unitCalc();
    //     }
    // }



    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)0.4*(0.9-Math.exp(-1*(x*x+y*y)/8.0));            //the terrain (so the ai detects water)
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 4;
        double holeCoory = 1;
        double ballCoorX = -3;
        double ballCoorY = 0;
        double radius = 3;                                  //radius of all trees
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 1;                         //the higher this value, the less likely the ai takes a route through a sandpit
        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);           
        SlopeField b = new SlopeField(interval,terrain);  




        Plot testing = new Plot(terrain,interval, a, b, ballCoorX, ballCoorY);
        //System.out.println(testing.shotPlottable());                                                    //works
        // System.out.println();
        double[] correctShot = testing.getCorrectShot(testing.pathX,testing.pathY,ballCoorX,ballCoorY);
        System.out.println("x: "+correctShot[0]);
        System.out.println("y: "+correctShot[1]);



        
    }


}
