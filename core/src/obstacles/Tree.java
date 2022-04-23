package obstacles;

import obstacles.Obstacles;

public class Tree implements Obstacles {

    private double coordX;
    private double coordY;
    private final double radius = 2;

    public Tree(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }
    @Override
    public double[] collide(double[] coordsAndVelocity) {
        //TODO:
        if(((coordsAndVelocity[0]-coordX)*(coordsAndVelocity[0]-coordX))+((coordsAndVelocity[1]-coordY)*(coordsAndVelocity[1]-coordY))<=radius*radius){
            //finding the line going throug the ball coords and center of the circle
            if(!(coordsAndVelocity[0]==coordX))
            int a = (coordsAndVelocity[1]-coordY)/(coordsAndVelocity[0]-coordX);
            int b = coordY-a*coordX;
            //we have a line l: y=a*x+b

            //ending point of the vector
            xEndVector = coordsAndVelocity[0]+coordsAndVelocity[2];
            yEndVector = coordsAndVelocity[1]+coordsAndVelocity[3];

            //orthogonal projection of a point (xEndVector, yEndVector) onto the line l





        }

    }
}
