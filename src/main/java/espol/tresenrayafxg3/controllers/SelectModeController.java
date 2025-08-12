/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package espol.tresenrayafxg3.controllers;

import espol.tresenrayafxg3.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author elmay
 */
public class SelectModeController implements Initializable {
    @FXML private Button btnStart;
    @FXML private ToggleGroup modeGroup;
    @FXML private ToggleGroup firstPlayerGroup;
    @FXML private VBox computerSymbolContainer;
    @FXML private ToggleGroup computerSymbolGroup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnStart.setOnAction(event -> {
            switchToBoard();
        });
        // Configurar los ToggleGroups
        modeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && "HUMAN_VS_AI".equals(newValue.getUserData())) {
                computerSymbolContainer.setVisible(true);
            } else {
                computerSymbolContainer.setVisible(false);
            }
        });
    }

    private void switchToBoard() {
        String mode = modeGroup.getSelectedToggle().getUserData().toString();
        boolean startX = firstPlayerGroup.getSelectedToggle().getUserData().toString().equals("X");
        String computerSymbol = null;
        if (computerSymbolGroup.getSelectedToggle() != null) {
            computerSymbol = computerSymbolGroup.getSelectedToggle().getUserData().toString();
        }

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("board.fxml"));

            if ("HUMAN_VS_HUMAN".equals(mode)) {
                // Controlador para Humano vs Humano
                BoardController controller = new BoardController();
                loader.setController(controller);
                Parent root = loader.load();
                controller.initData(startX);
                App.setRoot(root);

            } else if ("HUMAN_VS_AI".equals(mode)) {
                // Controlador para Humano vs IA
                BoardComputerController controller = new BoardComputerController();
                loader.setController(controller);
                Parent root = loader.load();

                boolean computerStarts = computerSymbol != null
                        && computerSymbol.equals(firstPlayerGroup.getSelectedToggle().getUserData().toString());
                controller.initData(startX, computerStarts);

                App.setRoot(root);
            } else {
                throw new IllegalArgumentException("Modo no soportado: " + mode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
