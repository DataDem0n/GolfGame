package engine;

import com.mygdx.game.main.DataField;
import solvers.Euler;
import solvers.RungeKutta2;
import solvers.RungeKutta4;

import java.util.Scanner;
import java.util.function.BiFunction;

public class GameEngineRK2 extends RungeKutta2 {

    Scanner s = new Scanner(System.in);
    /**
     * A constructor for the GameEngine class that initializes all instance fields, also in the superclass
     * @param terrain the function of two variables describing the terrain surface
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param kFriction the kinetic friction acting upon a ball
     * @param sFriction the static friction acting upon a ball
     * @param targetRXY an array that represents the target's radius on first position, target's X-coordinate on second and target's Y-coordinate
     */
    public GameEngineRK2(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double[] targetRXY){
        super(terrain, coordinatesAndVelocity, kFriction, sFriction, targetRXY);
    }

    /**
     * A method that manages the game
     */
    public void game() throws InterruptedException {

        while(DataField.GUI){
            Thread.sleep(50);
        }

        setCoordinates(DataField.x, DataField.y);

        if(!DataField.velocityX.isEmpty())
            setVelocity(DataField.velocityX.get(0), DataField.velocityY.get(0));
//        else{
//            return;
//        }

        setkFriction(DataField.kFriction);
        setsFriction(DataField.sFriction);
        setTargetRXY(DataField.targetRXY);//this should all be done in constructor?


        setTerrain(DataField.terrain);
        int index = 1;
        int silly = 0;
        // while ball is not in the target
        while (!(Math.pow(DataField.targetRXY[0] ,2)>(Math.pow((getXCoord()-DataField.targetRXY[1]), 2 )+Math.pow((getYCoord()-DataField.targetRXY[2]), 2 )))      ||    silly != 0){

            coordinatesAndVelocityUntilStop( 0.000001);//0.000001 for smooth animation

            DataField.x = getXCoord();
            DataField.y = getYCoord();

                if((((coordinatesAndVelocity[0] > DataField.targetRXY[1]-0.5 && coordinatesAndVelocity[0] < DataField.targetRXY[1]+0.5) && (coordinatesAndVelocity[1] > DataField.targetRXY[2]-0.5 && coordinatesAndVelocity[1] < DataField.targetRXY[1]+0.5)) && coordinatesAndVelocity[2] <= 2.0 && coordinatesAndVelocity[3] <= 2.0))
                {
                    System.out.println("YOU WON!");
                    silly += 1;
                    break;
                }
                else
                {

                    while(DataField.GUI){
                        Thread.sleep(50);
                    }

                    if(DataField.usingGui) {
                        if(!DataField.velocityX.isEmpty()) {
                            DataField.velocityX.remove(0);
                            DataField.velocityY.remove(0);
                        }
                        DataField.GUI = true;
                        game();
                    }
                    if(index!=DataField.velocityX.size()){

                        setVelocity(DataField.velocityX.get(index), DataField.velocityY.get(index));
                        index++;
                    }
                    else{
                        System.out.println("You did not win, better luck next time.\nPlease restart with a new file input.");
                        break;
                    }
                }
            }
        }


    public void run() {
        try {
            game();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

