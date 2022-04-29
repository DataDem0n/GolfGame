package solvers;

import obstacles.SandPits;
import obstacles.Tree;
import obstacles.Wall;
import physics.Physics;
import com.mygdx.game.main.DataField;
import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Timer;
import java.util.function.BiFunction;

public class RungeKutta4 extends Physics implements Solver {

    private double counter = 0;
    private int fps = 120;
    private BiFunction<Double, Double, Double> terrain;
    private double kFriction;
    private double sFriction;
    double[] targetRXY;
    public double[] tempCoordinates = new double [2];
    private double[] coordinatesAndVelocity;
    private Wall wall = new Wall(25,25);
    private SandPits sandPits = new SandPits(DataField.sandPit, 0.7, 0.8);
    private Tree tree = new Tree(5,5);

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
        double tempvelx1;
        double tempvely1;
        double tempvelx2;
        double tempvely2;
        double tempvelx3;
        double tempvely3;
        double tempvelx4;
        double tempvely4;
        double tempvelx5;
        double tempvely5;
        double tempvely6;
        double tempvelx6;
        double tempvelx7;
        double tempvely7;

        double tempcoorx1;
        double tempcoory1;
        double tempcoorx2;
        double tempcoory2;
        double tempcoorx3;
        double tempcoory3;


        double velx;
        double vely;

        coordinatesAndVelocity = maxSpeedReached(coordinatesAndVelocity);

        while(!hasBallStopped(coordinatesAndVelocity, sFriction, terrain, step)){

            tempvelx1 = accelerationrungeX(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, kFriction)*step;     //k1
            //System.out.println("K1 = " + tempvelx1);
            //___________________________

            tempvelx2 = coordinatesAndVelocity[2] + 0.5*tempvelx1;
            tempcoorx1 = coordinatesAndVelocity[0] + tempvelx2*step*0.5;
            tempvelx3 = accelerationrungeX(tempcoorx1,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;    //k2
            //System.out.println("K2 = " + tempvelx3);

            //____________________________

            tempvelx4 = coordinatesAndVelocity[2] + 0.5*tempvelx3;
            tempcoorx2 = coordinatesAndVelocity[0] + tempvelx4*step*0.5;
            tempvelx5 = accelerationrungeX(tempcoorx2,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;      //k3
            //System.out.println("K3 = " + tempvelx5);

            tempvelx6 = coordinatesAndVelocity[2] + tempvelx5;
            tempcoorx3 = coordinatesAndVelocity[0] + tempvelx6*step;
            tempvelx7 = accelerationrungeX(tempcoorx3,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;         //k4
            //System.out.println("K4 = " + tempvelx7);

            //IMPLEMENT 1,3,5,7
            tempvely1 = accelerationrungeY(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, kFriction)*step;      //k1

            tempvely2 = coordinatesAndVelocity[3] + 0.5*tempvely1;
            tempcoory1 = coordinatesAndVelocity[1] + tempvely2*step*0.5;
            tempvely3 = accelerationrungeY(coordinatesAndVelocity[0],tempcoory1,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;            //k2


            tempvely4 = coordinatesAndVelocity[3] + 0.5 * tempvely3;
            tempcoory2 = coordinatesAndVelocity[1] + tempvely4*step*0.5;
            tempvely5 = accelerationrungeY(coordinatesAndVelocity[0],tempcoory2,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;           //k3

            tempvely6 = coordinatesAndVelocity[3] + tempvely5;
            tempcoory3 = coordinatesAndVelocity[1] + tempvely6*step;
            tempvely7 = accelerationrungeY(coordinatesAndVelocity[0],tempcoory3,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;            //k4

            //IMPLEMENT 1,3,5,7

            coordinatesAndVelocity[2] += (tempvelx1+2*tempvelx3+2*tempvelx5+tempvelx7)/6;        //(0.166666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666667)
            //System.out.println("dX = " + (tempvelx1+2*tempvelx3+2*tempvelx5+tempvelx7)/6);
            //System.out.println("Xtot = " + coordinatesAndVelocity[2]);
            coordinatesAndVelocity[3] += (tempvely1+2*tempvely3+2*tempvely5+tempvely7)/6;
            // System.out.println("velocityX = " + coordinatesAndVelocity[2]);
            // System.out.println("velocityY = " + coordinatesAndVelocity[3]);
            // try {
            //         Thread.sleep(1000);
            //     }
            // catch (Exception e)
            // {
            // }

            //here updating the coordinates based on calculated velocities (step = timeInterval ALWAYS)
            coordinatesAndVelocity[0] = coordinatesAndVelocity[0] + coordinatesAndVelocity[2]*step;
            coordinatesAndVelocity[1] = coordinatesAndVelocity[1] + coordinatesAndVelocity[3]*step;
            }

            //here updating the coordinates based on calculated velocities (step = timeInterval ALWAYS)
            coordinatesAndVelocity[0] = coordinatesAndVelocity[0] + coordinatesAndVelocity[2]*step;
            coordinatesAndVelocity[1] = coordinatesAndVelocity[1] + coordinatesAndVelocity[3]*step;

            counter+= step;

//            if(counter>=1/fps) {
//                try {
//                    Thread.sleep(0,2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                counter = 0.0;
//            }

            DataField.x = (float)coordinatesAndVelocity[0];
            DataField.y = (float)coordinatesAndVelocity[1];

            //checking if the ball has fallen into water
            if(terrain.apply(coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < 0){
                System.out.println("YOU'RE IN THE WATER!!");
                coordinatesAndVelocity[0] = tempCoordinates[0];
                coordinatesAndVelocity[1] = tempCoordinates[1];
//                System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);

                return coordinatesAndVelocity;
            }
            wall.collide(coordinatesAndVelocity);
            sandPits.change(coordinatesAndVelocity);

//        System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);

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
        if(kFriction > 0.1){
            System.out.println("THE KINETIC FRICTION TOO HIGH, I SET IT TO 0.1");
            DataField.kFriction = 0.1;
        }
        else if(kFriction < 0.05){
            System.out.println("THE KINETIC FRICTION TOO LOW, I SET IT TO 0.05");
            DataField.kFriction = 0.05;
        }
        else{
            DataField.kFriction = kFriction;
        }
    };

    /**
     * A setter for static friction
     * @param sFriction static friction
     */
    @Override
    public void setsFriction(double sFriction){
        if(sFriction > 0.2){
            System.out.println("THE STATIC FRICTION TOO HIGH, I SET IT TO 0.2");
            DataField.sFriction = 0.2;
        }
        else if(sFriction < 0.1){
            System.out.println("THE STATIC FRICTION TOO LOW, I SET IT TO 0.1");
            DataField.sFriction = 0.1;
        }
        else{
            DataField.sFriction = sFriction;
        }
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

//    /**
//     * A getter for the X-velocity of the ball
//     * @return the X-velocity of the ball
//     */
//    public double getXVel() {
//        return this.coordinatesAndVelocity[2];
//    }
//
//    /**
//     * A getter for the Y-velocity of the ball
//     * @return the Y-velocity of the ball
//     */
//    public double getYVel() {
//        return this.coordinatesAndVelocity[3];
//    }
//
//    /**
//     * A getter for target's radius
//     * @return the target's radius
//     */
//    public double getTRadius() {
//        return this.targetRXY[0];
//    }
//
//    /**
//     * A getter for target's x-coordinate
//     * @return the target's x-coordinate
//     */
//    public double getXTarget() {
//        return this.targetRXY[1];
//    }
//
//    /**
//     * A getter for target's y-coordinate
//     * @return the target's y-coordinate
//     */
//    public double getYTarget() {
//        return targetRXY[2];
//    }

//    public static void main(String[] args) { testing
//        double[] coordinatesAndVelocity = {0,0,2,0};
//        double[] target = {1,4,4};
//        double kfriction = 0.05;
//        double staticFriction = 0.2;
//        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.1*x+1);
//        //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.pow(Math.E, -((x*x+y*y)/40)));
//       //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));
//       //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));
//        double step = 0.0000000001;
//        EulerSolver e = new EulerSolver(terrain, coordinatesAndVelocity, kfriction, staticFriction, target);
////        for (int i = 0; i < 100; i++) {
//            System.out.println(step +", " + e.coordinatesAndVelocityUntilStop(step)[0] + ", "+ e.coordinatesAndVelocityUntilStop(step)[1]+", ");
//            //step = step + 0.001;
//       //}
//
//    }
}

