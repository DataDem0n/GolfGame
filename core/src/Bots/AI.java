package Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class AI extends Simulations
{
    double holeCoorX, holeCoorY, holeRadius;
    
    public AI(BiFunction<Double, Double, Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY, double sFriction, double kFriction,  double holeCoorX, double holeCoorY, double holeRadius) 
    {
        super(terrain, interval, adjacency, slope, ballCoorX, ballCoorY, sFriction, kFriction);
        this.holeCoorX = holeCoorX;
        this.holeCoorY = holeCoorY;
        this.holeRadius = holeRadius;
        this.adjacency = adjacency;
    }

    public boolean endReached(double ballCoorX1, double ballcoorY1)
    {
        if((ballCoorX1 < holeCoorX+holeRadius && ballCoorX1 > holeCoorX-holeRadius) && (ballcoorY1 < holeCoorY+holeRadius && ballcoorY1 > holeCoorY-holeRadius))
        {
            return true;
        }
        return false;
    }
    

    public List<List> getAllVelocities(double ballCoorX1, double ballcoorY1)
    {
        ArrayList<List> allVelocities = new ArrayList<List>();
        allVelocities.add(new ArrayList<Double>());
        allVelocities.add(new ArrayList<Double>());


         
        while(!endReached(ballCoorX1,ballcoorY1))
        {            
            System.out.println("ballcoorX: " + ballCoorX);
            System.out.println("ballcoorY: " + ballCoorY);
            Simulations correctVel = new Simulations(terrain, interval, adjacency, slope, ballCoorX1, ballcoorY1, sFriction, kFriction);              //changed correctpos to bestshot
            double[] gIV = correctVel.getInitialVel(ballCoorX1, ballcoorY1);
            double[] usedVelocity = correctVel.simulate(gIV[0], gIV[1],ballCoorX1, ballcoorY1);
            // System.out.println("x: "+ correctVel.coordinatesAndVelocity[0]);
            // System.out.println("y: "+ correctVel.coordinatesAndVelocity[1]);
            
            allVelocities.get(0).add(usedVelocity[0]);
            allVelocities.get(1).add(usedVelocity[1]);
            
            ballCoorX1 = correctVel.coordinatesAndVelocity[0];
            ballcoorY1 = correctVel.coordinatesAndVelocity[1];
        }

        return allVelocities;
    }




    


    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(1);
        double[] coorTX = {7};       //x-coordinates of the trees
        double[] coorTY = {7};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 10;
        double holeCoory = 10;
        double holeRadius = 1;
        double ballCoorX = 0;
        double ballCoorY = 0;
        double sFriction = 0.2;
        double kFriction = 0.1;
        double radius = 2;                                  //radius of all trees
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 0;
        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);

        SlopeField b = new SlopeField(interval,terrain);

        AI newtonSlave = new AI(terrain, interval, a, b, ballCoorX, ballCoorY, sFriction, kFriction, holeCoorx, holeCoory, holeRadius);

        List<List> endtest = newtonSlave.getAllVelocities(ballCoorX,ballCoorY);


        System.out.println("velocityX1: "+endtest.get(0).get(0));
        System.out.println("velocityY1: "+endtest.get(1).get(0));
        System.out.println("velocityX2: "+endtest.get(0).get(1));
        System.out.println("velocityY2: "+endtest.get(1).get(1));
    }
}
