import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class java_ex3 {
    public static void main(String[] args) {
        String input_file_path = "input.txt";
        if (args != null && args.length >= 1)
            input_file_path = args[0];

        PointsFileReader reader;
        try {
            reader = new PointsFileReader(input_file_path);
        } catch (IOException e) {
            System.out.println("Error reading file " + input_file_path + ":");
            e.printStackTrace();
            System.exit(1);
        }


    }
}
