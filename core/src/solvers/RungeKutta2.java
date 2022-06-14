package solvers;

import com.mygdx.game.main.DataField;
import obstacles.*;
import physics.Acceleration;
import physics.HasBallStopped;
import physics.MaxSpeed;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class RungeKutta2 implements Solver{

    private MaxSpeed maxSpeed = new MaxSpeed();
    private Acceleration acceleration = new Acceleration();
    private HasBallStopped hasBallStopped = new HasBallStopped();
    private ArrayList<Double> pathX = new ArrayList<Double>();
    private ArrayList<Double> pathY = new ArrayList<Double>();

    private BiFunction<Double, Double, Double> terrain;
    double[] targetRXY;

    public double[] tempCoordinates = new double [2];
    protected double[] coordinatesAndVelocity;
    private Wall wall = new Wall(25,25);
    private SandPits sandPits = new SandPits(DataField.sandPit, DataField.kFriction+0.2, DataField.sFriction+0.2);
    private Forest f = DataField.gameForest;
    private Water water = new Water();
    private double bestDistance =100;
    private double bestFinalDistance = 100;
    private boolean didGoThroughWater;
    private Tree tree = DataField.gameForest.getForest().get(0);


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

    public RungeKutta2(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double[] targetRXY){
        this.terrain = terrain;
        this.coordinatesAndVelocity = coordinatesAndVelocity;
        DataField.kFriction = kFriction;
        DataField.sFriction = sFriction;
        this.targetRXY = targetRXY;
        this.didGoThroughWater =false;
    }

    @Override
    public double getBestDistance() {
        return bestDistance;
    }

    @Override
    public double getBestFinalDistance() {
        return bestFinalDistance;
    }

    /**
     * Method based on the Euler Method for solving differential equations that calculates the next velocities in the X-direction and Y-direction, after a certain step size
     * and calculates the next coordinates of the ball based on the resulting velocities, so that it tracks ball's movements
     * @param step a step size in the Euler's method
     * @return an array with final coordinates and velocities of a ball that has stopped after a shot
     */

    @Override
    public double[] coordinatesAndVelocityUntilStop(double step, boolean update) {

        tempCoordinates[0] = coordinatesAndVelocity[0];
        tempCoordinates[1] = coordinatesAndVelocity[1];

        if(!DataField.aiRunning)
            coordinatesAndVelocity = maxSpeed.maxSpeedReached(coordinatesAndVelocity);


        while (!hasBallStopped.hasBallStopped(coordinatesAndVelocity,  DataField.sFriction,terrain, step)) {
            if (coordinatesAndVelocity[2] == 0 && coordinatesAndVelocity[3] == 0) {
                coordinatesAndVelocity[2] = coordinatesAndVelocity[2] + (step * acceleration.accelerationX2(coordinatesAndVelocity, terrain, DataField.kFriction)); //X-Velocity = xVelocity + step*acc
                coordinatesAndVelocity[3] = coordinatesAndVelocity[3] + (step * acceleration.accelerationY2(coordinatesAndVelocity, terrain, DataField.kFriction)); //Y-Velocity = YVelocity + step*acc
            } else {
                double [] k1_coords = {coordinatesAndVelocity[0], coordinatesAndVelocity[1]};
                double [] k1_velocities = {coordinatesAndVelocity[2], coordinatesAndVelocity[3]};
                double[] k1_acceleration = {acceleration.accelerationrungeX(k1_coords[0],k1_coords[1],k1_velocities[0],k1_velocities[1] , terrain, DataField.kFriction),
                        acceleration.accelerationrungeY(k1_coords[0],k1_coords[1],k1_velocities[0],k1_velocities[1] , terrain, DataField.kFriction)};     //getting x-velocity using midpoint

                double[] k2_positions ={(k1_coords[0] + (2.0/3.0) * step * k1_velocities[0]), (k1_coords[1] + (2.0/3.0) * step * k1_velocities[1]) } ;
                double[] k2_velocities = {(k1_velocities[0] + (2.0/3.0) * step * k1_acceleration[0]),k1_velocities[1] + (2.0/3.0) * step * k1_acceleration[1]};
                double[] k2_acceleration = {acceleration.accelerationrungeX(k2_positions[0],k2_positions[1],k2_velocities[0],k2_velocities[1] , terrain, DataField.kFriction),
                        acceleration.accelerationrungeY(k2_positions[0],k2_positions[1],k2_velocities[0],k2_velocities[1] , terrain, DataField.kFriction)};

                double x_coor = coordinatesAndVelocity[0] + step*(1.0/4.0)*(k1_velocities[0] + 3*k2_velocities[0]);
                double y_coor = coordinatesAndVelocity[1] + step*(1.0/4.0)*(k1_velocities[1] + 3*k2_velocities[1]);
                double x_vel = coordinatesAndVelocity[2] + step*(1.0/4.0)*(k1_acceleration[0] + 3*k2_acceleration[0]);
                double y_vel = coordinatesAndVelocity[3] + step*(1.0/4.0)*(k1_acceleration[1] + 3*k2_acceleration[1]);

                coordinatesAndVelocity = new double[]{x_coor,y_coor,x_vel,y_vel};
            }

            if(DataField.terrain.apply(coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < 0){
                //System.out.println("did go trhough water");
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
            tree.collide(coordinatesAndVelocity, tempCoordinates);
//            DataField.gameForest.collide(coordinatesAndVelocity, tempCoordinates);
        }


        System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);


        double FINALDist = Math.sqrt((Math.pow( DataField.targetRXY[1]-coordinatesAndVelocity[0] , 2) + ( Math.pow( DataField.targetRXY[2]- coordinatesAndVelocity[1], 2))));
        bestFinalDistance = FINALDist;
        return coordinatesAndVelocity;
    }


    //SETTERS

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

    @Override
    public boolean getDidGoThroughWater() {
        return didGoThroughWater;
    }


}
