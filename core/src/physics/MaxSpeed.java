package physics;

public class MaxSpeed {
    /**
     * Method that checks whether the MAX_SPEED is exceeded, if so then it prevserves the direction of the resultant vector but shortens it to MAX_LENGTH
     * @param coordinatesAndVelocity an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions
     * @return an array with coordinates X and Y on first two positions and velocities X and Y in 3,4 positions with shortened vectors
     */

    public double[] maxSpeedReached(double [] coordinatesAndVelocity)
    {
        double unitVectorX = 0;
        double unitVectorY = 0;
        if(Math.sqrt(Math.pow(coordinatesAndVelocity[2], 2) + Math.pow(coordinatesAndVelocity[3], 2)) > 5){
            unitVectorX = (coordinatesAndVelocity[2]/(Math.sqrt(Math.pow(coordinatesAndVelocity[2], 2) + Math.pow(coordinatesAndVelocity[3], 2))));
            unitVectorY = (coordinatesAndVelocity[3]/(Math.sqrt(Math.pow(coordinatesAndVelocity[2], 2) + Math.pow(coordinatesAndVelocity[3], 2))));
            coordinatesAndVelocity[2] = 5 * unitVectorX;
            coordinatesAndVelocity[3] = 5 * unitVectorY;
        }
        return coordinatesAndVelocity;
    }
}
