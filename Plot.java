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


    public Plot(BiFunction<Double,Double,Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY)
    {
        super(adjacency, slope, ballCoorX, ballCoorY);
        this.terrain = terrain;
        this.interval = interval;
        step = 0.5*interval;
        this.adjacency = adjacency;
        int[] ballPos = getBallPosition();
        List<List> getpath = pathCalculator(ballPos[0], ballPos[1]);
        pathX = getpath.get(0);
        pathY = getpath.get(1);
        
        System.out.println(pathX.toString());

       
    }

    public double[] differenceCalc()                                                        //calculates the dx and dy of the shot
    {
        //pathCalculator(getBallPosition()[0], getBallPosition()[1]);
        double[] difference = new double[2];
        double x0 = Math.abs(ballCoorX);
        double x1 = Math.abs(pathX.get(pathX.size()-1));
        double y0 = Math.abs(ballCoorY);
        double y1 = Math.abs(pathY.get(pathY.size()-1));

        difference[0] = x1 - x0;
        difference[1] = y1-  y0;
        return difference;

    }

    public double unitCalc()                                                                    //doesnt work if x == 0, add another argument to shotplottable.
    {
        double differenceX = differenceCalc()[0];
        //System.out.println(differenceX);
        double differenceY = differenceCalc()[1];
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
        if(vY >= 0)
        {
            vY += 25;
        }
        vX /= interval;
        vY /= interval;
        // System.out.println("vX: " + (int)vX);
        // System.out.println("vY: " + (int)vY);
        if(adjacency.floodFillUpdateSandpits()[(int) vX][(int) vY] < 0)
        {
            return true;
        }
        return false;
        
        
    }

    public double pathConverter(int path)
    {
        if(path*interval > 25)
        {
            return path*interval-25;
        }
        else
        {
            return (-1.0*25) + path*interval;
        }
    }

    public boolean shotPlottable()                                                                                             
    {
        double currentX = ballCoorX;
        double currentY = ballCoorY;

        double xcoor = pathConverter(pathX.get(pathX.size()-1));
        double ycoor = pathConverter(pathY.get(pathY.size()-1));
        double unitVec = unitCalc();
        
        if(currentX < xcoor && currentY < ycoor)
        {
            System.out.println("pathX: " + pathX.get(pathX.size()-1));
            System.out.println("pathY: " + pathY.get(pathY.size()-1));
            while(currentX <= xcoor && currentY <= ycoor)
            {
                            
                    if(checkWaterOrTree(currentX, currentY))
                    {
                        return false;
                    }
                    currentX += step;
                    currentY += step*unitVec;
                    // System.out.println("currentX: "+currentX);
                    // System.out.println("currentY: "+currentY);

            }
            return true;
        }
        if(currentX > xcoor && currentY > ycoor)
        {
            while(currentX >= xcoor && currentY >= ycoor)
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
        if(currentX > xcoor && currentY < ycoor)
        {
            while(currentX >= xcoor && currentY <= ycoor)
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
        if(currentX < xcoor && currentY > ycoor)
        {
            while(currentX <= xcoor && currentY >= ycoor)
            {
                if(checkWaterOrTree(currentX, currentY))
                {
                    return false;
                }
                currentX += step;
                currentY -= step*unitVec;
            }
            return true;
        }
        
       return true;
    }

    
    public double[] getCorrectShot(List pathX, List pathY)
    {
        double[] correctPos = new double[2];

        while(!shotPlottable())
        {
            pathX = (List) decreasePath(pathX, pathY).get(0);
            pathY = (List) decreasePath(pathX, pathY).get(1);
        }

            correctPos[0] = pathConverter((int) pathX.get(pathX.size()-1));
            correctPos[1] = pathConverter((int) pathY.get(pathY.size()-1));
                    
        return correctPos;
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
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.1*x+1);
        double[] coorTX = {15};       //x-coordinates of the trees
        double[] coorTY = {15};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 20;
        double holeCoory = 20;
        double radius = 3;                                  //radius of all trees      
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 0;
        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);           
        SlopeField b = new SlopeField(interval,terrain);  




        Plot testing = new Plot(terrain,interval, a, b, 1, 1);
        //System.out.println(testing.shotPlottable());                                                    //works
        // System.out.println();
        System.out.println("x: "+testing.getCorrectShot(testing.pathX,testing.pathY)[0]);
        System.out.println("y: "+testing.getCorrectShot(testing.pathX,testing.pathY)[1]);

        
    }


}
