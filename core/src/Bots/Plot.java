//package Bots;
//
//import java.util.function.BiFunction;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//public class Plot
//{
//    private BiFunction<Double,Double,Double> terrain;
//    private double interval;
//    private double step;
//    private AdjacencyField adjacency;
//    private int[][] plain;
//    private PathCalculator pathCalc;
//
//    /**
//     * A constructor made to create the plot
//     * @param terrain this is the terrain function
//     * @param interval
//     * @param adjacency this adds the adjacency field
//     * @param slope height we consider a slope
//     * @param ballCoorX ball x
//     * @param ballCoorY ball y
//     */
//    public Plot(BiFunction<Double,Double,Double> terrain, double interval, AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY, PathCalculator pathCalc)
//    {
//        this.pathCalc = pathCalc;
//        this.terrain = terrain;
//        this.interval = interval;
//        step = 0.5*interval;
//        this.adjacency = adjacency;
//        plain = adjacency.floodFillUpdateSandpits();
//        int[] ballPos = pathCalc.getBallPosition();
//        List<List> getpath = pathCalc.pathCalculator(ballPos[0], ballPos[1]);
//        pathCalc.pathX = getpath.get(0);
//        pathCalc.pathY = getpath.get(1);
//
//    }
//
//    /**
//     * A method to calculate the difference between
//     * @param ballCoorX1 ball x
//     * @param ballCoorY1 ball y
//     * @return the difference
//     */
//    public double[] differenceCalc(double ballCoorX1, double ballCoorY1)                                                        //calculates the dx and dy of the shot
//    {
//        double[] difference = new double[2];
//        double x0 = ballCoorX1;
//        double x1 = pathConverter(pathCalc.pathX.get(pathCalc.pathX.size()-1));
//        double y0 = ballCoorY1;
//        double y1 = pathConverter(pathCalc.pathY.get(pathCalc.pathY.size()-1));
//
//        difference[0] = Math.abs(x1 - x0);
//        difference[1] = Math.abs(y1-  y0);
//        return difference;
//
//    }
//
//    /**
//     *
//     * @param ballCoorX1 ball x
//     * @param ballCoorY1 ball y
//     * @return the ratio between y and x
//     */
//    public double unitCalc(double ballCoorX1, double ballCoorY1)                                                                    //doesnt work if x == 0, add another argument to shotplottable.
//    {
//        double[] difference = differenceCalc(ballCoorX1,ballCoorY1);
//        double differenceX = difference[0];
//        double differenceY = difference[1];
//        if(differenceX <= 0.1)
//        {
//            return -666;
//        }
//
//        double unit = (differenceY/differenceX);                            //messes up if X is very small
//
//        return unit;
//    }
//
//    /**
//     *
//     * @param currentX current x being written
//     * @param currentY current y being written
//     * @return true if its a water or a tree
//     */
//    public boolean checkWaterOrTree(double currentX, double currentY)
//    {
//        double vX = currentX;
//        double vY = currentY;
//
//
//        if(vX >= 0)
//        {
//            vX += 25;
//        }
//        else
//        {
//         double temp = 25 - (vX*-1.0);
//         vX = temp;
//        }
//        if(vY >= 0)
//        {
//            vY += 25;
//        }
//        else
//        {
//         double temp = 25 - (vY*-1.0);
//         vY = temp;
//        }
//        vX /= interval;
//        vY /= interval;
//
//
//        if(plain[(int) vX][(int) vY] < 0)
//        {
//            return true;
//        }
//        return false;
//
//
//    }
//
//    /**
//     * checks if the shot finishes in normal terrain
//     * @param ballCoorX1 balls x
//     * @param ballCoorY1 balls y
//     * @return false if its not possible
//     */
//
//    public boolean shotPlottable(double ballCoorX1, double ballCoorY1)
//    {
//        double currentX = ballCoorX1;
//        double currentY = ballCoorY1;
//        double xcoor = pathConverter(pathCalc.pathX.get(pathCalc.pathX.size()-1));
//        double ycoor = pathConverter(pathCalc.pathY.get(pathCalc.pathY.size()-1));
//        double unitVec = unitCalc(ballCoorX1, ballCoorY1);
//
//        if(unitVec == -666 && currentY < ycoor)                                        //valid
//        {
//
//            while(currentY < ycoor)
//            {
//                    if(checkWaterOrTree(currentX, currentY))
//                    {
//                        return false;
//                    }
//
//                    currentX += 0;
//                    currentY += step;
//
//            }
//            return true;
//        }
//
//        if(unitVec == -666 && currentY > ycoor)                                         //valid
//        {
//
//            while(currentY > ycoor)
//            {
//
//                    if(checkWaterOrTree(currentX, currentY))
//                    {
//                        return false;
//                    }
//
//                    currentX += 0;
//                    currentY -= step;
//
//
//            }
//            return true;
//        }
//
//
//        if(currentX < xcoor && currentY <= ycoor)
//        {
//
//            while(currentX < xcoor || currentY < ycoor)
//            {
//
//                    if(checkWaterOrTree(currentX, currentY))
//                    {
//                        return false;
//                    }
//
//                    currentX += step;
//                    currentY += step*unitVec;
//
//
//            }
//            return true;
//        }
//
//        if(currentX < xcoor && currentY > ycoor)
//        {
//
//            while(currentX < xcoor || currentY > ycoor)
//            {
//                if(checkWaterOrTree(currentX, currentY))
//                {
//                    return false;
//                }
//
//                currentX += step;
//                currentY -= step*unitVec;
//            }
//            return true;
//        }
//
//
//        if(currentX > xcoor && currentY > ycoor)
//        {
//            while(currentX > xcoor || currentY > ycoor)
//            {
//                if(checkWaterOrTree(currentX, currentY))
//                {
//                    return false;
//                }
//                currentX -= step;
//                currentY -= step*unitVec;
//            }
//            return true;
//        }
//
//
//
//        if(currentX > xcoor && currentY <= ycoor)
//        {
//
//            while(currentX > xcoor || currentY < ycoor)
//            {
//                if(checkWaterOrTree(currentX, currentY))
//                {
//                    return false;
//                }
//                currentX -= step;
//                currentY += step*unitVec;
//            }
//            return true;
//        }
//
//
//       return true;
//    }
//
//    /**
//     *
//     * @param pathX list of x coords
//     * @param pathY list of y coords
//     * @param ballCoorX1 ball x
//     * @param ballCoorY1 ball y
//     * @return double array
//     */
//    public double[] getCorrectShot(List pathX, List pathY, double ballCoorX1, double ballCoorY1)
//    {
//        double[] correctPos = new double[2];
//
//        while(!shotPlottable(ballCoorX1,ballCoorY1))
//        {
//
//            pathX = (List) pathCalc.decreasePath(pathX, pathY).get(0);
//            pathY = (List) pathCalc.decreasePath(pathX, pathY).get(1);
//        }
//
//            correctPos[0] = pathConverter((int) pathX.get(pathX.size()-1));
//            correctPos[1] = pathConverter((int) pathY.get(pathY.size()-1));
//
//
//        return correctPos;
//
//    }
//
//    public double pathConverter(int path)
//    {
//        if(path*1.0*interval > 25)
//        {
//            return path*1.0*interval-25;
//        }
//        else
//        {
//            return (-1.0*25) + path*1.0*interval;
//        }
//    }
//
//
//    // public double getCorrectVelocity()
//    // {
//    //     int ballCoordinatesX = path.getBallPosition()[0];
//    //     int ballCoordinatesY = path.getBallPosition()[1];
//    //     List listX = path.pathCalculator(ballCoordinatesX, ballCoordinatesY).get(0);
//    //     List listY = path.pathCalculator(ballCoordinatesX, ballCoordinatesY).get(1);
//    //     double endX = getCorrectShot(listX, listY)[0];
//    //     double endY = getCorrectShot(listX, listY)[1];
//    //     double totAccX;
//    //     double totAccY;
//
//    //     while(ballCoordinatesX < endX && ballCoordinatesY < endY)
//    //     {
//    //         accelerationrungeX(ballCoordinatesX, ballCoordinatesY, velx, vely, terrain, friction)
//    //         ballCoordinatesX += step;
//    //         ballCoordinatesX += step*unitCalc();
//    //     }
//    // }
//
//
//    /**
//     * testing method.
//     * @param args
//     */
//    public static void main(String[] args)
//    {
//        BiFunction<Double,Double,Double> terrain = (x,y)->(double)0.4*(0.9-Math.exp(-1*(x*x+y*y)/8.0));            //the terrain (so the ai detects water)
//        double[] coorTX = {};       //x-coordinates of the trees
//        double[] coorTY = {};         //y-coordinates of the trees
//        double interval = 1;
//        double holeCoorx = 4;
//        double holeCoory = 1;
//        double ballCoorX = -3;
//        double ballCoorY = 0;
//        double radius = 3;                                  //radius of all trees
//        double[] beginX = {};                    //begin x-coordinates for the sandpits
//        double[] endX = {};                       //end x-coordinates for the sandpits
//        double[] beginY = {};                    //begin y-coordinates for the sandpits
//        double[] endY = {};                       //end y-coordinates for the sandpits
//        int sandpitResentment = 1;                         //the higher this value, the less likely the ai takes a route through a sandpit
//        AdjacencyField a = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX, coorTY, radius, beginX, endX, beginY, endY);
//        SlopeField b = new SlopeField(interval,terrain);
//
//
//
//
//        //Plot testing = new Plot(terrain,interval, a, b, ballCoorX, ballCoorY);
//        //System.out.println(testing.shotPlottable());                                                    //works
//        // System.out.println();
//        //double[] correctShot = testing.getCorrectShot(testing.pathCalc.pathX,testing.pathCalc.pathY,ballCoorX,ballCoorY);
//        //System.out.println("x: "+correctShot[0]);
//        //System.out.println("y: "+correctShot[1]);
//    }
//
//}
