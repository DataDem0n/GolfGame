package physics;

import java.util.function.BiFunction;

public class Acceleration2 {

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
        acc = -((GRAVITY* derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]))/(1+Math.pow(derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2)+Math.pow(derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2))) -
                    ((friction*GRAVITY)/(Math.sqrt(1+Math.pow(derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2)+Math.pow(derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2)))) *
                            (coordinatesAndVelocity[2]/(Math.sqrt(Math.pow(coordinatesAndVelocity[2],2)+Math.pow(coordinatesAndVelocity[3],2)+(Math.pow(derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*coordinatesAndVelocity[2]+derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*coordinatesAndVelocity[3],2)))));

                //-GRAVITY *derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction* GRAVITY * ((coordinatesAndVelocity[2])/(Math.sqrt((coordinatesAndVelocity[2]*coordinatesAndVelocity[2] + coordinatesAndVelocity[3]*coordinatesAndVelocity[3]))));
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
        acc =  -((GRAVITY*derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]))/(1+Math.pow(derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2)+Math.pow(derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2))) -
                ((friction*GRAVITY)/(Math.sqrt(1+Math.pow(derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2)+Math.pow(derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]),2)))) *
                        (coordinatesAndVelocity[3]/(Math.sqrt(Math.pow(coordinatesAndVelocity[2],2)+Math.pow(coordinatesAndVelocity[3],2)+(Math.pow(derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*coordinatesAndVelocity[2]+derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*coordinatesAndVelocity[3],2)))));
        return acc;
    }


    /**
     * Method that calculates acceleration in the Y direction
     * @param coorx x coordinate of the ball
     * @param coory y coordinate of the ball
     * @param velx  velocity of the ball in x direction
     * @param vely velocity of the ball in y direction
     * @param terrain the function of two variables describing the terrain surface
     * @param friction the kinetic friction acting upon a ball
     * @return the acceleration in the Y direction
     */

    public double accelerationrungeY(double coorx, double coory, double velx, double vely, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc =  -((GRAVITY*derivative.derivativeYValue(terrain, coorx, coory))/(1+Math.pow(derivative.derivativeXValue(terrain,coorx, coory),2)+Math.pow(derivative.derivativeYValue(terrain, coorx, coory),2))) -
                ((friction*GRAVITY)/(Math.sqrt(1+Math.pow(derivative.derivativeXValue(terrain, coorx, coory),2)+Math.pow(derivative.derivativeYValue(terrain, coorx, coory),2)))) *
                        (vely/(Math.sqrt(Math.pow(velx,2)+Math.pow(vely,2)+(Math.pow(derivative.derivativeXValue(terrain, coorx, coory)*velx+derivative.derivativeYValue(terrain, coorx, coory)*vely,2)))));
        return acc;
    }

    /**
     * Method that calculates acceleration in the X direction
     * @param coorx x coordinate of the ball
     * @param coory y coordinate of the ball
     * @param velx  velocity of the ball in x direction
     * @param vely velocity of the ball in y direction
     * @param terrain the function of two variables describing the terrain surface
     * @param friction the kinetic friction acting upon a ball
     * @return the acceleration in the X direction
     */
    public double accelerationrungeX(double coorx, double coory, double velx, double vely, BiFunction <Double, Double, Double> terrain, double friction)
    {

        double acc;
        acc =  -((GRAVITY*derivative.derivativeYValue(terrain, coorx, coory))/(1+Math.pow(derivative.derivativeXValue(terrain, coorx, coory),2)+Math.pow(derivative.derivativeYValue(terrain,coorx, coory),2))) -
                ((friction*GRAVITY)/(Math.sqrt(1+Math.pow(derivative.derivativeXValue(terrain, coorx, coory),2)+Math.pow(derivative.derivativeYValue(terrain, coorx, coory),2)))) *
                        (vely/(Math.sqrt(Math.pow(velx,2)+Math.pow(vely,2)+(Math.pow(derivative.derivativeXValue(terrain, coorx, coory)*velx+derivative.derivativeYValue(terrain, coorx, coory)*vely,2)))));
        return acc;


//        double acc;
//        acc = -((GRAVITY*derivative.derivativeYValue(terrain, coorx, coory))/(1+Math.pow(derivative.derivativeXValue(terrain, coorx, coory),2)+Math.pow(derivative.derivativeYValue(terrain, coorx, coory),2))) -
//                ((friction*GRAVITY)/(Math.sqrt(1+Math.pow(derivative.derivativeXValue(terrain, coorx, coory),2)+Math.pow(derivative.derivativeYValue(terrain, coorx, coory),2)))) *
//                        (vely/(Math.sqrt(Math.pow(velx,2)+Math.pow(vely,2)+(Math.pow(derivative.derivativeXValue(terrain, coorx, coory)*vely+derivative.derivativeYValue(terrain, coorx, coory)*vely,2)))));
//        return acc;
    }

    public double accelerationX2(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction * GRAVITY * (derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])/(Math.sqrt((derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) + derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])))));
        return acc;
    }

    /**
     * Method that calculates acceleration in the Y direction
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param terrain the function of two variables describing the terrain surface
     * @param friction the kinetic friction acting upon a ball
     * @return the acceleration in the Y direction
     */
    //accelerationY2 and accelerationY2 are used, when the total velocity is exactly 0
    public double accelerationY2(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction * GRAVITY * (derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])/(Math.sqrt((derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) + derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])*derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1])))));
        return acc;
    }
}
