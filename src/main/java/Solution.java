import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
Проверка на упорядоченность
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        //напишите тут ваш код

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            list.add(reader.readLine());

        }

        for (int i = 0; i < list.size()-1; i++) {

            if (!compareString(list.get(i), list.get(i + 1))) {

                System.out.println(list.get(i + 1));
                break;

            }

        }
    }


    public static boolean compareString(String s1, String s2) {
        if (s1.length() <= s2.length()) {
            return true;
        } else {
            return false;
        }
    }
}


//
//    Проверка на упорядоченность
//        1. Введи с клавиатуры 10 слов в список строк.
//        2. Определить, является ли список упорядоченным по возрастанию длины строки.
//        3. В случае отрицательного ответа вывести на экран номер первого элемента, нарушающего такую упорядоченность.