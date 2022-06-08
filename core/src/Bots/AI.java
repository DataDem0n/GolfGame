//package Bots;
//
//import com.mygdx.game.main.DataField;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.BiFunction;
//
//public class AI
//{
//    double holeCoorX, holeCoorY, holeRadius;
//    private AdjacencyField a;
//    private Simulations simulations;
//    private SlopeField b;
//    private BiFunction<Double, Double, Double> terrain;
//    private double interval;
//    private PathCalculator pc;
//
//    public AI(BiFunction<Double, Double, Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY, double sFriction, double kFriction,  double holeCoorX, double holeCoorY, double holeRadius)
//    {
//        this.terrain = terrain;
//        this.holeCoorX = holeCoorX;
//        this.holeCoorY = holeCoorY;
//        this.holeRadius = holeRadius;
//        a = new AdjacencyField(1, DataField.targetRXY[1], DataField.targetRXY[2], 0, DataField.terrain, new double[]{DataField.gameForest.getForest().get(0).getCoordX()}, new double[]{DataField.gameForest.getForest().get(0).getCoordY()}, 2, new double[]{DataField.sandPit[0]}, new double[]{DataField.sandPit[2]}, new double[]{DataField.sandPit[1]}, new double[]{DataField.sandPit[3]});
//        b= new SlopeField(1,DataField.terrain);
//        simulations = new Simulations(terrain,interval,a,b,ballCoorX,ballCoorY,sFriction,kFriction);
//    }
//
//    /**
//     * A method to check if the ball has reached its target
//     * @param ballCoorX1 the ball's current X position
//     * @param ballcoorY1 the ball's current Y position
//     * @return true if the ball has reached the target
//     */
//    public boolean endReached(double ballCoorX1, double ballcoorY1)
//    {
//        if((ballCoorX1 < holeCoorX+holeRadius && ballCoorX1 > holeCoorX-holeRadius) && (ballcoorY1 < holeCoorY+holeRadius && ballcoorY1 > holeCoorY-holeRadius))
//        {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * A getter method that receives all of the ball's velocities
//     * @param ballCoorX1 the ball's current X position
//     * @param ballcoorY1 the ball's current Y position
//     * @return all velocities that the ball has currently
//     */
//    public List<List> getAllVelocities(double ballCoorX1, double ballcoorY1)
//    {
//        ArrayList<List> allVelocities = new ArrayList<List>();
//        allVelocities.add(new ArrayList<Double>());
//        allVelocities.add(new ArrayList<Double>());
//
//
//
//        while(!endReached(ballCoorX1,ballcoorY1))
//        {
//
//            Simulations correctVel = new Simulations(terrain, interval, a, b, ballCoorX1, ballcoorY1, simulations.sFriction, simulations.kFriction);              //changed correctpos to bestshot
//            double[] gIV = correctVel.getInitialVel(ballCoorX1, ballcoorY1, 6);                                                          //max velocity used to get initialvel  = simulationsRan
//            double[] usedVelocity = correctVel.simulate(gIV[0], gIV[1],ballCoorX1, ballcoorY1, 0, 20);                                     //counter is always 0, scaler = the change in velocity at each recusive call (in percentages)
//
//
//            allVelocities.get(0).add(usedVelocity[0]);
//            allVelocities.get(1).add(usedVelocity[1]);
//
//            ballCoorX1 = correctVel.coordinatesAndVelocity[0];
//            ballcoorY1 = correctVel.coordinatesAndVelocity[1];
//
//        }
//
//        for(int i = 0; i<allVelocities.get(0).size();i++)
//        {
//            System.out.println("Shot "+(i+1));
//            System.out.println("X velocity: "+ allVelocities.get(0).get(i));
//            System.out.println("Y velocity: "+ allVelocities.get(1).get(i));
//            System.out.println();
//        }
//        return allVelocities;
//    }
//
//
//    /**
//     * A testing method with pre-determined values
//     */
//
//    public static void main(String[] args)
//    {
//        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(1);
//        double[] coorTX = {7};       //x-coordinates of the trees
//        double[] coorTY = {7};         //y-coordinates of the trees
//        double interval = 1;
//        double holeCoorx = 10;
//        double holeCoory = 10;
//        double holeRadius = 1;
//        double ballCoorX = 0;
//        double ballCoorY = 0;
//        double sFriction = 0.2;
//        double kFriction = 0.1;
//        double radius = 2;                                  //radius of all trees
//        double[] beginX = {};                    //begin x-coordinates for the sandpits
//        double[] endX = {};                       //end x-coordinates for the sandpits
//        double[] beginY = {};                    //begin y-coordinates for the sandpits
//        double[] endY = {};                       //end y-coordinates for the sandpits
//        int sandpitResentment = 0;
//        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);
//
//        SlopeField b = new SlopeField(interval,terrain);
//
//        AI newtonSlave = new AI(terrain, interval, a, b, ballCoorX, ballCoorY, sFriction, kFriction, holeCoorx, holeCoory, holeRadius);
//
//        List<List> endtest = newtonSlave.getAllVelocities(ballCoorX,ballCoorY);
//
//
//        System.out.println("velocityX1: "+endtest.get(0).get(0));
//        System.out.println("velocityY1: "+endtest.get(1).get(0));
//        System.out.println("velocityX2: "+endtest.get(0).get(1));
//        System.out.println("velocityY2: "+endtest.get(1).get(1));
//    }
//}
