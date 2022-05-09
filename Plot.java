import java.util.function.BiFunction;
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
    }

    public double[] differenceCalc()                                                        //calculates the dx and dy of the shot
    {
        pathCalculator(getBallPosition()[0], getBallPosition()[1]);
        double[] difference = new double[2];
        double x0 = ballCoorX;
        double x1 = pathX.get(pathX.size()-1);
        double y0 = ballCoorY;
        double y1 = pathY.get(pathY.size()-1);

        difference[0] = x1 - x0;
        difference[1] = y1-y0;
        return difference;

    }

    public double unitCalc()
    {
        double differenceX = differenceCalc()[0];
        double differenceY = differenceCalc()[1];
        double unit = (differenceY/differenceX);                            //*100
        return unit;
    }

    public boolean checkWaterOrTree(double currentX, double currentY)
    {
        double vX = currentX;
        double vY = currentY;
        if(vX > 0)
        {
            vX += 25;
        }
        if(vY > 0)
        {
            vY += 25;
        }
        if(adjacency.floodFillUpdateSandpits()[(int) vX][(int) vY] < 0)
        {
            return true;
        }
        return false;
        
        
    }

    public boolean shotPlottable()
    {
        double currentX = ballCoorX;
        double currentY = ballCoorY;
        while(currentX < pathX.get(pathX.size()-1) && currentY < pathY.get(pathY.size()-1))
        {
            if(checkWaterOrTree(currentX, currentY))
            {
                return false;
            }
            currentX += step;
            currentY += step*unitCalc();
        }
        return true;
    }

    
    public double[] getCorrectShot(List pathX, List pathY)
    {
        double[] correctPos = new double[2];

        while(!shotPlottable())
        {
            decreasePath(pathX, pathY).get(0);
        }
        if((double) pathX.get(pathX.size()-1)*interval > 25)
        {
            correctPos[0] = (double) pathX.get(pathX.size()-1)*interval - 25;
        }
        else
        {
            correctPos[0] = (double) pathX.get(pathX.size()-1)*interval;
        }
        
        if((double) pathY.get(pathY.size()-1)*interval > 25)
        {
            correctPos[1] = (double) pathY.get(pathY.size()-1)*interval - 25;
        }
        else
        {
            correctPos[1] = (double) pathY.get(pathY.size()-1)*interval;
        }
                    
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


}
