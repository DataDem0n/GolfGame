package engine;

import com.mygdx.game.main.DataField;
import solvers.AdamsMoulton;
import solvers.Euler;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.zip.Adler32;

public class GameEngineAM extends AdamsMoulton {

    Scanner s = new Scanner(System.in);
    /**
     * A constructor for the GameEngine class that initializes all instance fields, also in the superclass
     * @param terrain the function of two variables describing the terrain surface
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param kFriction the kinetic friction acting upon a ball
     * @param sFriction the static friction acting upon a ball
     * @param targetRXY an array that represents the target's radius on first position, target's X-coordinate on second and target's Y-coordinate
     */
    public GameEngineAM(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double[] targetRXY){
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
        setVelocity(DataField.velocityX.get(0), DataField.velocityY.get(0));
        setkFriction(DataField.kFriction);
        setsFriction(DataField.sFriction);
        setTargetRXY(DataField.targetRXY);

        setTerrain(DataField.terrain);
        int index = 1;

        // while ball is not in the target
        while (!(Math.pow(DataField.targetRXY[0] ,2)>(Math.pow((getXCoord()-DataField.targetRXY[1]), 2 )+Math.pow((getYCoord()-DataField.targetRXY[2]), 2 )))){

            coordinatesAndVelocityUntilStop( 0.00001);

            DataField.x = (float) getXCoord();
            DataField.y = (float) getYCoord();

                if((Math.pow(DataField.targetRXY[0] ,2)>(Math.pow((getXCoord()-DataField.targetRXY[1]), 2 )+Math.pow((getYCoord()-DataField.targetRXY[2]), 2 ))))
                {
                    System.out.println("YOU WON!");
                }
                else
                {

                    while(DataField.GUI){
                        Thread.sleep(50);
                    }

                    if(DataField.GUI) {
                        DataField.velocityX.remove(0);
                        DataField.velocityY.remove(0);
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

    @Override
    public void run() {
        try {
            game();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

