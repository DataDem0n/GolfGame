import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

public class PathCalculator {
    
    double ballCoorX;
    double ballCoorY;
    AdjacencyField adjacency;
    SlopeField slope;
    ArrayList<Integer> pathX;
    ArrayList<Integer> pathY;


    public PathCalculator(AdjacencyField adjacency, SlopeField slope, double ballCoorX, double ballCoorY)
    {
        this.adjacency = adjacency;
        this.slope = slope;
        this.ballCoorX = ballCoorX;
        this.ballCoorY = ballCoorY;
        pathX = new ArrayList<Integer>();
        pathY = new ArrayList<Integer>();
    }

    public int[] getBallPosition()
    {
        int[] arrayPosition = new int[2];
        double coordinateX = ballCoorX;
        double coordinateY = ballCoorY;

        if(coordinateX > 0)
        {
        coordinateX += 25;
        }
        if(coordinateY > 0)
        {
        coordinateY += 25;
        }
        
        arrayPosition[0] = (int)(coordinateX/adjacency.interval);
        arrayPosition[1] = (int)(coordinateY/adjacency.interval);
        
        pathX.add(arrayPosition[0]);
        pathY.add(arrayPosition[1]);
        
        return arrayPosition;
    }



    public List<List> pathCalculator(int xPos, int yPos)
    {
        int min = Integer.MAX_VALUE;
        int minX = 0;
        int minY = 0;
        ArrayList getPath = new ArrayList<List>();
        if(adjacency.floodFillUpdateSandpits()[xPos][yPos] == 0)
        {
            System.out.println("PATH FOUND");
            getPath.add(pathX);
            getPath.add(pathY);
            return getPath;
        }
        else
        {
            if(xPos + 1 < adjacency.floodFillUpdateSandpits().length)
            {
                  if(min > adjacency.floodFillUpdateSandpits()[xPos+1][yPos] && adjacency.floodFillUpdateSandpits()[xPos+1][yPos] >= 0)
                  {
                      min = adjacency.floodFillUpdateSandpits()[xPos+1][yPos];
                      minX = xPos+1;
                      minY = yPos;
                  }
                  
            }
            if(xPos - 1 >= 0)
            {
                if(min > adjacency.floodFillUpdateSandpits()[xPos-1][yPos] && adjacency.floodFillUpdateSandpits()[xPos-1][yPos] >= 0)
                {
                    min = adjacency.floodFillUpdateSandpits()[xPos-1][yPos];
                    minX = xPos-1;
                    minY = yPos;
                }
                  
            }
            if(yPos + 1 < adjacency.floodFillUpdateSandpits()[0].length)
            {
                if(min > adjacency.floodFillUpdateSandpits()[xPos][yPos+1] && adjacency.floodFillUpdateSandpits()[xPos][yPos+1] >= 0)
                {
                    min = adjacency.floodFillUpdateSandpits()[xPos][yPos+1];
                    minX = xPos;
                    minY = yPos+1;
                }
                  
            }
            if(yPos - 1 >= 0)
            {
                if(min > adjacency.floodFillUpdateSandpits()[xPos][yPos-1] && adjacency.floodFillUpdateSandpits()[xPos][yPos-1] >= 0)
                {
                    min = adjacency.floodFillUpdateSandpits()[xPos][yPos-1];
                    minX = xPos;
                    minY = yPos-1;
                }
                  
            }
            pathX.add(minX);
            pathY.add(minY);
            System.out.println("x: "+minX);
            System.out.println("y: "+minY);

            pathCalculator(minX,minY);
        }
        return null;
    }


    public List decreasePath(List pathX, List pathY)
    {
        ArrayList getPath = new ArrayList<List>();
        pathX.remove(pathX.size()-1);
        pathY.remove(pathY.size()-1);
        getPath.add(pathX);
        getPath.add(pathY);
        return getPath;

    }





    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {

        /*
         * int[][] field = new int[4][4];
         * for (int i = 0; i < field.length; i++) {
         * for (int j = 0; j < field[i].length; j++) {
         * field[i][j] = 0;
         * }
         * }
         * field[0][0] = 4;
         * field[0][1] = 3;
         * field[0][2] = 2;
         * field[0][3] = 3;
         * field[1][0] = 3;
         * field[1][1] = 2;
         * field[1][2] = 1;
         * field[1][3] = 2;
         * field[2][0] = 2;
         * field[2][1] = 1;
         * field[2][2] = 0;
         * field[2][3] = 1;
         * field[3][0] = 3;
         * field[3][1] = 2;
         * field[3][2] = 1;
         * field[3][3] = 2;
         * 
         * // Get ball position (for testing purposes, assume)
         * int a = 3;
         * int b = 0;
         * // Assume hole position
         * int[] holePosition = new int[2];
         * holePosition[0] = 2;
         * holePosition[1] = 2;
         * 
         * int[] currentPosition = new int[2];
         * currentPosition[0] = a;
         * currentPosition[1] = b;
         */

        /*
         * while ((a != holePosition[0]) || (b != holePosition[1])) {
         * try {
         * if (field[a][b] > field[a][b + 1]) {
         * b = b + 1;
         * currentPosition[0] = a;
         * currentPosition[1] = b;
         * }
         * } catch (ArrayIndexOutOfBoundsException e) {
         * System.out.println("a");
         * }
         * 
         * try {
         * 
         * if (field[a][b] > field[a][b - 1]) {
         * b = b - 1;
         * currentPosition[0] = a;
         * currentPosition[1] = b;
         * }
         * } catch (ArrayIndexOutOfBoundsException e) {
         * System.out.println("bb");
         * }
         * try {
         * if (field[a][b] > field[a + 1][b]) {
         * a = a + 1;
         * currentPosition[0] = a;
         * currentPosition[1] = b;
         * }
         * } catch (ArrayIndexOutOfBoundsException e) {
         * System.out.println("c");
         * }
         * try {
         * if (field[a][b] > field[a - 1][b]) {
         * a = a - 1;
         * currentPosition[0] = a;
         * currentPosition[1] = b;
         * }
         * } catch (ArrayIndexOutOfBoundsException e) {
         * System.out.println("d");
         * }
         * System.out.println(a);
         * System.out.println(b);
         * }
         */

        BiFunction<Double, Double, Double> terrain = (x, y) -> (double) (0.5 * (Math.sin((x - y) / 7) + 0.9));

        double[] coorTX = { 19, 12, 14, -12, -8, -6 }; // x-coordinates of the trees
        double[] coorTY = { 8, 11, 18, -8, -7, -2 }; // y-coordinates of the trees
        double interval = 1;
        double holeCoorx = 20;
        double holeCoory = 20;
        double radius = 2; // radius of all trees
        double[] beginX = { 1, -6, -20 }; // begin x-coordinates for the sandpits
        double[] endX = { 8, 0, -15 }; // end x-coordinates for the sandpits
        double[] beginY = { 1, -6, -20 }; // begin y-coordinates for the sandpits
        double[] endY = { 8, 0, -15 }; // end y-coordinates for the sandpits
        int sandpitResentment = 1; // the higher this value, the less likely the ai takes a route through a sandpit

        AdjacencyField cf = new AdjacencyField(interval, holeCoorx, holeCoory, sandpitResentment, terrain, coorTX,
                coorTY, radius, beginX, endX, beginY, endY);
        int[][] field = cf.floodFillUpdateSandpits();

        boolean check = false;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == 0) {
                    check = true;
                    int c = i;
                    int d = j;
                    System.out.println(check);
                    System.out.println(c);
                    System.out.println(d);

                }
            }

        }
        System.out.println(check);
    //a and b are the coordinates of the ball at the beginning 
        int a = 4;
        int b = 5;
        int[] currentPosition = new int[2];
        currentPosition[0] = a;
        currentPosition[1] = b;


        // i made the field using double holeCoorx = 20; double holeCoory = 20;, for some reason when i looped through the field the 0 is in the squre with coordinates 45 and 45
        int hx = 45;
        int hy = 45;

        while ((a != hx) || (b != hy)) {

            try {
                if (field[a][b] > field[a + 1][b]) {
                    a = a + 1;
                    currentPosition[0] = a;
                    currentPosition[1] = b;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("c");
            }
            try {
                if (field[a][b] > field[a - 1][b]) {
                    a = a - 1;
                    currentPosition[0] = a;
                    currentPosition[1] = b;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("d");
            }
            try {
                if (field[a][b] > field[a][b + 1]) {
                    b = b + 1;
                    currentPosition[0] = a;
                    currentPosition[1] = b;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("a");
            }

            try {

                if (field[a][b] > field[a][b - 1]) {
                    b = b - 1;
                    currentPosition[0] = a;
                    currentPosition[1] = b;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("bb");
            }

            System.out.println(a);
            System.out.println(b);

        }

    }
}
