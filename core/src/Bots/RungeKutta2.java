package Bots;

import physics.Acceleration;
import physics.HasBallStopped;

import java.util.function.BiFunction;
//TEMPORARY ODE SOLVER HANDLING ONLY FOR PHASE 2

public class RungeKutta2 extends Physics{

    private Acceleration acceleration = new Acceleration();
    private HasBallStopped hasBallStopped = new HasBallStopped();

    private BiFunction<Double, Double, Double> terrain;
    private double kFriction;
    private double sFriction;
    double stepsize;
    private double[] coordinatesAndVelocity;


    public RungeKutta2(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double stepsize){
        this.terrain = terrain;
        this.kFriction = kFriction;
        this.sFriction = sFriction;
        this.stepsize = stepsize;
        this.coordinatesAndVelocity = coordinatesAndVelocity;

  
    }


    public double[] coordinatesAndVelocityUntilStop(double step) {

        double tempvelx1;
        double tempvely1;
        double tempvelx2;
        double tempvely2;
        double tempvelx3;
        double tempvely3;
        double tempcoorx1;
        double tempcoory1;


        while (!hasBallStopped.hasBallStopped(coordinatesAndVelocity, sFriction, terrain, stepsize)) {


            tempvelx1 = acceleration.accelerationrungeX(coordinatesAndVelocity[0], coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, kFriction) * step;        //getting x-velocity using midpoint
            tempvelx2 = coordinatesAndVelocity[2] + 0.5 * tempvelx1;
            tempcoorx1 = coordinatesAndVelocity[0] + tempvelx2 * step * 0.5;
            tempvelx3 = acceleration.accelerationrungeX(tempcoorx1, coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, kFriction) * step;

            tempvely1 = acceleration.accelerationrungeY(coordinatesAndVelocity[0], coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, kFriction) * step;        //getting y-velocity using midpoint
            tempvely2 = coordinatesAndVelocity[3] + 0.5 * tempvely1;
            tempcoory1 = coordinatesAndVelocity[1] + tempvely2 * step * 0.5;
            tempvely3 = acceleration.accelerationrungeY(coordinatesAndVelocity[0], tempcoory1, coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, kFriction) * step;


            coordinatesAndVelocity[2] += 0.5 * (tempvelx1 + tempvelx3);


            coordinatesAndVelocity[3] += 0.5 * (tempvely1 + tempvely3);

            coordinatesAndVelocity[0] += coordinatesAndVelocity[2] * step;
            coordinatesAndVelocity[1] += coordinatesAndVelocity[3] * step;

        }
        return coordinatesAndVelocity;
    }

    

    

 
}
