package lbgui.dataModels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lbgui.factories.FactionFactory;
import lbgui.listbuilder.Utils;

import java.util.Map;

public class Starweaver {

    private final FactionFactory.MyObject starweaver;
    private String name;
    private int PL;
    private int pointcost;
    private String role;
    private Object[] roleUtils;
    private TitledPane objTitledPane;
    private VBox starweaverInfoBox;
    private final VBox rightSplitPaneVbox;

    public Starweaver() {
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.starweaver = ourfactionMap.get("Starweaver");
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }
    public TitledPane getUnitTitledPane(Label delete){
        Label starPlLabel = new Label();
        Label startPointsLabel = new Label();
        starPlLabel.setText(this.PL + " PL,");
        startPointsLabel.setText(this.pointcost + " pts");
        return Utils.getUnitTitledPane(this.name, starPlLabel, startPointsLabel,delete,"\u2715");

    }
    public TitledPane getStarweaverRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
    }
    public void setObjTitledPane(TitledPane titledPane){
        this.objTitledPane = titledPane;
        createStarweaverInfoBox();
        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(starweaverInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(starweaverInfoBox);
                }
            } else {
                this.rightSplitPaneVbox.getChildren().clear();
            }

        });



    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }
    public Label getDTPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getDTPointsLabel() {
        return (Label) this.roleUtils[2];
    }
    public int getPL(){
        return this.PL;
    }
    public int getPointcost(){
        return this.pointcost;
    }
    public String getRole() {
        return this.role;
    }

    private void initializeVariables() {
            if (this.starweaver != null) {
                this.name = this.starweaver.getName();
                this.PL = this.starweaver.getPL();
                this.pointcost = this.starweaver.getPointCost();
                this.role = this.starweaver.getRole();
                this.roleUtils = Utils.getRoleTitledPane(this.role);
            }
        }

    public void createStarweaverInfoBox() {
        starweaverInfoBox = new VBox(10);
        starweaverInfoBox.setPadding(new Insets(10));
        starweaverInfoBox.setAlignment(Pos.TOP_LEFT);
        Label label = new Label("Nothing to Edit");

        starweaverInfoBox.getChildren().add(label);

    }

    @Override
    public String toString() {

        return "Starweaver" + "\n" +
                "Power Level: " + getPL() + " Point Cost: " + getPointcost() + "\n";
    }
}
