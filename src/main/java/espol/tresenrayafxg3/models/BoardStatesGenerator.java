package espol.tresenrayafxg3.models;

import java.util.Iterator;

// Clase Iterable que genera todos los posibles estados de un tablero
// y el valor correspondiente con el que se debe llenar
public class BoardStatesGenerator implements Iterable<Board> {
    private final Board board;
    private boolean finished;

    public BoardStatesGenerator(Board board) {
        this.board = board; // objeto independiente de su padre
    }

    @Override
    public Iterator<Board> iterator() {
        return new Iterator<Board>() {
            int row = 0;
            int column = 0;

            private void nextPosition() {
                column++;
                if (column > 2) {
                    column = 0;
                    row++;
                }
            }

            @Override
            public boolean hasNext() {
                if (finished || board.isFinished()) {
                    return false; // No hay más estados si el juego ya ha terminado
                }
                if (row < 3 && column < 3) {
                    if (board.isEmptyBox(row, column)) {
                        return true;
                    } else {
                        nextPosition();
                        return hasNext();
                    }
                }
                return false;
            }

            @Override
            public Board next() {
                Board newBoard = board.clone(); // clonar para seguir usando el tablero, nunca modificar el tablero
                                                // original
                try {
                    newBoard.markBox(row, column);
                    if (newBoard.isFinished())
                        finished = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nextPosition();
                return newBoard;
            }
        };
    }
}
