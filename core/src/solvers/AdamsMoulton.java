package solvers;

import obstacles.SandPits;
import obstacles.Wall;
import physics.Acceleration;
import physics.HasBallStopped;
import physics.MaxSpeed;
import com.mygdx.game.main.DataField;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.function.BiFunction;
//NOT FINISHED METHOD ODE SOLVER
// WE DECIDE IT TO KEEP IT IN THE PROJECT SO WE CAN WORK  ON IT DURING PHASE 3
public class AdamsMoulton{// implements Solver{
//
//    private MaxSpeed maxSpeed = new MaxSpeed();
//    private Acceleration acceleration = new Acceleration();
//    private HasBallStopped hasBallStopped = new HasBallStopped();
//
//    private double counter = 0;
//    private int fps = 120;
//    private BiFunction<Double, Double, Double> terrain;
//    private double kFriction;
//    private double sFriction;
//    double[] targetRXY;
//    public double[] tempCoordinates = new double [2];
//    private double[] coordinatesAndVelocity;
//    private Wall wall = new Wall(25,25);
//    private SandPits sandPits = new SandPits(DataField.sandPit, 0.7, 0.8);
//
//    // Overview of what is stored in the coordinatedAndVelocity array:
//    // [0] - coordinateX
//    // [1] - coordinateY
//    // [2] - velocityX
//    // [3] - velocityY
//
//    /**
//     * Constructor for the EulerSolver class initializing instance variables
//     * @param terrain the function of two variables describing the terrain surface
//     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
//     * @param kFriction the kinetic friction acting upon a ball
//     * @param sFriction the static friction acting upon a ball
//     * @param targetRXY an array that represents the target's radius on first position, target's X-coordinate on second and target's Y-coordinate
//     */
//    public AdamsMoulton(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double[] targetRXY){
//        this.terrain = terrain;
//        this.coordinatesAndVelocity = coordinatesAndVelocity;
//        DataField.kFriction = kFriction;
//        DataField.sFriction = sFriction;
//        this.targetRXY = targetRXY;
//    }
//
//    /**
//     * Method based on the Euler Method for solving differential equations that calculates the next velocities in the X-direction and Y-direction, after a certain step size
//     * and calculates the next coordinates of the ball based on the resulting velocities, so that it tracks ball's movements
//     * @param step a step size in the Euler's method
//     * @return an array with final coordinates and velocities of a ball that has stopped after a shot
//     */
//    public double[] coordinatesAndVelocityUntilStop(double step){
//
//        double[] historyX = new double[3];
//        double[] historyY = new double[3];
//        double[] tempHistoryX = new double[3];
//        double[] tempHistoryY = new double[3];
//        int counter = -1;
//        double CurrentAccx;
//        double CurrentAccy;
//        double futureAccx;
//        double futureAccy;
//        double[] futureArray = new double[4];
//
//
////kickstart for moulton with s = 3
//        for(int p = 0; p <3; p++)       //3 == s
//        {
//            counter++;
//
//            historyX[counter] = rk4(step)[0];
//            System.out.println(historyX[counter]);
//            historyY[counter] = rk4(step)[1];
//
//            System.out.println(coordinatesAndVelocity[2]);
//            coordinatesAndVelocity[2] +=  historyX[counter];
//            coordinatesAndVelocity[3] +=  historyY[counter];
//            coordinatesAndVelocity[0] = coordinatesAndVelocity[0] + coordinatesAndVelocity[2]*step;
//            coordinatesAndVelocity[1] = coordinatesAndVelocity[1] + coordinatesAndVelocity[3]*step;
//            System.out.println(coordinatesAndVelocity[2]);
//            try {
//                Thread.sleep(10002);
//            } catch (Exception e) {
//                //TODO: handle exception
//            }
//        }
//
//
//        while(!hasBallStopped.hasBallStopped(coordinatesAndVelocity, sFriction, terrain, 0.0001))
//        {
//            //current acc
//            CurrentAccx = rk4(step)[0];
//            CurrentAccy = rk4(step)[1];
//
//            //future acc
//            futureArray[2] = coordinatesAndVelocity[2] + CurrentAccx;
//            futureArray[3] = coordinatesAndVelocity[3] + CurrentAccy;
//            futureArray[0] = coordinatesAndVelocity[0] + futureArray[2]*step;
//            futureArray[1] = coordinatesAndVelocity[1] + futureArray[3]*step;
//
//            futureAccx = futureRK4(step,futureArray)[0];
//            futureAccy = futureRK4(step,futureArray)[1];
//
//
//
//
//            coordinatesAndVelocity[2] += -264.0*historyX[2]/720.0 + 106.0*historyX[1]/720.0 -19.0*historyX[0]/720.0 + 646.0*CurrentAccx/720.0 + 251.0*futureAccx/720.0;
//            coordinatesAndVelocity[3] += -264.0*historyY[2]/720.0 + 106.0*historyY[1]/720.0 -19.0*historyY[0]/720.0 + 646.0*CurrentAccy/720.0 + 251.0*futureAccy/720.0;
//            //System.out.println("x: " + (-264.0*historyX[2]/720.0 + 106.0*historyX[1]/720.0 -19.0*historyX[0]/720.0 + 646.0*CurrentAccx/720.0 + 251.0*futureAccx/720.0));
//            //System.out.println("y: " + (-264.0*historyY[2]/720.0 + 106.0*historyY[1]/720.0 -19.0*historyY[0]/720.0 + 646.0*CurrentAccy/720.0 + 251.0*futureAccy/720.0));
//
//            coordinatesAndVelocity[0] = coordinatesAndVelocity[0] + coordinatesAndVelocity[2]*step;
//            coordinatesAndVelocity[1] = coordinatesAndVelocity[1] + coordinatesAndVelocity[3]*step;
//
//            for(int p = 0; p<historyX.length-1;p++)
//            {
//                tempHistoryX[p] = historyX[p+1];
//                tempHistoryY[p] = historyY[p+1];
//            }
//            tempHistoryX[historyX.length-1] = -264.0*historyX[2]/720.0 + 106.0*historyX[1]/720.0 -19.0*historyX[0]/720.0 + 646.0*CurrentAccx/720.0 + 251.0*futureAccx/720.0;    //....................................................
//            tempHistoryY[historyX.length-1] = -264.0*historyY[2]/720.0 + 106.0*historyY[1]/720.0 -19.0*historyY[0]/720.0 + 646.0*CurrentAccy/720.0 + 251.0*futureAccy/720.0;
//            for(int p = 0; p<historyX.length;p++)
//            {
//                historyX[p] = tempHistoryX[p];
//                historyY[p] = tempHistoryY[p];
//            }
//
//            DataField.x = (float)coordinatesAndVelocity[0];
//            DataField.y = (float)coordinatesAndVelocity[1];
//
//            //checking if the ball has fallen into water
//            if(terrain.apply(coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < 0){
//                System.out.println("YOU'RE IN THE WATER!!");
//                coordinatesAndVelocity[0] = tempCoordinates[0];
//                coordinatesAndVelocity[1] = tempCoordinates[1];
////                System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);
//
//                return coordinatesAndVelocity;
//            }
//            //wall.collide(coordinatesAndVelocity);
//            sandPits.change(coordinatesAndVelocity);
//
//
//        }
//        //System.out.println("FINAL ACC: " + accelerationX(coordinatesAndVelocity, terrain, kFriction));
//        return coordinatesAndVelocity;
//    }
//
//    @Override
//    public double getBestDistance() {
//        return 0;
//    }
//
//    @Override
//    public double[] coordinatesAndVelocityUntilStop(double step, boolean update) {
//        return new double[0];
//    }
//
//    @Override
//    public void setkFriction(double kFriction) {
//
//    }
//
//    @Override
//    public void setsFriction(double sFriction) {
//
//    }
//
//    @Override
//    public void setCoordinates(double X, double Y) {
//
//    }
//
//    @Override
//    public void setVelocity(double X, double Y) {
//
//    }
//
//    @Override
//    public void setTargetRXY(double[] targetRXY) {
//
//    }
//
//    @Override
//    public void setTerrain(BiFunction<Double, Double, Double> terrain) {
//
//    }
//
//    @Override
//    public double getXCoord() {
//        return 0;
//    }
//
//    @Override
//    public double getYCoord() {
//        return 0;
//    }
////        System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);
//
//
//
//    public double[] rk4(double step)
//    {
//        double tempvelx1;
//        double tempvely1;
//        double tempvelx2;
//        double tempvely2;
//        double tempvelx3;
//        double tempvely3;
//        double tempvelx4;
//        double tempvely4;
//        double tempvelx5;
//        double tempvely5;
//        double tempvely6;
//        double tempvelx6;
//        double tempvelx7;
//        double tempvely7;
//
//        double tempcoorx1;
//        double tempcoory1;
//        double tempcoorx2;
//        double tempcoory2;
//        double tempcoorx3;
//        double tempcoory3;
//
//        tempvelx1 = acceleration.accelerationrungeX(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, DataField.kFriction)*step;     //k1
//        System.out.println("tempvelx1"+tempvelx1);
//
//        tempvelx2 = coordinatesAndVelocity[2] + 0.5*tempvelx1;
//        tempcoorx1 = coordinatesAndVelocity[0] + tempvelx2*step*0.5;
//        tempvelx3 = acceleration.accelerationrungeX(tempcoorx1,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;    //k2
//
//        tempvelx4 = coordinatesAndVelocity[2] + 0.5*tempvelx3;
//        tempcoorx2 = coordinatesAndVelocity[0] + tempvelx4*step*0.5;
//        tempvelx5 = acceleration.accelerationrungeX(tempcoorx2,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;      //k3
//
//        tempvelx6 = coordinatesAndVelocity[2] + tempvelx5;
//        tempcoorx3 = coordinatesAndVelocity[0] + tempvelx6*step;
//        tempvelx7 = acceleration.accelerationrungeX(tempcoorx3,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;         //k4
//
//
//
//        tempvely1 =acceleration. accelerationrungeY(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, DataField.kFriction)*step;      //k1
//
//        tempvely2 = coordinatesAndVelocity[3] + 0.5*tempvely1;
//        tempcoory1 = coordinatesAndVelocity[1] + tempvely2*step*0.5;
//        tempvely3 = acceleration.accelerationrungeY(coordinatesAndVelocity[0],tempcoory1,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;            //k2
//
//        tempvely4 = coordinatesAndVelocity[3] + 0.5 * tempvely3;
//        tempcoory2 = coordinatesAndVelocity[1] + tempvely4*step*0.5;
//        tempvely5 = acceleration.accelerationrungeY(coordinatesAndVelocity[0],tempcoory2,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;           //k3
//
//        tempvely6 = coordinatesAndVelocity[3] + tempvely5;
//        tempcoory3 = coordinatesAndVelocity[1] + tempvely6*step;
//        tempvely7 = acceleration.accelerationrungeY(coordinatesAndVelocity[0],tempcoory3,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;            //k4
//
//
//        //return ACCx and ACCy as kickstarter and predictor values for adams-bashforth
//        double[] results = new double[2];
//        results[0] = (tempvelx1+2.0*tempvelx3+2.0*tempvelx5+tempvelx7)/6.0;
//
//
//        results[1] = (tempvely1+2.0*tempvely3+2.0*tempvely5+tempvely7)/6.0;
//        return results;
//    }
//
//    public double[] futureRK4(double step, double[] futureArray)
//    {
//        double tempvelx1;
//        double tempvely1;
//        double tempvelx2;
//        double tempvely2;
//        double tempvelx3;
//        double tempvely3;
//        double tempvelx4;
//        double tempvely4;
//        double tempvelx5;
//        double tempvely5;
//        double tempvely6;
//        double tempvelx6;
//        double tempvelx7;
//        double tempvely7;
//
//        double tempcoorx1;
//        double tempcoory1;
//        double tempcoorx2;
//        double tempcoory2;
//        double tempcoorx3;
//        double tempcoory3;
//
//        tempvelx1 = acceleration.accelerationrungeX(futureArray[0],futureArray[1],futureArray[2],futureArray[3] , terrain, DataField.kFriction)*step;     //k1
//
//        tempvelx2 = futureArray[2] + 0.5*tempvelx1;
//        tempcoorx1 = futureArray[0] + tempvelx2*step*0.5;
//        tempvelx3 = acceleration.accelerationrungeX(tempcoorx1,futureArray[1],futureArray[2],futureArray[3],terrain,DataField.kFriction)*step;    //k2
//
//        tempvelx4 = futureArray[2] + 0.5*tempvelx3;
//        tempcoorx2 = futureArray[0] + tempvelx4*step*0.5;
//        tempvelx5 = acceleration.accelerationrungeX(tempcoorx2,futureArray[1],futureArray[2],futureArray[3],terrain,DataField.kFriction)*step;      //k3
//
//        tempvelx6 = futureArray[2] + tempvelx5;
//        tempcoorx3 = futureArray[0] + tempvelx6*step;
//        tempvelx7 = acceleration.accelerationrungeX(tempcoorx3,futureArray[1],futureArray[2],futureArray[3],terrain,DataField.kFriction)*step;         //k4
//
//
//        tempvely1 = acceleration.accelerationrungeY(futureArray[0],futureArray[1],futureArray[2],futureArray[3] , terrain, DataField.kFriction)*step;      //k1
//
//        tempvely2 = futureArray[3] + 0.5*tempvely1;
//        tempcoory1 = futureArray[1] + tempvely2*step*0.5;
//        tempvely3 = acceleration.accelerationrungeY(futureArray[0],tempcoory1,futureArray[2],futureArray[3],terrain,DataField.kFriction)*step;            //k2
//
//        tempvely4 = futureArray[3] + 0.5 * tempvely3;
//        tempcoory2 = futureArray[1] + tempvely4*step*0.5;
//        tempvely5 = acceleration.accelerationrungeY(futureArray[0],tempcoory2,futureArray[2],futureArray[3],terrain,DataField.kFriction)*step;           //k3
//
//        tempvely6 = futureArray[3] + tempvely5;
//        tempcoory3 = futureArray[1] + tempvely6*step;
//        tempvely7 = acceleration.accelerationrungeY(futureArray[0],tempcoory3,futureArray[2],futureArray[3],terrain,DataField.kFriction)*step;            //k4
//
//
//
//        //return ACCx and ACCy as kickstarter and predictor values for adams-bashforth
//        double[] results = new double[2];
//        results[0] = (tempvelx1+2.0*tempvelx3+2.0*tempvelx5+tempvelx7)/6.0;
//        results[1] = (tempvely1+2.0*tempvely3+2.0*tempvely5+tempvely7)/6.0;
//        return results;
//    }
//    @Override
//    public double getXVelocity() {
//        return coordinatesAndVelocity[2];
//    }
//
//    @Override
//    public double getYVelocity() {
//        return coordinatesAndVelocity[3];
//    }
//
//


}

//test

