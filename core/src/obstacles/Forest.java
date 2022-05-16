package obstacles;

import java.util.ArrayList;
import java.util.Random;

public class Forest implements Obstacles{
    ArrayList<Tree> forest = new ArrayList<>();
    public Forest(int amountOfTrees){
        Random r = new Random();
//        for (int i = 0;i<amountOfTrees;i++) {
//            double x = (r.nextDouble()*50)-25;
//            double y = (r.nextDouble()*50)-25;
//            forest.add(new Tree(x,y));
//            System.out.println(x+" "+y);
//        }
        forest.add(new Tree(15.0,15.0));
    }

    public ArrayList<Tree> getForest() {
        return forest;
    }

    @Override
    public double[] collide(double[] coordsAndVelocity, double[] tempCoords) {
        for (Tree ivern:forest){
            ivern.collide(coordsAndVelocity,tempCoords);
        }
        return coordsAndVelocity;
    }
}
