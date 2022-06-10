package Bot_Work;

import com.mygdx.game.main.DataField;

import java.util.ArrayList;
import java.util.Arrays;

public class CosineFinder implements VectorFinder{

    double step;
    double basisRatioX;
    double basisRatioY;

//    double ballX;
//    double ballY;
//    double holeX;
//    double holeY;
    ArrayList<Double> v;
    public CosineFinder() { //double ballX, double ballY, double holeX, double holeY
        step = 0.2;
//        this.ballX = ballX;
//        this.ballY = ballY;
//        this.holeX = holeX;
//        this.holeY = holeY;
    }

    @Override
    public ArrayList<WeightedVector> vectorFind(double ballX, double ballY, double holeX, double holeY) {
        WeightedVector basisVector;

        ArrayList<WeightedVector> out = new ArrayList<>();
        basisVector = vectorToPoint(ballX,ballY,holeX,holeY);
        out.add(basisVector);
        out.addAll(lineFind(ballX,ballY,holeX,holeY,basisVector));

        ArrayList<WeightedVector> temp = reduceVectors(out,step);
        out.addAll(temp);

        for (int i = 0; i < 5 ; i++) {
            ArrayList<WeightedVector> y = reduceVectors(temp,step);
            out.addAll(y);
            temp=y;
        }

        return out;
    }

    //for trying shorter shots
    public ArrayList<WeightedVector> reduceVectors(ArrayList<WeightedVector> out, double step){
        ArrayList<WeightedVector> addition = new ArrayList<>();

        for (WeightedVector wv:out) {
            if (Math.signum(wv.getY())==-step&&Math.signum(wv.getX())==-step){
                addition.add(new WeightedVector(wv.getX()+.5,wv.getY()+.5));
            }
            else if (Math.signum(wv.getY())==step&&Math.signum(wv.getX())==step){
                addition.add(new WeightedVector(wv.getX()-.5,wv.getY()-.5));
            }
            else if (Math.signum(wv.getY())==step&&Math.signum(wv.getX())==-step){
                addition.add(new WeightedVector(wv.getX()+.5,wv.getY()-.5));
            }
            else if (Math.signum(wv.getY())==-step&&Math.signum(wv.getX())==step){
                addition.add(new WeightedVector(wv.getX()-.5,wv.getY()+.5));
            }
        }

        return addition;
    }


    private ArrayList<WeightedVector> lineFind(double ballX, double ballY, double holeX, double holeY, WeightedVector basisVector){
        ArrayList<WeightedVector> wv = new ArrayList<>();
        for (double i = step; i < 5.0; i=i+step) {
            double xAddition = -i;
            double yAddition = -i;

            if(ballX>holeX)
                xAddition = xAddition*-1;
            if(ballY>holeY)
                yAddition = yAddition*-1;

            //if the y vectors/ x vectors are less than 5 then fix it.

            if((holeX<ballX+1.0||holeX>ballX-1.0)){
                wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()+xAddition));
                wv.add(new WeightedVector(basisVector.getX(),basisVector.getY()-xAddition));
            }
            else if((holeY<ballY+1.0 || holeY>ballY-1.0)){
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

    //Distance between two points method (can use the return as a weight value if needed)
    private double Euclidistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow((x2-x1) ,2) + Math.pow((y2-y1), 2));
    }


    public WeightedVector vectorToPoint(double ballX, double ballY, double holeX, double holeY){
        double xt = Distance(ballX,holeX);
        double yt = Distance(holeY,ballY);

        if (xt>5.0)
            xt =5.0;

        if(yt>5.0)
            yt=5.0;

        if(ballX>holeX)
            xt = -xt;
        if(ballY>holeY)
            yt = -yt;

        return new WeightedVector(xt,yt);
    }

//    public double[] findMax(double x,double y){
//        double basisRatio = Math.min(x,y)/Math.max(x,y);
//        if(x>y)
//            return new double[]{5,5*basisRatio};
//        else
//            return new double[]{5*basisRatio, 5};
//
//    }


    //testing method
    public static void main(String[] args) {
        CosineFinder cf = new CosineFinder();
        ArrayList<WeightedVector> wv = cf.vectorFind(-3, 0, 4, 1);
        System.out.println(wv);
    }

}