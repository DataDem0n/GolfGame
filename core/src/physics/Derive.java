package physics;

import java.util.function.BiFunction;

public class Derive {


    /**
     * Method that calculates a value of derviative with respect to X at a certain point (x,y)
     * @param terrain the function of two variables describing the terrain surface
     * @param xValue the x-coordinate of the point in which the derivative is calculated
     * @param yValue the y-coordinate of the point in which the derivative is calculated
     * @return the result of plugging in (x,y) coordinates in the X-derivative
     */
    //returns result of plugging in function values to a partial derivative with respect to X
    static double derivativeXValue(BiFunction<Double, Double, Double> terrain, double xValue, double yValue) {
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
    static double derivativeYValue(BiFunction<Double, Double, Double> terrain, double xValue, double yValue) {
        BiFunction<Double,Double,Double> result = (x,y)->
                (terrain.apply(x , y+ 0.0000000001) - terrain.apply(x, y)) / 0.0000000001;

        return result.apply(xValue,yValue);
    }
}
