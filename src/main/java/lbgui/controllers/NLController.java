package lbgui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lbgui.listbuilder.Utils;
import java.io.File;



import static lbgui.listbuilder.Utils.getSheetNamesFromXLS;

public class NLController {

    @FXML
    private ComboBox<String> selectFactionBox;
    @FXML
    private ComboBox<String> selectDetachmentBox;
    @FXML
    private Button generateUnitsButton;
    @FXML
    private BorderPane NLBorderPane;


    @FXML
    public void initialize(){
        selectFactionBox.setItems(getSheetNamesFromXLS(new File("Factions.xls")));
        selectDetachmentBox.setItems(getSheetNamesFromXLS(new File("Detachments.xls")));
        generateUnitsButton.setDisable(true);
        selectFactionBox.valueProperty().addListener((observable, oldValue, newValue) -> checkComboBoxes());
        selectDetachmentBox.valueProperty().addListener((observable, oldValue, newValue) -> checkComboBoxes());
        generateUnitsButton.setOnAction(ActionEvent -> switchNLtoMainListScene());
    }
    public void switchNLtoMainListScene() {

        try{
            String selectedFaction = selectFactionBox.getValue();
            String selectedDetachment = selectDetachmentBox.getValue();

            Utils.createDetachmentMap(selectedDetachment);
            Utils.createFactionMap(selectedFaction);
            Utils.createArmyAbilitiesMap(selectedFaction);

            if(selectedFaction.equals("Harlequins")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lbgui/listbuilder/Harlequins.fxml"));
                Parent root = loader.load();
                HarlequinsController harlequinsController = loader.getController();
                harlequinsController.initialize(selectedFaction, selectedDetachment);
                Scene newScene = new Scene(root, 900, 700);
                ((Stage) NLBorderPane.getScene().getWindow()).setScene(newScene);
                harlequinsController.setScene(newScene);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void checkComboBoxes() {
        generateUnitsButton.setDisable(selectFactionBox.getValue() == null || selectDetachmentBox.getValue() == null);
    }

}
