package espol.tresenrayafxg3.models;

public class BoardMove {
    private final int row;
    private final int column;
    private final char value;

    public BoardMove(int row, int column, char value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BoardMove{" +
                "row=" + row +
                ", column=" + column +
                ", value=" + value +
                '}';
    }
}
