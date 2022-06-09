package solvers;

import com.mygdx.game.main.DataField;
import obstacles.Water;
import physics.Acceleration;
import physics.HasBallStopped;
import physics.MaxSpeed;
import obstacles.SandPits;
import obstacles.Tree;
import obstacles.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Euler implements Solver {
    private MaxSpeed maxSpeed = new MaxSpeed();
    private Acceleration acceleration = new Acceleration();
    private HasBallStopped hasBallStopped = new HasBallStopped();
    private int fps = 120;
    private BiFunction<Double, Double, Double> terrain;
    double[] targetRXY;


    public double[] tempCoordinates = new double [2];
    public double[] coordinatesAndVelocity=new double[4] ;
    private Wall wall = new Wall(25,25);
    private SandPits sandPits = new SandPits(DataField.sandPit, 0.7, 0.8);
    private Water water = new Water();
    private ArrayList<Double> pathX = new ArrayList<Double>();
    private ArrayList<Double> pathY = new ArrayList<Double>();
    private double bestFinalDistance = 100;


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
    public Euler(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double[] targetRXY){
        this.terrain = terrain;
        this.coordinatesAndVelocity = coordinatesAndVelocity;
        DataField.kFriction = kFriction;
        DataField.sFriction = sFriction;
        this.targetRXY = targetRXY;
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
        pathX=null;
        pathY=null;

        if(!DataField.aiRunning)
            coordinatesAndVelocity = maxSpeed.maxSpeedReached(coordinatesAndVelocity);

        while(!hasBallStopped.hasBallStopped(coordinatesAndVelocity,DataField.sFriction, terrain, step)){

            if(coordinatesAndVelocity[2] == 0 && coordinatesAndVelocity[3] == 0){
                coordinatesAndVelocity[2] = coordinatesAndVelocity[2] + (step * acceleration.accelerationX2(coordinatesAndVelocity, terrain, DataField.kFriction)); //X-Velocity = xVelocity + step*acc
                coordinatesAndVelocity[3] = coordinatesAndVelocity[3] + (step * acceleration.accelerationY2(coordinatesAndVelocity, terrain, DataField.kFriction)); //Y-Velocity = YVelocity + step*acc
            }
            else{
                coordinatesAndVelocity[2] = coordinatesAndVelocity[2] + (step * acceleration.accelerationX(coordinatesAndVelocity, terrain, DataField.kFriction)); //X-Velocity = xVelocity + step*acc
                coordinatesAndVelocity[3] = coordinatesAndVelocity[3] + (step * acceleration.accelerationY(coordinatesAndVelocity, terrain, DataField.kFriction)); //Y-Velocity = YVelocity + step*acc
            }

            //here updating the coordinates based on calculated velocities (step = timeInterval ALWAYS)
            coordinatesAndVelocity[0] = coordinatesAndVelocity[0] + coordinatesAndVelocity[2]*step;
            coordinatesAndVelocity[1] = coordinatesAndVelocity[1] + coordinatesAndVelocity[3]*step;

            if(update) {
                DataField.x = coordinatesAndVelocity[0];
                DataField.y = coordinatesAndVelocity[1];
            }
            double x = coordinatesAndVelocity[0];
            double y = coordinatesAndVelocity[1];

            pathX.add(coordinatesAndVelocity[0]);
            pathY.add(coordinatesAndVelocity[1]);

            water.collide(coordinatesAndVelocity, tempCoordinates);
            wall.collide(coordinatesAndVelocity, new double[0]);
            sandPits.change(coordinatesAndVelocity);
            DataField.gameForest.collide(coordinatesAndVelocity, tempCoordinates);

       }
        System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);
        System.out.println("accx: " + coordinatesAndVelocity[2]);
        System.out.println("accy: " + coordinatesAndVelocity[3]);

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
    public double getBestDistance(){return 0;}

    @Override
    public double getBestFinalDistance() {
        return bestFinalDistance;
    }


}