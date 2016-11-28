import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Solution {
    public static void main(String[] args)
    {
        ArrayList<String> list = new ArrayList<>(100);

        list.add("123");
        list.add("12");
        list.add("123");
        list.add("12");

        list.sort(new MyComparator());

        int maxLength = list.get(0).length();

        list.forEach((string) -> {
            if (string.length() == maxLength) System.out.println(string);
        });


        //System.out.println(list.get(0).length() - list.get(1).length());
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).length() == maxLength) {
//                System.out.println(list.get(i));
//            } else {
//                break;
//            }
//        }
    }

    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o2.length()> o1.length()) return 1;
            else if (o1.length()< o2.length()) return -1;
            else return 0;

           // return o2.length() - o1.length();
        }
    }
}