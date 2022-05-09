import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class AI extends Simulations
{
    double holeCoorX, holeCoorY, holeRadius;
    public AI(BiFunction<Double, Double, Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, PathCalculator path, double ballCoorX, double ballCoorY,Plot correctPos, int simulationsRan, double sFriction, double kFriction,  double holeCoorX, double holeCoorY, double holeRadius) 
    {
        super(terrain, interval, adjacency, slope, path, ballCoorX, ballCoorY, correctPos, simulationsRan, sFriction, kFriction);
        this.holeCoorX = holeCoorX;
        this.holeCoorY = holeCoorY;
        this.holeRadius = holeRadius;
    }

    public boolean endReached()
    {
        if((ballCoorX < holeCoorX+holeRadius && ballCoorX > holeCoorX+holeRadius-holeRadius) && (ballCoorY < holeCoorY+holeRadius && ballCoorY > holeCoorY+holeRadius-holeRadius))
        {
            return true;
        }
        return false;
    }
    

    public List<List> getAllVelocities()
    {
        ArrayList<List> allVelocities = new ArrayList<List>();


         
        while(!endReached())
        {
            PathCalculator path = new PathCalculator(adjacency, slope, ballCoorX, ballCoorY);
            ArrayList<List> foundPath = new ArrayList<List>();
            foundPath.add(path.pathCalculator(path.getBallPosition()[0], path.getBallPosition()[1]).get(0));
            foundPath.add(path.pathCalculator(path.getBallPosition()[0], path.getBallPosition()[1]).get(1));
            
            
            
            Plot bestShot = new Plot(terrain, interval, adjacency, slope, ballCoorX, ballCoorY);
            double[] shot = new double[2];
            shot[0] = bestShot.getCorrectShot(foundPath.get(0), foundPath.get(1))[0];
            shot[1] = bestShot.getCorrectShot(foundPath.get(0), foundPath.get(1))[1];

            
            
            
            Simulations correctVel = new Simulations(terrain, interval, adjacency, slope, path, ballCoorX, ballCoorY, bestShot, simulationsRan, sFriction, kFriction);              //changed correctpos to bestshot
            double[] usedVelocity = new double[2];
            usedVelocity[0] = correctVel.simulate(correctVel.getInitialVel()[0], correctVel.getInitialVel()[1])[0];
            usedVelocity[1] = correctVel.simulate(correctVel.getInitialVel()[0], correctVel.getInitialVel()[1])[1];
            


            
            allVelocities.get(0).add(usedVelocity[0]);
            allVelocities.get(1).add(usedVelocity[1]);
            
            
            
            ballCoorX = shot[0];
            ballCoorY = shot[1];
        }

        return allVelocities;
    }




    
}
