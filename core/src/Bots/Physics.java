package Bots;

import java.util.function.BiFunction;
//TEMPORARY PHYSICS HANDLING ONLY FOR PHASE 2
public class Physics{

    public final double GRAVITY = 9.81;

    //acceleration in the X-direction
    public double accelerationX(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction){
        double acc;
        acc = -GRAVITY *derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction* GRAVITY * ((coordinatesAndVelocity[2])/(Math.sqrt((coordinatesAndVelocity[2]*coordinatesAndVelocity[2] + coordinatesAndVelocity[3]*coordinatesAndVelocity[3]))));
        return acc;
    }
    public double accelerationrungeX(double coorx, double coory, double velx, double vely, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivativeXValue(terrain, coorx, coory) - friction * GRAVITY * ((velx)/(Math.sqrt((velx*velx + vely*vely))));
        return acc;
    }

    //acceleration in the Y-direction
    public double accelerationY(double [] coordinatesAndVelocity, BiFunction <Double, Double, Double> terrain, double friction){
        double acc;
        acc = -GRAVITY *derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) - friction* GRAVITY * ((coordinatesAndVelocity[3])/(Math.sqrt((coordinatesAndVelocity[2]*coordinatesAndVelocity[2] + coordinatesAndVelocity[3]*coordinatesAndVelocity[3]))));
        return acc;
    }
    public double accelerationrungeY(double coorx, double coory, double velx, double vely, BiFunction <Double, Double, Double> terrain, double friction)
    {
        double acc;
        acc = -GRAVITY * derivativeYValue(terrain, coorx, coory) - friction * GRAVITY * ((vely)/(Math.sqrt((velx*velx + vely*vely))));
        return acc;
    }
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

    //returns result of plugging in function values to a partial derivative with respect to X
    static double derivativeXValue(BiFunction <Double, Double, Double> terrain, double xValue, double yValue) {
        BiFunction<Double,Double,Double> result = (x,y)->
                (terrain.apply(x + 0.0000000001, y) - terrain.apply(x, y)) / 0.0000000001;
        return result.apply(xValue,yValue);
    }

    //returns result of plugging in function values to a partial derivative with respect to Y
    static double derivativeYValue(BiFunction <Double, Double, Double> terrain, double xValue, double yValue) {
        BiFunction<Double,Double,Double> result = (x,y)->
                (terrain.apply(x , y+ 0.0000000001) - terrain.apply(x, y)) / 0.0000000001;

        return result.apply(xValue,yValue);
    }

    //position of the ball
    public double [] newCoordinates(double velocityX, double velocityY, double [] coordinates, double timeInterval){
        double [] newCoordinates = new double[2];
        newCoordinates[0] = coordinates[0] + velocityX*timeInterval;
        newCoordinates[1] = coordinates[1] + velocityY*timeInterval;
        return newCoordinates;
    }


    //stopping condition (on the slope VS not on the slope)
    public boolean hasBallStopped(double [] coordinatesAndVelocity, double staticFriction, BiFunction<Double, Double, Double> terrain, double step, double kFriction) {

        double scalingSlope = 0.0008;


        if (Math.abs(coordinatesAndVelocity[2]) < step * Math.abs(accelerationX(coordinatesAndVelocity, terrain, kFriction)) &&
                Math.abs(coordinatesAndVelocity[3]) < step * Math.abs(accelerationY(coordinatesAndVelocity, terrain, kFriction))
                && (derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < Math.abs(scalingSlope))
                && (derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < Math.abs(scalingSlope))) {
            return true;
        } else {
            return Math.abs(coordinatesAndVelocity[2]) < step * Math.abs(accelerationX(coordinatesAndVelocity, terrain, kFriction))
                    && Math.abs(coordinatesAndVelocity[3]) < step * Math.abs(accelerationY(coordinatesAndVelocity, terrain, kFriction))
                    && ((derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) >= Math.abs(scalingSlope)
                    || derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) >= Math.abs(scalingSlope)))
                    && staticFriction > Math.sqrt((Math.pow(derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2))
                    + Math.pow(derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2));
        }
    }
}