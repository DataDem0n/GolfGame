package Bot_Work;

import com.mygdx.game.main.DataField;

import java.util.ArrayList;

public class CosineFinder implements VectorFinder{

    double ballX;
    double ballY;
    double holeX;
    double holeY; //holey ;)? kinky.

    //step size for multiple shots
    double step;
    ArrayList<Double> v;
    public CosineFinder() {
//        ballX = DataField.x;
//        ballY = DataField.y;
//        holeX = DataField.targetRXY[1];
//        holeY = DataField.targetRXY[2];

        step = 0.2;
    }

    @Override
    public ArrayList<WeightedVector> vectorFind(double ballX, double ballY, double holeX, double holeY) {
        ArrayList<WeightedVector> wv = new ArrayList<>();
        WeightedVector basisVector = vectorToPoint(ballX,ballY,holeX,holeY);
        System.out.println(basisVector);
        for (double i = step; i < 10.0; i=i+step) {
            double xAddition = -i;
            double yAddition = -i;

            if(ballX>holeX)
                xAddition = xAddition*-1;
            if(ballY>holeY)
                yAddition = yAddition*-1;


                if((holeX<ballX+1.0&&holeX>ballX-1.0)){
                    wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()+xAddition));
                    wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()-xAddition));
                }
                else if((holeY<ballY+1.0 && holeY>ballY-1.0)){
                    wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()+yAddition));
                    wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()-yAddition));
                }

            else {
                wv.add(new WeightedVector(basisVector.getX() + xAddition, basisVector.getY()));
                wv.add(new WeightedVector(basisVector.getX(), basisVector.getY()+ yAddition));
            }
        }

        return wv;
    }

    private double Distance(double x1, double x2){
        return Math.sqrt(Math.pow((x2-x1) ,2));
    }

//    //Distance between two points method (can use the return as a weight value if needed)
    private double Euclidistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow((x2-x1) ,2) + Math.pow((y2-y1), 2));
    }

    public WeightedVector vectorToPoint(double ballX, double ballY, double holeX, double holeY){
        double xt = Distance(ballX,holeX);
        double yt = Distance(holeY,ballY);

        if(ballX>holeX)
            xt = -xt;
        if(ballY>holeY)
            yt = -yt;

        return new WeightedVector(xt,yt);
    }

    //testing method
    public static void main(String[] args) {
        CosineFinder cf = new CosineFinder();
        ArrayList<WeightedVector> wv = cf.vectorFind(-3, -3, 1, 1);
        System.out.println(wv);
    }

}