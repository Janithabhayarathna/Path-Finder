import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String fileName = "maze10_1.txt";   // input file name

        char[][] gridHolder = parser(fileName); // parse the input file
    }

    public static char[][] parser(String fileName) {

        int rowNo = 0;
        try {
            FileReader fr = new FileReader("input files/" + fileName);  // open the file
            BufferedReader br = new BufferedReader(fr); // read the file
            String line = "";
            int length = br.readLine().length();    // get the length of the first line

            char[][] grid = new char[length][length];

            while ((line = br.readLine()) != null) {    // read the file line by line
                char[] column = line.toCharArray();
                grid[rowNo] = column;
                rowNo++;
            }

            // display the grid
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    System.out.print(grid[i][j]);
                }
                System.out.println(" ");
            }
            return grid;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");

        } catch (IOException e) {
            System.out.println("IO Exception");
        }

        return new char[0][0];
    }
}
