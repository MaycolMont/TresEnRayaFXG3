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
        // getting BoardController
        try { // pasarle un callback que funcione como decorador
            FXMLLoader loader = new FXMLLoader(App.class.getResource("board.fxml"));
            Parent root = loader.load();
            BoardController boardController = loader.getController();
            boardController.initData(
                firstPlayerGroup.getSelectedToggle().getUserData().toString().equals("X")
            );
            App.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mode = modeGroup.getSelectedToggle().getUserData().toString();
        String firstPlayer = firstPlayerGroup.getSelectedToggle().getUserData().toString();
        String computerSymbol = computerSymbolGroup.getSelectedToggle().getUserData().toString();
    }
}
