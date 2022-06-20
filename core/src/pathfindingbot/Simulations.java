package pathfindingbot;

import java.util.function.BiFunction;

/**
 * Simulations
 */
public class Simulations extends Plot
{
    double[] coordinatesAndVelocity = new double[4];
    double endCoorX;
    double endCoorY;
    double ballCoorX;
    double ballCoorY; 
    //BiFunction<Double,Double,Double> terrain;
    double kFriction;
    double sFriction;
    AdjacencyField adjacency;
    SlopeField slope;
    PathCalculator path;
    Plot correctPos;
    int simulationsRan;
    double step = 0.001;
    double proximity;
    double[] correctShot;
    double holeCoorx, holeCoory;
    double[] targetRXY;
    boolean endHole;
    double scale;

    int iteration = 0;


    /**
     * constructor
     * @param terrain: this is the function that the adjacency field is build from
     * @param interval: this determines how big each tile is in for the adjacency field
     * @param adjacency: the adjacency field
     * @param slope: the slope field
     * @param ballCoorX: x-coordinate of the ball
     * @param ballCoorY: y-coordinate of the ball
     * @param sFriction: the static friction that is being used
     * @param kFriction: the kinetic friction that is being used
     * @param targetRXY: an array containing the radius, x-coordinate and y-coordinate of the hole
     * @param endHole: a boolean describing if the position the bot is shooting at is equal to the hole's position
     */
    public Simulations(BiFunction<Double,Double,Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY, double sFriction, double kFriction, double[] targetRXY, boolean endHole)
    {
        super(terrain, interval, adjacency, slope, ballCoorX, ballCoorY, sFriction, kFriction, targetRXY);
        this.sFriction = sFriction;
        this.kFriction = kFriction;
        scale = 1;
        coordinatesAndVelocity[0] = ballCoorX;  
        coordinatesAndVelocity[1] = ballCoorY;
        correctShot = slopeCompensator(ballCoorX,ballCoorY);
        this.endHole = endHole;
        if(correctShot[2] > 50 && !endHole)
        {
            proximity = (interval+scale)*2.5;
        }
        else
        {
            proximity = interval*0.5;
        }
        
        endCoorX = correctShot[0];
        endCoorY = correctShot[1];
    }


    /**
     * This method calculates the euclidean distance between 2 points
     * @param x1: x-coordinate of the first point
     * @param y1: y-coordinate of the first point
     * @param getCorrectShot: an array containing the coordinates of the second point
     * @return: the euclidean distance between the points
     */
    public double getEuclideanDistance(double x1, double y1, double[] getCorrectShot)
    {
        double distance = Integer.MAX_VALUE;
        double x2 = getCorrectShot[0];
        double y2 = getCorrectShot[1];
        distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
        return distance;
    }





    /**
     * This method gets the optimal initial velocities (that are the closest to the desired position) using hill climbing
     * @param ballcoorX1: x-coordinate of the ball
     * @param ballcoorY1: y-coordinate of the ball
     * @param simulationlength: amount of simulations to get the optimal velocities (trade-off between accuracy and speed)
     * @return: an array containing the optimal x and y velocity
     */
    public double[] getInitialVel(double ballcoorX1, double ballcoorY1, int simulationlength)
    {
        double[] initialVel = new double[2];
        RungeKutta2 test;
        double min = Integer.MAX_VALUE;
        double[] correctShot = this.correctShot;
       
        double beginX = ballcoorX1;
        double beginY = ballcoorY1;

        
        
        for(int i = 1; i<=simulationlength; i++)
        {
            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = i;

            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);

            

            double[] vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;

            double distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                
                initialVel[0] = i;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -1.0*i;
            coordinatesAndVelocity[3] = -1.0*i;

            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = -i;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = -i;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole); 
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = i;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = i;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = -i;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = i/2.0;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = i;
                initialVel[1] = i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i/2.0;
            coordinatesAndVelocity[3] = i;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = i/2.0;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = -i/2.0;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = -i;
                initialVel[1] =-i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i/2.0;
            coordinatesAndVelocity[3] = -i;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = -i/2.0;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = -i/2.0;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = i;
                initialVel[1] = -i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = i/2.0;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = -i/2.0;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = i/2.0;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = -i;
                initialVel[1] = i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i/2.0;
            coordinatesAndVelocity[3] = -i;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = i/2.0;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = 0.1;
            coordinatesAndVelocity[3] = i;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = 0.1;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = 0.1;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = i;
                initialVel[1] = 0.1;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = 0.1;
            coordinatesAndVelocity[3] = -i;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = 0.1;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = 0.1;
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);  
            vel = test.coordinatesAndVelocityUntilStop(step);
            iteration++;
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                initialVel[0] = -i;
                initialVel[1] = 0.1;
            }

        }
        return initialVel;
    }








    /**
     * This method makes small adjustments to the initial velocities until the desired position is reached
     * @param moddedXVel: the current x-velocity
     * @param moddedYVel: the current y-velocity
     * @param ballCoorX1: the x-coordinates of the ball
     * @param ballCoorY1: the y-coordinates of the ball
     * @param counter: counter that keeps up with the amount of iterations
     * @param scaler: scales the percentual changes, so they become smaller after a set amount of iterations
     * @param scale: scales the error so it becomes a bit larger after a set amount of iterations
     * @param counter2: second counter that keeps up with the amount of iterations
     * @return: an array with the correct velocities needed
     */
    public double[] simulate(double moddedXVel, double moddedYVel, double ballCoorX1, double ballCoorY1, int counter, double scaler,double scale, int counter2)
    {
        
        counter2++;
        if(counter == 10 && scaler > 5)
        {
            scaler /= 2;
            counter = 0;
        }
        else
        {
            counter++;
        }

        if(counter2 == 300)
        {
            scale *= 1.2;
            counter2 = 0;
        }
        coordinatesAndVelocity[0] = ballCoorX1;
        coordinatesAndVelocity[1] = ballCoorY1;
        double[] correctVel = {moddedXVel, moddedYVel, 0, 0};
        coordinatesAndVelocity[2] = correctVel[0];
        coordinatesAndVelocity[3] = correctVel[1];
        RungeKutta2 test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step, targerRXY, endHole);

        double[] reached = test.coordinatesAndVelocityUntilStop(step);
        iteration++;
        double reachedCoordX = reached[0];
        double reachedCoordY = reached[1];

        if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity))        
        {
            correctVel[2] = reachedCoordX;
            correctVel[3] = reachedCoordY;
            return correctVel;
        }
        else if(reachedCoordX < endCoorX-proximity && reachedCoordY < endCoorY-proximity  )    // && moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = increaseBoth(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }
        else if(reachedCoordX > endCoorX+proximity && reachedCoordY > endCoorY+proximity )    // && moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = decreaseBoth(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }
        else if(reachedCoordX < endCoorX-proximity && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity) )  //&& moddedXVel>=0
        {
            correctVel = increaseX(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }
        else if(reachedCoordX > endCoorX+proximity && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity))  // && moddedXVel>=0
        {
            correctVel = decreaseX(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }
        else if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && reachedCoordY < endCoorY-proximity)  // && moddedYVel>=0
        {
            correctVel = increaseY(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }
        else if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && reachedCoordY > endCoorY+proximity )  //&& moddedYVel>=0
        {
            correctVel = decreaseY(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }
        else if(reachedCoordX < endCoorX-proximity && reachedCoordY > endCoorY+proximity )  //&& moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = increaseXDecreaseY(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }
        else if(reachedCoordX > endCoorX+proximity && reachedCoordY < endCoorY-proximity )    //&& moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = decreaseXIncreaseY(correctVel,scaler);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1, counter, scaler, scale, counter2);
        }

        return correctVel;
    }


    public double[] increaseBoth(double[] correctVel, double scaler)
    {
        if(correctVel[0] < 0)
        {
            correctVel[0] *= 1-(scaler/100);
        }
        else
        {
            correctVel[0] *= 1+(scaler/100);
        }

        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1-(scaler/100);
        }
        else
        {
            correctVel[1] *= 1+(scaler/100);
        }

        return correctVel;
    }

    public double[] decreaseBoth(double[] correctVel, double scaler)
    {

        if(correctVel[0] < 0)
        {
            correctVel[0] *= 1+(scaler/100);
        }
        else
        {
            correctVel[0] *= 1-(scaler/100);
        }

        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1+(scaler/100);
        }
        else
        {
            correctVel[1] *= 1-(scaler/100);
        }

        return correctVel;
    }

    public double[] increaseX(double[] correctVel, double scaler)
    {
        if(correctVel[0] < 0)
        {
            correctVel[0] *= 1-(scaler/100);
        }
        else
        {
            correctVel[0] *= 1+(scaler/100);
        }

        return correctVel;
    }

    public double[] decreaseX(double[] correctVel, double scaler)
    {
        if(correctVel[0] < 0)
        {
            correctVel[0] *= 1+(scaler/100);
        }
        else
        {
            correctVel[0] *=  1-(scaler/100);
        }

        return correctVel;
    }
    
    public double[] increaseY(double[] correctVel, double scaler)
    {
        if(correctVel[1] < 0)
        {
            correctVel[1] *=  1-(scaler/100);
        }
        else
        {
            correctVel[1] *= 1+(scaler/100);
        }
        
        return correctVel;
    }

    public double[] decreaseY(double[] correctVel, double scaler)
    {
        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1+(scaler/100);
        }
        else
        {
            correctVel[1] *=  1-(scaler/100);
        }
        
        return correctVel;
    }

    public double[] increaseXDecreaseY(double[] correctVel, double scaler)
    {

        if(correctVel[0] < 0)
        {
            correctVel[0] *=  1-(scaler/100);
        }
        else
        {
            correctVel[0] *= 1+(scaler/100);
        }


        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1+(scaler/100);
        }
        else
        {
            correctVel[1] *=  1-(scaler/100);
        }
        return correctVel;
    }
    public double[] decreaseXIncreaseY(double[] correctVel, double scaler)
    {
        if(correctVel[0] < 0)
        {
            correctVel[0] *= 1+(scaler/100);
        }
        else
        {

            correctVel[0] *=  1-(scaler/100);
        }

        
        if(correctVel[1] < 0)
        {
            correctVel[1] *=  1-(scaler/100);
        }
        else
        {

            correctVel[1] *= 1+(scaler/100);
        }
        return correctVel;
    }
    

    





    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->0.4*(0.9-Math.exp(-1*(x*x+y*y)/8.0));             //-0.1+(x*x+y*y)/1000.0
        ;
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 4;
        double holeCoory = 1;
        double ballCoorX = -3;
        double ballCoorY = 0;
        double sFriction = 0.3;
        double kFriction = 0.15;
        double radius = 2;                                  //radius of all trees      
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 0;
            
        SlopeField b = new SlopeField(interval,terrain);  
        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY, b);

        double scaler = 40;
        //2.6979688828108785
        //2.654208

        //3.747179003903998
        //3.6864

        //3.8191843160041046
        //3.8291917154455404
        Simulations testing = new Simulations(terrain, interval, a, b, ballCoorX, ballCoorY, sFriction, kFriction, endY, true);
        double[] goodvel = testing.getInitialVel(ballCoorX, ballCoorY, 12);                                                                         //___________________________________________________
        double[] correctVel = testing.simulate(goodvel[0], goodvel[1], ballCoorX, ballCoorY, 0, scaler, 2.5, 0);
        //double[] arr = {20,12};
         //double correctdistance = testing.getEuclideanDistance(0.0001, -0.0004, arr);
         //System.out.println(correctdistance);
        // //double[] realVel = testing.simulate(correctVel[0],correctVel[1]);
        System.out.println("xVel: "+correctVel[0]);
        System.out.println("yVel: "+correctVel[1]);
        // System.out.println("xcoor: "+testing.coordinatesAndVelocity[0]);
        // System.out.println("ycoor: "+testing.coordinatesAndVelocity[1]);
        //System.out.println(testing.getEuclideanDistance(0.099, 1.706, testing.getCorrectShot(testing.pathX, testing.pathY)));         //working!!!!!!!!!!
        
        
    }
}
