package hw1.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import hw1.main;
import hw1.Vehicle;

public class MyTest
{
    @Test
    public void commentTest()
    {
        int x[] = {2, 3, 5};
        int ans = hw1.main.findLast(x, 2);
        assertEquals(ans, 0);
    }

    @Test(expected = NullPointerException.class)
    public void nullArryTest()
    {
        main.findLast(null, 2);
    }

    @Test
    public void emptyArray()
    {
        int x[] = {};
        assertEquals(-1, main.findLast(x, 1));
    }

    @Test
    public void  vehicleCloneTest()
    {
        Vehicle.Truck t = new Vehicle.Truck(2);
        Vehicle.Truck c = (Vehicle.Truck) t.clone();
        assertEquals(t.getClass(), c.getClass());
        assertEquals(t.getY(), c.getY());
    }
}