package hw1;

public class Vehicle implements Cloneable
{
    private int x;
    public Vehicle(int y)
    {
        this.x = y;
    }
    public Object clone()
    {
        Object result = null;
        try
        {
            result = super.clone();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static class Truck extends Vehicle
    {
        private int y;
        public Truck(int x)
        {
            super(x);
            this.y = x;
        }
        public Object clone()
        {
            Object result = super.clone();
            ((Truck) result).y = this.y;
            return result;
        }
        public int getY()
        {
            return this.y;
        }
    }
}