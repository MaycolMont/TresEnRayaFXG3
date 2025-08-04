package espol.tresenrayafxg3.models;

public class PlayerIA implements Player {
    private Board board;
    private static int depth = 2; // profundidad por defecto para el árbol de estados

    public PlayerIA(Board board) {
        this.board = board;
    }

    public Position getMove() {
        StateTree stateTree = new StateTree(board, depth);
        BoardMove move = stateTree.getBestMove();
        System.out.println("Jugada: " + move);
        return new Position(move.getRow(), move.getColumn());
    }
}
