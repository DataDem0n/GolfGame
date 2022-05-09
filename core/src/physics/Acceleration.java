package physics;

import java.util.function.BiFunction;

public class Acceleration {
    private Derive derivative = new Derive();
    public final double GRAVITY = 9.81;

    /**
     * Method that calculates acceleration in the X direction
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param terrain the function of two variables describing the terrain surface
     * @param friction the kinetic friction acting upon a ball
     * @return the acceleration in the X direction
     */
    //acceleration in the X-direction
    public double accelerationX(double [] coordinatesAndVelocity, BiFunction<Double, Double, Double> terrain, double friction){
        double acc;
        acc = -GRAVITY *derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction* GRAVITY * ((coordinatesAndVelocity[2])/(Math.sqrt((coordinatesAndVelocity[2]*coordinatesAndVelocity[2] + coordinatesAndVelocity[3]*coordinatesAndVelocity[3]))));
        return acc;
    }

    /**
     * Method that calculates acceleration in the Y direction
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param terrain the function of two variables describing the terrain surface
     * @param friction the kinetic friction acting upon a ball
     * @return the acceleration in the Y direction
     */

    //acceleration in the Y-direction
    public double accelerationY(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction){
        double acc;
        acc = -GRAVITY *derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction* GRAVITY * ((coordinatesAndVelocity[3])/(Math.sqrt((coordinatesAndVelocity[2]*coordinatesAndVelocity[2] + coordinatesAndVelocity[3]*coordinatesAndVelocity[3]))));
        return acc;
    }

    //accelerationX2 and accelerationY2 are used, when the total velocity is exactly 0
    public double accelerationX2(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;

        acc = -GRAVITY * derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction * GRAVITY * (derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])/(Math.sqrt((derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) + derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])))));
        return acc;
    }

    public double accelerationY2(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction * GRAVITY * (derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])/(Math.sqrt((derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) + derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])))));
        return acc;
    }


    public double accelerationrungeY(double coorx, double coory, double velx, double vely, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivative.derivativeYValue(terrain, coorx, coory) - friction * GRAVITY * ((vely)/(Math.sqrt((velx*velx + vely*vely))));
        return acc;
    }
    public double accelerationrungeX(double coorx, double coory, double velx, double vely, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivative.derivativeXValue(terrain, coorx, coory) - friction * GRAVITY * ((velx)/(Math.sqrt((velx*velx + vely*vely))));
        return acc;
    }

}
