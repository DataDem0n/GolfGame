package obstacles;

import com.mygdx.game.main.DataField;

public class Water implements DifferentTerrain {

    public  Water(){

    }
    @Override
    public void change(double[] coordinatesAndVelocity) {

        if(DataField.terrain.apply(coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < 0){
            System.out.println("YOU'RE IN THE WATER!!");
            //coordinatesAndVelocity[0] = Physics.tempCoordinates[0];
            //coordinatesAndVelocity[1] = Physics.tempCoordinates[1];
            System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);


        }

    }
}
