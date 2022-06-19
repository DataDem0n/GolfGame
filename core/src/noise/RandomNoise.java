package noise;

import java.util.ArrayList;

public class RandomNoise {
    private double lowerBound;
    private double upperBound;
    private static ArrayList<Double> seed;

    /**
     * This method creates random noise based on an upper bound and a lower bound to be used as a multiplier when taking shots with our bots
     * @param lowerBound represents the lower bound of the noise multiplier.
     * @param upperBound represents the upper bound of the noise multiplier.
     */
    public RandomNoise(double lowerBound, double upperBound){
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seed = generateSeed();
    }

    /**
     * This method generates a seed for creating consistent noise between the bots
     * @return the seed value
     */
    public ArrayList<Double> generateSeed(){
        ArrayList<Double> temp = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            temp.add((Math.random()*(upperBound-lowerBound))+lowerBound);
        }
        seed = temp;
        return seed;
    }
}