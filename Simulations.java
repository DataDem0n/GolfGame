import java.util.function.BiFunction;

/**
 * Simulations                      end game (compensates for slopes)
 */
public class Simulations extends Plot
{
    double[] coordinatesAndVelocity = {0,0,2,0};
    double endCoorX;                                //the x-coordinates calculated by the pathfinder
    double endCoorY;                                //the y-coordinates calculated by the pathfinder 
    double ballCoorX;
    double ballCoorY; 
    BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.1*x+1);
    double kFriction;
    double sFriction;
    AdjacencyField adjacency;
    SlopeField slope;
    PathCalculator path;
    Plot correctPos;
    int simulationsRan;
    double step = 0.000001;
    double proximity;

    public Simulations(BiFunction<Double,Double,Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, PathCalculator path, double ballCoorX, double ballCoorY, Plot correctPos, int simulationsRan, double sFriction, double kFriction)
    {
        super(terrain, interval, adjacency, slope, ballCoorX, ballCoorY);
        this.simulationsRan = simulationsRan;
        this.sFriction = sFriction;
        this.kFriction = kFriction;
        proximity = interval;
    }


    

    public double getEuclideanDistance(double x1, double y1)
    {
        double distance = Integer.MAX_VALUE;
        double x2 = getCorrectShot(pathX, pathY)[0];
        double y2 = getCorrectShot(pathX, pathY)[0];
        distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
        return distance;
    }


    public double[] getInitialVel()
    {
        int range = 12;
        double[] initialVel = new double[2];
        RungeKutta2 test;
        int i = 0;
        double min = Integer.MAX_VALUE;
        while(i<simulationsRan)
        {
            coordinatesAndVelocity[2] = (int)(Math.random()*range - 6);
            coordinatesAndVelocity[3] = (int)(Math.random()*range - 6);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction);
            double distance = getEuclideanDistance(test.coordinatesAndVelocityUntilStop(step)[0], test.coordinatesAndVelocityUntilStop(step)[1]);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = coordinatesAndVelocity[2];
                initialVel[1] = coordinatesAndVelocity[3];
            }
            i++;
        }
         return initialVel;
    }





    public double[] simulate(double moddedXVel, double moddedYVel)
    {
        double[] correctVel = {moddedXVel, moddedYVel};
        coordinatesAndVelocity[2] = correctVel[1];
        coordinatesAndVelocity[3] = correctVel[2];
        RungeKutta2 test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction);

        if((test.coordinatesAndVelocityUntilStop(step)[0] < endCoorX+proximity && test.coordinatesAndVelocityUntilStop(step)[0] > endCoorX-proximity) && (test.coordinatesAndVelocityUntilStop(step)[1] < endCoorY+proximity && test.coordinatesAndVelocityUntilStop(step)[1] > endCoorY-proximity))        
        {
            return correctVel;
        }
        else if(test.coordinatesAndVelocityUntilStop(step)[0] < endCoorX-proximity && test.coordinatesAndVelocityUntilStop(step)[1] < endCoorY-proximity)
        {
            correctVel = increaseBoth(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(test.coordinatesAndVelocityUntilStop(step)[0] > endCoorX+proximity && test.coordinatesAndVelocityUntilStop(step)[1] > endCoorY+proximity)
        {
            correctVel = decreaseBoth(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(test.coordinatesAndVelocityUntilStop(step)[0] < endCoorX-proximity && (test.coordinatesAndVelocityUntilStop(step)[1] < endCoorY+proximity && test.coordinatesAndVelocityUntilStop(step)[1] > endCoorY-proximity))
        {
            correctVel = increaseX(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(test.coordinatesAndVelocityUntilStop(step)[0] > endCoorX+proximity && (test.coordinatesAndVelocityUntilStop(step)[1] < endCoorY+proximity && test.coordinatesAndVelocityUntilStop(step)[1] > endCoorY-proximity))
        {
            correctVel = decreaseX(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if((test.coordinatesAndVelocityUntilStop(step)[0] < endCoorX+proximity && test.coordinatesAndVelocityUntilStop(step)[0] > endCoorX-proximity) && test.coordinatesAndVelocityUntilStop(step)[1] < endCoorY-proximity)
        {
            correctVel = increaseY(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if((test.coordinatesAndVelocityUntilStop(step)[0] < endCoorX+proximity && test.coordinatesAndVelocityUntilStop(step)[0] > endCoorX-proximity) && test.coordinatesAndVelocityUntilStop(step)[1] > endCoorY+proximity)
        {
            correctVel = decreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(test.coordinatesAndVelocityUntilStop(step)[0] < endCoorX-proximity && test.coordinatesAndVelocityUntilStop(step)[1] > endCoorY+proximity)
        {
            correctVel = increaseXDecreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(test.coordinatesAndVelocityUntilStop(step)[0] > endCoorX+proximity && test.coordinatesAndVelocityUntilStop(step)[1] < endCoorY-proximity)
        {
            correctVel = decreaseXIncreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        return correctVel;
    }

    public double[] increaseBoth(double[] correctVel)
    {
        correctVel[0] *= 1.01;
        correctVel[1] *= 1.01;
        return correctVel;
    }
    public double[] decreaseBoth(double[] correctVel)
    {
        correctVel[0] *= 0.99;
        correctVel[1] *= 0.99;
        return correctVel;
    }
    public double[] increaseX(double[] correctVel)
    {
        correctVel[0] *= 1.01;
        return correctVel;
    }
    public double[] decreaseX(double[] correctVel)
    {
        correctVel[0] *= 0.99;
        return correctVel;
    }
    public double[] increaseY(double[] correctVel)
    {
        correctVel[1] *= 1.01;
        return correctVel;
    }
    public double[] decreaseY(double[] correctVel)
    {
        correctVel[1] *= 0.99;
        return correctVel;
    }
    public double[] increaseXDecreaseY(double[] correctVel)
    {
        correctVel[0] *= 1.01;
        correctVel[1] *= 0.99;
        return correctVel;
    }
    public double[] decreaseXIncreaseY(double[] correctVel)
    {
        correctVel[0] *= 0.99;
        correctVel[1] *= 1.01;
        return correctVel;
    }
}
