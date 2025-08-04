/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.tresenrayafxg3.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import espol.tresenrayafxg3.models.*;

/**
 * FXML Controller class
 *
 * @author maycmont
 */
public class BoardController implements Initializable {
    // FXML GridPane that represents the game board
    // It will contain buttons for each cell in the 3x3 grid

    @FXML
    private GridPane board;

    // Reference to the Board model
    protected Board gameBoard;
    protected Button[][] buttons = new Button[3][3];

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButtons();
        System.out.println("BoardController initialized");
    }

    public void initData(boolean startX) {
        char firstPlayer = startX ? 'X' : 'O';
        char secondPlayer = startX ? 'O' : 'X';
        gameBoard = new Board(firstPlayer, secondPlayer);
    }

    private void addButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setId("btn" + row + col);
                button.getStyleClass().add("board-button");
                button.setOnAction((event) -> handleButtonClick(button));
                GridPane.setRowIndex(button, row);
                GridPane.setColumnIndex(button, col);
                buttons[row][col] = button;
                board.getChildren().add(button);
            }
        }
    }

    private void resetBoard() {
        board.getChildren().clear();
        gameBoard = new Board('X', 'O'); // Reset the game board
        System.out.println("Board reset");
        addButtons();
    }

    protected void handleButtonClick(Button button) {
        // Get the position of the button
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        // Mark the corresponding box on the game board
        try {
            button.setText(String.valueOf(gameBoard.getCurrentPlayer()));
            gameBoard.markBox(row, col);
            button.setDisable(true); // desabilita el botón para no poder volver a ser presionado
            if (gameBoard.isFinished()) {
                System.out.println("Game finished! Current player: " + gameBoard.getCurrentPlayer());
                resetBoard();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
