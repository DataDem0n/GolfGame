package obstacles;

import java.util.ArrayList;
import java.util.Random;

public class Forest implements Obstacles{
    ArrayList<Tree> forest = new ArrayList<>();
    public Forest(int amountOfTrees){
        Random r = new Random();
        for (int i = 0;i<amountOfTrees;i++) {
            double x = (r.nextDouble()*50)-25;
            double y = (r.nextDouble()*50)-25;
            forest.add(new Tree(x,y));
        }
    }

    public ArrayList<Tree> getForest() {
        return forest;
    }

    @Override
    public double[] collide(double[] coordsAndVelocity) {
        for (Tree ivern:forest){
            if (coordsAndVelocity[0] < ivern.getCoordX() || coordsAndVelocity[0] <= -ivern.getCoordX()) {
                coordsAndVelocity[2] = -coordsAndVelocity[2];
            }
            if (coordsAndVelocity[1] >= ivern.getCoordY() || coordsAndVelocity[1] <= -ivern.getCoordY()) {
                coordsAndVelocity[3] = -coordsAndVelocity[3];
            }
        }
        return coordsAndVelocity;
    }
}
