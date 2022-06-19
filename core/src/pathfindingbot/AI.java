package pathfindingbot;

import noise.RandomNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class AI
{
    double[] targetRXY;
    boolean finalShot;
    AdjacencyField adjacency;
    BiFunction<Double, Double, Double> terrain;
    double interval;
    SlopeField slope;
    double ballCoorX;
    double ballCoorY;
    double sFriction;
    double kFriction;

    double totalIterations = 0;

    RandomNoise noise;

    /**TODO
     *
     * @param terrain
     * @param interval
     * @param adjacency
     * @param slope
     * @param ballCoorX
     * @param ballCoorY
     * @param sFriction
     * @param kFriction
     * @param targetRXY
     */
    
    public AI(BiFunction<Double, Double, Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY, double sFriction, double kFriction, double[] targetRXY)
    {
        this.targetRXY = targetRXY;
        this.adjacency = adjacency;
        this.slope = slope;
        this.terrain = terrain;
        this.interval = interval;
        this.ballCoorX = ballCoorX;
        this.ballCoorY = ballCoorY;
        this.sFriction = sFriction;
        this.kFriction = kFriction;
        noise = new RandomNoise(1.0, 1.0);

    }

    /**TODO
     *
     * @param ballCoorX1
     * @param ballcoorY1
     * @return
     */

    public boolean endReached(double ballCoorX1, double ballcoorY1)
    {
        if((ballCoorX1 < targetRXY[1]+targetRXY[0] && ballCoorX1 > targetRXY[1]-targetRXY[0]) && (ballcoorY1 < targetRXY[2]+targetRXY[0] && ballcoorY1 > targetRXY[2]-targetRXY[0]))
        {
            return true;
        }
        return false;
    }

    /**TODO
     *
     * @param ballCoorX1
     * @param ballcoorY1
     * @return
     */

    public List<List> getAllVelocities(double ballCoorX1, double ballcoorY1)
    {
        ArrayList<List> allVelocities = new ArrayList<List>();
        allVelocities.add(new ArrayList<Double>());
        allVelocities.add(new ArrayList<Double>());



         
        while(!endReached(ballCoorX1,ballcoorY1))
        {            
            System.out.println("ballcoorX: " + ballCoorX1);
            System.out.println("ballcoorY: " + ballcoorY1);
            // try 
            // {
            //     Thread.sleep(2000);
            // } 
            // catch (InterruptedException e) 
            // {

            // }
            
            
            Plot check = new Plot(terrain, interval, adjacency, slope, ballCoorX, ballCoorY, sFriction, kFriction, targetRXY);

            double[] save = check.slopeCompensator(ballCoorX1, ballcoorY1);


            double[] usedVelocity;

            Simulations correctVel;


            if(save[0] > targetRXY[1]-1 && save[0] < targetRXY[1]+1 && save[1] > targetRXY[2]-1 && save[1] < targetRXY[2]+1)
            {

                correctVel = new Simulations(terrain, interval, adjacency, slope, ballCoorX1, ballcoorY1, sFriction, kFriction, targetRXY, true);              //changed correctpos to bestshot
                double[] gIV = correctVel.getInitialVel(ballCoorX1, ballcoorY1, 7);                                                                                            //________________________________________________________________________
                usedVelocity = correctVel.simulate(gIV[0], gIV[1],ballCoorX1, ballcoorY1, 0, 20, 2.5, 0);
                totalIterations += correctVel.iteration;

            }
            else
            {

                correctVel = new Simulations(terrain, interval, adjacency, slope, ballCoorX1, ballcoorY1, sFriction, kFriction, targetRXY, false);              //changed correctpos to bestshot
                double[] gIV = correctVel.getInitialVel(ballCoorX1, ballcoorY1, 7);                                                                                            //________________________________________________________________________
                usedVelocity = correctVel.simulate(gIV[0], gIV[1],ballCoorX1, ballcoorY1, 0, 20, 2.5, 0);
                totalIterations += correctVel.iteration;
            }

            // System.out.println("x: "+ correctVel.coordinatesAndVelocity[0]);
            // System.out.println("y: "+ correctVel.coordinatesAndVelocity[1]);
            
            allVelocities.get(0).add(usedVelocity[0]);
            allVelocities.get(1).add(usedVelocity[1]);
            
            
            
            ballCoorX1 = usedVelocity[2];
            ballcoorY1 = usedVelocity[3];
            System.out.println(ballCoorX1+"clomX");
            System.out.println(ballcoorY1+"clomY");
            // try 
            // {
            //     Thread.sleep(2000);
            // } 
            // catch (InterruptedException e) 
            // {

            // }

        }

        ArrayList<Double> random = new ArrayList<Double>();
        random = noise.generateSeed();
        ArrayList<List> newVelocities = new ArrayList<List>();
        ArrayList<Double> velX = new ArrayList<Double>();
        ArrayList<Double> velY = new ArrayList<Double>();
        newVelocities.add(velX);
        newVelocities.add(velY);

        for(int i = 0; i < allVelocities.get(0).size(); i++)
        {
            newVelocities.get(0).add(new Double(allVelocities.get(0).get(i).toString())*random.get(i));
            newVelocities.get(1).add(new Double(allVelocities.get(1).get(i).toString())*random.get(i));
        }
        System.out.println("amount of iterations: "+totalIterations);
        System.out.println("xVel: "+newVelocities.get(0).get(0));
        System.out.println("yVel: "+newVelocities.get(1).get(0));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return newVelocities;
    }




    


    
    
    
    
    
    
    
    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->1.0;//-0.1+(x*x+y*y)/1000.0;             //0.4*(0.9-Math.exp(-1*(x*x+y*y)/8.0))                        //(double)0.5*(Math.sin((x+y)/10))+1            0.4*(0.9-Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/8))
        double[] coorTX = {15, 15, 8};       //x-coordinates of the trees    
        double[] coorTY = {15, 8, 15};         //y-coordinates of the trees
        double interval = 1;
        double[] targetRXY = {0.5,18,18};
        double holeCoorx = 18;
        double holeCoory = 18;
        double ballCoorX = -15;
        double ballCoorY = 15;
        double sFriction = 0.2;
        double kFriction = 0.1;
        double radius = 2;                                  //radius of all trees      
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 0;
        SlopeField slope = new SlopeField(interval, terrain);
        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY, slope);
        SlopeField b = new SlopeField(interval,terrain);  

        
        AI newtonSlave = new AI(terrain, interval, a, b, ballCoorX, ballCoorY, sFriction, kFriction, targetRXY);

        List<List> endtest = newtonSlave.getAllVelocities(ballCoorX,ballCoorY);



        for(int i = 0; i < endtest.get(0).size(); i++)
        {        
        System.out.println("velocityX"+i+":   "+endtest.get(0).get(i));
        System.out.println("velocityY"+i+":   "+endtest.get(1).get(i));
        }

    }
}









//if the shot is towards the hole turn the boolean in the simulations true.
