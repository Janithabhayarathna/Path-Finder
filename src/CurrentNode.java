public class CurrentNode {

    private int rowNumber;            // row number of the node
    private int columnNumber;         // column number of the node
    private CurrentNode previous;    // node object to store its parent node
    private String move;

    public CurrentNode(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public CurrentNode getPrevious() {
        return previous;
    }

    public void setPrevious(CurrentNode previous) {
        this.previous = previous;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
