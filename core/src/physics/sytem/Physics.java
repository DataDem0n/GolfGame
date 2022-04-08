package physics.sytem;

import java.util.function.BiFunction;

public class Physics extends Thread{

    public final double GRAVITY = 9.81;

    /**
     * Method that calculates acceleration in the X direction
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param terrain the function of two variables describing the terrain surface
     * @param friction the kinetic friction acting upon a ball
     * @return the acceleration in the X direction
     */
    //acceleration in the X-direction
    public double accelerationX(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction){
        double acc;
        acc = -GRAVITY *derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction* GRAVITY * ((coordinatesAndVelocity[2])/(Math.sqrt((coordinatesAndVelocity[2]*coordinatesAndVelocity[2] + coordinatesAndVelocity[3]*coordinatesAndVelocity[3]))));
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
        acc = -GRAVITY *derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction* GRAVITY * ((coordinatesAndVelocity[3])/(Math.sqrt((coordinatesAndVelocity[2]*coordinatesAndVelocity[2] + coordinatesAndVelocity[3]*coordinatesAndVelocity[3]))));
        return acc;
    }
    //accelerationX2 and accelerationY2 are used, when the total velocity is exactly 0
    public double accelerationX2(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;

        acc = -GRAVITY * derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction * GRAVITY * (derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])/(Math.sqrt((derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) + derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])))));
        return acc;
    }

    public double accelerationY2(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction * GRAVITY * (derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])/(Math.sqrt((derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) + derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])))));
        return acc;
    }

    /**
     * Method that calculates a value of derviative with respect to X at a certain point (x,y)
     * @param terrain the function of two variables describing the terrain surface
     * @param xValue the x-coordinate of the point in which the derivative is calculated
     * @param yValue the y-coordinate of the point in which the derivative is calculated
     * @return the result of plugging in (x,y) coordinates in the X-derivative
     */
    //returns result of plugging in function values to a partial derivative with respect to X
    private static double derivativeXValue(BiFunction <Double, Double, Double> terrain, double xValue, double yValue) {
        BiFunction<Double,Double,Double> result = (x,y)->
                (terrain.apply(x + 0.0000000001, y) - terrain.apply(x, y)) / 0.0000000001;
        return result.apply(xValue,yValue);
    }

    /**
     * Method that calculates a value of derviative with respect to Y at a certain point (x,y)
     * @param terrain the function of two variables describing the terrain surface
     * @param xValue the x-coordinate of the point in which the derivative is calculated
     * @param yValue the y-coordinate of the point in which the derivative is calculated
     * @return the result of plugging in (x,y) coordinates in the Y-derivative
     */
    //returns result of plugging in function values to a partial derivative with respect to Y
    private static double derivativeYValue(BiFunction <Double, Double, Double> terrain, double xValue, double yValue) {
        BiFunction<Double,Double,Double> result = (x,y)->
                (terrain.apply(x , y+ 0.0000000001) - terrain.apply(x, y)) / 0.0000000001;

        return result.apply(xValue,yValue);
    }

    /**
     * Method that checks whether the MAX_SPEED is exceeded, if so then it prevserves the direction of the resultant vector but shortens it to MAX_LENGTH
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @return an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions with shortened vectors
     */

    public double[] maxSpeedReached(double [] coordinatesAndVelocity)
    {
        double unitVectorX = 0;
        double unitVectorY = 0;
        if(Math.sqrt(Math.pow(coordinatesAndVelocity[2], 2) + Math.pow(coordinatesAndVelocity[3], 2)) > 5){
            unitVectorX = (coordinatesAndVelocity[2]/(Math.sqrt(Math.pow(coordinatesAndVelocity[2], 2) + Math.pow(coordinatesAndVelocity[3], 2))));
            unitVectorY = (coordinatesAndVelocity[3]/(Math.sqrt(Math.pow(coordinatesAndVelocity[2], 2) + Math.pow(coordinatesAndVelocity[3], 2))));
            coordinatesAndVelocity[2] = 5 * unitVectorX;
            coordinatesAndVelocity[3] = 5 * unitVectorY;
        }
        return coordinatesAndVelocity;
    }

    /**
     * A method that checks whether a ball has stopped due to the friction and resulting velocity
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param staticFriction the static friction acting upon a ball
     * @param terrain the function of two variables describing the terrain surface
     * @return true if the ball has stopped, false if not
     */
    public boolean hasBallStopped(double [] coordinatesAndVelocity, double staticFriction, BiFunction <Double, Double, Double> terrain, double step)
    {
        double scalingSlope = 0.0008;

        if(Math.abs(coordinatesAndVelocity[2]) < step &&
                Math.abs(coordinatesAndVelocity[3]) < step
                && (derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])< Math.abs(scalingSlope))
                && (derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])< Math.abs(scalingSlope)))
        { return true; }

        else if(Math.abs(coordinatesAndVelocity[2]) < step
                && Math.abs(coordinatesAndVelocity[3]) < step
                && ((derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])>=Math.abs(scalingSlope)
                || derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])>=Math.abs(scalingSlope))))
        {
            if(staticFriction>Math.sqrt((Math.pow(derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2))+
                    Math.pow(derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2)))
            { return true; }
            else
            { return false; }
        }
        return false;
    }

    public static void main(String[] args) {
        double[] coords = {0,0};
        double[] velocityXY = {0,0,8,6};
        double staticFriction = 0.9;
//      BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5 * ( Math.sin(x+y) / 7  + 0.9 + y ) );
        //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.pow(Math.E, -((x*x+y*y)/40)));
        //System.out.println(derivativeYValue(terrain,1,1));
//      BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.sin(x + y));
        Physics p = new Physics();
        double velx = p.maxSpeedReached(velocityXY)[2];
        double vely = p.maxSpeedReached(velocityXY)[3];
        System.out.println(velx + " " + vely);
//      System.out.println(p.hasBallStopped(coords, velocity[0], velocity[1], staticFriction, terrain));
//      System.out.println(p.derivativeXValue(terrain, 1,1));
//      System.out.println(p.derivativeYValue(terrain, 1,1));
//      System.out.println(p.accelerationX(coords,1,1, terrain, 0.15));
    }


}