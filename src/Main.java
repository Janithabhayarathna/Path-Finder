import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String fileName = "";

        char[][] gridHolder = parser(fileName);
    }

    public static char[][] parser(String fileName) {

        int rowNo = 0;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            int length = br.readLine().length();

            char[][] grid = new char[length][length];

            while ((line = br.readLine()) != null) {
                char[] column = line.toCharArray();
                grid[rowNo] = column;
                rowNo++;
            }

            // display the grid
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    System.out.print(grid[i][j]);
                }
                System.out.println();
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
