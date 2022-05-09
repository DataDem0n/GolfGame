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
    public double[] collide(double[] coordsAndVelocity, double [] tempCoords) {

        if(((coordsAndVelocity[0]-coordX)*(coordsAndVelocity[0]-coordX))+((coordsAndVelocity[1]-coordY)*(coordsAndVelocity[1]-coordY))<=radius*radius) {
        coordsAndVelocity[0]=tempCoords[0];
        coordsAndVelocity[1]=tempCoords[1];
        //TODO: we think setting this = 0 causes error when dividing.
        coordsAndVelocity[2]=(double) 0.000000001;
        coordsAndVelocity[3]=(double) 0.000000001;

        }
        return coordsAndVelocity;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public double getRadius() {
        return radius;
    }
}
