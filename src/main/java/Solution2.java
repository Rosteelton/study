import java.util.HashSet;

public class Solution2
{

    public static void main(String[] args) {
        createSet();
    }

    public static HashSet<Integer> createSet()
    {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(12);
        set.add(8);
        removeAllNumbersMoreThan10(set);
        return  set;
    }

    public static HashSet<Integer> removeAllNumbersMoreThan10(HashSet<Integer> set)
    {
        for (Integer i:set)
        {
            set.remove(i);
        }
        return set;
    }
}
