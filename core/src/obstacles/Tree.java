package obstacles;


public class Tree implements Obstacles {

    private double coordX;
    private double coordY;
    private final double radius = 1.5;

    public Tree(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * @param coordsAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param tempCoords temporary coordinates of the ball stored in the beginning of the shot
     * @return returns updated array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     */
    @Override
    public double[] collide(double[] coordsAndVelocity, double [] tempCoords) {

        if(((coordsAndVelocity[0]-coordX)*(coordsAndVelocity[0]-coordX))+((coordsAndVelocity[1]-coordY)*(coordsAndVelocity[1]-coordY))<=radius*radius) {
        coordsAndVelocity[0]=tempCoords[0];
        coordsAndVelocity[1]=tempCoords[1];
        coordsAndVelocity[2]=(double) 0.000000001;
        coordsAndVelocity[3]=(double) 0.000000001;
        }
        return coordsAndVelocity;
    }

    /**
     * getter for the tree's X coordinate
     * @return X coordinate of the tree's centre
     */
    public double getCoordX() {
        return coordX;
    }

    /**
     * getter for the tree's Y coordinate
     * @return Y coordinate of the tree's centre
     */
    public double getCoordY() {
        return coordY;
    }

    /**
     * getter for the radius of the tree
     * @return Readius of the tree that is fixed
     */
    public double getRadius() {
        return radius;
    }
}
