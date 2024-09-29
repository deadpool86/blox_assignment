package Question2;

import java.util.*;


/*
 * 7. Row-by-Row Content Comparison:
   Compare the data for each row in each table, field by field, to ensure no discrepancies.
   Write a script that pulls data row by row from both the source and target databases, 
   converts them into a comparable format (e.g., List<Map<String,String>>), and checks for differences.   
 */

public class RowByRowDataComparator {

    public static void main(String[] args) {

     
        List<Map<String, String>> localData = fetchDataFromLocalDB();
        List<Map<String, String>> cloudData = fetchDataFromCloudDB();

        compareData(localData, cloudData);
    }

    public static void compareData(List<Map<String, String>> localData, List<Map<String, String>> cloudData) {
        if (localData.size() != cloudData.size()) {
            System.out.println("Data size mismatch! Local data has " + localData.size() + " rows, but cloud data has " + cloudData.size() + " rows.");
            return;
        }

        boolean dataMatches = true;

        
        for (int i = 0; i < localData.size(); i++) {
            Map<String, String> localRow = localData.get(i);
            Map<String, String> cloudRow = cloudData.get(i);

           
            if (!compareRows(localRow, cloudRow, i)) {
                dataMatches = false;
            }
        }

        if (dataMatches) {
            System.out.println("All data matches between local and cloud databases.");
        } else {
            System.out.println("Data mismatch found.");
        }
    }

    public static boolean compareRows(Map<String, String> localRow, Map<String, String> cloudRow, int rowIndex) {
        if (!localRow.keySet().equals(cloudRow.keySet())) {
            System.out.println("Key mismatch at row " + rowIndex);
            return false;
        }

        for (String key : localRow.keySet()) {
            String localValue = localRow.get(key);
            String cloudValue = cloudRow.get(key);

            if (!Objects.equals(localValue, cloudValue)) {
                System.out.println("Value mismatch at row " + rowIndex + " for key '" + key + "': Local value = '" + localValue + "', Cloud value = '" + cloudValue + "'");
                return false;
            }
        }
        return true;
    }

    
    public static List<Map<String, String>> fetchDataFromLocalDB() {
        List<Map<String, String>> data = new ArrayList<>();
        
        Map<String, String> row1 = new HashMap<>();
        row1.put("id", "1");
        row1.put("name", "Itachi");
        row1.put("age", "20");
        
        Map<String, String> row2 = new HashMap<>();
        row2.put("id", "2");
        row2.put("name", "Kakashi");
        row2.put("age", "25");

        data.add(row1);
        data.add(row2);

        return data;
    }

   
    public static List<Map<String, String>> fetchDataFromCloudDB() {
        List<Map<String, String>> data = new ArrayList<>();

        Map<String, String> row1 = new HashMap<>();
        row1.put("id", "1");
        row1.put("name", "Itachi");
        row1.put("age", "20");
        
        Map<String, String> row2 = new HashMap<>();
        row2.put("id", "2");
        row2.put("name", "Kakashi");
        row2.put("age", "25");

        data.add(row1);
        data.add(row2);

        return data;
    }
}
