package lbgui.dataModels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lbgui.factories.FactionFactory;
import lbgui.listbuilder.Utils;
import java.util.Map;

public class WebwayGate {
    private final FactionFactory.MyObject webwaygate;
    private String name;
    private int PL;
    private int pointcost;
    private String role;
    private Object[] roleUtils;
    private final VBox rightSplitPaneVbox;
    private TitledPane objTitledPane;
    private VBox webwayGateInfoBox;

    public WebwayGate(){ // WebwayGate Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.webwaygate = ourfactionMap.get("Webway Gate");
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }
    public TitledPane getUnitTitledPane(Label delete){
        Label webPlLabel = new Label();
        Label webPointsLabel = new Label();
        webPlLabel.setText(this.PL + " PL,");
        webPointsLabel.setText(this.pointcost + " pts");
        return Utils.getUnitTitledPane(this.name, webPlLabel, webPointsLabel,delete,"\u2715");

    }
    public TitledPane getWebwayGateRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
    }
    public Label getFortificationPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getFortificationPointsLabel() {
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
    public void initializeVariables(){
        if(this.webwaygate!= null) {
            this.name = this.webwaygate.getName();
            this.PL = this.webwaygate.getPL();
            this.pointcost = this.webwaygate.getPointCost();
            this.role = this.webwaygate.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
        }
    }
    public void setObjTitledPane(TitledPane titledPane) {
        this.objTitledPane = titledPane;
        createWebwayGateInfoBox();
        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(webwayGateInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(webwayGateInfoBox);
                }
            } else {
                this.rightSplitPaneVbox.getChildren().clear();
            }

        });
    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }

    public void createWebwayGateInfoBox() {
        webwayGateInfoBox = new VBox(10);
        webwayGateInfoBox.setPadding(new Insets(10));
        webwayGateInfoBox.setAlignment(Pos.TOP_LEFT);
        Label label = new Label("Nothing to Edit");
        webwayGateInfoBox.getChildren().add(label);
    }

    @Override
    public String toString() {

        return "Webway Gate" + "\n" +
                "Power Level: " + getPL() + " Point Cost: " + getPointcost() + "\n";
    }

}