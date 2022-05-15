package Bots;

public class testrange
{
    public static void main(String[] args) 
    {
        int range = 12;
        int i = 0;
        while(i<1000)
        {
            System.out.println((int)(Math.random()*range - 6));
            i++;
        }
        
    }
}
