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


public class TroupeMaster {
    private final FactionFactory.MyObject master;
    private String name;
    private StringProperty totalPlProperty;
    private StringProperty totalPointsProperty;
    private int PL;
    private int pointcost;
    private String role;
    private Object[] roleUtils;
    private TitledPane objTitledPane;
    private int pivotalRoleSelection;
    private int rangedWeaponSelection;
    private int meleeWeaponSelection;
    private final VBox rightSplitPaneVbox;
    private VBox masterInfoBox;
    private VBox meleeBox;
    private VBox rangedBox;
    private VBox pivVbox;
    private VBox innerVBox;

    private int prevPivPl;
    private int prevPivPoints;
    private int prevMeleePoints;
    private int prevRangedPoints;

    private int originalPL;
    private int originalPoints;

    private Label tmPlLabel;
    private Label tmPointsLabel;

    private Label HqPlLabel;
    private Label HqPointsLabel;

    private Label meleeWep;
    private Label rangedWep;
    private Label pivotalRole;

    public TroupeMaster(){ // Troupe Master Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.master = ourfactionMap.get("Troupe Master");
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }
    public int getPivotalRoleSelection() {
        return pivotalRoleSelection;
    }

    public void setPivotalRoleSelection(int pivotalRoleSelection) {
        this.pivotalRoleSelection = pivotalRoleSelection;
        this.pivVbox.getChildren().clear();
        this.innerVBox.getChildren().remove(this.pivVbox);

        if (this.pivotalRoleSelection != 0) {
            if (this.pivotalRoleSelection == 1) {
                this.pivotalRole.setText("Prince of Light");
            } else if (this.pivotalRoleSelection == 2) {
                this.pivotalRole.setText("Queen of Shards");
            } else if (this.pivotalRoleSelection == 3) {
                this.pivotalRole.setText("Veiled King");
            }
            this.pivVbox.getChildren().add(this.pivotalRole);
            this.innerVBox.getChildren().add(0,this.pivVbox);
        }
        updatePLAndPointCostLabels();
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
        if(this.pivVbox.getChildren().size() > 0) {
            this.innerVBox.getChildren().add(1, this.meleeBox);
        }else {
            this.innerVBox.getChildren().add(0,this.meleeBox);
        }

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
        if(this.pivVbox.getChildren().size() > 0) {
            this.innerVBox.getChildren().add(2, this.rangedBox);
        }else {
            this.innerVBox.getChildren().add(1,this.rangedBox);
        }

        updatePLAndPointCostLabels();
    }
    public FactionFactory.MyObject getTroupeMaster() {
        return this.master;
    }

    public TitledPane getUnitTitledPane(Label delete){
        this.tmPlLabel = new Label();
        this.tmPointsLabel = new Label();
        this.tmPlLabel.setText(this.PL + " PL,");
        this.tmPointsLabel.setText(this.pointcost + " pts");
        return Utils.getUnitTitledPane(this.name,this.tmPlLabel,this.tmPointsLabel,delete,"\u2715");

    }
    public TitledPane getTroupeMasterRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
    }
    public Label getHqPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getHqPointsLabel() {return (Label) this.roleUtils[2];}
    public int getPL(){
        return this.PL;
    }
    public int getPointcost(){
        return this.pointcost;
    }
    public String getRole() {
        return this.role;
    }
    public void setHqPlLabel(Label label) {
        this.HqPlLabel = label;
    }
    public void setHqPointsLabel(Label label ){
        this.HqPointsLabel = label;
    }
    public void initializeVariables(){
        if(this.master!= null) {
            this.name = this.master.getName();
            this.PL = this.master.getPL();
            this.pointcost = this.master.getPointCost();
            this.role = this.master.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
            this.pivotalRole = new Label();
            this.pivVbox = new VBox();
            this.HqPlLabel = new Label();
            this.HqPointsLabel = new Label();
            this.originalPL = this.PL;
            this.originalPoints = this.pointcost;
        }
    }

    private void updatePLAndPointCostLabels() {
        int tempPL = Integer.parseInt(this.HqPlLabel.getText().split(" ")[0]);
        int tempPoints = Integer.parseInt(this.HqPointsLabel.getText().split(" ")[0]);

        tempPL = tempPL - this.originalPL;
        tempPoints = tempPoints - this.originalPoints;

        this.HqPlLabel.setText((tempPL + this.PL) + " PL,");
        this.HqPointsLabel.setText((tempPoints + this.pointcost) + " pts");

        this.tmPlLabel.setText(this.PL + " PL,");
        this.tmPointsLabel.setText(this.pointcost + " pts");

        this.originalPL = this.PL;
        this.originalPoints = this.pointcost;
    }

    public void setObjTitledPane(TitledPane titledPane,StringProperty totalPlProperty, StringProperty totalPointsProperty) {
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
        createTroupeMasterInfoBox();
        this.totalPlProperty = totalPlProperty;
        this.totalPointsProperty = totalPointsProperty;

        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane!=null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(masterInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(masterInfoBox);
                }
            }else {
                this.rightSplitPaneVbox.getChildren().clear();
            }
        });
    }
    public void removeTitledPane(){
        this.objTitledPane = null;
    }


    public void createTroupeMasterInfoBox() {
        masterInfoBox = new VBox(10);
        masterInfoBox.setPadding(new Insets(10));
        masterInfoBox.setAlignment(Pos.TOP_LEFT);

        FactionFactory.MyObject tMaster = this.getTroupeMaster();
        String pivRoles = tMaster.getUpgrades();
        String[] lines = pivRoles.split("\n");
        String[] output = new String[3];

        for (int i = 0; i < lines.length; i += 3) {
            String name = lines[i].replace(";", "");
            String value1 = lines[i+1];
            String value2 = lines[i+2].replace(";", "");
            output[i/3] = name + " \u2022 " + value1 + "pts " + value2 + "PL";
        }
        Label PivotalRoles = new Label("Pivotal Roles");
        RadioButton PR1 = new RadioButton("(None)");
        RadioButton PR2 = new RadioButton(output[0]);
        RadioButton PR3 = new RadioButton(output[1]);
        RadioButton PR4 = new RadioButton(output[2]);

        ToggleGroup pivotalRolesGroup = new ToggleGroup();
        RadioButton[] pivRadioButtons = {PR1, PR2, PR3, PR4};

        VBox pivRolesVbox = new VBox();
        pivRolesVbox.setPadding(new Insets(5));
        pivRolesVbox.getChildren().add(PivotalRoles);
        pivRolesVbox.getChildren().addAll(pivRadioButtons);
        BorderPane pivRoleBP = new BorderPane();
        pivRoleBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        pivRoleBP.setCenter(pivRolesVbox);

        for(RadioButton radioButton : pivRadioButtons){
            radioButton.setToggleGroup(pivotalRolesGroup);
        }

        if (this.getPivotalRoleSelection() != -1) {
            pivRadioButtons[this.getPivotalRoleSelection()].setSelected(true);
        } else {
            pivotalRolesGroup.selectToggle(null);
        }

        this.prevPivPl = 0;
        this.prevPivPoints = 0;

        for (int i = 0; i < pivRadioButtons.length; i++) {
            int index = i;
            pivRadioButtons[i].setOnAction(event -> {
                if(index == 0) {
                    this.PL = this.PL - this.prevPivPl;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty, this.prevPivPl)));
                    this.pointcost = this.pointcost - this.prevPivPoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevPivPoints)));
                    this.prevPivPl = 0;
                    this.prevPivPoints = 0;
                }
                if (index == 1) {
                    this.PL = this.PL - this.prevPivPl;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty, this.prevPivPl)));
                    this.pointcost = this.pointcost - this.prevPivPoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevPivPoints)));
                    this.PL++;
                    this.totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty, 1)));
                    this.pointcost = this.pointcost + 20;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(totalPointsProperty, 20)));
                    this.prevPivPl = 1;
                    this.prevPivPoints = 20;
                }
                if (index == 2) {
                    this.PL = this.PL - this.prevPivPl;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty, this.prevPivPl)));
                    this.pointcost = this.pointcost - this.prevPivPoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevPivPoints)));
                    this.PL++;
                    this.totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty, 1)));
                    this.pointcost = this.pointcost + 25;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(totalPointsProperty, 25)));
                    this.prevPivPl = 1;
                    this.prevPivPoints = 25;
                }
                if (index == 3) {
                    this.PL = this.PL - this.prevPivPl;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty, this.prevPivPl)));
                    this.pointcost = this.pointcost - this.prevPivPoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevPivPoints)));

                    this.PL++;
                    this.totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty, 1)));
                    this.pointcost = this.pointcost + 20;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(totalPointsProperty, 20)));
                    this.prevPivPl = 1;
                    this.prevPivPoints = 20;
                }

                this.setPivotalRoleSelection(index);
            });
        }

//---------------------------------------------------------------------------------------

        String[] wG = tMaster.getWargear().split("\\r?\\n");
        String[] oWG = tMaster.getOpWargear().split("\n");

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
        RadioButton[] rangedRadioButtons = {RW1, RW2, RW3};

        VBox rangeWVbox = new VBox();
        rangeWVbox.setPadding(new Insets(5));
        rangeWVbox.getChildren().add(RangedWeapon);
        rangeWVbox.getChildren().addAll(RW1, RW2, RW3);
        BorderPane rangeWBP = new BorderPane();
        rangeWBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        rangeWBP.setCenter(rangeWVbox);

        for(RadioButton radioButton : rangedRadioButtons){
            radioButton.setToggleGroup(rangedWeaponGroup);
        }

        if (this.getRangedWeaponSelection() == 0) {
            RW1.setSelected(true);
        } else if (this.getRangedWeaponSelection() == 1) {
            RW2.setSelected(true);
        } else {
            rangedWeaponGroup.selectToggle(null);
        }

        this.prevRangedPoints = 0;

        for (int i = 0; i < rangedRadioButtons.length; i++) {
            int index = i;
            rangedRadioButtons[i].setOnAction(event -> {
                if(index == 0) {
                    this.pointcost = this.pointcost - this.prevRangedPoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevRangedPoints)));
                    this.prevRangedPoints = 0;
                }
                if (index == 1) {
                    this.pointcost = this.pointcost - this.prevRangedPoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevRangedPoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,5)));
                    this.prevRangedPoints = 5;
                }
                if (index == 2) {
                    this.pointcost = this.pointcost - this.prevRangedPoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevRangedPoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,5)));
                    this.prevRangedPoints = 5;
                }
                this.setRangedWeaponSelection(index);
            });
        }


//-----------------------------------------------------------------------------------------
        Label MeleeWeapon = new Label("Melee Weapon");
        RadioButton MW1 = new RadioButton(wG[7]);
        RadioButton MW2 = new RadioButton(resultArray[2]);
        RadioButton MW3 = new RadioButton(resultArray[3]);
        RadioButton MW4 = new RadioButton(resultArray[4]);
        RadioButton MW5 = new RadioButton(resultArray[5]);

        ToggleGroup meleeWeaponGroup = new ToggleGroup();
        RadioButton[] meleeRadioButtons = {MW1, MW2, MW3, MW4,MW5};


        VBox meleeWVbox = new VBox();
        meleeWVbox.setPadding(new Insets(5));
        meleeWVbox.getChildren().add(MeleeWeapon);
        meleeWVbox.getChildren().addAll(MW1, MW2, MW3, MW4, MW5);
        BorderPane meleeWBP = new BorderPane();
        meleeWBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        meleeWBP.setCenter(meleeWVbox);

        for(RadioButton radioButton : meleeRadioButtons){
            radioButton.setToggleGroup(meleeWeaponGroup);
        }

        if (this.getMeleeWeaponSelection() == 0) {
            MW1.setSelected(true);
        } else if (this.getMeleeWeaponSelection() == 1) {
            MW2.setSelected(true);
        } else {
            meleeWeaponGroup.selectToggle(null);
        }

        this.prevMeleePoints = 0;

        for (int i = 0; i < meleeRadioButtons.length; i++) {
            int index = i;
            meleeRadioButtons[i].setOnAction(event -> {
                if(index == 0) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevMeleePoints)));
                    this.prevMeleePoints = 0;
                }
                if (index == 1) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 5;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,5)));
                    this.prevMeleePoints = 5;
                }
                if (index == 2) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 10;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,10)));
                    this.prevMeleePoints = 10;
                }
                if (index == 3) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 10;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,10)));
                    this.prevMeleePoints = 10;
                }
                if (index == 4) {
                    this.pointcost = this.pointcost - this.prevMeleePoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty, this.prevMeleePoints)));
                    this.pointcost = this.pointcost + 10;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,10)));
                    this.prevMeleePoints = 10;
                }

                this.setMeleeWeaponSelection(index);
            });
        }

        masterInfoBox.getChildren().addAll(pivRoleBP, meleeWBP, rangeWBP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Troupe Master").append("\n");
        sb.append("Power Level: ").append(getPL()).append(" Point Cost: ").append(getPointcost()).append("\n");

        //Selected options
        sb.append("Pivotal Roles:\n");
        if(getPivotalRoleSelection() == 0){
            sb.append("No Pivotal Role Selected").append("\n");
        } else if (getPivotalRoleSelection() == 1) {
            sb.append("Prince of Light").append("\n");
        } else if (getPivotalRoleSelection() == 2) {
            sb.append("Queen of Shards").append("\n");
        } else if (getPivotalRoleSelection() == 3) {
            sb.append("Veiled King").append("\n");
        }

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

