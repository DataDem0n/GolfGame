import java.util.function.BiFunction;

/**
 * Simulations                      end game (compensates for slopes)
 */
public class Simulations extends Plot
{
    double[] coordinatesAndVelocity = {0,0,0,0};
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
    double step = 0.000001;
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
        correctShot = getCorrectShot(pathX, pathY);
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




    
    public double[] getInitialVel()
    {
        double[] initialVel = new double[2];
        RungeKutta2 test;
        double min = Integer.MAX_VALUE;
        double[] correctShot = this.correctShot;
        double beginX = coordinatesAndVelocity[0];
        double beginY = coordinatesAndVelocity[1];
        
        
        for(int i = 1; i<6; i++)
        {
            coordinatesAndVelocity[0] = beginX;
            coordinatesAndVelocity[1] = beginY;
            coordinatesAndVelocity[2] = i;
            coordinatesAndVelocity[3] = i;
            //System.out.println();
            //System.out.println("xvel: "+  coordinatesAndVelocity[2] + "yvel: "+  coordinatesAndVelocity[3]);
            test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step); 
            
            System.out.println("i: "+i);
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

        }

        return initialVel;
    }





    public double[] simulate(double moddedXVel, double moddedYVel)
    {
        coordinatesAndVelocity[0] = ballCoorX;
        coordinatesAndVelocity[1] = ballCoorY;
        double[] correctVel = {moddedXVel, moddedYVel};
        coordinatesAndVelocity[2] = correctVel[0];
        coordinatesAndVelocity[3] = correctVel[1];
        RungeKutta2 test = new RungeKutta2(terrain, coordinatesAndVelocity, kFriction, sFriction, step);

        double[] reached = test.coordinatesAndVelocityUntilStop(step);
        double reachedCoordX = reached[0];
        double reachedCoordY = reached[1];

        
       
       
       
        if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity))        
        {
            return correctVel;
        }
        else if(reachedCoordX < endCoorX-proximity && reachedCoordY < endCoorY-proximity)
        {
            correctVel = increaseBoth(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(reachedCoordX > endCoorX+proximity && reachedCoordY > endCoorY+proximity)
        {
            correctVel = decreaseBoth(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(reachedCoordX < endCoorX-proximity && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity))
        {
            correctVel = increaseX(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(reachedCoordX > endCoorX+proximity && (reachedCoordY < endCoorY+proximity && reachedCoordY > endCoorY-proximity))
        {
            correctVel = decreaseX(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && reachedCoordY < endCoorY-proximity)
        {
            correctVel = increaseY(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if((reachedCoordX < endCoorX+proximity && reachedCoordX > endCoorX-proximity) && reachedCoordY > endCoorY+proximity)
        {
            correctVel = decreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(reachedCoordX < endCoorX-proximity && reachedCoordY > endCoorY+proximity)
        {
            correctVel = increaseXDecreaseY(correctVel);
            return simulate(correctVel[0], correctVel[1]);
        }
        else if(reachedCoordX > endCoorX+proximity && reachedCoordY < endCoorY-proximity)
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





    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(1);
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 13;
        double holeCoory = 13;
        double ballCoorX = 0;
        double ballCoorY = 0;
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
        double[] goodvel = testing.getInitialVel();
         double[] correctVel = testing.simulate(goodvel[0], goodvel[1]);
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
