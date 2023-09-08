package lbgui.dataModels;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lbgui.factories.FactionFactory;
import lbgui.listbuilder.Utils;
import java.util.Map;


public class Voidweavers {
    private final FactionFactory.MyObject voidweavers;
    private String name;
    private int PL;
    private int pointcost;
    private String role;
    private Object[] roleUtils;
    private TitledPane objTitledPane;
    private int rangedWeaponSelection;
    private final VBox rightSplitPaneVbox;
    private  VBox VweaverInfoBox;
    private VBox innerVBox;

    private VBox primWepVbox;
    private Label primWep;

    public Voidweavers(){ // Voidweavers Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.voidweavers = ourfactionMap.get("Voidweaver");
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }

    public int getRangedWeaponSelection() {
        return rangedWeaponSelection;
    }

    public void setRangedWeaponSelection(int rangedWeaponSelection) {
        this.rangedWeaponSelection = rangedWeaponSelection;
        this.primWepVbox.getChildren().clear();
        this.innerVBox.getChildren().remove(this.primWepVbox);

        if (this.rangedWeaponSelection == 0) {
            this.primWep.setText("Shuriken Cannon");
        }
        if(this.rangedWeaponSelection == 1) {
            this.primWep.setText("Prismatic Cannon - Focused Lance");
        }
        if(this.rangedWeaponSelection == 2) {
            this.primWep.setText("Prismatic Cannon - Dispersed Pulse");
        }
        this.primWepVbox.getChildren().add(this.primWep);
        this.innerVBox.getChildren().add(this.primWepVbox);
    }
    public FactionFactory.MyObject getVoidweavers() {
        return this.voidweavers;
    }

    public TitledPane getUnitTitledPane(Label delete){
        Label voidPlLabel = new Label();
        Label voidPointsLabel = new Label();
        voidPlLabel.setText(this.PL + " PL,");
        voidPointsLabel.setText(this.pointcost + "pts");
        return Utils.getUnitTitledPane(this.name, voidPlLabel, voidPointsLabel,delete,"\u2715");

    }
    public TitledPane getVoidweaversRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
    }
    public Label getHeavySupportPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getHeavySupportPointsLabel() {
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
        if(this.voidweavers!= null) {
            this.name = this.voidweavers.getName();
            this.PL = this.voidweavers.getPL();
            this.pointcost = this.voidweavers.getPointCost();
            this.role = this.voidweavers.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
        }
    }
    public void setObjTitledPane(TitledPane titledPane, StringProperty totalPlProperty, StringProperty totalPointsProperty) {
        this.objTitledPane = titledPane;
        this.innerVBox = new VBox();

        this.primWep = new Label();
        this.primWep.setText("Shuriken Cannon");
        this.primWepVbox = new VBox();
        this.primWepVbox.getChildren().add(this.primWep);

        this.innerVBox.getChildren().add(this.primWepVbox);
        this.objTitledPane.setContent(this.innerVBox);
        createVoidweaversInfoBox();

        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(VweaverInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(VweaverInfoBox);
                }
            }else {
                this.rightSplitPaneVbox.getChildren().clear();
            }
        });
    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }

    public void createVoidweaversInfoBox() {
        VweaverInfoBox = new VBox(10);
        VweaverInfoBox.setPadding(new Insets(10));
        VweaverInfoBox.setAlignment(Pos.TOP_LEFT);

        FactionFactory.MyObject sSeer = this.getVoidweavers();
        String wG = sSeer.getWargear().split("\\r?\\n")[0];
        String[] oWG = sSeer.getOpWargear().split("\n");

        Label RangedWeapon = new Label("Ranged Weapon");
        RadioButton RW1 = new RadioButton(wG);
        RadioButton RW2 = new RadioButton(oWG[0]);
        RadioButton RW3 = new RadioButton(oWG[7]);
        ToggleGroup rangedWeaponGroup = new ToggleGroup();
        RadioButton[] radioButtons = {RW1, RW2, RW3};

        VBox rangeWVbox = new VBox();
        rangeWVbox.setPadding(new Insets(5));
        rangeWVbox.getChildren().add(RangedWeapon);
        rangeWVbox.getChildren().addAll(RW1, RW2, RW3);
        BorderPane rangeWBP = new BorderPane();
        rangeWBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        rangeWBP.setCenter(rangeWVbox);

        for(RadioButton radioButton : radioButtons){
            radioButton.setToggleGroup(rangedWeaponGroup);
        }

        if (this.getRangedWeaponSelection() == 0) {
            RW1.setSelected(true);
        } else if (this.getRangedWeaponSelection() == 1) {
            RW2.setSelected(true);
        } else {
            rangedWeaponGroup.selectToggle(null);
        }

        for (int i = 0; i < radioButtons.length; i++) {
            int index = i;
            radioButtons[i].setOnAction(event -> {

                this.setRangedWeaponSelection(index);
            });
        }
        VweaverInfoBox.getChildren().addAll(rangeWBP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Voidweaver").append("\n");
        sb.append("Power Level: ").append(getPL()).append(" Point Cost: ").append(getPointcost()).append("\n");

        sb.append("Ranged Weapon:\n");
        if (getRangedWeaponSelection() == 0) {
            sb.append("Shuriken Cannon").append("\n");
        }
        if(getRangedWeaponSelection() == 1) {
            sb.append("Prismatic Cannon - Focused Lance").append("\n");
        }
        if(getRangedWeaponSelection() == 2) {
            sb.append("Prismatic Cannon - Dispersed Pulse").append("\n");
        }

        return sb.toString();
    }
}