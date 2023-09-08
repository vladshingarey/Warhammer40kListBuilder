package lbgui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lbgui.listbuilder.Utils;

public class MainController extends Utils {

    @FXML
    private BorderPane MainBorderPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button newListButton;

    @FXML
    public void initialize(){
        closeButton.setOnAction(actionEvent -> closeButtonApplication());
        newListButton.setOnAction(ActionEvent -> switchMainToNLScene());
    }
    @FXML
    private void closeButtonApplication() {
        try {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchMainToNLScene() {
        try{
            Utils.SceneSwitcher(MainBorderPane, "/lbgui/listbuilder/NL.fxml",900, 700);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}