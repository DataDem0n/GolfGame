package Bots;

import com.mygdx.game.main.DataField;

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


        while(!hasBallStopped(coordinatesAndVelocity, DataField.sFriction, terrain, stepsize, DataField.kFriction) )     //|| (((coordinatesAndVelocity[0] > DataField.targetRXY[1] -DataField.targetRXY[0] && coordinatesAndVelocity[0] < DataField.targetRXY[1]+DataField.targetRXY[0]) && (coordinatesAndVelocity[1] > DataField.targetRXY[2]-DataField.targetRXY[0] && coordinatesAndVelocity[1] < DataField.targetRXY[2]+DataField.targetRXY[0])) && coordinatesAndVelocity[2] <= 2.0 && coordinatesAndVelocity[3] <= 2.0)
        {
            if (coordinatesAndVelocity[2] == 0 && coordinatesAndVelocity[3] == 0) {
                coordinatesAndVelocity[2] = coordinatesAndVelocity[2] + (step * accelerationX2(coordinatesAndVelocity, terrain, DataField.kFriction)); //X-Velocity = xVelocity + step*acc
                coordinatesAndVelocity[3] = coordinatesAndVelocity[3] + (step * accelerationY2(coordinatesAndVelocity, terrain, DataField.kFriction)); //Y-Velocity = YVelocity + step*acc
            } else {
                tempvelx1 = accelerationrungeX(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, DataField.kFriction)*step;        //getting x-velocity using midpoint
                tempvelx2 = coordinatesAndVelocity[2] + 0.5*tempvelx1;
                tempcoorx1 = coordinatesAndVelocity[0] + tempvelx2*step*0.5;
                tempvelx3 = accelerationrungeX(tempcoorx1,coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;

                tempvely1 = accelerationrungeY(coordinatesAndVelocity[0],coordinatesAndVelocity[1],coordinatesAndVelocity[2],coordinatesAndVelocity[3] , terrain, DataField.kFriction)*step;        //getting y-velocity using midpoint
                tempvely2 = coordinatesAndVelocity[3] + 0.5*tempvely1;
                tempcoory1 = coordinatesAndVelocity[1] + tempvely2*step*0.5;
                tempvely3 = accelerationrungeY(coordinatesAndVelocity[0],tempcoory1,coordinatesAndVelocity[2],coordinatesAndVelocity[3],terrain,DataField.kFriction)*step;

//                System.out.println("xACC: " + 0.5*(tempvelx1+tempvelx3));
                coordinatesAndVelocity[2] += 0.5*(tempvelx1+tempvelx3);
//                System.out.println("yACC: " + 0.5*(tempvely1+tempvely3));

                coordinatesAndVelocity[3] += 0.5*(tempvely1+tempvely3);


            }
            coordinatesAndVelocity[0] += coordinatesAndVelocity[2]*step;
            coordinatesAndVelocity[1] += coordinatesAndVelocity[3]*step;
        //     try {
        //         Thread.sleep(1000);
        //     }
        // catch (Exception e) 
        // {
        // }


            //testing purposes:
            // System.out.println(" ACC:  " + accelerationX(coordinatesAndVelocity, terrain, DataField.kFriction));
            // System.out.println("   ---------   ");
            // System.out.println("  X:"+ coordinatesAndVelocity[0] + "  Y:" + coordinatesAndVelocity[1] + "   VelX:" + coordinatesAndVelocity[2] + "  VelY" + coordinatesAndVelocity[3]);
            //System.out.println((((coordinatesAndVelocity[0] > DataField.targetRXY[1]-0.5 && coordinatesAndVelocity[0] < DataField.targetRXY[1]+0.5) && (coordinatesAndVelocity[1] > DataField.targetRXY[2]-0.5 && coordinatesAndVelocity[1] < DataField.targetRXY[1]+0.5)) && coordinatesAndVelocity[2] <= 2.0 && coordinatesAndVelocity[3] <= 2.0));
            
        }

        
        //System.out.println("FINAL ACC: " + accelerationX(coordinatesAndVelocity, terrain, DataField.kFriction));
        return coordinatesAndVelocity;
    }

    

    

   public static void main(String[] args) 
   { 
       double[] coordinatesAndVelocity = {0,0,1,1};
       double stepsize = 0.001;
       double kFriction = 0.05;
       double staticFriction = 0.1;
       BiFunction<Double,Double,Double> terrain = (x,y)->(double)(1);
       //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.pow(Math.E, -((x*x+y*y)/40)));
      //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));
      //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));

       RungeKutta2 e = new RungeKutta2(terrain, coordinatesAndVelocity, DataField.kFriction, staticFriction, stepsize);
//        for (int i = 0; i < 100; i++) {
           System.out.println(stepsize +", " + e.coordinatesAndVelocityUntilStop(stepsize)[0] + ", "+ e.coordinatesAndVelocityUntilStop(stepsize)[1]+", ");
           //step = step + 0.001;
      //}

   }
}
