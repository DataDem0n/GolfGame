package obstacles;

import obstacles.Obstacles;

public class Tree implements Obstacles {

    private double coordX;
    private double coordY;
    private final double radius = 2;

    public Tree(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }
    @Override
    public double[] collide(double[] coordsAndVelocity) {
        //TODO:
        if(coordsAndVelocity[0] < coordX || coordsAndVelocity[0] <= -coordX){
            coordsAndVelocity[2] = - coordsAndVelocity[2];
        }
        if(coordsAndVelocity[1] >= coordY || coordsAndVelocity[1] <= -coordY){
            coordsAndVelocity[3] = - coordsAndVelocity[3];
        }
        return coordsAndVelocity;
    }
}
