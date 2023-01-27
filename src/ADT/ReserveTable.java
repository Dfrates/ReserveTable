package ADT;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReserveTable {

    // fields
    private final int maxSize;

    // LinkedHashMap is used to preserve the order of the entries
    private final Map<String, Integer> reserveTable;

    // constructor for Reserve Table
    public ReserveTable(int size) {
        maxSize = size;
        reserveTable = new LinkedHashMap<>(maxSize);
    }

    // adds a name and code to the table
    public int Add(String name, int code) {
        int index;

        // checks if the table is full
        if(reserveTable.size() >= maxSize) {
            System.out.printf("Reached max size of %d\n", maxSize);
            System.out.printf("Unable to add item: {\"%s\", %d}\n", name, code);
            return -1;
        }

        // adds the name and code to the end of the table
        reserveTable.put(name, code);
        index = reserveTable.size() - 1;

        // returns index of the entry
        return index;
    }



    // looks up code by name in the table
    public int LookupName(String name) {

        // checks if the table contains the given name (case-insensitive)
        for(Map.Entry<String, Integer> entry: reserveTable.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        // returns -1 if the name is not found
        return -1;
    }

    // looks up name by value in the table
    public String LookupCode(int code) {
        // checks if the table contains the given code
        if(!reserveTable.containsValue(code)) {
            return "";
        }

        // goes through each entry and compares the value to the given code
        for(Map.Entry<String, Integer> entry: reserveTable.entrySet()) {
            if (entry.getValue() == code) {
                return entry.getKey();
            }
        }
        // returns an empty string if the code is not found
        return "";
    }

    // gets the longest key in the table
    private int GetLongestKey() {
        int longestKey = 0;
        // goes through each entry and compares the length of the key
        for(Map.Entry<String, Integer> entry: reserveTable.entrySet()) {
            if (entry.getKey().length() > longestKey) {
                longestKey = entry.getKey().length();
            }
        }
        return longestKey;
    }

    // prints the table to a file
    public void PrintReserveTable(String filename) {
        try {
            // gets the longest key in the table
            int longest = GetLongestKey();

            // sets the counter for the index column
            int counter = 1;

            // sets the offset for the index column
            int offset = Integer.toString(counter).length() + 2;

            // creates the file and writes the header
            FileWriter writer = new FileWriter(filename);
            writer.write(pad("Index", offset + 6, false) +
                         pad("Name", longest + 5, false) + "Code\n");

            // goes through each entry and writes it to the file
            for(Map.Entry<String, Integer> entry: reserveTable.entrySet()) {
                writer.write(pad("", 2, true) +
                             pad(Integer.toString(counter), offset + 5, false) +
                             pad(entry.getKey(), longest + 5, false) +
                             entry.getValue() + "\n");
                counter++;
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // pads a string with spaces to a given length on the left or right
    private String pad(String input, int len, boolean left) {

        // creates a StringBuilder from the input string
        StringBuilder inputBuilder = new StringBuilder(input);

        // adds spaces to the left or right of the string
        // until the string is the given length
        while (inputBuilder.length() < len){
            if (left)
                inputBuilder.insert(0, " ");
            else
                inputBuilder.append(" ");
        }
        // converts the StringBuilder back to a string
        input = inputBuilder.toString();

        return input;
    }


}
