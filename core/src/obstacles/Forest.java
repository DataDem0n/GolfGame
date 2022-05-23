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

    /**
     * Method that checks if the ball has collided with any trees in the forest
     * @param coordsAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param tempCoords temporary coordinates of the ball stored in the beginning of the shot
     * @return returns updated array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     */
    @Override
    public double[] collide(double[] coordsAndVelocity, double[] tempCoords) {
        for (Tree t: forest){

            if (((coordsAndVelocity[0] - t.getCoordX()) * (coordsAndVelocity[0] - t.getCoordX())) + ((coordsAndVelocity[1] - t.getCoordY()) * (coordsAndVelocity[1] - t.getCoordY())) <= t.getRadius() * t.getRadius()) {
                coordsAndVelocity[0] = tempCoords[0];
                coordsAndVelocity[1] = tempCoords[1];
                coordsAndVelocity[2] = (double) 0.000000001;
                coordsAndVelocity[3] = (double) 0.000000001;
            }
        }
        return coordsAndVelocity;
    }
}
