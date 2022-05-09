package obstacles;

import com.mygdx.game.main.DataField;

public class Water implements Obstacles {

    public  Water(){

    }
    @Override
    public double[] collide(double[] coordinatesAndVelocity, double [] tempCoords) {

        if(DataField.terrain.apply(coordinatesAndVelocity[0], coordinatesAndVelocity[1]) < 0){
            System.out.println("YOU'RE IN THE WATER!!");
            coordinatesAndVelocity[0] = tempCoords[0];
            coordinatesAndVelocity[1] = tempCoords[1];
            coordinatesAndVelocity[2]=(double) 0.000000001;
            coordinatesAndVelocity[3]=(double) 0.000000001;
            //System.out.println("x: "+coordinatesAndVelocity[0] +" y: "+ coordinatesAndVelocity[1]);


        }
        return coordinatesAndVelocity;

    }
}
