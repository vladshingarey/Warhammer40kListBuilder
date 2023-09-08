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


public class Skyweavers {
    private final FactionFactory.MyObject skyweavers;
    private String name;
    private StringProperty totalPointsProperty;
    private int PL;
    private int pointcost;
    private String role;
    private Object[] roleUtils;
    private TitledPane objTitledPane;
    private int rangedWeaponSelection;
    private int meleeWeaponSelection;
    private final VBox rightSplitPaneVbox;
    private  VBox SweaversInfoBox;
    private Label skyPlLabel;
    private Label skyPointsLabel;
    private VBox innerVbox;

    private VBox meleeBox;
    private VBox rangedBox;

    private Label meleeWep;
    private Label rangedWep;

    public Skyweavers(){ // Skyweaver Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.skyweavers = ourfactionMap.get("Skyweaver");
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }

    public int getRangedWeaponSelection() {
        return rangedWeaponSelection;
    }

    public void setRangedWeaponSelection(int rangedWeaponSelection) {
        this.rangedWeaponSelection = rangedWeaponSelection;
        this.rangedBox.getChildren().clear();
        this.innerVbox.getChildren().remove(this.rangedBox);

        if (this.rangedWeaponSelection == 0) {
            this.rangedWep.setText("Shuriken Cannon");
        }
        if(this.rangedWeaponSelection == 1) {
            this.rangedWep.setText("Skyweaver Haywire Cannon");
        }
        this.rangedBox.getChildren().add(this.rangedWep);
        this.innerVbox.getChildren().add(1,this.rangedBox);
        updatePLAndPointCostLabels();
    }
    public int getMeleeWeaponSelection() {
        return meleeWeaponSelection;
    }

    public void setMeleeWeaponSelection(int meleeWeaponSelection) {
        this.meleeWeaponSelection = meleeWeaponSelection;
        this.meleeBox.getChildren().clear();
        this.innerVbox.getChildren().remove(this.meleeBox);

        if (this.meleeWeaponSelection == 0) {
            this.meleeWep.setText("Star Bolas");
        }
        if(this.meleeWeaponSelection == 1) {
            this.meleeWep.setText("Zephyrglaive");
        }
        this.meleeBox.getChildren().add(this.meleeWep);
        this.innerVbox.getChildren().add(0,this.meleeBox);

        updatePLAndPointCostLabels();
    }
    public FactionFactory.MyObject getSkyweavers() {
        return this.skyweavers;
    }

    public TitledPane getUnitTitledPane(Label delete){
        this.skyPlLabel = new Label();
        this.skyPointsLabel = new Label();
        this.skyPlLabel.setText(this.PL + " PL,");
        this.skyPointsLabel.setText(this.pointcost + " pts");
        return Utils.getUnitTitledPane(this.name,this.skyPlLabel,this.skyPointsLabel,delete,"\u2715");
    }
    public TitledPane getSkyweaversRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
    }
    public Label getFastAttackPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getFastAttackPointsLabel() {
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
        if(this.skyweavers!= null) {
            this.name = this.skyweavers.getName();
            this.PL = this.skyweavers.getPL();
            this.pointcost = this.skyweavers.getPointCost();
            this.role = this.skyweavers.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
        }
    }
    public void setObjTitledPane(TitledPane titledPane, StringProperty totalPointsProperty) {
        this.objTitledPane = titledPane;
        this.innerVbox = new VBox();

        this.meleeBox = new VBox();
        this.meleeWep = new Label();
        this.meleeWep.setText("Star Bolas");
        this.meleeBox.getChildren().add(this.meleeWep);

        this.rangedBox = new VBox();
        this.rangedWep = new Label();
        this.rangedWep.setText("Shuriken Cannon");
        this.rangedBox.getChildren().add(this.rangedWep);

        this.innerVbox.getChildren().addAll(this.meleeBox, this.rangedBox);
        this.objTitledPane.setContent(this.innerVbox);

        this.totalPointsProperty = totalPointsProperty;

        createSkyweaversInfoBox();
        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(SweaversInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(SweaversInfoBox);
                }
            }else {
                this.rightSplitPaneVbox.getChildren().clear();
            }

        });
    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }

    private void updatePLAndPointCostLabels() {
        Label plLabel = getFastAttackPlLabel();
        Label pointCostLabel = getFastAttackPointsLabel();

        plLabel.setText(this.PL + " PL,");
        pointCostLabel.setText(this.pointcost + " pts");

        this.skyPlLabel.setText(this.PL + " PL,");
        this.skyPointsLabel.setText(this.pointcost + " pts");
    }

    public void createSkyweaversInfoBox() {
        SweaversInfoBox = new VBox(10);
        SweaversInfoBox.setPadding(new Insets(10));
        SweaversInfoBox.setAlignment(Pos.TOP_LEFT);

        FactionFactory.MyObject sWeavers = this.getSkyweavers();

        String[] wG = sWeavers.getWargear().split("\\r?\\n");
        String pWeapon = wG[0];
        String sWeapon = wG[7];
        String[] oWG = sWeavers.getOpWargear().split("\n");
        String rangedOp = oWG[0] + " " + oWG[7];
        String meleeOp = oWG[8] + " " + oWG[14];
        rangedOp = rangedOp.replace(";", "");
        meleeOp = meleeOp.replace(";", "");


        Label MeleeWeapon = new Label("Melee Weapons");
        RadioButton MW1 = new RadioButton(sWeapon);
        RadioButton MW2 = new RadioButton(meleeOp + "pts");
        ToggleGroup meleeWeaponGroup = new ToggleGroup();
        MW1.setToggleGroup(meleeWeaponGroup);
        MW2.setToggleGroup(meleeWeaponGroup);

        VBox meleeWVbox = new VBox();
        meleeWVbox.setPadding(new Insets(5));
        meleeWVbox.getChildren().add(MeleeWeapon);
        meleeWVbox.getChildren().addAll(MW1, MW2);
        BorderPane meleeWBP = new BorderPane();
        meleeWBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        meleeWBP.setCenter(meleeWVbox);

        if (this.getMeleeWeaponSelection() == 0) {
            MW1.setSelected(true);
        } else if (this.getMeleeWeaponSelection() == 1) {
            MW2.setSelected(true);
        } else {
            meleeWeaponGroup.selectToggle(null);
        }

        MW1.setOnAction(event -> {
            this.pointcost = this.pointcost - 5;
            this.totalPointsProperty.set(Integer.toString(Utils.subPL(this.totalPointsProperty,5)));
            this.setMeleeWeaponSelection(0);
        });
        MW2.setOnAction(event -> {
            this.pointcost = this.pointcost + 5;
            this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,5)));
            this.setMeleeWeaponSelection(1);

        });

        Label RangedWeapon = new Label("Ranged Weapons");
        RadioButton RW1 = new RadioButton(pWeapon);
        RadioButton RW2 = new RadioButton(rangedOp + "pts");
        ToggleGroup rangedWeaponGroup = new ToggleGroup();
        RW1.setToggleGroup(rangedWeaponGroup);
        RW2.setToggleGroup(rangedWeaponGroup);

        VBox rangeWVbox = new VBox();
        rangeWVbox.setPadding(new Insets(5));
        rangeWVbox.getChildren().add(RangedWeapon);
        rangeWVbox.getChildren().addAll(RW1, RW2);
        BorderPane rangeWBP = new BorderPane();
        rangeWBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        rangeWBP.setCenter(rangeWVbox);

        if (this.getRangedWeaponSelection() == 0) {
            RW1.setSelected(true);
        } else if (this.getRangedWeaponSelection() == 1) {
            RW2.setSelected(true);
        } else {
            rangedWeaponGroup.selectToggle(null);
        }
        RW1.setOnAction(event -> {
            this.pointcost = this.pointcost - 5;
            this.totalPointsProperty.set(Integer.toString(Utils.subPL(this.totalPointsProperty,5)));
            setRangedWeaponSelection(0);
        });
        RW2.setOnAction(event -> {
            this.pointcost = this.pointcost + 5;
            this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,5)));
            setRangedWeaponSelection(1);
        });
        SweaversInfoBox.getChildren().addAll(meleeWBP, rangeWBP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Skyweaver").append("\n");
        sb.append("Power Level: ").append(getPL()).append(" Point Cost: ").append(getPointcost()).append("\n");

        sb.append("Ranged Weapon:\n");
        if (getRangedWeaponSelection() == 0) {
            sb.append("Shuriken Cannon").append("\n");
        }
        if(getRangedWeaponSelection() == 1) {
            sb.append("Skyweaver Haywire Cannon").append("\n");
        }

        sb.append("Melee Weapon:\n");
        if (getMeleeWeaponSelection() == 0) {
            sb.append("Star Bolas").append("\n");
        }
        if(getMeleeWeaponSelection() == 1) {
            sb.append("Zephyrglaive").append("\n");
        }
        return sb.toString();
    }
}