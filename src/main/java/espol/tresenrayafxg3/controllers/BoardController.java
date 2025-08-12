/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.tresenrayafxg3.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
    protected GridPane board;

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

    protected void addButtons() {
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
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        try {
            button.setText(String.valueOf(gameBoard.getCurrentPlayer()));
            gameBoard.markBox(row, col);
            button.setDisable(true);

            if (gameBoard.isFinished()) {
                handleEndGame();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private void handleEndGame() {
    char winner = gameBoard.getWinner();
    String message;
    if (winner == 'X' || winner == 'O') {
        message = "¡Ha ganado el jugador '" + winner + "'!";
    } else {
        message = "¡Empate!";
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Juego Terminado");
    alert.setHeaderText(message);
    alert.setContentText("¿Deseas volver a jugar o regresar al menú principal?");

    ButtonType btnReplay = new ButtonType("Volver a jugar");
    ButtonType btnMenu = new ButtonType("Menú Principal");

    alert.getButtonTypes().setAll(btnReplay, btnMenu);

    alert.showAndWait().ifPresent(response -> {
        if (response == btnReplay) {
            resetBoard();
        } else if (response == btnMenu) {
            goToMainMenu();
        }
    });
}

    private void goToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/espol/tresenrayafxg3/selectMode.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) board.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
