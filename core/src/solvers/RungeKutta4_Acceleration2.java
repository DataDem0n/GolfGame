package solvers;

import obstacles.SandPits;
import obstacles.Tree;
import obstacles.Wall;
import obstacles.Water;
import physics.Acceleration;
import physics.Acceleration2;
import physics.HasBallStopped;
import physics.MaxSpeed;
import com.mygdx.game.main.DataField;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.function.BiFunction;

public class RungeKutta4_Acceleration2 implements Solver {

    private MaxSpeed maxSpeed = new MaxSpeed();
    private Acceleration2 acceleration = new Acceleration2();
    private HasBallStopped hasBallStopped = new HasBallStopped();
    private int fps = 120;
    private BiFunction<Double, Double, Double> terrain;
    double[] targetRXY;
    public double[] tempCoordinates = new double [2];
    public double[] coordinatesAndVelocity;
    private Wall wall = new Wall(25,25);
    private SandPits sandPits = new SandPits(DataField.sandPit, 0.7, 0.8);
    private Water water = new Water();
    private double bestDistance =100;
    private double bestFinalDistance = 100;
    private boolean didGoThroughWater;

    // Overview of what is stored in the coordinatedAndVelocity array:
    // [0] - coordinateX
    // [1] - coordinateY
    // [2] - velocityX
    // [3] - velocityY

    /**
     * Constructor for the EulerSolver class initializing instance variables
     * @param terrain the function of two variables describing the terrain surface
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param kFriction the kinetic friction acting upon a ball
     * @param sFriction the static friction acting upon a ball
     * @param targetRXY an array that represents the target's radius on first position, target's X-coordinate on second and target's Y-coordinate
     */
    public RungeKutta4_Acceleration2(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double[] targetRXY){
        this.terrain = terrain;
        this.coordinatesAndVelocity = coordinatesAndVelocity;
        DataField.kFriction = kFriction;
        DataField.sFriction = sFriction;
        this.targetRXY = targetRXY;
        this.didGoThroughWater = false;
    }

    /**
     * Method based on the Euler Method for solving differential equations that calculates the next velocities in the X-direction and Y-direction, after a certain step size
     * and calculates the next coordinates of the ball based on the resulting velocities, so that it tracks ball's movements
     * @param step a step size in the Euler's method
     * @return an array with final coordinates and velocities of a ball that has stopped after a shot
     */
    @Override
    public double[] coordinatesAndVelocityUntilStop(double step, boolean update)  {
        tempCoordinates[0] = coordinatesAndVelocity[0];
        tempCoordinates[1] = coordinatesAndVelocity[1];

        if(!DataField.aiRunning)
            coordinatesAndVelocity = maxSpeed.maxSpeedReached(coordinatesAndVelocity);

        while(!hasBallStopped.hasBallStopped(coordinatesAndVelocity, DataField.sFriction, terrain, step)) {

            if(coordinatesAndVelocity[2] == 0 && coordinatesAndVelocity[3] == 0)
            {
                coordinatesAndVelocity[2] = coordinatesAndVelocity[2] + (step * acceleration.accelerationX2(coordinatesAndVelocity, terrain, DataField.kFriction)); //X-Velocity = xVelocity + step*acc
                coordinatesAndVelocity[3] = coordinatesAndVelocity[3] + (step * acceleration.accelerationY2(coordinatesAndVelocity, terrain, DataField.kFriction)); //Y-Velocity = YVelocity + step*acc
            }
            else
            {
                double [] k1_coords = {coordinatesAndVelocity[0], coordinatesAndVelocity[1]};
                double [] k1_velocities = {coordinatesAndVelocity[2], coordinatesAndVelocity[3]};
                double[] k1_acceleration = {acceleration.accelerationrungeX(k1_coords[0],k1_coords[1],k1_velocities[0],k1_velocities[1] , terrain, DataField.kFriction),
                        acceleration.accelerationrungeY(k1_coords[0],k1_coords[1],k1_velocities[0],k1_velocities[1] , terrain, DataField.kFriction)};

                double[] k2_coords ={(k1_coords[0] + (1.0/2.0) * step * k1_velocities[0]), (k1_coords[1] + (1.0/2.0) * step * k1_velocities[1]) };
                double[] k2_velocities = {(k1_velocities[0] + (1.0/2.0) * step * k1_acceleration[0]),k1_velocities[1] + (1.0/2.0) * step * k1_acceleration[1]};
                double[] k2_acceleration = {acceleration.accelerationrungeX(k2_coords[0],k2_coords[1],k2_velocities[0],k2_velocities[1] , terrain, DataField.kFriction),
                        acceleration.accelerationrungeY(k2_coords[0],k2_coords[1],k2_velocities[0],k2_velocities[1] , terrain, DataField.kFriction)};

                double[] k3_coords = new double[]{(k1_coords[0] +(1.0/2.0) * step * k2_velocities[0]) ,k1_coords[1] + (1.0/2.0) * step * k2_velocities[1] } ;
                double[] k3_velocities= {k1_velocities[0] + (1.0/2.0) * step * k2_acceleration[0] ,k1_velocities[1] + (1.0/2.0) * step * k2_acceleration[1]};
                double[] k3_acceleration = {acceleration.accelerationrungeX(k3_coords[0],k3_coords[1],k3_velocities[0],k3_velocities[1] , terrain, DataField.kFriction),
                        acceleration.accelerationrungeY(k3_coords[0],k3_coords[1],k3_velocities[0],k3_velocities[1] , terrain, DataField.kFriction)};

                double[] k4_coords = new double[]{k1_coords[0] + (1.0/2.0) * step * k3_coords[0],k1_coords[1] + (1.0/2.0) * step* k3_coords[1] };
                double[] k4_velocities = new double[]{k1_velocities[0] +(1.0/2.0) * step * k3_acceleration[0] ,k1_velocities[1] + (1.0/2.0) * step * k3_acceleration[1]};
                double[] k4_acceleration = {acceleration.accelerationrungeX(k4_coords[0],k4_coords[1],k4_velocities[0],k4_velocities[1] , terrain, DataField.kFriction),
                        acceleration.accelerationrungeY(k4_coords[0],k4_coords[1],k4_velocities[0],k4_velocities[1] , terrain, DataField.kFriction)};;


                double x_coor = coordinatesAndVelocity[0] + step*(1.0/6.0)*(k1_velocities[0] + 2.0*k2_velocities[0] + 2.0*k3_velocities[0] + k4_velocities[0]);
                double y_coor = coordinatesAndVelocity[1] + step*(1.0/6.0)*(k1_velocities[1] + 2.0*k2_velocities[1] + 2.0*k3_velocities[1] + k4_velocities[1]);
                double x_vel = coordinatesAndVelocity[2] + step*(1.0/6.0)*(k1_acceleration[0] + 2.0*k2_acceleration[0] + 2.0*k3_acceleration[0] + k4_acceleration[0]);
                double y_vel = coordinatesAndVelocity[3] + step*(1.0/6.0)*(k1_acceleration[1] + 2.0*k2_acceleration[1] + 2.0*k3_acceleration[1] +k4_acceleration[1]);

                coordinatesAndVelocity = new double[]{x_coor, y_coor,x_vel,y_vel};
            }
            if(DataField.terrain.apply(coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < 0){
                didGoThroughWater = true;
            }

            if(update) {
                DataField.x = coordinatesAndVelocity[0];
                DataField.y = coordinatesAndVelocity[1];
            }

            double tempDist = Math.sqrt((Math.pow( (DataField.targetRXY[1]-coordinatesAndVelocity[0]) , 2) + ( Math.pow( (DataField.targetRXY[2]- coordinatesAndVelocity[1]), 2))));
            if (tempDist < bestDistance){
                bestDistance=tempDist;
            }


            if(update) water.collide(coordinatesAndVelocity, tempCoordinates);
            wall.collide(coordinatesAndVelocity, new double[0]);
            sandPits.change(coordinatesAndVelocity);
        }

        double FINALDist = Math.sqrt((Math.pow( DataField.targetRXY[1]-coordinatesAndVelocity[0] , 2) + ( Math.pow( DataField.targetRXY[2]- coordinatesAndVelocity[1], 2))));
        bestFinalDistance = FINALDist;

//        System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);
          System.out.println("velx: " + coordinatesAndVelocity[2]);
          System.out.println("vely: " + coordinatesAndVelocity[3]);

        return coordinatesAndVelocity;
    }


    //0.05-0.1
    //Static friction (grass) µS 0.1-0.2
    /**
     * A setter for kinetic friction
     * @param kFriction kinetic friction
     */
    @Override
    public void setkFriction(double kFriction){
        DataField.kFriction = kFriction;
    };

    /**
     * A setter for static friction
     * @param sFriction static friction
     */
    @Override
    public void setsFriction(double sFriction){
        DataField.sFriction = sFriction;
    }

    /**
     * A setter for x and y coordinates
     * @param X x-coordinate
     * @param Y y-coordinate
     */
    @Override
    public void setCoordinates(double X, double Y) {
        this.coordinatesAndVelocity [0] = X;
        this.coordinatesAndVelocity [1] = Y;
    }

    /**
     * A setter for X and Y velocities
     * @param X velocity in the X-direction
     * @param Y velocity in the Y-direction
     */
    @Override
    public void setVelocity(double X, double Y) {
        this.coordinatesAndVelocity [2] = X;
        this.coordinatesAndVelocity [3] = Y;
    }

    /**
     * A setter for taget's position and radius
     * @param targetRXY and array containing target's radius and x,y coordinates
     */
    @Override
    public void setTargetRXY(double[] targetRXY) {
        this.targetRXY = targetRXY;
    }

    /**
     * A setter for terrain
     * @param terrain the function of two variables describing the terrain surface
     */
    @Override
    public void setTerrain(BiFunction<Double,Double,Double> terrain){
        this.terrain = terrain;
    }

    //GETTERS
    /**
     * A getter for x-Coordinate of the ball's position
     * @return x-Coordinate of the ball's position
     */
    @Override
    public double getXCoord(){ return this.coordinatesAndVelocity[0]; };

    /**
     * A getter for y-Coordinate of the ball's position
     * @return y-Coordinate of the ball's position
     */
    @Override
    public double getYCoord() { return this.coordinatesAndVelocity[1]; }

    @Override
    public double getXVelocity() {
        return coordinatesAndVelocity[2];
    }

    @Override
    public double getYVelocity() {
        return coordinatesAndVelocity[3];
    }

    public double getBestDistance(){
        return bestDistance;
    }

    @Override
    public double getBestFinalDistance() {
        return bestFinalDistance;
    }

    public boolean getDidGoThroughWater(){
        return didGoThroughWater;
    }

}