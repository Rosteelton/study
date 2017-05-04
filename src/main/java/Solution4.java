import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Solution4
{
    public static void main(String[] args)
    {
        Set<Cat> cats = createCats();
        printCats(cats);

        Iterator<Cat> iterator = cats.iterator();

        if (iterator.hasNext()) {
            Cat nextCat = iterator.next();
            iterator.remove();
        }

        System.out.println("NEW SET:");
        printCats(cats);
    }

    public static Set<Cat> createCats()
    {
        Set<Cat> set = new HashSet<Cat>();

        Cat cat1 = new Cat(1);
        Cat cat2 = new Cat(2);
        Cat cat3 = new Cat(3);

        set.add(cat1);
        set.add(cat2);
        set.add(cat3);


        return set;
    }

    public static void printCats(Set<Cat> cats)
    {

        for (Cat cat:cats)
        {
            System.out.println(cat);
        }
    }

    public static class Cat {
        public int catNumber;

        public Cat(int catNumber) {
            this.catNumber = catNumber;
        }

        @Override
        public String toString() {
            return "Cat №" + catNumber;
        }
    }
}