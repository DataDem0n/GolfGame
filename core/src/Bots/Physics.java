package Bots;

import java.util.function.BiFunction;

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
    public boolean hasBallStopped(double [] coordinatesAndVelocity, double staticFriction, BiFunction <Double, Double, Double> terrain, double step, double kFriction)
   {
       double scalingVelX = 0.1;   //step * Math.abs(accelerationX(coordinatesAndVelocity, terrain, DataField.kFriction))
       double scalingVelY = 0.1;  //step * Math.abs(accelerationY(coordinatesAndVelocity, terrain,  DataField.kFriction));

       if(Math.abs(coordinatesAndVelocity[2]) < scalingVelX &&
               Math.abs(coordinatesAndVelocity[3]) < scalingVelY
               && (derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) == 0.0)
               && (derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]) == 0.0))
       {
           return true;
       }

       else if(Math.abs(coordinatesAndVelocity[2]) < scalingVelX && Math.abs(coordinatesAndVelocity[3]) < scalingVelY)
       {
           return staticFriction>Math.sqrt((Math.pow(derivativeXValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2))+       
                   Math.pow(derivativeYValue(terrain, coordinatesAndVelocity[0], coordinatesAndVelocity[1]), 2));
       }
       return false;
   }



    public static void main(String[] args) {
        double[] coords = {0,0};
        double[] velocity = {0,0};
        double staticFriction = 0.9;
//      BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5 * ( Math.sin(x+y) / 7  + 0.9 + y ) );
        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.pow(Math.E, -((x*x+y*y)/40)));
        System.out.println(derivativeYValue(terrain,1,1));
//      BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.sin(x + y));
        Physics p = new Physics();
//      System.out.println(p.hasBallStopped(coords, velocity[0], velocity[1], staticFriction, terrain));
//      System.out.println(p.derivativeXValue(terrain, 1,1));
//      System.out.println(p.derivativeYValue(terrain, 1,1));
//      System.out.println(p.accelerationX(coords,1,1, terrain, 0.15));
    }
}