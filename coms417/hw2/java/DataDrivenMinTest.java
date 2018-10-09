import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@RunWith (Parameterized.class)
public class DataDrivenMinTest<T extends Comparable<? super T>>
{
    private List<? extends T> list;

    private Comparable<? super T> min;

    public DataDrivenMinTest(List<? extends T> list, T min)
    {
        this.list = list;
        this.min = min;
    }

    @Parameters
    public static Collection<Object[]> MinValues()
    {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        ArrayList<Object> c = new ArrayList<>();
        for(int i = 0; i<26; i++)
        {
            c.add("" + (char)((int)'a' + i));
        }
        list.add(new Object[]{c, "a"});
        c = new ArrayList<>();
        for(int i = 0; i < 100; i++)
        {
            c.add(i);
        }
        list.add(new Object[]{c, 0});
        return list;
    }

    @Test
    public void minTest()
    {
        assertTrue("min Test", 0 == min.compareTo(Min.min(list)));
    }
}