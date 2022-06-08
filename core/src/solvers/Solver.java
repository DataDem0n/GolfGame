package solvers;

import java.util.function.BiFunction;

public interface Solver {
    /** INTERFACE USED FOR SOLVERS
     * @param step step size for ODE
     * @return coordinatef after the ball has stopped
     */
    public double getBestDistance();

    public double getBestFinalDistance();

    public double[] coordinatesAndVelocityUntilStop(double step, boolean update);

    /**
     *  Method to set kFriction
     * @param kFriction kinetic friction
     */
    public void setkFriction(double kFriction);

    /**
     *Method to set sFriction
     * @param sFriction kinetic friction
     */

    public void setsFriction(double sFriction);

    /**
     * Method sets X and Y coordinates
     * @param X x coordinate
     * @param Y y coordinate
     */
    public void setCoordinates(double X, double Y);

    /**
     *Method sets X and Y velocities
     * @param X x velocity
     * @param Y y velocity
     */
    public void setVelocity(double X, double Y);

    /**
     * Getter for target's radius and center coordinates
     * @param targetRXY Radius X coordinate and Y coordinate of the target stored in the array
     */
    public void setTargetRXY(double[] targetRXY);

    /** terrain setter
     * @param terrain height profile of the game
     */
    public void setTerrain(BiFunction<Double,Double,Double> terrain);

    /**
     *  X coordinate getter
     * @return X coordinate of the ball
     */
    public double getXCoord();
    /**
     *  Y coordinate getter
     * @return Y coordinate of the ball
     */
    public double getYCoord();

    public double getXVelocity();

    public double getYVelocity();
}
