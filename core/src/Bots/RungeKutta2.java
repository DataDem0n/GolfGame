package Bots;

import java.util.function.BiFunction;

public class RungeKutta2 extends Physics{


    private BiFunction<Double, Double, Double> terrain;
    private double kFriction;
    private double sFriction;
    double stepsize;
    double[] targetRXY;
    public double[] tempCoordinates = new double [2];
    private double[] coordinatesAndVelocity;
    double radius;
    double[] treeX = {-10,10,-20,20};
    double[] treeY = {-10,10,-20,20};

    public RungeKutta2(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double stepsize){
        this.terrain = terrain;
        this.kFriction = kFriction;
        this.sFriction = sFriction;
        this.stepsize = stepsize;
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


        while(!hasBallStopped(coordinatesAndVelocity, sFriction, terrain, stepsize, kFriction))
        {

                // System.out.println("xvel: "+ coordinatesAndVelocity[2]);
                // System.out.println("yvel: "+ coordinatesAndVelocity[3]);
                // System.out.println("xcoor: "+ coordinatesAndVelocity[0]);
                // System.out.println("ycoor: "+ coordinatesAndVelocity[1]);
                //System.out.println();
                tempvelx1 = accelerationrungeX(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, kFriction)*step;        //getting x-velocity using midpoint
                tempvelx2 = coordinatesAndVelocity[2] + 0.5*tempvelx1;
                tempcoorx1 = coordinatesAndVelocity[0] + tempvelx2*step*0.5;
                tempvelx3 = accelerationrungeX(tempcoorx1,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;

                tempvely1 = accelerationrungeY(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, kFriction)*step;        //getting y-velocity using midpoint
                tempvely2 = coordinatesAndVelocity[3] + 0.5*tempvely1;
                tempcoory1 = coordinatesAndVelocity[1] + tempvely2*step*0.5;
                tempvely3 = accelerationrungeY(coordinatesAndVelocity[0],tempcoory1,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,kFriction)*step;

                //System.out.println("xACC: " + 0.5*(tempvelx1+tempvelx3));
                
                coordinatesAndVelocity[2] += 0.5*(tempvelx1+tempvelx3);
                //System.out.println("yACC: " + 0.5*(tempvely1+tempvely3));

                coordinatesAndVelocity[3] += 0.5*(tempvely1+tempvely3);

                coordinatesAndVelocity[0] += coordinatesAndVelocity[2]*step;
                coordinatesAndVelocity[1] += coordinatesAndVelocity[3]*step;
            
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

        
        //System.out.println("FINAL ACC: " + accelerationX(coordinatesAndVelocity, terrain, kFriction));
        return coordinatesAndVelocity;
    }

    

    

   public static void main(String[] args) 
   { 
       double[] coordinatesAndVelocity = {3.2092974430811343,1.7777481376936488, 3.0, -1.5};
       double stepsize = 0.001;
       double kfriction = 0.5;
       double staticFriction = 0.2;
       BiFunction<Double,Double,Double> terrain = (x,y)->0.4*(0.9-Math.exp(-1*(x*x+y*y)/8.0));
       //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.pow(Math.E, -((x*x+y*y)/40)));
      //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));
      //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));

       RungeKutta2 e = new RungeKutta2(terrain, coordinatesAndVelocity, kfriction, staticFriction, stepsize);
//        for (int i = 0; i < 100; i++) {
           System.out.println(stepsize +", " + e.coordinatesAndVelocityUntilStop(stepsize)[0] + ", "+ e.coordinatesAndVelocityUntilStop(stepsize)[1]+", ");
           //step = step + 0.001;
      //}

   }
}
