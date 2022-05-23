package obstacles;

import com.mygdx.game.main.DataField;

public class SandPits implements DifferentTerrain {

    private double kFrictionSand;
    private double sFrictionSand;
    double tempKinetic;
    double tempSatatic;

    /**
     * @param sandPit an array containing the four corner coordinates of the sandpit
     * @param kFriction the kinetic friction of the sandpit
     * @param sFriction the static friction of the sandpit
     */
    public SandPits(double [] sandPit, double kFriction, double sFriction){
        this.kFrictionSand = kFriction;
        this.sFrictionSand = sFriction;
        tempSatatic = DataField.sFriction;
        tempKinetic = DataField.kFriction;
    }

    /**
     *  Method that changes the friction of the ball depending on the fact if the ball is in the sand
     * @param coordsAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     */
    public void change(double [] coordsAndVelocity){

        if(coordsAndVelocity[0] > DataField.sandPit[0] && coordsAndVelocity[0] < DataField.sandPit[2] &&
                coordsAndVelocity[1] > DataField.sandPit[1] && coordsAndVelocity[1] < DataField.sandPit[3]) {
            DataField.sFriction = sFrictionSand;
            DataField.kFriction = kFrictionSand;
        }else {
            DataField.sFriction = tempSatatic;
            DataField.kFriction = tempKinetic;
        }
    }
}
