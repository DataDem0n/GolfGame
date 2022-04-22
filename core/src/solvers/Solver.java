package solvers;

import java.util.function.BiFunction;

public interface Solver {
    public double[] coordinatesAndVelocityUntilStop(double step);
    public void setkFriction(double kFriction);
    public void setsFriction(double sFriction);
    public void setCoordinates(double X, double Y);
    public void setVelocity(double X, double Y);
    public void setTargetRXY(double[] targetRXY);
    public void setTerrain(BiFunction<Double,Double,Double> terrain);
    public double getXCoord();
    public double getYCoord();
}
