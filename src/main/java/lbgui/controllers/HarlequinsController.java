package lbgui.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lbgui.factories.ArmyAbilitiesFactory;
import lbgui.factories.DetachmentFactory;
import lbgui.factories.FactionFactory;
import lbgui.listbuilder.EventHandlers;
import lbgui.listbuilder.Utils;
import java.io.File;
import java.util.*;

public class HarlequinsController {

    @FXML
    private VBox leftSplitPaneVbox;
    @FXML
    private VBox middleSplitPaneVBox;
    @FXML
    private VBox rightSplitPaneVbox;
    @FXML
    private VBox topBox;
    private String selectedFaction;
    private String selectedDetachment;
    private VBox RHVbox;
    private StringProperty totalPlProperty;
    private StringProperty totolPointsProperty;
    private ArrayList<StringProperty> detachmentSlotLabels;
    private Map<String, FactionFactory.MyObject> ourfactionMap; // Faction Map
    private Map<String, DetachmentFactory.MyObject> detachmentMap; // Detachment Map
    private DetachmentFactory.MyObject ourDetachment;
    private EventHandlers eventHandlers;
    private boolean saveComplete;

    @FXML
    public void initialize(String selectedFaction, String selectedDetachment ) {
        this.selectedFaction = selectedFaction; // pulls in the selected Faction from new list menu
        this.selectedDetachment = selectedDetachment; // pulls in the selected Detahment from the new list menu
        this.detachmentSlotLabels = new ArrayList<>();

        setOurDetachment(); // sets our detachment for this class
        setOurFactionMap(); // gets our faction map of Harlequins
        setOurArmyAbilitiesMap();
        passRightPaneVboxToUtils();
        initializeLeftVbox(); // sets the title of the Accordian for faction
        initializeMiddleVbox();
        initializeTopBox();
        saveComplete = false;

        eventHandlers = new EventHandlers(this.RHVbox, this.detachmentSlotLabels, this.ourDetachment,
                this.totalPlProperty,this.totolPointsProperty);
        for (Label label : Utils.getPlusSignLabels()) {
            label.setOnMouseClicked(eventHandlers);
        }
    }
    private void setOurDetachment(){
        this.ourDetachment = Utils.getDetachment(this.selectedDetachment);
    }
    private void setOurFactionMap(){
        this.ourfactionMap = Utils.getFactionMap();
    }
    private void setOurArmyAbilitiesMap(){
        Map<String, ArmyAbilitiesFactory.MyObject> ourArmyAbilitiesMap = Utils.getArmyAbilitiesMap();
    }
    private void passRightPaneVboxToUtils(){Utils.setRightPaneVbox(rightSplitPaneVbox);}

    public void setScene(Scene scene){
        Stage stage = (Stage) scene.getWindow();
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            closeButtonFunc();
        });
    }

    private void initializeMiddleVbox(){

        Accordion middleAccordian = new Accordion();
        this.middleSplitPaneVBox.getChildren().add(middleAccordian);
        TitledPane rosterHarlequinsPane = new TitledPane();
        rosterHarlequinsPane.setAlignment(Pos.CENTER);
        rosterHarlequinsPane.setExpanded(true);
        HBox rosterHbox = createRosterHB();
        rosterHbox.setId("RosterHbox");
        rosterHarlequinsPane.setGraphic(rosterHbox);
        middleAccordian.getPanes().add(rosterHarlequinsPane);
        this.RHVbox = new VBox();
        this.RHVbox.setId("MainVBox");
        rosterHarlequinsPane.setContent(RHVbox);
        middleAccordian.setExpandedPane(rosterHarlequinsPane);

        Label hqTag = new Label("HQ: ");
        hqTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty hqProperty = new SimpleStringProperty("0");
        Label hqSlots = new Label();
        hqSlots.textProperty().bind(Bindings.concat(hqProperty," / ", ourDetachment.getHq()));
        hqTag.setPadding(new Insets(0,0,0,40));

    }

    private void initializeLeftVbox(){
        Accordion leftAccordian = new Accordion();
        leftSplitPaneVbox.getChildren().add(leftAccordian);
        TitledPane harlequinsPane = new TitledPane();
        harlequinsPane.setText(selectedFaction);
        harlequinsPane.setAlignment(Pos.CENTER);
        leftAccordian.getPanes().add(harlequinsPane);
        leftAccordian.setExpandedPane(harlequinsPane);

        TitledPane hq = new TitledPane();
        TitledPane troop = new TitledPane();
        TitledPane elite = new TitledPane();
        TitledPane fastAttack = new TitledPane();
        TitledPane heavySupport = new TitledPane();
        TitledPane dedicatedTransport = new TitledPane();
        TitledPane fortification = new TitledPane();

        hq.setText("HQ");
        troop.setText("Troops");
        elite.setText("Elites");
        fastAttack.setText("Fast Attack");
        heavySupport.setText("Heavy Support");
        dedicatedTransport.setText("Dedicated Transport");
        fortification.setText("Fortification");

        VBox hqBox = new VBox();
        VBox troopBox = new VBox();
        VBox eliteBox = new VBox();
        VBox fastAttackBox = new VBox();
        VBox heavySupportBox = new VBox();
        VBox dedicatedTransportBox = new VBox();
        VBox fortificationBox = new VBox();

        hq.setContent(hqBox);
        troop.setContent(troopBox);
        elite.setContent(eliteBox);
        fastAttack.setContent(fastAttackBox);
        heavySupport.setContent(heavySupportBox);
        dedicatedTransport.setContent(dedicatedTransportBox);
        fortification.setContent(fortificationBox);

        harlequinsPane.setContent(new VBox(hq, troop, elite, fastAttack, heavySupport, dedicatedTransport, fortification));

        for (Map.Entry<String, FactionFactory.MyObject> entry : ourfactionMap.entrySet()) {
            String name = entry.getKey();
            String role = entry.getValue().getRole();
            int PL = entry.getValue().getPL();
            int pointcost = entry.getValue().getPointCost();

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            if(role.equals("HQ")){
                if(name.equals("Shadowseer")){
                    HBox shadowSeerHbox = Utils.getHbox(name,PL, pointcost, "addShadowseer");
                    hqBox.getChildren().add(shadowSeerHbox);
                }
                if(name.equals("Troupe Master")){
                    HBox troupeMasterrHbox = Utils.getHbox(name,PL, pointcost, "addTroupeMaster");
                    hqBox.getChildren().add(troupeMasterrHbox);
                }
            }
            if(role.equals("Troop")){
                HBox troupeHbox = Utils.getHbox(name,PL, pointcost, "addTroupe");
                troopBox.getChildren().add(troupeHbox);
            }
            if(role.equals("Elite")) {
                if(name.equals("Death Jester")) {
                    HBox deathJesterHbox = Utils.getHbox(name,PL, pointcost, "addDeathJester");
                    eliteBox.getChildren().add(deathJesterHbox);
                }
                if(name.equals("Solitaire")) {
                    HBox solitaireHbox = Utils.getHbox(name,PL, pointcost, "addSolitaire");
                    eliteBox.getChildren().add(solitaireHbox);
                }
            }
            if(role.equals("Fast Attack")) {
                HBox skyweaversHbox = Utils.getHbox(name,PL, pointcost, "addskyweavers");
                fastAttackBox.getChildren().add(skyweaversHbox);
            }
            if(role.equals("Heavy Support")) {
                HBox voidweaversHbox = Utils.getHbox(name,PL, pointcost, "addVoidweavers");
                heavySupportBox.getChildren().add(voidweaversHbox);
            }
            if(role.equals("Dedicated Transport")) {
                HBox starweaverHbox = Utils.getHbox(name,PL, pointcost, "addStarweaver");
                dedicatedTransportBox.getChildren().add(starweaverHbox);
            }
            if(role.equals("Fortification")){
                HBox webwayGateHbox = Utils.getHbox(name,PL, pointcost, "addWebwayGate");
                fortificationBox.getChildren().add(webwayGateHbox);
            }
        }

    }
    private void initializeTopBox() {
        Button searchButton = new Button();
        searchButton.setText("Search");
        TextField textField = new TextField();
        textField.setPrefWidth(270);
        textField.setPromptText("Name, Role, etc...");

        //HQ slots
        Label hqTag = new Label("HQ: ");
        hqTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty hqProperty = new SimpleStringProperty("0");
        Label hqSlots = new Label();
        hqSlots.textProperty().bind(Bindings.concat(hqProperty," / ", ourDetachment.getHq()));
        hqTag.setPadding(new Insets(0,0,0,40));

        //Troop slots
        Label troopTag = new Label("Troop: ");
        troopTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty troopProperty = new SimpleStringProperty("0");
        Label troopSlots = new Label();
        troopSlots.textProperty().bind(Bindings.concat(troopProperty," / ", ourDetachment.getTroops()));
        troopTag.setPadding(new Insets(0,0,0,25));

        // Elite slots
        Label eliteTag = new Label("Elite: ");
        eliteTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty eliteProperty = new SimpleStringProperty("0");
        Label eliteSlots = new Label();
        eliteSlots.textProperty().bind(Bindings.concat(eliteProperty," / ", ourDetachment.getElites()));
        eliteTag.setPadding(new Insets(0,0,0,25));

        //Elite character slots
        Label eliteCharTag = new Label("Elite Character: ");
        eliteCharTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty eliteCharPropertly = new SimpleStringProperty("0");
        Label eliteCharSlots = new Label();
        eliteCharSlots.textProperty().bind(Bindings.concat(eliteCharPropertly," / ", ourDetachment.getEliteCharacter()));
        eliteCharTag.setPadding(new Insets(0,0,0,25));

        //Fast Attack slots
        Label faTag = new Label("Fast Attack: ");
        faTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty faTagPropertly = new SimpleStringProperty("0");
        Label faSlots = new Label();
        faSlots.textProperty().bind(Bindings.concat(faTagPropertly," / ", ourDetachment.getFastAttack()));
        faTag.setPadding(new Insets(0,0,0,25));

        //Heavy support slots
        Label heavyTag = new Label("Heavy Support: ");
        heavyTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty heavyTagPropertly = new SimpleStringProperty("0");
        Label heavySlots = new Label();
        heavySlots.textProperty().bind(Bindings.concat(heavyTagPropertly," / ", ourDetachment.getHeavySupport()));
        heavyTag.setPadding(new Insets(0,0,0,25));

        //Flyer slots
        Label flyerTag = new Label("Flyer: ");
        flyerTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty flyerTagPropertly = new SimpleStringProperty("0");
        Label flyerSlots = new Label();
        flyerSlots.textProperty().bind(Bindings.concat(flyerTagPropertly," / ", ourDetachment.getFlyer()));
        flyerTag.setPadding(new Insets(0,0,0,25));

        //Lord of War Slots
        Label lowTag = new Label("Lord of War: ");
        lowTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty lowTagPropertly = new SimpleStringProperty("0");
        Label lowSlots = new Label();
        lowSlots.textProperty().bind(Bindings.concat(lowTagPropertly," / ", ourDetachment.getLordOfWar()));
        lowTag.setPadding(new Insets(0,0,0,25));

        //Fortification SLots
        Label fortTag = new Label("Fortification: ");
        fortTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty fortTagPropertly = new SimpleStringProperty("0");
        Label fortSlots = new Label();
        fortSlots.textProperty().bind(Bindings.concat(fortTagPropertly," / ", ourDetachment.getFortification()));
        fortTag.setPadding(new Insets(0,0,0,25));

        //Transport Slots
        Label transTag = new Label("Transport: ");
        transTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty transTagPropertly = new SimpleStringProperty("0");
        StringProperty transTagInfantryProperty = new SimpleStringProperty("0");
        Label transSlots = new Label();
        transSlots.textProperty().bind(Bindings.concat(transTagPropertly," / ", transTagInfantryProperty));
        transTag.setPadding(new Insets(0,0,0,25));

        int cpVal = ourDetachment.getCP();
        Label cpTag = new Label("Command Points: ");
        cpTag.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        StringProperty cpTagPropertly = new SimpleStringProperty(String.valueOf(cpVal));
        Label cpCap = new Label();
        cpCap.textProperty().bind(Bindings.concat(cpTagPropertly," / ", ourDetachment.getCP()));
        cpTag.setPadding(new Insets(0,0,0,25));

        this.detachmentSlotLabels.addAll(List.of());
        this.detachmentSlotLabels.addAll(Arrays.asList(hqProperty,troopProperty,eliteProperty,eliteCharPropertly,
                faTagPropertly, heavyTagPropertly,flyerTagPropertly,lowTagPropertly,fortTagPropertly,transTagPropertly,
                transTagInfantryProperty,cpTagPropertly));

        HBox background = new HBox(textField, searchButton, cpTag, cpCap,hqTag, hqSlots,troopTag,
                troopSlots, eliteTag, eliteSlots, eliteCharTag, eliteCharSlots,
                faTag, faSlots,  heavyTag, heavySlots,  flyerTag, flyerSlots, lowTag,
                lowSlots,  fortTag, fortSlots, transTag,transSlots);
        background.setAlignment(Pos.TOP_LEFT);

        Insets margin = new Insets(10, 0, 0, 0);
        for (int m = 0; m < background.getChildren().size(); m++) {
            HBox.setMargin(background.getChildren().get(m), margin);
        }
        HBox.setMargin(textField, new Insets(5, 2, 2, 2));
        HBox.setMargin(searchButton, new Insets(5, 2, 2, 2));
        topBox.getChildren().add(background);

    }
    private HBox createRosterHB(){
        HBox hbox = new HBox();
        Label rosterTag = new Label("Roster: " + this.selectedFaction);
        rosterTag.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        Label bullet = new Label("\u2022");
        Label bullet2 = new Label("\u2022");
        this.totalPlProperty = new SimpleStringProperty("0");
        Label rosterPLLabel = new Label();
        rosterPLLabel.textProperty().bind(Bindings.concat(totalPlProperty, " PL"));
        this.totolPointsProperty = new SimpleStringProperty("0");
        Label rosterTotalPointsLabel = new Label();
        rosterTotalPointsLabel.setId("RTPLabel");
        rosterTotalPointsLabel.textProperty().bind(Bindings.concat(totolPointsProperty," / 2000 Pts"));
        bullet.setPadding(new Insets(0,0,0,12));
        bullet2.setPadding(new Insets(0,0,0,7));
        rosterPLLabel.setPadding(new Insets(0,0,0,7));
        rosterTotalPointsLabel.setPadding(new Insets(0,0,0,10));

        hbox.getChildren().addAll(rosterTag, bullet,rosterPLLabel, bullet2, rosterTotalPointsLabel);

        return  hbox;
    }

    @FXML
    private void saveFunc() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            eventHandlers.printListToFile(filePath);
            saveComplete = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Canceled");
            alert.setHeaderText(null);
            alert.setContentText("Save operation canceled.");
            alert.showAndWait();
            saveComplete = false;
        }
    }

    @FXML
    private void closeButtonFunc(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to save before closing?");

        ButtonType saveButton = new ButtonType("Save");
        ButtonType closeButton = new ButtonType("Close Without Saving");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveButton, closeButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == saveButton) {
                // Perform the save operation here
                saveFunc();
                if(saveComplete) {
                    closeApplication();
                }
            } else if (result.get() == closeButton) {
                closeApplication();
            }
        }
    }

    private void closeApplication() {
        try {
            Stage stage = (Stage) topBox.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}