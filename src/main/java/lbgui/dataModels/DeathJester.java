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


public class DeathJester {
    private final FactionFactory.MyObject deathjester;
    private String name;
    private StringProperty totalPlProperty;
    private StringProperty totalPointsProperty;
    private int PL;
    private int pointcost;
    private String role;
    private Object[] roleUtils;
    private TitledPane objTitledPane;
    private int pivotalRoleSelection;
    private final VBox rightSplitPaneVbox;
    private VBox DjesterInfoBox;
    private VBox innerVbox;
    private int originalPL;
    private int originalPoints;

    private int prevPL;
    private int prevpoints;

    private VBox pivVbox;
    private Label pivotalRole;
    private Label djPlLabel;
    private Label djPointsLabel;

    private Label ElitesPlLabel;
    private Label ElitesPointsLabel;

    public DeathJester(){ // DeathJester Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.deathjester = ourfactionMap.get("Death Jester");
        rightSplitPaneVbox = Utils.getRightSplitPaneVbox();
        initializeVariables();
    }
    public int getPivotalRoleSelection() {
        return pivotalRoleSelection;
    }

    public void setElitesPlLabel(Label label ) {
        this.ElitesPlLabel = label;
    }
    public void setElitesPointsLabel(Label label) {
        this.ElitesPointsLabel = label;
    }

    public void setPivotalRoleSelection(int pivotalRoleSelection) {
        this.pivotalRoleSelection = pivotalRoleSelection;
        this.pivVbox.getChildren().clear();
        this.innerVbox.getChildren().remove(this.pivVbox);

        if (pivotalRoleSelection != 0) {

            if (pivotalRoleSelection == 1) {
                this.pivotalRole.setText("Harvester of Torment");
            } else if (pivotalRoleSelection == 2) {
                this.pivotalRole.setText("Lord of Crystal Bones");
            } else if (pivotalRoleSelection == 3) {
                this.pivotalRole.setText("Rift Ghoul");
            }

            this.pivVbox.getChildren().add(this.pivotalRole);
            this.innerVbox.getChildren().add(this.pivVbox);
        }
        updatePLAndPointCostLabels();
    }

    public FactionFactory.MyObject getDeathjester() {
        return this.deathjester;
    }

    public TitledPane getUnitTitledPane(Label delete){
        this.djPlLabel.setText(this.PL + " PL,");
        this.djPointsLabel.setText(this.pointcost + " pts");
        return Utils.getUnitTitledPane(this.name,this.djPlLabel,this.djPointsLabel,delete,"\u2715");
    }
    public Label getElitesPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getElitesPointsLabel() {
        return (Label) this.roleUtils[2];
    }
    public TitledPane getDeathJesterRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
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
        if(this.deathjester!= null) {
            this.name = this.deathjester.getName();
            this.PL = this.deathjester.getPL();
            this.pointcost = this.deathjester.getPointCost();
            this.role = this.deathjester.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
            this.djPlLabel = new Label();
            this.djPointsLabel = new Label();
            this.pivotalRole = new Label();
            this.originalPL = this.PL;
            this.originalPoints = this.pointcost;
        }

    }
    private void updatePLAndPointCostLabels() {
        int tempPL = Integer.parseInt(this.ElitesPlLabel.getText().split(" ")[0]);
        int tempPoints = Integer.parseInt(this.ElitesPointsLabel.getText().split(" ")[0]);

        tempPL = tempPL - this.originalPL;
        tempPoints = tempPoints - this.originalPoints;

        this.ElitesPlLabel.setText((tempPL + this.PL) + " PL,");
        this.ElitesPointsLabel.setText((tempPoints + this.pointcost) + " pts");

        this.djPlLabel.setText(this.PL + " PL,");
        this.djPointsLabel.setText(this.pointcost + " pts");

        this.originalPL = this.PL;
        this.originalPoints = this.pointcost;
    }


    public void setObjTitledPane(TitledPane titledPane, StringProperty totalPlProperty, StringProperty totalPointsProperty) {
        this.objTitledPane = titledPane;
        this.innerVbox = new VBox();
        this.pivVbox = new VBox();
        this.innerVbox.getChildren().add(this.pivVbox);
        this.objTitledPane.setContent(this.innerVbox);
        createDeathJesterInfoBox();

        this.totalPlProperty = totalPlProperty;
        this.totalPointsProperty = totalPointsProperty;

        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(DjesterInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(DjesterInfoBox);
                }
            }else {
                this.rightSplitPaneVbox.getChildren().clear();
            }
        });
    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }

    public void createDeathJesterInfoBox() {
        DjesterInfoBox = new VBox(10);
        DjesterInfoBox.setPadding(new Insets(10));
        DjesterInfoBox.setAlignment(Pos.TOP_LEFT);

        FactionFactory.MyObject dJester = this.getDeathjester();
        String pivRoles = dJester.getUpgrades();
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
                    this.pointcost = this.pointcost + 40;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,40)));
                    this.prevPL = 1;
                    this.prevpoints = 40;
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
                    this.pointcost = this.pointcost + 20;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,20)));
                    this.prevPL = 1;
                    this.prevpoints = 20;
                }

                this.setPivotalRoleSelection(index);

            });
        }
        VBox rangeWVbox = new VBox();
        rangeWVbox.setPadding(new Insets(5));
        BorderPane rangeWBP = new BorderPane();
        rangeWBP.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 1px;");
        rangeWBP.setCenter(rangeWVbox);
        DjesterInfoBox.getChildren().addAll(pivRoleBP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Death Jester").append("\n");
        sb.append("Power Level: ").append(getPL()).append(" Point Cost: ").append(getPointcost()).append("\n");

        //Selected options
        sb.append("Pivotal Roles:\n");
        if(getPivotalRoleSelection() == 0){
            sb.append("No Pivotal Role Selected").append("\n");
        } else if (getPivotalRoleSelection() == 1) {
            sb.append("Harvester of Torment").append("\n");
        } else if (getPivotalRoleSelection() == 2) {
            sb.append("Lord of Crystal Bones").append("\n");
        } else if (getPivotalRoleSelection() == 3) {
            sb.append("Rift Goul").append("\n");
        }
        return sb.toString();
    }
}
