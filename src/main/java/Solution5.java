import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Solution5 {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.US);
        LocalDate a = LocalDate.parse("", formatter);


        System.out.println(a.atStartOfDay().toString());
    }
}
