package engine;
import com.mygdx.game.main.DataField;
import solvers.Solver;

public class GameEngine extends Thread {

    /**
     * A constructor for the GameEngine class that initializes all instance fields, also in the superclass
     * @param terrain the function of two variables describing the terrain surface
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @param kFriction the kinetic friction acting upon a ball
     * @param sFriction the static friction acting upon a ball
     * @param targetRXY an array that represents the target's radius on first position, target's X-coordinate on second and target's Y-coordinate
     */
    private Solver solver;
    public GameEngine(Solver solver){
        this.solver=solver;
    }

    /**
     * A method that manages the game
     */
    public void game() throws InterruptedException {

        while(DataField.GUI){
            Thread.sleep(50);
        }

        solver.setCoordinates(DataField.x, DataField.y);
        solver.setVelocity(DataField.velocityX.get(0), DataField.velocityY.get(0));
        solver.setkFriction(DataField.kFriction);
        solver.setsFriction(DataField.sFriction);
        solver.setTargetRXY(DataField.targetRXY);
        solver.setTerrain(DataField.terrain);
        int index = 1;
        int silly =0;
        // while ball is not in the target
        while (!(Math.pow(DataField.targetRXY[0] ,2)>(Math.pow((solver.getXCoord()-DataField.targetRXY[1]), 2 )+Math.pow((solver.getYCoord()-DataField.targetRXY[2]), 2 )))){

            solver.coordinatesAndVelocityUntilStop( 0.00001, true);

            DataField.x = solver.getXCoord();
            DataField.y = solver.getYCoord();

            if((((solver.getXCoord() > DataField.targetRXY[1]-0.5 && solver.getXCoord() < DataField.targetRXY[1]+0.5) && (solver.getYCoord() > DataField.targetRXY[2]-0.5 && solver.getYCoord() < DataField.targetRXY[2]+0.5)) && solver.getXVelocity() <= 2.0 && solver.getYVelocity() <= 2.0))
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
                    DataField.velocityX.remove(0);
                    DataField.velocityY.remove(0);
                    DataField.GUI = true;
                    game();
                }
                if(index!=DataField.velocityX.size()){
                    solver.setVelocity(DataField.velocityX.get(index), DataField.velocityY.get(index));
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