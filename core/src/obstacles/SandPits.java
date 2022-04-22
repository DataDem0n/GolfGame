package obstacles;

import com.mygdx.game.main.DataField;

public class SandPits implements DifferentTerrain {

    private double kFrictionSand;
    private double sFrictionSand;

    public SandPits(double [] sandPit, double kFriction, double sFriction){
        this.kFrictionSand = kFriction;
        this.sFrictionSand = sFriction;
    }

    public void change(double [] coordsAndVelocity){
        double tempSatatic = DataField.sFriction;
        double tempKinetic = DataField.kFriction;

        if(coordsAndVelocity[0] > DataField.sandPit[0] && coordsAndVelocity[0] < DataField.sandPit[1] &&
                coordsAndVelocity[1] > DataField.sandPit[2] && coordsAndVelocity[1] < DataField.sandPit[3]) {
            DataField.sFriction = sFrictionSand;
            DataField.kFriction = kFrictionSand;
        }else {
            DataField.sFriction = tempSatatic;
            DataField.kFriction = tempKinetic;
        }
    }
}
