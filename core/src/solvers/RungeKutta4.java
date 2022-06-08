package solvers;

import obstacles.SandPits;
import obstacles.Tree;
import obstacles.Wall;
import obstacles.Water;
import physics.Acceleration;
import physics.HasBallStopped;
import physics.MaxSpeed;
import com.mygdx.game.main.DataField;
import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Timer;
import java.util.function.BiFunction;

public class RungeKutta4 implements Solver {

    private MaxSpeed maxSpeed = new MaxSpeed();
    private Acceleration acceleration = new Acceleration();
    private HasBallStopped hasBallStopped = new HasBallStopped();
    private int fps = 120;
    private BiFunction<Double, Double, Double> terrain;
    double[] targetRXY;
    public double[] tempCoordinates = new double [2];
    public double[] coordinatesAndVelocity;
    private Wall wall = new Wall(25,25);
    private SandPits sandPits = new SandPits(DataField.sandPit, 0.7, 0.8);
    private Water water = new Water();

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
    public RungeKutta4(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double[] targetRXY){
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
    public double[] coordinatesAndVelocityUntilStop(double step)  {
        tempCoordinates[0] = coordinatesAndVelocity[0];
        tempCoordinates[1] = coordinatesAndVelocity[1];

        double tempvelx1,tempvely1,tempvelx2, tempvely2, tempvelx3, tempvely3, tempvelx4, tempvely4, tempvelx5, tempvely5, tempvely6, tempvelx6, tempvelx7, tempvely7;
        double tempcoorx1, tempcoory1, tempcoorx2, tempcoory2, tempcoorx3, tempcoory3;

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
                tempvelx1 = acceleration.accelerationrungeX(coordinatesAndVelocity[0], coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;     //k1

                tempvelx2 = coordinatesAndVelocity[2] + 0.5 * tempvelx1;
                tempcoorx1 = coordinatesAndVelocity[0] + tempvelx2 * step * 0.5;
                tempvelx3 = acceleration.accelerationrungeX(tempcoorx1, coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;    //k2

                tempvelx4 = coordinatesAndVelocity[2] + 0.5 * tempvelx3;
                tempcoorx2 = coordinatesAndVelocity[0] + tempvelx4 * step * 0.5;
                tempvelx5 = acceleration.accelerationrungeX(tempcoorx2, coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;      //k3

                tempvelx6 = coordinatesAndVelocity[2] + tempvelx5;
                tempcoorx3 = coordinatesAndVelocity[0] + tempvelx6 * step;
                tempvelx7 = acceleration.accelerationrungeX(tempcoorx3, coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;         //k4

                //IMPLEMENT 1,3,5,7
                tempvely1 = acceleration.accelerationrungeY(coordinatesAndVelocity[0], coordinatesAndVelocity[1], coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;      //k1

                tempvely2 = coordinatesAndVelocity[3] + 0.5 * tempvely1;
                tempcoory1 = coordinatesAndVelocity[1] + tempvely2 * step * 0.5;
                tempvely3 = acceleration.accelerationrungeY(coordinatesAndVelocity[0], tempcoory1, coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;            //k2


                tempvely4 = coordinatesAndVelocity[3] + 0.5 * tempvely3;
                tempcoory2 = coordinatesAndVelocity[1] + tempvely4 * step * 0.5;
                tempvely5 = acceleration.accelerationrungeY(coordinatesAndVelocity[0], tempcoory2, coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;           //k3

                tempvely6 = coordinatesAndVelocity[3] + tempvely5;
                tempcoory3 = coordinatesAndVelocity[1] + tempvely6 * step;
                tempvely7 = acceleration.accelerationrungeY(coordinatesAndVelocity[0], tempcoory3, coordinatesAndVelocity[2], coordinatesAndVelocity[3], terrain, DataField.kFriction) * step;            //k4

                //IMPLEMENT 1,3,5,7

                coordinatesAndVelocity[2] += (tempvelx1 + 2 * tempvelx3 + 2 * tempvelx5 + tempvelx7) / 6;
                coordinatesAndVelocity[3] += (tempvely1 + 2 * tempvely3 + 2 * tempvely5 + tempvely7) / 6;
            }

            //here updating the coordinates based on calculated velocities (step = timeInterval ALWAYS)
            coordinatesAndVelocity[0] = coordinatesAndVelocity[0] + coordinatesAndVelocity[2] * step;
            coordinatesAndVelocity[1] = coordinatesAndVelocity[1] + coordinatesAndVelocity[3] * step;

            DataField.x = coordinatesAndVelocity[0];
            DataField.y = coordinatesAndVelocity[1];

            water.collide(coordinatesAndVelocity, tempCoordinates);
            wall.collide(coordinatesAndVelocity, new double[0]);
            sandPits.change(coordinatesAndVelocity);
//            tree.collide(coordinatesAndVelocity, tempCoordinates);
//            DataField.gameForest.collide(coordinatesAndVelocity, tempCoordinates);
        }
        System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);
        System.out.println("accx: " + coordinatesAndVelocity[2]);
        System.out.println("accy: " + coordinatesAndVelocity[3]);

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
}