package lbgui.listbuilder;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lbgui.factories.ArmyAbilitiesFactory;
import lbgui.factories.DetachmentFactory;
import lbgui.factories.FactionFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Utils {

    public static Map<String, DetachmentFactory.MyObject> detachmentMap;
    public static Map<String, FactionFactory.MyObject> factionMap;
    public static Map<String, ArmyAbilitiesFactory.MyObject> armyAbilitiesMap;
    public static VBox rightPaneVbox;

    public static final ArrayList<Label> plusSignLabels = new ArrayList<>();

    public static int addPL(StringProperty totalPlProperty, int pl){
        int tempPLMain = Integer.parseInt(totalPlProperty.get());
        tempPLMain = tempPLMain + pl;
        return tempPLMain;
    }
    public static int subPL(StringProperty totalPlProperty, int pl){
        int tempPLMain = Integer.parseInt(totalPlProperty.get());
        tempPLMain = tempPLMain - pl;
        return tempPLMain;
    }
    public static int addPoints(StringProperty totalPointsProperty, int points) {
        int tempPointsMain = Integer.parseInt(totalPointsProperty.get());
        tempPointsMain = tempPointsMain + points;
        return tempPointsMain;
    }
    public static int subPoints(StringProperty totalPointsProperty, int points){
        int tempPointsMain = Integer.parseInt(totalPointsProperty.get());
        tempPointsMain = tempPointsMain - points;
        return tempPointsMain;
    }

    public static void SceneSwitcher(Parent currentRoot, String fxmlFile, double newWidth, double newHeight) {
        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlFile));
            Parent newRoot = loader.load();
            Scene newScene = new Scene(newRoot, newWidth, newHeight);
            ((Stage) currentRoot.getScene().getWindow()).setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> getSheetNamesFromXLS(File file){
        ObservableList<String> allSheetNames = FXCollections.observableArrayList();

        Workbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(workbook!=null) {
            for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
                allSheetNames.add(workbook.getSheetName(i));

            }
        }
        return allSheetNames;
    }

    public static void createFactionMap(String sheetName) {
        try {
            File file = new File("Factions.xls");
            factionMap = FactionFactory.createObjectsFromFactionXlsx(file, sheetName);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    public static void createDetachmentMap(String sheetName) {
        try {
            File file = new File("Detachments.xls");
            detachmentMap = DetachmentFactory.createObjectsFromDetachmentXlsx(file,sheetName);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    public static void createArmyAbilitiesMap(String sheetName){
        try{
            File file = new File("ArmyAbilities.xls");
            armyAbilitiesMap = ArmyAbilitiesFactory.createObjectsFromArmyAbilitiesXlsx(file, sheetName);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static HBox getHbox(String name, int PL, int points, String buttonName) {
        Label PlusSIgn = new Label("\u002B");
        Label bullet = new Label("\u2022");
        Label bullet2 = new Label("\u2022");
        Label square = new Label("\u25A0");
        Region region = new Region();
        Label unitName = new Label(name);
        Label unitPC = new Label(PL + " PL");
        Label unitPoints = new Label(points + " pts");

        PlusSIgn.setId(buttonName);
        plusSignLabels.add(PlusSIgn);

        square.setPadding(new Insets(0, 0, 0, 5));
        unitName.setPadding(new Insets(0, 0, 0, 5));
        bullet.setPadding(new Insets(0, 0, 0, 5));
        unitPC.setPadding(new Insets(0, 0, 0, 5));
        bullet2.setPadding(new Insets(0, 0, 0, 5));
        unitPoints.setPadding(new Insets(0, 0, 0, 5));
        PlusSIgn.setFont(new Font("Arial", 16));

        HBox.setHgrow(region, Priority.ALWAYS);

        return new HBox(square,unitName,bullet,unitPC,bullet2,unitPoints,region, PlusSIgn);
    }

    public static TitledPane getUnitTitledPane(String name, Label PLL, Label pointsss, Label lbl, String addDelete) {
        if(addDelete.equals("\u2715")){
            lbl.setText("\u2715");
        }
        else {
            lbl.setText("\u002B");
        }

        Label bullet = new Label("\u2022");
        Label bullet2 = new Label("\u2022");
        Label circle = new Label("\u25CF");
        Label unitName = new Label(name);

        circle.setPadding(new Insets(0, 0, 0, 5));
        unitName.setPadding(new Insets(0, 0, 0, 5));
        bullet.setPadding(new Insets(0, 0, 0, 5));
        PLL.setPadding(new Insets(0, 0, 0, 5));
        bullet2.setPadding(new Insets(0, 0, 0, 5));
        pointsss.setPadding(new Insets(0, 0, 0, 5));
        lbl.setFont(new Font("Arial", 14));

        TitledPane titledPane = new TitledPane();
        titledPane.setAlignment(Pos.CENTER);
        titledPane.setAlignment(Pos.CENTER);

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        HBox contentVbox = new HBox();
        contentVbox.setAlignment(Pos.CENTER);
        contentVbox.setPadding(new Insets(0, 10, 0, 20));
        contentVbox.minWidthProperty().bind(titledPane.widthProperty());

        HBox region = new HBox();
        region.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(region, Priority.ALWAYS);
        contentVbox.getChildren().addAll(circle,unitName,bullet,PLL,bullet2,pointsss,region,lbl);

        titledPane.setGraphic(contentVbox);
        return titledPane;
    }


    public static Object[] getRoleTitledPane(String role ) {
        Label unitRole = new Label(role);
        Label unitPC = new Label(null + " PL,");
        Label unitPoints = new Label(null + "pts");
        Label dot = new Label("\u2022");

        unitPoints.setId(role+"PointsLabel");
        unitPC.setId(role+"PlLabel");


        unitRole.setPadding(new Insets(0, 0, 0, 5));
        unitPC.setPadding(new Insets(0, 0, 0, 5));
        unitPoints.setPadding(new Insets(0, 0, 0, 5));
        dot.setPadding(new Insets(0, 0, 0, 5));

        TitledPane titledPane = new TitledPane();
        titledPane.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER_LEFT);

        HBox contentVbox = new HBox();
        contentVbox.setAlignment(Pos.CENTER_LEFT);
        contentVbox.getChildren().addAll(unitRole,dot,unitPC, unitPoints);
        titledPane.setGraphic(contentVbox);

        return new Object[]{titledPane,unitPC,unitPoints};
    }

    public static DetachmentFactory.MyObject getDetachment(String selectedDetachment){

        return detachmentMap.get(selectedDetachment);
    }
    public static Map<String, FactionFactory.MyObject> getFactionMap() {

        return factionMap;
    }
    public static Map<String, ArmyAbilitiesFactory.MyObject> getArmyAbilitiesMap(){
        return armyAbilitiesMap;
    }
    public static ArrayList<Label> getPlusSignLabels() {
        return plusSignLabels;
    }

    public static void setRightPaneVbox(VBox rp) {
        rightPaneVbox = rp;
    }
    public static VBox getRightSplitPaneVbox(){
        return rightPaneVbox;
    }

}
