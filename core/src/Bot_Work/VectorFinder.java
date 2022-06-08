package Bot_Work;

import java.util.ArrayList;

public interface VectorFinder {
    public ArrayList<WeightedVector> vectorFind(double ballX,double ballY, double holeX, double holeY);
}
