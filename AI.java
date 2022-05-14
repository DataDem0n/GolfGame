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

    public boolean endReached()
    {
        if((ballCoorX < holeCoorX+holeRadius && ballCoorX > holeCoorX-holeRadius) && (ballCoorY < holeCoorY+holeRadius && ballCoorY > holeCoorY-holeRadius))
        {
            return true;
        }
        return false;
    }
    

    public List<List> getAllVelocities()
    {
        ArrayList<List> allVelocities = new ArrayList<List>();
        allVelocities.add(new ArrayList<Double>());
        allVelocities.add(new ArrayList<Double>());


         
        while(!endReached())
        {            
            Simulations correctVel = new Simulations(terrain, interval, adjacency, slope, ballCoorX, ballCoorY, sFriction, kFriction);              //changed correctpos to bestshot
            double[] gIV = correctVel.getInitialVel();
            double[] usedVelocity = correctVel.simulate(gIV[0], gIV[1]);
            
            allVelocities.get(0).add(usedVelocity[0]);
            allVelocities.get(1).add(usedVelocity[1]);
            
            
            
            ballCoorX = coordinatesAndVelocity[0];
            ballCoorY = coordinatesAndVelocity[1];
        }

        return allVelocities;
    }




    


    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(1);
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 13;
        double holeCoory = 13;
        double holeRadius = 2;
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
        AI newtonSlave = new AI(terrain, interval, a, b, ballCoorX, ballCoorY, sFriction, kFriction, holeCoorx, holeCoory, holeRadius);

        List<List> endtest = newtonSlave.getAllVelocities();


        System.out.println(endtest.get(0).get(1));
    }
}
