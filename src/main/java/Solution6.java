import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Solution6
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //list of addresses
        HashMap<String, String> addresses = new HashMap<String, String>();
        while (true)
        {
            System.out.println("Введите family");
            String family = reader.readLine();
            if (family.isEmpty()) break;

            System.out.println("Введите city");
            String city = reader.readLine();
            addresses.put(city, family);
        }
        System.out.println("Введите город");
        //read city number
        String town = reader.readLine();
        for (Map.Entry<String, String> pair : addresses.entrySet())
        {
            if (town.equals(pair.getKey()))
            {
                String familySecondName = pair.getValue();
                System.out.println(familySecondName);
            }
        }
    }

}