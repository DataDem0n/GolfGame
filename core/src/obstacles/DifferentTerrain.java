package obstacles;



public interface DifferentTerrain {
    /**INTERFACE USED FOR OBSTACLES
     *Method used to handle the collision with obstacles
     * @param coordsAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     */
    public void change(double [] coordsAndVelocity);
}
