package Bots;

import java.util.function.BiFunction;

/**
 * Simulations                      end game (compensates for slopes)
 */
public class Simulations extends Plot
{
    double[] coordinatesAndVelocity = new double[4];
    double endCoorX;                                //the x-coordinates calculated by the pathfinder
    double endCoorY;                                //the y-coordinates calculated by the pathfinder 
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

    public Simulations(BiFunction<Double,Double,Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY, double sFriction, double kFriction)
    {
        super(terrain, interval, adjacency, slope, ballCoorX, ballCoorY);
        this.sFriction = sFriction;
        this.kFriction = kFriction;
        proximity = interval*0.5;
        coordinatesAndVelocity[0] = ballCoorX;  
        coordinatesAndVelocity[1] = ballCoorY;

        correctShot = getCorrectShot(pathX, pathY,ballCoorX,ballCoorY);

        endCoorX = correctShot[0];
        endCoorY = correctShot[1];




    }


    

    public double getEuclideanDistance(double x1, double y1, double[] getCorrectShot)
    {
        double distance = Integer.MAX_VALUE;
        double x2 = getCorrectShot[0];
        double y2 = getCorrectShot[1];
        distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
        return distance;
    }




    
    public double[] getInitialVel(double ballcoorX1, double ballcoorY1)
    {
        System.out.println("beginX: "+ballcoorX1);
        System.out.println("beginY: "+ballcoorY1);
        double[] initialVel = new double[2];
        RungeKutta2 test;
        double min = Integer.MAX_VALUE;
        double[] correctShot = this.correctShot;
       
        double beginX = ballcoorX1;                                          //here it is already 0
        double beginY = ballcoorY1;

        
        
        for(int i = 1; i<6; i++)
        {
            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            
            //System.out.println("i: "+i);
            double[] vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xvel: "+vel[2]+"     yvel: "+vel[3]);
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            double distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = i;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -1.0*i;
            coordinatesAndVelocity[3] = -1.0*i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = -i;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = -i;
            //System.out.println();
           // System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
           // System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = i;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = -i;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = i/2.0;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = i;
                initialVel[1] = i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i/2.0;
            coordinatesAndVelocity[3] = i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = i/2.0;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = -i/2.0;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = -i;
                initialVel[1] =-i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i/2.0;
            coordinatesAndVelocity[3] = -i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = -i/2.0;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = -i/2.0;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            ///System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = i;
                initialVel[1] = -i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i/2.0;
            coordinatesAndVelocity[3] = i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = -i/2.0;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = i/2.0;
            System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = -i;
                initialVel[1] = i/2.0;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i/2.0;
            coordinatesAndVelocity[3] = -i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = i/2.0;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = 0.1;
            coordinatesAndVelocity[3] = i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = 0.1;
                initialVel[1] = i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = 0.1;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = i;
                initialVel[1] = 0.1;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = 0.1;
            coordinatesAndVelocity[3] = -i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = 0.1;
                initialVel[1] = -i;
            }

            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = -i;
            coordinatesAndVelocity[3] = 0.1;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);
            vel = test.coordinatesAndVelocityUntilStop(step); 
            //System.out.println("xcoor: "+vel[0]+"     ycoor: "+vel[1]);
            distance = getEuclideanDistance(vel[0], vel[1], correctShot);
            if(distance < min)
            {
                min = distance;
                //System.out.println("current min: "+min);
                
                initialVel[0] = -i;
                initialVel[1] = 0.1;
            }

        }

        System.out.println("initialX: "+ initialVel[0]);
        System.out.println("initialY: "+ initialVel[1]);
        return initialVel;
    }





    public double[] simulate(double moddedXVel, double moddedYVel, double ballCoorX1, double ballCoorY1)
    {
        coordinatesAndVelocity[0] = ballCoorX1;
        coordinatesAndVelocity[1] = ballCoorY1;
        double[] correctVel = {moddedXVel, moddedYVel};
        coordinatesAndVelocity[2] = correctVel[0];
        coordinatesAndVelocity[3] = correctVel[1];
        RungeKutta2 test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);

        double[] reached = test.coordinatesAndVelocityUntilStop(step);
        double reachedCoordX = reached[0];
        double reachedCoordY = reached[1];
//        System.out.println("endcoorX: "+endCoorX);
//        System.out.println("endcoorY: "+endCoorY);
        // System.out.println("reachedCoordX: "+reachedCoordX);
        // System.out.println("reachedCoordY: "+reachedCoordY);
        // try {
        //     Thread.sleep(1000);
        // } catch (Exception e) {
        //     //TODO: handle exception
        // }
        // System.out.println("checked xvel: "+ moddedXVel);
        // System.out.println("checked yvel: "+ moddedYVel);

        
       
       
       
        if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity))        
        {
            System.out.println("reached");
            return correctVel;
        }
        else if(reachedCoordX < endCoorX-proximity && reachedCoordY < endCoorY-proximity  )    // && moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = increaseBoth(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }
        else if(reachedCoordX > endCoorX+proximity && reachedCoordY > endCoorY+proximity )    // && moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = decreaseBoth(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }
        else if(reachedCoordX < endCoorX-proximity && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity) )  //&& moddedXVel>=0
        {
            correctVel = increaseX(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }
        else if(reachedCoordX > endCoorX+proximity && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity))  // && moddedXVel>=0
        {
            correctVel = decreaseX(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }
        else if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && reachedCoordY < endCoorY-proximity)  // && moddedYVel>=0
        {
            correctVel = increaseY(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }
        else if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && reachedCoordY > endCoorY+proximity )  //&& moddedYVel>=0
        {
            correctVel = decreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }
        else if(reachedCoordX < endCoorX-proximity && reachedCoordY > endCoorY+proximity )  //&& moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = increaseXDecreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }
        else if(reachedCoordX > endCoorX+proximity && reachedCoordY < endCoorY-proximity )    //&& moddedXVel>=0 && moddedYVel>=0
        {
            correctVel = decreaseXIncreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        }

        
        
        
        // else if(reachedCoordX > endCoorX+proximity && reachedCoordY < endCoorY-proximity && moddedXVel<0 && moddedYVel>=0)
        // {
        //     correctVel = increaseBoth(correctVel);
        //     return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        // }

        // else if(reachedCoordX < endCoorX-proximity && reachedCoordY < endCoorY-proximity && moddedXVel<0 && moddedYVel>=0)
        // {
        //     correctVel = decreaseXIncreaseY(correctVel);
        //     return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        // }

        // else if(reachedCoordX < endCoorX-proximity && reachedCoordY > endCoorY+proximity && moddedXVel<0 && moddedYVel>=0)
        // {
        //     correctVel = decreaseBoth(correctVel);
        //     return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        // }

        // else if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && reachedCoordY < endCoorY-proximity && moddedXVel<0 && moddedYVel>=0)
        // {
        //     correctVel = increaseY(correctVel);
        //     return simulate(correctVel[0], correctVel[1], ballCoorX1, ballCoorY1);
        // }

        // else if ()




        // else if(reachedCoordX > endCoorX+proximity && reachedCoordY < endCoorY-proximity && moddedXVel<0 && moddedYVel<0) 
        // {

        // }

        
        
        return correctVel;
    }







    public double[] increaseBoth(double[] correctVel)
    {
        if(correctVel[0] < 0)
        {
            correctVel[0] *= 0.95;
        }
        else
        {
            correctVel[0] *= 1.05;
        }

        if(correctVel[1] < 0)
        {
            correctVel[1] *= 0.95;
        }
        else
        {
            correctVel[1] *= 1.05;
        }

        return correctVel;
    }
    
    public double[] decreaseBoth(double[] correctVel)
    {

        if(correctVel[0] < 0)
        {
            correctVel[0] *= 1.05;
        }
        else
        {
            correctVel[0] *= 0.95;
        }

        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1.05;
        }
        else
        {
            correctVel[1] *= 0.95;
        }

        return correctVel;
    }
    
    public double[] increaseX(double[] correctVel)
    {
        if(correctVel[0] < 0)
        {
            correctVel[0] *= 0.95;
        }
        else
        {
            correctVel[0] *= 1.05;
        }

        return correctVel;
    }
    
    
    
    
    
    public double[] decreaseX(double[] correctVel)
    {
        if(correctVel[0] < 0)
        {
            correctVel[0] *= 1.05;
        }
        else
        {
            correctVel[0] *= 0.95;
        }

        return correctVel;
    }
    
    
    
    public double[] increaseY(double[] correctVel)
    {
        if(correctVel[1] < 0)
        {
            correctVel[1] *= 0.95;
        }
        else
        {
            correctVel[1] *= 1.05;
        }
        
        return correctVel;
    }



    public double[] decreaseY(double[] correctVel)
    {
        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1.05;
        }
        else
        {
            correctVel[1] *= 0.95;
        }
        
        return correctVel;
    }
    
    public double[] increaseXDecreaseY(double[] correctVel)
    {

        if(correctVel[1] < 0)
        {
            correctVel[1] *= 0.95;
        }
        else
        {
            correctVel[1] *= 1.05;
        }
        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1.05;
        }
        else
        {
            correctVel[1] *= 0.95;
        }
        return correctVel;
    }
    public double[] decreaseXIncreaseY(double[] correctVel)
    {
        if(correctVel[1] < 0)
        {
            correctVel[1] *= 1.05;
        }
        else
        {
            correctVel[1] *= 0.95;
        }
        if(correctVel[1] < 0)
        {
            correctVel[1] *= 0.95;
        }
        else
        {
            correctVel[1] *= 1.05;
        }
        return correctVel;
    }
    

    





    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(1);
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = -10;
        double holeCoory = -10;
        double ballCoorX = -24;
        double ballCoorY = -24;
        double sFriction = 0.2;
        double kFriction = 0.05;
        double radius = 3;                                  //radius of all trees      
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 0;
        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);           
        SlopeField b = new SlopeField(interval,terrain);  

        Simulations testing = new Simulations(terrain, interval, a, b, ballCoorX, ballCoorY, sFriction, kFriction);
        double[] goodvel = testing.getInitialVel(ballCoorX, ballCoorY);
         double[] correctVel = testing.simulate(goodvel[0], goodvel[1], ballCoorX, ballCoorY);
         //double[] arr = {20,12};
         //double correctdistance = testing.getEuclideanDistance(0.0001, -0.0004, arr);
         //System.out.println(correctdistance);
        // //double[] realVel = testing.simulate(correctVel[0],correctVel[1]);
        System.out.println("xVel: "+correctVel[0]);
        System.out.println("yVel: "+correctVel[1]);
        System.out.println("xcoor: "+testing.coordinatesAndVelocity[0]);
        System.out.println("ycoor: "+testing.coordinatesAndVelocity[1]);
        //System.out.println(testing.getEuclideanDistance(0.099, 1.706, testing.getCorrectShot(testing.pathX, testing.pathY)));         //working!!!!!!!!!!
        
        
    }
}
