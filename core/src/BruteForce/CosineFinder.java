package BruteForce;
import java.time.Duration;
import com.mygdx.game.main.DataField;

import java.util.ArrayList;

public class CosineFinder {

    final static double step = 0.2;

    ArrayList<Double> v;

    public ArrayList<WeightedVector> vectorFind(double ballX, double ballY, double holeX, double holeY, WeightedVector basisVector, double step, boolean reduce, double bound) {

        ArrayList<WeightedVector> out = new ArrayList<>();
        out.add(basisVector);

        out.addAll(lineFind(ballX,ballY,holeX,holeY,basisVector, step, bound ));

        if(reduce) {
            ArrayList<WeightedVector> temp = reduceVectors(out, 0.6);

            out.addAll(temp);

            for (int i = 0; i < 10; i++) {
                ArrayList<WeightedVector> y = reduceVectors(temp, 0.6);
                out.addAll(y);
                temp = y;
            }
        }

        System.out.println("reduce vector time loop.  " +System.nanoTime());
        return out;
    }

    public ArrayList<WeightedVector> reduceVectors(ArrayList<WeightedVector> out, double step) {
        ArrayList<WeightedVector> addition = new ArrayList<>();

        for (WeightedVector wv:out) {

            if (Math.signum(wv.getY())==-1&&Math.signum(wv.getX())==-1){
                addition.add(new WeightedVector(wv.getX()+step,wv.getY()+step));
            }

            else if (Math.signum(wv.getY())==1&&Math.signum(wv.getX())==1){
                addition.add(new WeightedVector(wv.getX()-step,wv.getY()-step));
            }

            else if (Math.signum(wv.getY())==1&&Math.signum(wv.getX())==-1){
                addition.add(new WeightedVector(wv.getX()+step,wv.getY()-step));
            }

            else if (Math.signum(wv.getY())==-1&&Math.signum(wv.getX())==1){
                addition.add(new WeightedVector(wv.getX()-step,wv.getY()+step));
            }
        }

        return addition;
    }

    private ArrayList<WeightedVector> lineFind(double ballX, double ballY, double holeX, double holeY, WeightedVector basisVector, double step, double bound) {
        ArrayList<WeightedVector> wv = new ArrayList<>();

        for (double i = step; i < 4.0; i=i+step) {
            double xAddition = -i;
            double yAddition = -i;

            if(ballX>holeX)
                xAddition = xAddition*-1;
            if(ballY>holeY)
                yAddition = yAddition*-1;
            if((holeX<ballX+1.0||holeX>ballX-1.0)){
                wv.add(new WeightedVector(basisVector.getX(), basisVector.getY()+xAddition));
                wv.add(new WeightedVector(basisVector.getX(), basisVector.getY()-xAddition));
                wv.add(new WeightedVector(basisVector.getX()+xAddition, basisVector.getY()));
                wv.add(new WeightedVector(basisVector.getX()-xAddition, basisVector.getY()));
            }
            else if((holeY<ballY+1.0 || holeY>ballY-1.0)){
                wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()+yAddition));
                wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()-yAddition));
                wv.add(new WeightedVector(basisVector.getX()+yAddition,basisVector.getY()));
                wv.add(new WeightedVector(basisVector.getX()-yAddition,basisVector.getY()));
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

    public WeightedVector vectorToPoint(double ballX, double ballY, double holeX, double holeY) {
        double xt = Distance(ballX, holeX);
        double yt = Distance(holeY, ballY);

        if (xt > 5.0){
            xt = 5.0;}

        if(yt>5.0) {
            yt = 5.0;
        }

        if(ballX > holeX)
            xt = -xt;
        if(ballY > holeY)
            yt = -yt;

        return new WeightedVector(xt,yt);
    }

    //testing method
    public static void main(String[] args) {
        CosineFinder cf = new CosineFinder();
    }

}