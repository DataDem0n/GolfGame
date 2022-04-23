package obstacles;

public class Tree implements Obstacles{

    private double coordX;
    private double coordY;
    private final double radius = 1;

    public Tree(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }
    @Override
    public double[] collide(double[] coordsAndVelocity,  double[] tempCoordinates) {
        if((Math.pow(radius ,2)>=(Math.pow((coordsAndVelocity[0]-coordX), 2 )+Math.pow((coordsAndVelocity[1]-coordY), 2 ))))
        {
            coordsAndVelocity[0] = tempCoordinates[0];
            coordsAndVelocity[1] = tempCoordinates[1];
            coordsAndVelocity[2]=0;
            coordsAndVelocity[3]=0;
            System.out.println("you hit the tree!!!");
        }
        return coordsAndVelocity;
    }
}
