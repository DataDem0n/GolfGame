package pathfindingbot;

import java.util.function.BiFunction;
import java.util.List;

public class Plot extends PathCalculator
{
    BiFunction<Double,Double,Double> terrain;
    double interval;
    double step;
    AdjacencyField adjacency;
    SlopeField slope;

    double ballCoorX, ballCoorY;
    int[][] plain;
    double[][] slopeFieldX;
    double[][] slopeFieldY;
    Physics newton = new Physics();
    double staticFriction;
    double kineticFriction;
    double[] targerRXY;
    double[] parameters;

    /**
     * constructor
     * @param terrain: this is the function that the adjacency field is build from
     * @param interval: this determines how big each tile is in for the adjacency field
     * @param adjacency: the adjacency field
     * @param slope: the slope field
     * @param ballCoorX: x-coordinate of the ball
     * @param ballCoorY: y-coordinate of the ball
     * @param staticFriction: the static friction that is being used
     * @param kineticFriction: the kinectic friction that is being used
     * @param targerRXY: an array containing the radius, x-coordinate and y-coordinate of the hole
     */


    public Plot(BiFunction<Double,Double,Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY, double staticFriction, double kineticFriction, double[] targerRXY)
    {

        super(adjacency, slope, ballCoorX, ballCoorY);
        this.terrain = terrain;
        this.interval = interval;
        step = 0.5*interval;
        this.adjacency = adjacency;
        plain = adjacency.floodFillUpdateSandpits();
         this.slopeFieldX = slope.slopeXCalculator();
         this.slopeFieldY = slope.slopeYCalculator();
        this.staticFriction = staticFriction;
        this.kineticFriction = kineticFriction;
        this.targerRXY = targerRXY;
        this.ballCoorX = ballCoorX;
        this.ballCoorY = ballCoorY;
        int[] ballPos = getBallPosition();
        System.out.println("ballposX: "+ballPos[0]);
        System.out.println("ballposY: "+ballPos[1]);
        List<List> getpathX = pathCalculatorX(ballPos[0], ballPos[1]);
        List<List> getpathY = pathCalculatorY(ballPos[0], ballPos[1]);
        List<List> getpathMixed = pathCalculatorMixed(ballPos[0], ballPos[1]);
        pathXX = getpathX.get(0);
        pathXY = getpathX.get(1);
        pathYX = getpathY.get(0);
        pathYY = getpathY.get(1);
        pathMX = getpathMixed.get(0);
        pathMY = getpathMixed.get(1);
        parameters = parameters();
    }

    /**
     * This method checks of the results are a new minimum coordinates, or maximum coordinates
     * @param parameters: current minumum and maximum coordinates
     * @param results: the coordinates that are being checked
     * @return: returns the updated minima and maxima
     */
    public double[] checker(double[] parameters, double[] results)
    {   
        if(parameters[0] > results[0])
        {
            parameters[0] = results[0];
        }
        if(parameters[1] < results[0])
        {
            parameters[1] = results[0];
        }
        if(parameters[2] > results[1])
        {
            parameters[2] = results[1];
        }
        if(parameters[3] < results[1])
        {
            parameters[3] = results[1];
        }
        return parameters;
    }

    /**
     * this method calculates what the range of the shot is with the max speed limit
     * @return: returns a radius in which the bot can take the shot with the max speed limit
     */
    public double[] parameters()
    {
      
        double minX = Integer.MAX_VALUE;
        double maxX = -25;
        double minY = Integer.MAX_VALUE;
        double maxY = -25;
        double[] parameters = {minX,maxX,minY,maxY};
        double[] coordinatesAndVelocity = {ballCoorX, ballCoorY, 5, 0.1, Integer.MAX_VALUE};
        RungeKutta2 rg = new RungeKutta2(terrain, coordinatesAndVelocity, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        double[] results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step1");
        System.out.println();
        

        double[] coordinatesAndVelocity2 = {ballCoorX, ballCoorY, 0.1, 5, Integer.MAX_VALUE};
         rg = new RungeKutta2(terrain, coordinatesAndVelocity2, kineticFriction, staticFriction, 0.0001, targerRXY, false);
         results = rg.coordinatesAndVelocityUntilStop(0.0001);
         parameters = checker(parameters, results);
         System.out.println("step2");
         System.out.println();
        
        double[] coordinatesAndVelocity3 = {ballCoorX, ballCoorY, -5, 0.1, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity3, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step3");
        System.out.println();

        
        double[] coordinatesAndVelocity4 = {ballCoorX, ballCoorY, 0.1, -5, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity4, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step4");
        System.out.println();

        double[] coordinatesAndVelocity5 = {ballCoorX, ballCoorY, 3, 4, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity5, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step5");
        System.out.println();
        
        double[] coordinatesAndVelocity6 = {ballCoorX, ballCoorY, -3, -4, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity6, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step6");
        System.out.println();

        double[] coordinatesAndVelocity7 = {ballCoorX, ballCoorY, -3, 4, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity7, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step7");
        System.out.println();

        double[] coordinatesAndVelocity8 = {ballCoorX, ballCoorY, 3, -4, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity8, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step8");
        System.out.println();

        
        double[] coordinatesAndVelocity9 = {ballCoorX, ballCoorY, 4, 3, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity9, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step9");
        System.out.println();



        double[] coordinatesAndVelocity10 = {ballCoorX, ballCoorY, -4, -3, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity10, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step10");
        System.out.println();

        

        double[] coordinatesAndVelocity11 = {ballCoorX, ballCoorY, -4, 3, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity11, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step11");
        System.out.println();



        double[] coordinatesAndVelocity12 = {ballCoorX, ballCoorY, 4, -3, Integer.MAX_VALUE};
        rg = new RungeKutta2(terrain, coordinatesAndVelocity12, kineticFriction, staticFriction, 0.0001, targerRXY, false);
        results = rg.coordinatesAndVelocityUntilStop(0.0001);
        parameters = checker(parameters, results);
        System.out.println("step12");
        System.out.println();



        return parameters;

    }

    /**
     * This method calculates the difference between the x-coordinates and y-coordinates
     * @param ballCoorX1: x-coordinate of the ball
     * @param ballCoorY1: y-coordinate of the ball
     * @param endPathX: the x-coordinate the bot is trying to reach
     * @param endPathY: the y-coordinate the bot is trying to reach
     * @return: an array containing the difference between the x-coordinates and y-coordinates
     */
    

    public double[] differenceCalc(double ballCoorX1, double ballCoorY1, double endPathX, double endPathY)
    {
        double[] difference = new double[2];
        double x0 = ballCoorX1;
        double x1 = endPathX;
        double y0 = ballCoorY1;
        double y1 = endPathY;

        difference[0] = Math.abs(x1 - x0);
        difference[1] = Math.abs(y1-  y0);
        return difference;

    }

    /**
     * This method calculates the relative difference between the x-length and y-length of the shot (to get the direction of the shot)
     * @param ballCoorX1: x-coordinate of the ball
     * @param ballCoorY1: y-coordinate of the ball
     * @param endPathX: the x-coordinate the bot is trying to reach
     * @param endPathY the y-coordinate the bot is trying to reach
     * @return: an unit vector giving the direction of the shot
     */
    public double unitCalc(double ballCoorX1, double ballCoorY1, double endPathX, double endPathY)
    {
        double[] difference = differenceCalc(ballCoorX1,ballCoorY1,endPathX,endPathY);
        double differenceX = difference[0];
        double differenceY = difference[1];
        if(differenceX <= 0.1)
        {
            return -666;
        }
        System.out.println("differenceX: "+differenceX);
        System.out.println("differenceY: "+differenceY);
        double unit = (differenceY/differenceX);
        return unit;
    }

    /**
     * This method checks if there are any obstacles at the current x and y position
     * @param currentX: current x-position
     * @param currentY: current y-position
     * @return: a boolean value
     */
    public boolean checkWaterOrTree(double currentX, double currentY)
    {
        double vX = currentX;
        double vY = currentY;
        

        if(vX >= 0)
        {
            vX += 25;
        }
        else
        {
         double temp = 25 - (vX*-1.0);
         vX = temp;
        }
        if(vY >= 0)
        {
            vY += 25;
        }
        else
        {
         double temp = 25 - (vY*-1.0);
         vY = temp;
        }
        vX /= interval;
        vY /= interval;
       
        if(plain[(int) vX][(int) vY] < 0)            
        {
            return true;
        }
        return false;
        
        
    }

    /**
     *  This method checks if there are any obstacles between the ball-coordinates and the coordinates the bot is trying to reach (plots the shot)
     * @param ballCoorX1: x-coordinate of the ball
     * @param ballCoorY1: y-coordinate of the ball
     * @param endpathX: the x-coordinate the bot is trying to reach
     * @param endpathY: the y-coordinate the bot is trying to reach
     * @return: a boolean value
     */
    public boolean shotPlottable(double ballCoorX1, double ballCoorY1, double endpathX, double endpathY)
    {
        double currentX = ballCoorX1;
        double currentY = ballCoorY1;

        double xcoor = endpathX;
        double ycoor = endpathY;
        
        double unitVec = unitCalc(ballCoorX1, ballCoorY1,endpathX,endpathY);

        if(xcoor < parameters[0] || xcoor > parameters[1] || ycoor < parameters[2] || ycoor > parameters[3])
        {
            return false;
        }

        if(unitVec == -666 && currentY < ycoor)
        {

            System.out.println("col1");
            while(currentY < ycoor)
            {
                            
                    if(checkWaterOrTree(currentX, currentY))
                    {
                        return false;
                    }
                    currentX += 0;
                    currentY += step;

            }
            return true;
        }

        if(unitVec == -666 && currentY > ycoor)
        {
            while(currentY > ycoor)
            {
                            
                    if(checkWaterOrTree(currentX, currentY))
                    {
                        return false;
                    }
                    currentX += 0;
                    currentY -= step;

            }
            return true;
        }

        if(currentX < xcoor && currentY <= ycoor)
        {
            while(currentX < xcoor || currentY < ycoor)
            {
                            
                    if(checkWaterOrTree(currentX, currentY))
                    {
                        return false;
                    }
                    currentX += step;
                    currentY += step*unitVec;
            }
            return true;
        }

        if(currentX < xcoor && currentY > ycoor)
        {
            while(currentX < xcoor || currentY > ycoor)
            {
                if(checkWaterOrTree(currentX, currentY))
                {
                    return false;
                }
                currentX += step;
                currentY -= step*unitVec;
            }
            return true;
        }

        if(currentX > xcoor && currentY > ycoor)
        {
            while(currentX > xcoor || currentY > ycoor)
            {
                if(checkWaterOrTree(currentX, currentY))
                {
                    return false;
                }
                currentX -= step;
                currentY -= step*unitVec;
            }
            return true;
        }

        if(currentX > xcoor && currentY <= ycoor)
        {
            while(currentX > xcoor || currentY < ycoor)
            {
                if(checkWaterOrTree(currentX, currentY))
                {
                    return false;
                }
                currentX -= step;
                currentY += step*unitVec;
            }
            return true;
        }

       return true;
    }

    /**
     * This method keeps backtracking in the path calculated by the A* pathfinding algorithm until the shot is plottable (no obstacles along the way)
     * @param pathXX: a list with the x-positions of the calculated path
     * @param pathXY: a list with the y-positions of the calculated path
     * @param ballCoorX1: x-coordinates of the ball
     * @param ballCoorY1: x-coordinates of the ball
     * @return: an array containing the best position the ball can reach without encountering any obstacles
     */
    public int[] getCorrectShotX(List pathXX, List pathXY, double ballCoorX1, double ballCoorY1)
    {
        int counter = 0;
        int[] correctPos = new int[3];
        double endX = pathConverter((int) pathXX.get(pathXX.size()-1));
        double endY = pathConverter((int) pathXY.get(pathXY.size()-1));

        
        while(!shotPlottable(ballCoorX1,ballCoorY1,endX,endY))
        {
            
            List completePath = decreasePath(pathXX, pathXY);
            pathXX = (List)completePath.get(0);
            pathXY = (List)completePath.get(1);
              endX = pathConverter((int) pathXX.get(pathXX.size()-1));
              endY = pathConverter((int) pathXY.get(pathXY.size()-1));

            counter++;
        }

            correctPos[0] =  (int) pathXX.get(pathXX.size()-1);
            correctPos[1] =  (int) pathXY.get(pathXY.size()-1);
            correctPos[2] = counter;

        return correctPos;
        
    }




    /**
     * This method keeps backtracking in the alternative path calculated by the A* pathfinding algorithm until the shot is plottable (no obstacles along the way)
     * @param pathYX: a list with the x-positions of the calculated path
     * @param pathYY: a list with the y-positions of the calculated path
     * @param ballCoorX1: x-coordinates of the ball
     * @param ballCoorY1: x-coordinates of the ball
     * @return: an array containing the best position the ball can reach without encountering any obstacles
     */
    public int[] getCorrectShotY(List pathYX, List pathYY, double ballCoorX1, double ballCoorY1)
    {
        int counter = 0;
        int[] correctPos = new int[3];
        double endX = pathConverter((int) pathYX.get(pathYX.size()-1));
        double endY = pathConverter((int) pathYY.get(pathYY.size()-1));
        
        while(!shotPlottable(ballCoorX1,ballCoorY1,endX,endY))
        {
            List completePath = decreasePath(pathYX, pathYY);                                                      
            pathYX = (List) completePath.get(0);
            pathYY = (List) completePath.get(1);

              endX = pathConverter((int) pathYX.get(pathYX.size()-1));
              endY = pathConverter((int) pathYY.get(pathYY.size()-1));
            counter++;
        }

            correctPos[0] =  (int) pathYX.get(pathYX.size()-1);
            correctPos[1] =  (int) pathYY.get(pathYY.size()-1);
            correctPos[2] = counter;
          
        // System.out.println("correctX: "+ correctPos[0]);
        // System.out.println("correctY: "+ correctPos[1]);
        return correctPos;
        
    }

    /**TODO
     *
     * @param container
     * @param neighbourX
     * @param neighbourY
     * @return
     */
//    public int[] getCorrectShotM(List pathMX, List pathMY, double ballCoorX1, double ballCoorY1)
//    {
//        int counter = 0;
//        int[] correctPos = new int[3];
//        double endX = pathConverter((int) pathMX.get(pathMX.size()-1));
//        double endY = pathConverter((int) pathMY.get(pathMY.size()-1));
//
//        while(!shotPlottable(ballCoorX1,ballCoorY1,endX,endY))
//        {
//            System.out.println("not plotted");
//            List completePath = decreasePath(pathYX, pathYY);
//            pathMX = (List) completePath.get(0);
//            pathMY = (List) completePath.get(1);
//             endX = pathConverter((int) pathMX.get(pathMX.size()-1));
//             endY = pathConverter((int) pathMY.get(pathMY.size()-1));
//            counter++;
//        }
//
//            correctPos[0] =  (int) pathMX.get(pathMX.size()-1);
//            correctPos[1] =  (int) pathMY.get(pathMY.size()-1);
//            correctPos[2] = counter;
//
//        // System.out.println("correctX: "+ correctPos[0]);
//        // System.out.println("correctY: "+ correctPos[1]);
//        return correctPos;
//
//    }





//    public boolean container(List<int[]> container, int neighbourX, int neighbourY)
//    {
//        for(int i = 0; i < container.size(); i++)
//        {
//            if(neighbourX == container.get(i)[0] && neighbourY == container.get(i)[1])
//            {
//                return true;
//            }
//        }
//        return false;
//    }




    /**
     * This method checks if the ball can stop on the calculated postion (so the slope is not too steep), else it picks another position to reach
     * @param ballCoorX1: x-coordinate of the ball
     * @param ballCoorY1: y-coordinate of the ball
     * @return: an array containing the position where the ball can come to a stop
     */
    public double[] slopeCompensator(double ballCoorX1, double ballCoorY1)
    {
       int[] correctPos;
       int[] correctPosX = getCorrectShotX(pathXX, pathXY, ballCoorX1, ballCoorY1);
       int[] correctPosY = getCorrectShotY(pathYX, pathYY, ballCoorX1, ballCoorY1);
       int[] correctPosM = getCorrectShotY(pathMX, pathMY, ballCoorX1, ballCoorY1);
       
       double[] slopeComp = new double[3];
       slopeComp[2] = 0.0;




       int min = correctPosX[2];
       correctPos = correctPosX;
        
       if(correctPosY[2] < min)
       {
           min = correctPosY[2];
           correctPos = correctPosY;
       }
       if(correctPosM[2] < min)
       {
           min = correctPosM[2];
           correctPos = correctPosM;

       }



       int tempPosX = correctPos[0];
       int tempPosY = correctPos[1];
       
       if(correctPosX[2] == correctPosY[2] && correctPosX[2] < correctPosM[2])
       {
        if(Math.sqrt(Math.pow(slopeFieldX[correctPosX[0]][correctPosX[1]], 2)  +   Math.pow(slopeFieldY[correctPosX[0]][correctPosX[1]], 2)) > staticFriction)
        {
            if(Math.sqrt(Math.pow(slopeFieldX[correctPosY[0]][correctPosY[1]], 2)  +   Math.pow(slopeFieldY[correctPosY[0]][correctPosY[1]], 2)) <= staticFriction)
            {
                slopeComp[0] = pathConverter(correctPosY[0]);
                slopeComp[1] = pathConverter(correctPosY[1]);
                return slopeComp;
            }
        }
        else
        {
            
            slopeComp[0] = pathConverter(correctPosX[0]);
            slopeComp[1] = pathConverter(correctPosX[1]);
            return slopeComp;
        }

       }    
       else if(correctPosY[2] == correctPosM[2] && correctPosY[2] < correctPosX[2])
       {
        if(Math.sqrt(Math.pow(slopeFieldX[correctPosY[0]][correctPosY[1]], 2)  +   Math.pow(slopeFieldY[correctPosY[0]][correctPosY[1]], 2)) > staticFriction)
        {
            if(Math.sqrt(Math.pow(slopeFieldX[correctPosM[0]][correctPosM[1]], 2)  +   Math.pow(slopeFieldY[correctPosM[0]][correctPosM[1]], 2)) <= staticFriction)
            {
                slopeComp[0] = pathConverter(correctPosM[0]);
                slopeComp[1] = pathConverter(correctPosM[1]);
                return slopeComp;
            }
        }
        else
        {
            
            slopeComp[0] = pathConverter(correctPosY[0]);
            slopeComp[1] = pathConverter(correctPosY[1]);
            return slopeComp;
        }

       }
       else if(correctPosX[2] == correctPosM[2] && correctPosX[2] < correctPosY[2])
       {
        if(Math.sqrt(Math.pow(slopeFieldX[correctPosX[0]][correctPosX[1]], 2)  +   Math.pow(slopeFieldY[correctPosX[0]][correctPosX[1]], 2)) > staticFriction)
        {
            if(Math.sqrt(Math.pow(slopeFieldX[correctPosM[0]][correctPosM[1]], 2)  +   Math.pow(slopeFieldY[correctPosM[0]][correctPosM[1]], 2)) <= staticFriction)
            {
                slopeComp[0] = pathConverter(correctPosM[0]);
                slopeComp[1] = pathConverter(correctPosM[1]);
                return slopeComp;
            }
        }
        else
        {
            
            slopeComp[0] = pathConverter(correctPosX[0]);
            slopeComp[1] = pathConverter(correctPosX[1]);
            return slopeComp;
        }
       }


       if(Math.sqrt(Math.pow(slopeFieldX[correctPos[0]][correctPos[1]], 2)  +   Math.pow(slopeFieldY[correctPos[0]][correctPos[1]], 2)) > staticFriction && plain[correctPos[0]][correctPos[1]] != 0)
       {

        if(Math.sqrt(Math.pow(slopeFieldX[correctPosX[0]][correctPosX[1]], 2)  +   Math.pow(slopeFieldY[correctPosX[0]][correctPosX[1]], 2)) > staticFriction)
        {
          slopeComp = new double[3];
         slopeComp[0] = pathConverter(correctPosX[0]);
         slopeComp[1] = pathConverter(correctPosX[1]);
         return slopeComp;
        }
        else if(Math.sqrt(Math.pow(slopeFieldX[correctPosY[0]][correctPosY[1]], 2)  +   Math.pow(slopeFieldY[correctPosY[0]][correctPosY[1]], 2)) > staticFriction)
        {
          slopeComp = new double[3];
         slopeComp[0] = pathConverter(correctPosY[0]);
         slopeComp[1] = pathConverter(correctPosY[1]);
         return slopeComp;
        }
        else if(Math.sqrt(Math.pow(slopeFieldX[correctPosM[0]][correctPosM[1]], 2)  +   Math.pow(slopeFieldY[correctPosM[0]][correctPosM[1]], 2)) > staticFriction)
        {
         slopeComp = new double[3];
         slopeComp[0] = pathConverter(correctPosM[0]);
         slopeComp[1] = pathConverter(correctPosM[1]);
         return slopeComp;
        }
        else
        {
            slopeComp[2] = 666.6;
        }
        }


       slopeComp[0] = pathConverter(tempPosX);
       slopeComp[1] = pathConverter(tempPosY);

       return slopeComp;


    }










    /**
     *  converts a position on the adjaceny field back to a coordinate on the terrain
     * @param path: a position on the adjaceny field
     * @return: a coordinate on the terrain
     */
    public double pathConverter(int path)
    {
        if(path*1.0*interval > 25)
        {
            return path*1.0*interval-25;
        }
        else
        {
            return (-1.0*25) + path*1.0*interval;
        }
    }





    // public double getCorrectVelocity()
    // {
    //     int ballCoordinatesX = path.getBallPosition()[0];
    //     int ballCoordinatesY = path.getBallPosition()[1];
    //     List listX = path.pathCalculator(ballCoordinatesX, ballCoordinatesY).get(0);
    //     List listY = path.pathCalculator(ballCoordinatesX, ballCoordinatesY).get(1);
    //     double endX = getCorrectShot(listX, listY)[0];
    //     double endY = getCorrectShot(listX, listY)[1];
    //     double totAccX;
    //     double totAccY;

    //     while(ballCoordinatesX < endX && ballCoordinatesY < endY)
    //     {
    //         accelerationrungeX(ballCoordinatesX, ballCoordinatesY, velx, vely, terrain, friction)
    //         ballCoordinatesX += step;
    //         ballCoordinatesX += step*unitCalc();
    //     }
    // }



    public static void main(String[] args) 
    {
        BiFunction<Double,Double,Double> terrain = (x,y)->0.4*(0.9-Math.exp(-1*(x*x+y*y)/8.0));            //the terrain (so the ai detects water)                      
        double[] coorTX = {};       //x-coordinates of the trees
        double[] coorTY = {};         //y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 4;
        double holeCoory = 1;
        double ballCoorX = -3;
        double ballCoorY = 0;
        double radius = 3;                                  //radius of all trees
        double[] beginX = {};                    //begin x-coordinates for the sandpits
        double[] endX = {};                       //end x-coordinates for the sandpits
        double[] beginY = {};                    //begin y-coordinates for the sandpits
        double[] endY = {};                       //end y-coordinates for the sandpits
        int sandpitResentment = 0;                         //the higher this value, the less likely the ai takes a route through a sandpit
        SlopeField b = new SlopeField(interval,terrain);  

        
        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY, b);
        double[] targetRXY = {1,4,1};




        Plot testing = new Plot(terrain,interval, a, b, ballCoorX, ballCoorY, 0.3, 0.15,targetRXY);
        // int[] shotX = testing.getCorrectShotX(testing.pathXX, testing.pathXY, ballCoorX, ballCoorY);
        // int[] shotM = testing.getCorrectShotX(testing.pathMX, testing.pathMY, ballCoorX, ballCoorY);
        // int[] shotY = testing.getCorrectShotY(testing.pathYX, testing.pathYY, ballCoorX, ballCoorY);
        // System.out.println();
        // System.out.println(shotX[0]);
        // System.out.println(shotX[1]);
        // System.out.println(shotX[2]);

        // System.out.println();
        // System.out.println(shotY[0]);
        // System.out.println(shotY[1]);
        // System.out.println(shotY[2]);

        // System.out.println();
        // System.out.println(shotM[0]);
        // System.out.println(shotM[1]);
        // System.out.println(shotM[2]);


        double[] correctShot = testing.slopeCompensator(ballCoorX, ballCoorY);
        System.out.println(correctShot[0]);
        System.out.println(correctShot[1]);
        System.out.println(correctShot[2]);



        


        
    }


}























        // List<Integer> pathXX = testing.pathXX;
        // List<Integer> pathXY = testing.pathXY;

//         System.out.println(pathXX.toString());
//         System.out.println(pathXY.toString());

// System.out.println();
// System.out.println();

//         List<Integer> pathYX = testing.pathYX;                          
//         List<Integer> pathYY = testing.pathYY;

//         System.out.println(pathYX.toString());
//         System.out.println(pathYY.toString());

// System.out.println();
// System.out.println();

//         List<Integer> pathMX = testing.pathMX;
//         List<Integer> pathMY = testing.pathMY;

//         System.out.println(pathMX.toString());
//         System.out.println(pathMY.toString());

























// Queue<int[]> queue = new ArrayDeque<int[]>();
// ArrayList<int[]> container = new ArrayList<int[]>();
// boolean found = false;
// int[] start = {correctPos[0], correctPos[1]};
// queue.add(start);
// List<int[]> box = new ArrayList<int[]>();

// while(queue.peek() != null && !found)                                                                   
// {
//     if(!container(box, queue.peek()[0]+1, queue.peek()[1]))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0]+1, queue.peek()[1], queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]+1), pathConverter(queue.peek()[1])};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0]+1, queue.peek()[1]};
//             queue.add(save);
//             box.add(save);
//         }
//     }



//     if(!container(box, queue.peek()[0]-1, queue.peek()[1]))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0]-1, queue.peek()[1], queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]-1), pathConverter(queue.peek()[1])};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0]-1, queue.peek()[1]};
//             queue.add(save);
//             box.add(save);
//         }
//     }


//     if(!container(box, queue.peek()[0], queue.peek()[1]+1))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0], queue.peek()[1]+1, queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]), pathConverter(queue.peek()[1]+1)};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0], queue.peek()[1]+1};
//             queue.add(save);
//             box.add(save);
//         }
//     }

    
//     if(!container(box, queue.peek()[0], queue.peek()[1]-1))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0], queue.peek()[1]-1, queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]), pathConverter(queue.peek()[1]-1)};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0], queue.peek()[1]-1};
//             queue.add(save);
//             box.add(save);
//         }
//     }
    

//     if(!container(box, queue.peek()[0]+1, queue.peek()[1]+1))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0]+1, queue.peek()[1]+1, queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]+1), pathConverter(queue.peek()[1]+1)};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0]+1, queue.peek()[1]+1};
//             queue.add(save);
//             box.add(save);
//         }
//     }
    

//     if(!container(box, queue.peek()[0]+1, queue.peek()[1]-1))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0]+1, queue.peek()[1]-1, queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]+1), pathConverter(queue.peek()[1]-1)};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0]+1, queue.peek()[1]-1};
//             queue.add(save);
//             box.add(save);
//         }
//     }
    

//     if(!container(box, queue.peek()[0]-1, queue.peek()[1]+1))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0]-1, queue.peek()[1]+1, queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]-1), pathConverter(queue.peek()[1]+1)};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0]-1, queue.peek()[1]+1};
//             queue.add(save);
//             box.add(save);
//         }
//     }
    

//     if(!container(box, queue.peek()[0]-1, queue.peek()[1]-1))
//     {
//         if(check(ballCoorX1, ballCoorY1, queue.peek()[0]-1, queue.peek()[1]-1, queue.peek()[0], queue.peek()[1]))
//         {
//             double[] returner = {pathConverter(queue.peek()[0]-1), pathConverter(queue.peek()[1]-1)};
//             return returner;
//         }
//         else
//         {
//             int[] save = {queue.peek()[0]-1, queue.peek()[1]-1};
//             queue.add(save);
//             box.add(save);
//         }
//     }
   

    



// }
//    //BFS search for closest neighbour without steep slope





    // public double getEuclideanDistance(double x1, double y1, double x2, double y2)              //took over from simulations (so valid)
    // {
    //     double distance = Integer.MAX_VALUE;
    //     distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    //     return distance;
    // }

    // public boolean check(double ballCoorX1, double ballCoorY1, int a, int b, int originalA, int originalB)
    // {
    //     if(Math.sqrt(Math.pow(slopeFieldX[a][b], 2)  +   Math.pow(slopeFieldY[a][b], 2)) < staticFriction && shotPlottable(ballCoorX1, ballCoorY1, pathConverter((int) a), pathConverter((int) b)) && getEuclideanDistance(ballCoorX1, ballCoorY1,  pathConverter((int) a), pathConverter((int) b)) > getEuclideanDistance(ballCoorX1, ballCoorY1,  pathConverter((int) originalA), pathConverter((int) originalB)) && plain[a][b] > 0)
    //     {
    //         return true;
    //     }
    //     else
    //     {
    //         return false;
    //     }
    // }