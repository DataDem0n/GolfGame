package Noise;

import java.util.ArrayList;
import java.util.Random;

public class RandomNoise {
    private double lowerBound;
    private double upperBound;
    private static ArrayList<Double> seed;

    public RandomNoise(double lowerBound, double upperBound){
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seed = generateSeed();
    }

    public ArrayList<Double> generateSeed(){
        ArrayList<Double> temp = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            temp.add((Math.random()*(upperBound-lowerBound))+lowerBound);
        }
        seed = temp;
        return seed;
    }

//    public static void main(String[] args) {
//        RandomNoise n = new RandomNoise(0.1, 0.3);
//        System.out.println(n.generateSeed());
//    }
}
