package espol.tresenrayafxg3.controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;
import espol.tresenrayafxg3.models.*;

import java.io.IOException;

public class BoardComputerController extends BoardController {
    private PlayerIA computer;
    private char computerSymbol;
    private boolean humanStarts;

    public void initData(boolean startX, boolean startComputer) {
        super.initData(startX);

        humanStarts = !startComputer;

        computer = new PlayerIA(gameBoard);
        if (startComputer) {
            computerSymbol = gameBoard.getCurrentPlayer();
            playComputer();
        } else {
            computerSymbol = gameBoard.getNextPlayer();
        }
    }

    @Override
    protected void handleButtonClick(Button button) {
        super.handleButtonClick(button); // Jugada del humano

        if (gameBoard.getCurrentPlayer() == computerSymbol && !gameBoard.isFinished()) {
            playComputer();
        }
    }

    private void playComputer() {
        if (gameBoard.isFinished())
            return;

        disableBoard(true);
        PauseTransition waitForAI = new PauseTransition(Duration.seconds(0.3));
        waitForAI.setOnFinished(e -> {
            Position computerMove = computer.getMove();
            int row = computerMove.getRow();
            int column = computerMove.getColumn();
            Button boxButton = buttons[row][column];
            disableBoard(false);
            boxButton.setText(String.valueOf(computerSymbol));
            try {
                gameBoard.markBox(row, column);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            if (gameBoard.isFinished()) {
                // Mostrar el diálogo después de que JavaFX termine el ciclo actual
                Platform.runLater(() -> {
                    // Verificar que la escena aún está activa
                    if (board.getScene() != null) {
                        handleEndGame();
                    }
                });
            }
        });
        waitForAI.play();
    }

    private void handleEndGame() {
        // Verifica si el tablero sigue visible (para evitar doble ejecución al cambiar
        // de escena)
        if (board.getScene() == null)
            return;

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

    private void resetBoard() {
        board.getChildren().clear();
        addButtons(); // Este método está en BoardController (cámbialo a protected si era private)

        // Reconfigura los jugadores con base al inicio original
        char firstPlayer = humanStarts ? 'X' : 'O';
        char secondPlayer = humanStarts ? 'O' : 'X';
        gameBoard = new Board(firstPlayer, secondPlayer);
        computer = new PlayerIA(gameBoard);

        if (!humanStarts) {
            computerSymbol = gameBoard.getCurrentPlayer();
            playComputer();
        } else {
            computerSymbol = gameBoard.getNextPlayer();
        }
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

    private void disableBoard(boolean value) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = buttons[row][col];
                button.setDisable(value);
            }
        }
    }
}
