package espol.tresenrayafxg3.controllers;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;
import espol.tresenrayafxg3.models.*;

public class BoardComputerController extends BoardController {
    private PlayerIA computer;
    private char computerSymbol;

    public void initData(boolean startX, boolean startComputer) {
        super.initData(startX);

        computer = new PlayerIA(gameBoard);
        if (startComputer) {
            computerSymbol = gameBoard.getCurrentPlayer();
            playComputer();
        } else {
            computerSymbol = gameBoard.getNextPlayer();
        }
    }

    private void playComputer() {
        if (gameBoard.isFinished()) return;

        disableBoard(true);
        PauseTransition waitForAI = new PauseTransition(Duration.seconds(1));
        waitForAI.setOnFinished(e -> {
            Position computerMove = computer.getMove();
            int row = computerMove.getRow();
            int column = computerMove.getColumn();
            Button boxButton = buttons[row][column];
            disableBoard(false);
            boxButton.fire();
        });
        waitForAI.play();
    }

    @Override
    protected void handleButtonClick(Button button) {
        super.handleButtonClick(button); // jugada del humano

        // Si el juego sigue y es turno de la IA
        if (!gameBoard.isFinished() && (Character) gameBoard.getCurrentPlayer() == computerSymbol) {
            playComputer();
        }
    }

    private void disableBoard(boolean value) {
        for (int row=0; row<3; row++) {
            for (int col=0; col<3; col++) {
                Button button = buttons[row][col];
                if (value == true) { // solo habilitar las casillas no marcadas
                    String textButton = button.getText();
                    boolean buttonMarked = "X".equals(textButton) || "O".equals(textButton);
                    if (!buttonMarked) {
                        button.setDisable(value);
                    }
                } else {
                    button.setDisable(value);
                }
            }
        }
    }
}
