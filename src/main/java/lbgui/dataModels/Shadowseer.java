package lbgui.dataModels;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lbgui.factories.ArmyAbilitiesFactory;
import lbgui.factories.FactionFactory;
import lbgui.listbuilder.Utils;
import java.util.Map;

public class Shadowseer {
    private final Map<String, ArmyAbilitiesFactory.MyObject> ourArmyAbilitiesMap;
    private final FactionFactory.MyObject shadowseer;
    private StringProperty totalPlProperty;
    private StringProperty totalPointsProperty;
    private String name;
    private int PL;
    private int pointcost;
    private String role;
    private Object[] roleUtils;
    private TitledPane objTitledPane;
    boolean[] phantasmancySelections = new boolean[6];
    private int pivotalRoleSelection;
    private final VBox rightSplitPaneVbox;
    private VBox SseerInfoBox;
    private VBox innerVBox;
    private int rangedWeaponSelection;

    private int prevPL;
    private int prevpoints;
    private Label sPlLabel;
    private Label sPointsLabel;

    private Label HqPlLabel;
    private Label HqPointsLabel;

    private int originalPL;
    private int originalPoints;

    private VBox pivVbox;
    private VBox phantaVbox;
    private VBox rangeWeapVBox;

    private Label phanta1;
    private Label phanta2;
    private Label rangedWeapon;
    private Label pivotalRole;

    public Shadowseer(){ // Shadowseer Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.shadowseer = ourfactionMap.get("Shadowseer");
        ourArmyAbilitiesMap = Utils.getArmyAbilitiesMap();
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }
    public void initializeVariables(){
        if(this.shadowseer!= null) {
            this.name = this.shadowseer.getName();
            this.PL = this.shadowseer.getPL();
            this.pointcost = this.shadowseer.getPointCost();
            this.role = this.shadowseer.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
            phanta1 = new Label("");
            phanta2 = new Label("");
            sPlLabel = new Label();
            sPointsLabel = new Label();
            this.pivotalRole = new Label();
            this.pivVbox = new VBox();
            this.phantaVbox = new VBox();
            this.HqPlLabel = new Label();
            this.HqPointsLabel = new Label();
            this.originalPL = this.PL;
            this.originalPoints = this.pointcost;
        }
    }

    public TitledPane getUnitTitledPane(Label delete){
        this.sPlLabel.setText(this.PL + " PL,");
        this.sPointsLabel.setText(this.pointcost + "pts");
        return Utils.getUnitTitledPane(this.name,this.sPlLabel,this.sPointsLabel,delete,"\u2715");
    }

    public Label getPhanta1(){
        return this.phanta1;
    }
    public Label getPhanta2() {
        return this.phanta2;
    }

    public void setPhantasmancySelections(int index, boolean isSelected) {
        this.phantasmancySelections[index] = isSelected;
        this.innerVBox.getChildren().remove(this.phantaVbox);
        this.phantaVbox.getChildren().clear();

        if (phanta1.getText().isEmpty() && phanta2.getText().isEmpty()) {
            this.innerVBox.getChildren().remove(this.phantaVbox);
        } else {
            if (!phanta1.getText().isEmpty()) {
                this.phantaVbox.getChildren().add(this.phanta1);
            }
            if (!phanta2.getText().isEmpty()) {
                this.phantaVbox.getChildren().add(this.phanta2);
            }
            this.innerVBox.getChildren().add(this.phantaVbox);
        }
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
                this.pivotalRole.setText("Agent of Pandemonium");
            } else if (this.pivotalRoleSelection == 2) {
                this.pivotalRole.setText("Gloom Spider");
            } else if (this.pivotalRoleSelection == 3) {
                this.pivotalRole.setText("Mirror Architect");
            }

            this.pivVbox.getChildren().add(this.pivotalRole);
            this.innerVBox.getChildren().add(this.pivVbox);

        }
        updatePLAndPointCostLabels();
    }


    public void setRangedWeaponSelection(int rangedWeaponSelection) {
        this.rangedWeaponSelection = rangedWeaponSelection;
        this.rangeWeapVBox.getChildren().clear();
        this.innerVBox.getChildren().remove(this.rangeWeapVBox);

        if (rangedWeaponSelection == 0) {
            this.rangedWeapon.setText("Neuro Disruptor");
        }
        if(rangedWeaponSelection == 1) {
            this.rangedWeapon.setText("Shuriken Pistol");
        }
        this.rangeWeapVBox.getChildren().add(this.rangedWeapon);
        this.innerVBox.getChildren().add(this.rangeWeapVBox);

        updatePLAndPointCostLabels();
    }
    private void updatePLAndPointCostLabels() {
        int tempPL = Integer.parseInt(this.HqPlLabel.getText().split(" ")[0]);
        int tempPoints = Integer.parseInt(this.HqPointsLabel.getText().split(" ")[0]);

        tempPL = tempPL - this.originalPL;
        tempPoints = tempPoints - this.originalPoints;

        this.HqPlLabel.setText((tempPL + this.PL) + " PL,");
        this.HqPointsLabel.setText((tempPoints + this.pointcost) + " pts");

        this.sPlLabel.setText(this.PL + " PL,");
        this.sPointsLabel.setText(this.pointcost + " pts");

        this.originalPL = this.PL;
        this.originalPoints = this.pointcost;

    }

    public FactionFactory.MyObject getShadowseer() {
        return this.shadowseer;
    }

    public boolean isPhantasmancySelected(int index) {
        if (phantasmancySelections == null) {
            return false;
        } else if (phantasmancySelections.length > index) {
            return phantasmancySelections[index];
        } else {
            return false;
        }
    }

    public TitledPane getShadowseerRoleTitledPane(){
        return (TitledPane) this.roleUtils[0];
    }
    public Label getHqPlLabel(){return  (Label) this.roleUtils[1];}
    public Label getHqPointsLabel() {
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
    public void setHqPlLabel(Label label){
        this.HqPlLabel = label;
    }
    public void setHqPointsLabel(Label label) {
        this.HqPointsLabel = label;
    }
    public int getRangedWeaponSelection() {
        return rangedWeaponSelection;
    }

    public void setObjTitledPane(TitledPane titledPane, StringProperty totalPlProperty, StringProperty totalPointsProperty) {
        this.objTitledPane = titledPane;
        this.innerVBox = new VBox();
        this.rangeWeapVBox = new VBox();
        this.rangedWeapon = new Label("Shuriken Pistol");
        this.rangeWeapVBox.getChildren().add(this.rangedWeapon);
        this.innerVBox.getChildren().add(this.rangeWeapVBox);
        this.objTitledPane.setContent(this.innerVBox);
        createShadowseerInfoBox();

        this.totalPlProperty = totalPlProperty;
        this.totalPointsProperty = totalPointsProperty;

        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(SseerInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(SseerInfoBox);
                }
            } else {
                this.rightSplitPaneVbox.getChildren().clear();
            }

        });
    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }

    public void createShadowseerInfoBox() {
        SseerInfoBox = new VBox(10);
        SseerInfoBox.setPadding(new Insets(10));
        SseerInfoBox.setAlignment(Pos.TOP_LEFT);
        final int MAX_SELECTED = 2;
        final int[] selectedCount = {0};

        FactionFactory.MyObject sSeer = this.getShadowseer();
        ArmyAbilitiesFactory.MyObject Phantas = ourArmyAbilitiesMap.get("Phantasmancy");

        Label Phant = new Label("Phantasmancy");
        CheckBox P1 = new CheckBox(Phantas.getColumnA().split(":")[0]);
        CheckBox P2 = new CheckBox(Phantas.getColumnB().split(":")[0]);
        CheckBox P3 = new CheckBox(Phantas.getColumnC().split(":")[0]);
        CheckBox P4 = new CheckBox(Phantas.getColumnD().split(":")[0]);
        CheckBox P5 = new CheckBox(Phantas.getColumnE().split(":")[0]);
        CheckBox P6 = new CheckBox(Phantas.getColumnF().split(":")[0]);

        CheckBox[] checkBoxes = {P1, P2, P3, P4, P5, P6};

        VBox checkBoxVBox = new VBox();
        checkBoxVBox.setPadding(new Insets(5));
        checkBoxVBox.getChildren().add(Phant);
        checkBoxVBox.getChildren().addAll(checkBoxes);
        BorderPane phantBP = new BorderPane();
        phantBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        phantBP.setCenter(checkBoxVBox);

        Label errorLabel = new Label("[NOTICE] Shadowseer cannot exceed 2 selections from Phantasmancy.");
        errorLabel.setStyle("-fx-text-fill: orange;");
        errorLabel.setPadding(new Insets(0,0,0,5));

        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setSelected(this.isPhantasmancySelected(i));
            int index = i;
            CheckBox checkBox = checkBoxes[i];

            checkBox.setOnAction(event -> {
                selectedCount[0] = 0;

                for (CheckBox cb : checkBoxes) {
                    if (cb.isSelected()) {
                        selectedCount[0]++;
                    }
                }

                if (selectedCount[0] > MAX_SELECTED) {
                    checkBox.setSelected(false);
                    selectedCount[0]--;
                    if (!rightSplitPaneVbox.getChildren().contains(errorLabel)) {
                        rightSplitPaneVbox.getChildren().add(0, errorLabel);
                    }
                } else {
                    rightSplitPaneVbox.getChildren().remove(errorLabel);

                    if (checkBox.isSelected()) {
                        if (phanta1.getText().isEmpty()) {
                            phanta1.setText(checkBox.getText());
                        } else if (phanta2.getText().isEmpty()) {
                            phanta2.setText(checkBox.getText());
                        }
                    } else {
                        if (checkBox.getText().equals(phanta1.getText())) {
                            phanta1.setText("");
                        } else if (checkBox.getText().equals(phanta2.getText())) {
                            phanta2.setText("");
                        }
                    }
                    this.setPhantasmancySelections(index, checkBox.isSelected());

                }
            });
        }
        String pivRoles = sSeer.getUpgrades();
        String[] lines = pivRoles.split("\n");
        String[] output = new String[3];

        for (int i = 0; i < lines.length; i += 3) {
            String name = lines[i].replace(";", "");
            String value1 = lines[i+1];
            String value2 = lines[i+2].replace(";", "");
            output[i/3] = name + " \u2022 " + value1 + "pts, " + value2 + " PL";
        }
        Label PivotalRoles = new Label("Pivotal Roles");
        RadioButton PR1 = new RadioButton("(None)");
        RadioButton PR2 = new RadioButton(output[0]);
        RadioButton PR3 = new RadioButton(output[1]);
        RadioButton PR4 = new RadioButton(output[2]);
        ToggleGroup pivotalRolesGroup = new ToggleGroup();
        RadioButton[] radioButtons = {PR1, PR2, PR3, PR4};

        VBox pivRolesVbox = new VBox();
        pivRolesVbox.setPadding(new Insets(5));
        pivRolesVbox.getChildren().add(PivotalRoles);
        pivRolesVbox.getChildren().addAll(radioButtons);
        BorderPane pivRoleBP = new BorderPane();
        pivRoleBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        pivRoleBP.setCenter(pivRolesVbox);

        for(RadioButton radioButton : radioButtons){
            radioButton.setToggleGroup(pivotalRolesGroup);
        }

        if (this.getPivotalRoleSelection() != -1) {
            radioButtons[this.getPivotalRoleSelection()].setSelected(true);
        } else {
            pivotalRolesGroup.selectToggle(null);
        }

        this.prevPL = 0;
        this.prevpoints = 0;

        for (int i = 0; i < radioButtons.length; i++) {
            int index = i;
            radioButtons[i].setOnAction(event -> {

                if(index == 0) {
                    this.PL = this.PL - this.prevPL;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(this.totalPlProperty,this.prevPL)));
                    this.pointcost = this.pointcost - this.prevpoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevpoints)));
                    this.prevPL = 0;
                    this.prevpoints = 0;
                }
                if (index == 1) {
                    this.PL = this.PL - this.prevPL;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(this.totalPlProperty,this.prevPL)));
                    this.pointcost = this.pointcost - this.prevpoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevpoints)));
                    this.PL++;
                    this.totalPlProperty.set(Integer.toString(Utils.addPL(this.totalPlProperty,1)));
                    this.pointcost = this.pointcost + 30;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,30)));
                    this.prevPL = 1;
                    this.prevpoints = 30;
                }
                if (index == 2) {
                    this.PL = this.PL - this.prevPL;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(this.totalPlProperty,this.prevPL)));
                    this.pointcost = this.pointcost - this.prevpoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevpoints)));
                    this.PL++;
                    this.totalPlProperty.set(Integer.toString(Utils.addPL(this.totalPlProperty,1)));
                    this.pointcost = this.pointcost + 20;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,20)));
                    this.prevPL = 1;
                    this.prevpoints = 20;
                }
                if (index == 3) {
                    this.PL = this.PL - this.prevPL;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(this.totalPlProperty,this.prevPL)));
                    this.pointcost = this.pointcost - this.prevpoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevpoints)));
                    this.PL++;
                    this.totalPlProperty.set(Integer.toString(Utils.addPL(this.totalPlProperty,1)));
                    this.pointcost = this.pointcost + 60;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            60)));
                    this.prevPL = 1;
                    this.prevpoints = 60;
                }
                this.setPivotalRoleSelection(index);
            });
        }

        String wG = sSeer.getWargear().split("\\r?\\n")[0];
        String[] oWG = sSeer.getOpWargear().split("\n");
        String OpWargear = oWG[0].trim() + " \u2022 " + oWG[lines.length - 2].trim();
        OpWargear = OpWargear.replace(";", "");

        Label RangedWeapon = new Label("Ranged Weapon");
        RadioButton RW1 = new RadioButton( OpWargear  +"pts");
        RadioButton RW2 = new RadioButton(wG);
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
        rangedWeaponGroup.selectToggle(RW2);

        RW1.setOnAction(event -> {
            this.pointcost = this.pointcost + 5;
            this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,5)));
            this.setRangedWeaponSelection(0);
        });
        RW2.setOnAction(event -> {
            this.pointcost = this.pointcost - 5;
            this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,5)));
            this.setRangedWeaponSelection(1);
        });
        SseerInfoBox.getChildren().addAll(phantBP, pivRoleBP, rangeWBP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Shadowseer").append("\n");
        sb.append("Power Level: ").append(getPL()).append(" Point Cost: ").append(getPointcost()).append("\n");

        //Selected options
        sb.append("Phantasmancy:\n");
        sb.append(getPhanta1().getText()).append("\n");
        sb.append(getPhanta2().getText()).append("\n");

        sb.append("Pivotal Roles:\n");
        if(getPivotalRoleSelection() == 0){
            sb.append("No Pivotal Role Selected").append("\n");
        } else if (getPivotalRoleSelection() == 1) {
            sb.append("Agent of Pandemonium").append("\n");
        } else if (getPivotalRoleSelection() == 2) {
            sb.append("Gloom Spider").append("\n");
        } else if (getPivotalRoleSelection() == 3) {
            sb.append("Mirror Architect").append("\n");
        }

        sb.append("Ranged Weapon:\n");
        if (getRangedWeaponSelection() == 0) {
            sb.append("Neuro Disruptor").append("\n");
        }
        if(getRangedWeaponSelection() == 1) {
            sb.append("Shuriken Pistol").append("\n");
        }

        return sb.toString();
    }

}
