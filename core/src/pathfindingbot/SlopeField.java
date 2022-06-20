package pathfindingbot;

import java.util.function.BiFunction;

/**
 * SlopeField
 */
public class SlopeField 
{
    double interval;
    BiFunction<Double,Double,Double> terrain;


    /**
     * constructor
     * @param interval: this determines how big each tile is in for the slope field
     * @param terrain: terrain function used to calculate the slope for each interval
     */
    public SlopeField(double interval, BiFunction<Double,Double,Double> terrain)
    {
        this.terrain = terrain;
        this.interval = interval;
        
    }

    /**
     * This creates a basic field which will be used to store the slopes in.
     * @return: an array that is a basic field which is used to store the slopes in
     */
    public double[][] Field()
    {
       
        double[][] field = new double[(int)(50/interval)][(int)(50/interval)];
        for(int i = 0; i<field.length;i++)
        {
            for(int j = 0;j<field[0].length; j++)
            {
               field[i][j] = -1;
            }
            
        }
        return field;
    }


    /**
     *  This method calculates the x-slope of the terrain on each interval
     * @return: an array containing the x-slope of the terrain on each interval
     */
    public double[][] slopeXCalculator()
    {
        double[][] field = Field();
        for(int i = 0; i<field.length;i++)
        {
            for(int j = 0;j<field[0].length; j++)
            {
               if(terrain.apply(i/interval-25, j/interval-25) >= 0)
               {
                   field[i][j] = Physics.derivativeXValue(terrain, i/interval-25, j/interval-25);
               }
            }
            
        }

        return field;
    }

    /**
     *  This method calculates the y-slope of the terrain on each interval
     * @return: an array containing the y-slope of the terrain on each interval
     */
    public double[][] slopeYCalculator()
    {
        double[][] field = Field();
        for(int i = 0; i<field.length;i++)
        {
            for(int j = 0;j<field[0].length; j++)
            {
               if(terrain.apply(i/interval-25, j/interval-25) >= 0)
               {
                   field[i][j] = Physics.derivativeYValue(terrain, i/interval-25, j/interval-25);
               }
            }
            
        }

        return field;                                                                       
    }

}
