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
    double fitnessValue = Integer.MAX_VALUE;
    boolean switcher;
 

    public RungeKutta2(BiFunction<Double, Double, Double> terrain, double[] coordinatesAndVelocity, double kFriction, double sFriction, double stepsize, double[] targetRXY, boolean switcher){
        this.terrain = terrain;
        this.kFriction = kFriction;
        this.sFriction = sFriction;
        this.stepsize = stepsize;
        this.coordinatesAndVelocity = coordinatesAndVelocity;               //the coordinatesAndVelocity, should also return the fitnessValue
        this.targetRXY = targetRXY;

    }

    public double getEuclideanDistance(double x1, double y1, double endX, double endY)
    {
        double distance = Integer.MAX_VALUE;
        double x2 = endX;
        double y2 = endY;
        distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
        return distance;
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
        double[] tempcoordinatesAndVelocity = coordinatesAndVelocity;
        int counter = 0;


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


                
                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException e) {

                // }
                // System.out.println("k"+kFriction);
                // System.out.println("s"+sFriction);
                // System.out.println(coordinatesAndVelocity[2]);
                // System.out.println(coordinatesAndVelocity[3]);
                // System.out.println("xACC: " + 0.5*(tempvelx1+tempvelx3));
                
                coordinatesAndVelocity[2] += 0.5*(tempvelx1+tempvelx3);
                //System.out.println("yACC: " + 0.5*(tempvely1+tempvely3));

                coordinatesAndVelocity[3] += 0.5*(tempvely1+tempvely3);

                coordinatesAndVelocity[0] += coordinatesAndVelocity[2]*step;
                coordinatesAndVelocity[1] += coordinatesAndVelocity[3]*step;


                
                
                
                if(switcher)
                {
                    if(coordinatesAndVelocity[0] < targetRXY[1]+targetRXY[0] && coordinatesAndVelocity[0] > targetRXY[1]-targetRXY[0] && coordinatesAndVelocity[1] < targetRXY[2]+targetRXY[0] && coordinatesAndVelocity[1] > targetRXY[2]-targetRXY[0] && coordinatesAndVelocity[2] <= 2.5 && coordinatesAndVelocity[3] <= 2.5)
                    {
                        return coordinatesAndVelocity;
                    }
                    if(coordinatesAndVelocity[2] <= 2.5 && coordinatesAndVelocity[3] <= 2.5)
                    {
                      double fitness = getEuclideanDistance(coordinatesAndVelocity[0], coordinatesAndVelocity[1], targetRXY[1], targetRXY[2]);
                        if(fitness < fitnessValue)
                        {
                            fitnessValue = fitness;
                            tempcoordinatesAndVelocity[0] = coordinatesAndVelocity[0];
                            tempcoordinatesAndVelocity[1] = coordinatesAndVelocity[1];
                            tempcoordinatesAndVelocity[2] = coordinatesAndVelocity[2];
                            tempcoordinatesAndVelocity[3] = coordinatesAndVelocity[3];
                            coordinatesAndVelocity[4] = fitnessValue;
                            counter++;
                        }
                    }
                }

                
            




            
        }


        
        if(switcher && counter > 0)
        {
            return tempcoordinatesAndVelocity;
        }
        else
        {
            return coordinatesAndVelocity;
        }
        
    }

    

    

   public static void main(String[] args) 
   { 
       double[] coordinatesAndVelocity = {3.0572986735408163,1.4420925491515144,2, 0.1, 768};
       double stepsize = 0.001;
       double kfriction = 0.15;
       double staticFriction = 0.3;
       BiFunction<Double,Double,Double> terrain = (x,y)->0.5*(Math.sin((x+y)/10))+1;          //
       double[] targetRXY = {1,4,1};
       //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(Math.pow(Math.E, -((x*x+y*y)/40)));
      //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));
      //BiFunction<Double,Double,Double> terrain = (x,y)->(double)(0.5*(Math.sin((x-y)/7)+0.9));

       RungeKutta2 e = new RungeKutta2(terrain, coordinatesAndVelocity, kfriction, staticFriction, stepsize, targetRXY, false);
//        for (int i = 0; i < 100; i++) {
           System.out.println(stepsize +", " + e.coordinatesAndVelocityUntilStop(stepsize)[0] + ", "+ e.coordinatesAndVelocityUntilStop(stepsize)[1]+", ");
           //step = step + 0.001;
      //}

   }
}
