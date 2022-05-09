package obstacles;

import obstacles.Obstacles;

public class Wall implements Obstacles {

    private double coordX;
    private double coordY;

    public Wall(double coordX, double coordY){
        this.coordX = coordX;
        this.coordY = coordY;
    }

    @Override
    public double[] collide(double[] coordsAndVelocity, double [] tempCoords) {
        if(coordsAndVelocity[0] >= coordX || coordsAndVelocity[0] <= -coordX){
            coordsAndVelocity[2] = - coordsAndVelocity[2];
        }
        if(coordsAndVelocity[1] >= coordY || coordsAndVelocity[1] <= -coordY){
            coordsAndVelocity[3] = - coordsAndVelocity[3];
        }
        return coordsAndVelocity;
    }
}
