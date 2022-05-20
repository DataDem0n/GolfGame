package physics;

import com.mygdx.game.main.DataField;

import java.util.function.BiFunction;

public class HasBallStopped {
    Derive derivative = new Derive();
    Acceleration acceleration = new Acceleration();
    /**
     * A method that checks whether a ball has stopped due to the friction and resulting velocity
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param staticFriction the static friction acting upon a ball
     * @param terrain the function of two variables describing the terrain surface
     * @return true if the ball has stopped, false if not
     */

    public boolean hasBallStopped(double [] coordinatesAndVelocity, double staticFriction, BiFunction<Double, Double, Double> terrain, double step) {

        double scalingSlope = 0.0008;

        if (Math.abs(coordinatesAndVelocity[2]) <= step * Math.abs(acceleration.accelerationX(coordinatesAndVelocity, terrain, DataField.kFriction)) &&
                Math.abs(coordinatesAndVelocity[3]) <= step * Math.abs(acceleration.accelerationY(coordinatesAndVelocity, terrain, DataField.kFriction))
                && (derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < Math.abs(scalingSlope))
                && (derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < Math.abs(scalingSlope))) {
            return true;
        } else {
            return Math.abs(coordinatesAndVelocity[2]) <= step * Math.abs(acceleration.accelerationX(coordinatesAndVelocity, terrain, DataField.kFriction))
                    && Math.abs(coordinatesAndVelocity[3]) <= step * Math.abs(acceleration.accelerationY(coordinatesAndVelocity, terrain, DataField.kFriction))
                    && ((derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) >= Math.abs(scalingSlope)
                    || derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) >= Math.abs(scalingSlope)))
                    && staticFriction > Math.sqrt((Math.pow(derivative.derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2))
                    + Math.pow(derivative.derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2));
        }
    }

//    public boolean hasBallStopped(double [] coordinatesAndVelocity, double staticFriction, BiFunction<Double, Double, Double> terrain, double step, double kFriction) {
//
//        double scalingSlope = 0.0008;
//
//
//        if (Math.abs(coordinatesAndVelocity[2]) < step * Math.abs(accelerationX(coordinatesAndVelocity, terrain, kFriction)) &&
//                Math.abs(coordinatesAndVelocity[3]) < step * Math.abs(accelerationY(coordinatesAndVelocity, terrain, kFriction))
//                && (derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < Math.abs(scalingSlope))
//                && (derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < Math.abs(scalingSlope))) {
//            return true;
//        } else {
//            return Math.abs(coordinatesAndVelocity[2]) < step * Math.abs(accelerationX(coordinatesAndVelocity, terrain, kFriction))
//                    && Math.abs(coordinatesAndVelocity[3]) < step * Math.abs(accelerationY(coordinatesAndVelocity, terrain, kFriction))
//                    && ((derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) >= Math.abs(scalingSlope)
//                    || derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) >= Math.abs(scalingSlope)))
//                    && staticFriction > Math.sqrt((Math.pow(derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2))
//                    + Math.pow(derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2));
//        }
//    }




}
