import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Janith Chanaka Abhayarathna.
 * Student ID: 20200571 / w1830253
 * Algorithms Coursework
 */

public class Main {

    private CurrentNode coordinate;
    private boolean[][] visitedNodes;
    private final ArrayList<CurrentNode> nodesQueue = new ArrayList<>();
    static char[][] gridHolder;

    public static void main(String[] args) {

        Main main = new Main();

        long startingTime;
        long endTime;

        System.out.println(" ");
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> Enter the file name: ");
        String fileName = scanner.nextLine().toLowerCase();    // get the file name

        System.out.println(" ");
        gridHolder = main.parser(fileName); // parse the input file
        System.out.println(" ");

        startingTime = System.currentTimeMillis();
        main.startingPosition(gridHolder);   // find the starting position

        main.shortestPath(gridHolder);   // find the shortest path
        endTime = System.currentTimeMillis();

        Double timeTaken = (endTime - startingTime) / 1000.0;
        System.out.println("Time taken to find the path: " + timeTaken + " s");
    }

    /**
     *
     * @param fileName
     * @return
     */
    public char[][] parser(String fileName) {

        int rowNo = 0;
        String line;
        try {
            FileReader fr = new FileReader("input files/" + fileName);  // open the file
            BufferedReader br = new BufferedReader(fr); // read the file
            br.mark(1); // mark the current position
            int length = br.readLine().length();    // get the length of the first line
            br.reset(); // reset the current position to the beginning of the file

            char[][] grid = new char[length][length];   // create a 2D array to store the grid

            while ((line = br.readLine()) != null) {    // read the file line by line
                char[] column = line.toCharArray();
                grid[rowNo] = column;
                rowNo++;
            }

            System.out.println("Grid_________________________");
            // displaying the grid
            for (char[] chars : grid) {
                for (char aChar : chars) {
                    System.out.print(aChar);
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

    /**
     *
     * @param grid
     */
    // finding the starting position
    public void startingPosition(char[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    coordinate = new CurrentNode(i, j);
                }
            }
        }
    }

    /**
     *
     * @param grid
     */
    // Find the shortest path from the starting position to the ending position
    public void shortestPath(char[][] grid) {

        visitedNodes = new boolean[grid.length][grid.length];
        nodesQueue.add(coordinate);
        visitedNodes[coordinate.getRowNumber()][coordinate.getColumnNumber()] = true;

        boolean found = false;
        while (!nodesQueue.isEmpty()) {
            coordinate = nodesQueue.remove(0);
            int visitedRowNumber = coordinate.getRowNumber();
            int visitedColumnNumber = coordinate.getColumnNumber();

            // Finding the end position
            if (grid[visitedRowNumber][visitedColumnNumber] == 'F') {
                found = true;
                break;
            }
            findNeighbours(coordinate, -1, 0, "left");
            findNeighbours(coordinate, 1, 0, "right");
            findNeighbours(coordinate, 0, 1, "down");
            findNeighbours(coordinate, 0, -1, "up");
        }

        if (found) {
            System.out.println("Shortest path found____________________");
            System.out.println(" ");
            displayPath(coordinate);
        } else {
            System.out.println("No Path Found");
        }
    }

    /**
     *
     * @param coordinate
     * @param x
     * @param y
     * @param direction
     */
    // Find the neighbours of the current node
    public void findNeighbours(CurrentNode coordinate, int x, int y, String direction) {

        int row = coordinate.getRowNumber();
        int column = coordinate.getColumnNumber();

        while(true) {
            row += y;
            column += x;

            if (!(row >= 0 && column >= 0 && row < gridHolder.length && column < gridHolder[0].length && gridHolder[row][column] != '0' && !visitedNodes[row][column])) {
                break;
            }

            if (gridHolder[row][column] == 'F') {

                CurrentNode neighbourNode = new CurrentNode(row, column);
                neighbourNode.setPreviousNode(coordinate);
                neighbourNode.setMove(direction);

                nodesQueue.add(0, neighbourNode);
                visitedNodes[row][column] = true;
                break;
            }

            int nextRow  = row + y;
            int nextColumn = column + x;

            if ((nextRow < 0 || nextColumn < 0) || (nextRow >= gridHolder.length || nextColumn >= gridHolder.length) || (gridHolder[nextRow][nextColumn] == '0')) {
                CurrentNode neighbourItem = new CurrentNode(row, column);
                neighbourItem.setPreviousNode(coordinate);
                neighbourItem.setMove(direction);

                nodesQueue.add(neighbourItem);
                visitedNodes[row][column] = true;
                break;
            }
        }
    }

    /**
     *
     * @param coordinate
     */
    // Display the shortest path
    public void displayPath(CurrentNode coordinate) {

        ArrayList<String> path = new ArrayList<>();

        while (coordinate.getPreviousNode() != null) {
            String step = "Move " + coordinate.getMove() + " to " + "(" + (coordinate.getColumnNumber() + 1) + ", " + (coordinate.getRowNumber() + 1) + ")";
            path.add(step);
            coordinate = coordinate.getPreviousNode();
        }
        path.add("Start at " + "(" + (coordinate.getColumnNumber() + 1) + ", " + (coordinate.getRowNumber() + 1) + ")");

        Collections.reverse(path);
        path.add("Done!");

        for (int i = 0; i < path.size(); i++) {
            System.out.println(i+1 + ". " + path.get(i));
        }
        System.out.println(" ");

    }
}
