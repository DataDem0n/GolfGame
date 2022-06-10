package Bot_Work;

public class WeightedVector {

        private double x;
        private double y;
        private double weight;
        WeightedVector(double x, double y){
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
        public double getWeight() {
            return weight;
        }

    @Override
        public String toString(){
            return "x: "+x+" y: "+y+" w: "+weight;
        }
}
