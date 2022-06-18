package BruteForce;

import java.util.ArrayList;

public class VectorFinder {
    /** This method uses all other methods to find a list of vectors that the bot should simulate
     * @param ballX ball position in x plane
     * @param ballY ball position in y plane
     * @param holeX hole position in x plane
     * @param holeY hole position in y plane
     * @param basisVector The initial vector to start (using line find).
     * @param step the size of the iterations that the finder returns
     * @param reduce boolean to decide if we want to find reduced vectors
     * @param bound the bound or angle at which we look for vectors
     * @return returns a list of vectors the bot will simulate
     */
    public ArrayList<WeightedVector> vectorFind(double ballX, double ballY, double holeX, double holeY, WeightedVector basisVector, double step, boolean reduce, double bound) {

        ArrayList<WeightedVector> out = new ArrayList<>();
        out.add(basisVector);

        out.addAll(lineFind(ballX,ballY,holeX,holeY,basisVector, step));

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
    /** reduces the magnitude of vectors
     *
     * @param out the vectors we want to reduce in magnitude
     * @param step the size of the reduction.
     * @return the reduced vectors and the initial  vectors
     */
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
    /** creates a bunch of vectors that are similar in magnitude to the basis vector but in altering directions.
     * @param ballX ball position in x plane
     * @param ballY ball position in y plane
     * @param holeX hole position in x plane
     * @param holeY hole position in y plane
     * @param basisVector The initial vector to start (using line find).
     * @param step the size of the iterations that the finder returns
     * @return and arraylist of vectors that are similar in magnitude to the basis vector but in altering directions.
     */
    private ArrayList<WeightedVector> lineFind(double ballX, double ballY, double holeX, double holeY, WeightedVector basisVector, double step) {
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

    /** finds Euclidean distance between two coordinates.
     *
     * @param x1 coordinate of the first position
     * @param x2 coordinate of the first position
     * @return the distance between the two.
     */
    private double Distance(double x1, double x2){
        return Math.sqrt(Math.pow((x2-x1) ,2));
    }

    /** this method was adapted from the basic rule based bot, its uses the distance from the hole to the ball
     *  and takes the ratio between the two to calculate a shot for the ball.
     *
     * @param ballX ball position in x plane
     * @param ballY ball position in y plane
     * @param holeX hole position in x plane
     * @param holeY hole position in y plane
     * @return The weighted vector that leads the bot directly to the hole based the distance from the hole.
     */
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
}