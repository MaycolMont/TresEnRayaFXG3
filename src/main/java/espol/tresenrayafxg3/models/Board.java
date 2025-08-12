/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tresenrayafxg3.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import espol.tresenrayafxg3.util.Suscriber;

/**
 * Clase que representa el tablero del juego Tres en Raya.
 * Contiene filas, columnas y diagonales que permiten marcar casillas.
 * 
 * @author maycmont
 */

public class Board implements Suscriber, Cloneable {
    private final Line[] rows = { new Line(), new Line(), new Line() };
    private final Line[] columns = { new Line(), new Line(), new Line() };
    private final Line[] diagonals = { new Line(), new Line() };
    private final List<Line> lines = new ArrayList<>();
    private boolean isFinished = false; // Indica si el juego ha terminado
    private char currentPlayer; // jugador que tiene el turno actual para jugar
    private char nextPlayer; // jugador del siguiente turno
    private char winner;

    private final List<BoardMove> movements = new ArrayList<>();

    /**
     * Constructor que inicializa el tablero creando las líneas
     * y suscribiendo las líneas a las notificaciones de cambios.
     */
    public Board(char currentPlayer, char nextPlayer) {
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
        diagonals[0].addSuscriber(this);
        diagonals[1].addSuscriber(this);
        for (int i = 0; i < 3; i++) {
            rows[i].addSuscriber(this); // Suscribe la fila al tablero
            columns[i].addSuscriber(this); // Suscribe la columna al tablero
            for (int j = 0; j < 3; j++) {
                Box newBox = new Box();
                rows[i].add(newBox);
                columns[j].add(newBox);
                if (i == j)
                    diagonals[0].add(newBox); // Diagonal principal
                if (Math.abs(i - j) > 1 || (i == 1 && j == 1)) {
                    diagonals[1].add(newBox); // Diagonal secundaria
                }
            }
        }
        lines.addAll(Arrays.asList(rows));
        lines.addAll(Arrays.asList(columns));
        lines.addAll(Arrays.asList(diagonals));
    }

    /**
     * Marca una casilla en el tablero con un valor específico.
     * 
     * @param position la posición de la casilla a marcar
     * @param value    el valor a asignar a la casilla
     * @throws Exception si la casilla ya está ocupada
     */
    public void markBox(int row, int column) throws Exception {
        if (isFinished) {
            throw new Exception("El juego ya ha terminado");
        }
        Box box = rows[row].getAt(column);
        try {
            box.setValue(currentPlayer);
            movements.add(new BoardMove(row, column, currentPlayer));
            changePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changePlayer() {
        if (movements.size() == 9) {
            isFinished = true; // Marca el juego como terminado
        }
        char auxValue = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = auxValue;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getNextPlayer() {
        return nextPlayer;
    }

    public BoardMove getLastMove() {
        return movements.get(movements.size() - 1);
    }

    public BoardMove getPreviousMove() {
        return movements.get(movements.size() - 2);
    }

    public boolean isFinished() {
        return isFinished;
    }

    public char getWinner() {
        return winner;
    }

    public boolean isEmptyBox(int row, int column) throws IndexOutOfBoundsException {
        if (0 > row || row > 2 || 0 > column || column > 2) {
            throw new IndexOutOfBoundsException("Posición de casilla fuera de rango");
        }
        Box box = rows[row].getAt(column);
        return box.isEmpty();
    }

    public int getUtility(char valuePlayer1, char valuePlayer2) {
        int avalaibleLinesPlayer1 = 0;
        int avalaibleLinesPlayer2 = 0;
        if (winner == valuePlayer2)
            return -10;
        if (winner == valuePlayer1)
            return 10;
        for (Line line : lines) {
            if (line.isAvailableFor(valuePlayer1)) {
                avalaibleLinesPlayer1++;
            }
            if (line.isAvailableFor(valuePlayer2)) {
                avalaibleLinesPlayer2++;
            }
        }
        return avalaibleLinesPlayer1 - avalaibleLinesPlayer2;
    }

    public Board clone() {
        char firstValue = currentPlayer;
        char secondValue = nextPlayer;
        if (movements.size() % 2 == 1) { // verificación adicional en caso de no existir movimientos
            firstValue = nextPlayer;
            secondValue = currentPlayer;
        }
        Board newBoard = new Board(firstValue, secondValue);
        for (BoardMove move : movements) {
            int row = move.getRow();
            int column = move.getColumn();
            try {
                newBoard.markBox(row, column);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newBoard;
    }

    public boolean isFull() {
        return movements.size() == 9;
    }

    @Override
    public String toString() {
        String rowsString = "";
        for (Line line : rows) {
            rowsString += line.toString() + "\n";
        }
        return rowsString;
    }

    @Override
    public <T> void onNotify(T object) {
        winner = (char) object;
        isFinished = true;
    }
}
