package obstacles;


public class Wall implements Obstacles {

    private double coordX;
    private double coordY;

    public Wall(double coordX, double coordY){
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * Method that checks if the tree collide with a wall
     * @param coordsAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param tempCoords temporary coordinates of the ball stored in the beginning of the shot
     * @return returns updated array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     */
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
