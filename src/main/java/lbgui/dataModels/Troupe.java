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

public class Troupe {
    private final FactionFactory.MyObject troupe;
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
    private  VBox troupeInfoBox;
    private VBox innerVBox;
    private VBox meleeBox;
    private VBox rangedBox;


    private int prevMeleePoints;
    private int prevRangePoints;

    private Label troupePlLabel;
    private Label troupePointsLabel;

    Label meleeWep;
    Label rangedWep;

    public Troupe(){ // Troupe Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.troupe = ourfactionMap.get("Troupe");
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }
    public int getRangedWeaponSelection() {
        return rangedWeaponSelection;
    }

    public int getMeleeWeaponSelection() {
        return meleeWeaponSelection;
    }

    public void setMeleeWeaponSelection(int meleeWeaponSelection) {
        this.meleeWeaponSelection = meleeWeaponSelection;
        this.meleeBox.getChildren().clear();
        this.innerVBox.getChildren().remove(this.meleeBox);

        if (this.meleeWeaponSelection == 0) {
            this.meleeWep.setText("Halrequin's Blade");
        }
        if(this.meleeWeaponSelection == 1) {
            this.meleeWep.setText("Aeldari Power Sword");
        }
        if(this.meleeWeaponSelection == 2) {
            this.meleeWep.setText("Halrequin's Caress");
        }
        if(this.meleeWeaponSelection == 3) {
            this.meleeWep.setText("Halrequin's Embrace");
        }
        if(this.meleeWeaponSelection == 4) {
            this.meleeWep.setText("Halrequin's Kiss");
        }
        this.meleeBox.getChildren().add(this.meleeWep);
        this.innerVBox.getChildren().add(0,this.meleeBox);

        updatePLAndPointCostLabels();
    }
    public void setRangedWeaponSelection(int rangedWeaponSelection) {
        this.rangedWeaponSelection = rangedWeaponSelection;
        this.rangedBox.getChildren().clear();
        this.innerVBox.getChildren().remove(this.rangedBox);

        if (this.rangedWeaponSelection == 0) {
            this.rangedWep.setText("Shuriken Pistol");
        }
        if(this.rangedWeaponSelection == 1) {
            this.rangedWep.setText("Neuro Dirupter");
        }
        if(this.rangedWeaponSelection == 2) {
            this.rangedWep.setText("Fusion Pistol");
        }
        this.rangedBox.getChildren().add(this.rangedWep);
        this.innerVBox.getChildren().add(1,this.rangedBox);

        updatePLAndPointCostLabels();
    }
    public FactionFactory.MyObject getTroupe() {
        return this.troupe;
    }

    private void updatePLAndPointCostLabels() {
        Label plLabel = getTroopPlLabel();
        Label pointCostLabel = getTroopPointsLabel();

        plLabel.setText(this.PL + " PL,");
        pointCostLabel.setText(this.pointcost + " pts");

        this.troupePlLabel.setText(this.PL + " PL,");
        this.troupePointsLabel.setText(this.pointcost + " pts");
    }

    public TitledPane getUnitTitledPane(Label delete){
        this.troupePlLabel = new Label();
        this.troupePointsLabel = new Label();
        this.troupePlLabel.setText(this.PL + " PL,");
        this.troupePointsLabel.setText(this.pointcost + " pts");
        return Utils.getUnitTitledPane(this.name,this.troupePlLabel,this.troupePointsLabel,delete,"\u2715");

    }
    public TitledPane getTroupeRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
    }
    public Label getTroopPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getTroopPointsLabel() {
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
        if(this.troupe!= null) {
            this.name = this.troupe.getName();
            this.PL = this.troupe.getPL();
            this.pointcost = this.troupe.getPointCost();
            this.role = this.troupe.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
        }

    }
    public void setObjTitledPane(TitledPane titledPane, StringProperty totalPointsProperty) {
        this.objTitledPane = titledPane;
        this.innerVBox = new VBox();

        this.meleeWep = new Label();
        this.meleeWep.setText("Halrequin's Blade");
        this.meleeBox = new VBox();
        this.meleeBox.getChildren().add(this.meleeWep);

        this.rangedWep = new Label();
        this.rangedWep.setText("Shuriken Pistol");
        this.rangedBox = new VBox();
        this.rangedBox.getChildren().add(this.rangedWep);

        this.innerVBox.getChildren().addAll(this.meleeBox, this.rangedBox);
        this.objTitledPane.setContent(this.innerVBox);
        createTroupeInfoBox();

        this.totalPointsProperty = totalPointsProperty;

        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(troupeInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(troupeInfoBox);
                }
            }else {
                this.rightSplitPaneVbox.getChildren().clear();
            }
        });
    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }

    public void createTroupeInfoBox() {
        troupeInfoBox = new VBox(10);
        troupeInfoBox.setPadding(new Insets(10));
        troupeInfoBox.setAlignment(Pos.TOP_LEFT);

        FactionFactory.MyObject troupe = this.getTroupe();

        String[] wG = troupe.getWargear().split("\\r?\\n");
        String[] oWG = troupe.getOpWargear().split("\n");

        int weaponCount = oWG.length / 7;
        String[] resultArray = new String[weaponCount];

        for (int i = 0; i < weaponCount; i++) {
            String name = oWG[i * 7].trim();
            String modifier = oWG[i * 7 + 6].trim().replace(";", "");

            resultArray[i] = name + " " + modifier + "pts";
        }
        Label RangedWeapon = new Label("Ranged Weapon");
        RadioButton RW1 = new RadioButton(wG[0]);
        RadioButton RW2 = new RadioButton(resultArray[0]);
        RadioButton RW3 = new RadioButton(resultArray[1]);
        ToggleGroup rangedWeaponGroup = new ToggleGroup();

        RadioButton[] rangedradioButtons = {RW1, RW2, RW3};

        for(RadioButton radioButton : rangedradioButtons){
            radioButton.setToggleGroup(rangedWeaponGroup);
        }

        VBox rangeWVbox = new VBox();
        rangeWVbox.setPadding(new Insets(5));
        rangeWVbox.getChildren().add(RangedWeapon);
        rangeWVbox.getChildren().addAll(RW1, RW2, RW3);
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

        this.prevRangePoints = 0;

        for (int i = 0; i < rangedradioButtons.length; i++) {
            int index = i;
            rangedradioButtons[i].setOnAction(event -> {
                if(index == 0) {
                    this.pointcost = this.pointcost - this.prevRangePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevRangePoints)));
                    this.prevRangePoints = 0;
                }
                if (index == 1) {
                    this.pointcost = this.pointcost - this.prevRangePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevRangePoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            5)));
                    this.prevRangePoints = 5;
                }
                if (index == 2) {
                    this.pointcost = this.pointcost - this.prevRangePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevRangePoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            5)));
                    this.prevRangePoints = 5;
                }
                this.setRangedWeaponSelection(index);
            });
        }

        Label MeleeWeapon = new Label("Melee Weapon");
        RadioButton MW1 = new RadioButton(wG[7]);
        RadioButton MW2 = new RadioButton(resultArray[2]);
        RadioButton MW3 = new RadioButton(resultArray[3]);
        RadioButton MW4 = new RadioButton(resultArray[4]);
        RadioButton MW5 = new RadioButton(resultArray[5]);
        ToggleGroup meleeWeaponGroup = new ToggleGroup();
        RadioButton[] meleeradioButtons = {MW1, MW2, MW3, MW4,MW5};

        for(RadioButton radioButton : meleeradioButtons){
            radioButton.setToggleGroup(meleeWeaponGroup);
        }

        VBox meleeWVbox = new VBox();
        meleeWVbox.setPadding(new Insets(5));
        meleeWVbox.getChildren().add(MeleeWeapon);
        meleeWVbox.getChildren().addAll(MW1, MW2, MW3, MW4, MW5);
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


        this.prevMeleePoints = 0;

        for (int i = 0; i < meleeradioButtons.length; i++) {
            int index = i;
            meleeradioButtons[i].setOnAction(event -> {
                if(index == 0) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevMeleePoints)));
                    this.prevMeleePoints = 0;
                }
                if (index == 1) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            5)));
                    this.prevMeleePoints = 5;
                }
                if (index == 2) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            5)));
                    this.prevMeleePoints = 5;
                }
                if (index == 3) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            5)));
                    this.prevMeleePoints = 5;
                }
                if (index == 4) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            5)));
                    this.prevMeleePoints = 5;
                }
                this.setMeleeWeaponSelection(index);
            });
        }

        troupeInfoBox.getChildren().addAll(meleeWBP,rangeWBP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Troupe").append("\n");
        sb.append("Power Level: ").append(getPL()).append(" Point Cost: ").append(getPointcost()).append("\n");

        sb.append("Ranged Weapon:\n");
        if (getRangedWeaponSelection() == 0) {
            sb.append("Shuriken Pistol").append("\n");
        }
        if(getRangedWeaponSelection() == 1) {
            sb.append("Neuro Disruptor").append("\n");
        }
        if(getRangedWeaponSelection() == 2) {
            sb.append("Fusion Pistol").append("\n");
        }

        sb.append("Melee Weapon:\n");
        if (getMeleeWeaponSelection() == 0) {
            sb.append("Halrequin's Blade").append("\n");
        }
        if(getMeleeWeaponSelection() == 1) {
            sb.append("Aeldari Power Sword").append("\n");
        }
        if(getMeleeWeaponSelection() == 2) {
            sb.append("Halrequin's Caress").append("\n");
        }
        if(getMeleeWeaponSelection() == 3) {
            sb.append("Halrequin's Embrace").append("\n");
        }
        if(getMeleeWeaponSelection() == 4) {
            sb.append("Halrequin's Kiss").append("\n");
        }

        return sb.toString();
    }
}