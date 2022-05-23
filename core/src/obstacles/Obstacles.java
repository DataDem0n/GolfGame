package obstacles;

public interface Obstacles {
    /**INTERFACE USED FOR OBSTACLES
     * @param coordsAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param tempCoords temporary coordinates of the ball stored in the beginning of the shot
     */
    public double[] collide(double [] coordsAndVelocity,double [] tempCoords);
}
