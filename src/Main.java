import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static CurrentNode coordinate;
    private static boolean[][] visitedNodes;
    private static final ArrayList<CurrentNode> nodesQueue = new ArrayList<>();
    static long startingTime;
    static long endTime;
    static char[][] gridHolder;

    public static void main(String[] args) {

        String fileName = "maze10_1.txt";   // input file name

        gridHolder = parser(fileName); // parse the input file

        startingTime = System.currentTimeMillis();
        startingPosition(gridHolder);

        shortestPath(gridHolder);

    }

    /**
     *
     * @param fileName
     * @return
     */
    public static char[][] parser(String fileName) {

        int rowNo = 0;
        try {
            FileReader fr = new FileReader("input files/" + fileName);  // open the file
            BufferedReader br = new BufferedReader(fr); // read the file
            br.mark(1); // mark the current position
            String line;
            int length = br.readLine().length();    // get the length of the first line
            br.reset();

            char[][] grid = new char[length][length];

            while ((line = br.readLine()) != null) {    // read the file line by line
                char[] column = line.toCharArray();
                grid[rowNo] = column;
                rowNo++;
            }

            // displaying the grid
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

    /**
     *
     * @param grid
     */
    // finding the starting position
    public static void startingPosition(char[][] grid) {

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
    public static void shortestPath(char[][] grid) {

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
                endTime = System.currentTimeMillis();
                found = true;
                break;
            }
            findNeighbours(coordinate, -1, 0, "left");
            findNeighbours(coordinate, 1, 0, "right");
            findNeighbours(coordinate, 0, 1, "bottom");
            findNeighbours(coordinate, 0, -1, "up");
        }

        if (found) {
            System.out.println("Shortest path found");
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
    public static void findNeighbours(CurrentNode coordinate, int x, int y, String direction) {

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
                neighbourNode.setPrevious(coordinate);
                neighbourNode.setMove(direction);

                nodesQueue.add(0, neighbourNode);
                visitedNodes[row][column] = true;
                break;
            }

            int nextRow  = row + y;
            int nextColumn = column + x;

            if ((nextRow < 0 || nextColumn < 0) || (nextRow >= gridHolder.length || nextColumn >= gridHolder.length) || (gridHolder[nextRow][nextColumn] == '0')) {
                CurrentNode neighbourItem = new CurrentNode(row, column);
                neighbourItem.setPrevious(coordinate);
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
    public static void displayPath(CurrentNode coordinate) {

        long timeTaken = endTime - startingTime;

        ArrayList<String> path = new ArrayList<>();
        int c = 0;

        while (coordinate.getPrevious() != null) {
            c++;
            String step = "Move " + coordinate.getMove() + " to " + "(" + (coordinate.getColumnNumber() + 1) + ", " + (coordinate.getRowNumber() + 1) + ")";
            path.add(step);
            coordinate = coordinate.getPrevious();
        }
        path.add("Start at " + "(" + (coordinate.getColumnNumber() + 1) + ", " + (coordinate.getRowNumber() + 1) + ")");

        Collections.reverse(path);
        path.add("Done.");

        for (int i = 0; i < path.size(); i++) {
            System.out.println(i+1 + ". " + path.get(i));
        }
        System.out.println(" ");
    }
}
