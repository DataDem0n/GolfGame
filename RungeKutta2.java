import java.util.function.BiFunction;

public class RungeKutta2 extends Physics{


    private BiFunction<Double, Double, Double> terrain;
    private double kFriction;
    private double sFriction;
    double[] targetRXY;
    public double[] tempCoordinates = new double [2];
    private double[] coordinatesAndVelocity;
    double radius;
    double[] treeX = {-10,10,-20,20};
    double[] treeY = {-10,10,-20,20};

    public RungeKutta2(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction){
        this.terrain = terrain;
        this.coordinatesAndVelocity = coordinatesAndVelocity;
    }


    public double[] coordinatesAndVelocityUntilStop(double step){

        double tempvelx1;
        double tempvely1;
        double tempvelx2;
        double tempvely2;
        double tempvelx3;
        double tempvely3;
        double tempcoorx1;
        double tempcoory1;


        while(!hasBallStopped(coordinatesAndVelocity, sFriction, terrain)){
                tempvelx1 = accelerationrungeX(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, kFriction)*step;        //getting x-velocity using midpoint
                // System.out.println("K1 = " + tempvelx1);
                tempvelx2 = coordinatesAndVelocity[2] + 0.5*tempvelx1;
                tempcoorx1 = coordinatesAndVelocity[0] + tempvelx2*step*0.5;
                tempvelx3 = accelerationrungeX(tempcoorx1,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;      
                // System.out.println("K2 = " + tempvelx2);                  


                tempvely1 = accelerationrungeY(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, kFriction)*step;        //getting y-velocity using midpoint
                tempvely2 = coordinatesAndVelocity[3] + 0.5*tempvely1;
                tempcoory1 = coordinatesAndVelocity[1] + tempvely2*step*0.5;
                tempvely3 = accelerationrungeY(coordinatesAndVelocity[0],tempcoory1,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;


                coordinatesAndVelocity[2] += 0.5*(tempvelx1+tempvelx3);
                // System.out.println("dX = " + 0.5*(tempvelx1+tempvelx3));    
                // System.out.println("Xtot = " + coordinatesAndVelocity[2]);        
                coordinatesAndVelocity[3] += 0.5*(tempvely1+tempvely3);
            //here updating the coordinates based on calculated velocities (step = timeInterval ALWAYS)
            coordinatesAndVelocity[0] = coordinatesAndVelocity[0] + coordinatesAndVelocity[2]*step;
            coordinatesAndVelocity[1] = coordinatesAndVelocity[1] + coordinatesAndVelocity[3]*step;
        //     try {
        //         Thread.sleep(1000);
        //     }
        // catch (Exception e) 
        // {
        // }


            //testing purposes:
            // System.out.println(" ACC:  " + accelerationX(coordinatesAndVelocity, terrain, kFriction));
            // System.out.println("   ---------   ");
            // System.out.println("  X:"+ coordinatesAndVelocity[0] + "  Y:" + coordinatesAndVelocity[1] + "   VelX:" + coordinatesAndVelocity[2] + "  VelY" + coordinatesAndVelocity[3]);
        }

        
        System.out.println("FINAL ACC: " + accelerationX(coordinatesAndVelocity, terrain, kFriction));
        return coordinatesAndVelocity;
    }

    

    

//    public static void main(String[] args) { testing
//        double[] coordinatesAndVelocity = {0,0,2,0};
//        double[] target = {1,4,4};
//        double kfriction = 0.05;
//        double staticFriction = 0.2;
//        BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.1*x+1);
//        //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.pow(Math.E, -((x*x+y*y)/40)));
//       //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));
//       //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));
//        double step = 0.0000000001;
//        EulerSolver e = new EulerSolver(terrain, coordinatesAndVelocity, kfriction, staticFriction, target);
////        for (int i = 0; i < 100; i++) {
//            System.out.println(step +", " + e.coordinatesAndVelocityUntilStop(step)[0] + ", "+ e.coordinatesAndVelocityUntilStop(step)[1]+", ");
//            //step = step + 0.001;
//       //}
//
//    }
}
