package Controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    public static String[] validator(String username, String password) {
        String filePath = "src\\Model\\customers.txt";
        String[][] data = parseTextFile(filePath);
        
        // Print the data to verify it
        for (String[] row : data) {
            if (row[0].equals(username) && row[1].equals(password)) {
                return row;
            }
        }
        return null;
    }

    public static String[][] parseTextFile(String filePath) {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                rows.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the list to a 2D array
        String[][] data = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

   public static void main(String[] args) {
        System.out.println(validator("raphael", "123"));
    }
}