import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* 
Задача по алгоритмам
*/

public class Solution7 {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> list = new ArrayList<String>();
        while (true) {
            String s = reader.readLine();
            if (s.isEmpty()) break;
            list.add(s);
        }

        String[] array = list.toArray(new String[list.size()]);
        sort(array);

        for (String x : array) {
            System.out.println(x);
        }
    }

    public static void sort(String[] array) {

        ArrayList<Integer> words = new ArrayList<Integer>();
        ArrayList<Integer> num = new ArrayList<Integer>();

        for (int i = 0; i < array.length; i++) {
            if (isNumber(array[i]) == false) {
                words.add(i);
            } else {
                num.add(i);
            }
        }


        for (int i = 0; i < words.size() - 1; i++) {
            String s = "";
            for (int j = 0; j < words.size() - 1; j++) {
                if (isGreaterThan(array[words.get(j)], array[words.get(j + 1)]) == true) {
                    s = array[words.get(j)];
                    array[words.get(j)] = array[words.get(j + 1)];
                    array[words.get(j + 1)] = s;
                }
            }
        }


        for (int i = 0; i < num.size() - 1; i++) {
            String s = "";
            for (int j = 0; j < num.size() - 1; j++) {
                if (Integer.parseInt(array[num.get(j)]) < Integer.parseInt(array[num.get(j+1)])) {
                    s = array[num.get(j)];
                    array[num.get(j)] = array[num.get(j + 1)];
                    array[num.get(j + 1)] = s;
                }
            }
        }
    }

    // Метод для сравнения строк: 'а' больше чем 'b'
    public static boolean isGreaterThan(String a, String b) {
        return a.compareTo(b) > 0;
    }


    // Переданная строка - это число?
    public static boolean isNumber(String s) {
        if (s.length() == 0) return false;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((i != 0 && c == '-') // есть '-' внутри строки
                    || (!Character.isDigit(c) && c != '-')) // не цифра и не начинается с '-'
            {
                return false;
            }
        }
        return true;
    }
}
