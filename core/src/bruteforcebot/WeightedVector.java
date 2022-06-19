package bruteforcebot;

public class WeightedVector {

        private double x;
        private double y;
        private double weight;

    /**
     *
     * @param x the x velocity
     * @param y the y velocity
     */
    WeightedVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    /** getter for x velocity
     *
     * @return
     */
    public double getX() {
            return x;
        }

    /** getter for y velocity
     *
     * @return
     */
        public double getY() {
            return y;
        }

    /** sets the x velocity
     *
     * @param x the x velocity to be set
     */
        public void setX(double x) {
            this.x = x;
        }

    /** sets the y velocity
     *
     * @param y the y velocity to be set
     */
        public void setY(double y) {
            this.y = y;
        }

    /** sets the weight
     *
     * @param weight a double indicating the weight of the vector
     */
        public void setWeight(double weight) {
            this.weight = weight;
        }

    /** getter for the weight
     *
     * @return a double value: the weight.
     */
        public double getWeight() {
            return weight;
        }

    /** override for the java object to string method
     *
     * @return a readable form of the vector. String
     */
    @Override
        public String toString(){
            return "x: "+x+" y: "+y+" w: "+weight;
        }
}
