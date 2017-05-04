import java.util.*;

public class Solution3 {

        public static void main(String[] args)
        {
            HashMap<String, String> map = createMap();
            getCountTheSameFirstName(map,"Наталья");
            getCountTheSameLastName(map, "Шиховцова");
        }
        public static HashMap<String, String> createMap()
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("Киреева","Наталья");
            map.put("Ширяева","Ирина");
            map.put("Лужков","Кирилл");
            map.put("Розанова","Лилия");
            map.put("Курочкина","Ольга");
            map.put("Шиховцова","Анастасия");
            map.put("Рогова","Наталья");
            map.put("Шиховцова","Зинаида");
            map.put("Петров","Никита");
            map.put("Воробьев","Антон");
            return map;
        }

        public static int getCountTheSameFirstName(HashMap<String, String> map, String name)
        {
            name = "Наталья";

            int count = 0;

            Collection<String> value = map.values();
            for (String val:value)
            {
                if (val == name){
                    count++;
                }
            }
            System.out.println("names"+ count);
            return count;
        }


        public static int getCountTheSameLastName(HashMap<String, String> map, String lastName)
        {
            lastName = "Шиховцова";
            int count=0;
            Set<String> keys = map.keySet();
            for (String key:keys)
            {
                if(key == lastName){
                    count++;
                }
            }
            System.out.println("lastnames"+ count);
            return count;

        }
    }


