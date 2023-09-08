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

public class Solitaire {
    private final FactionFactory.MyObject solitaire;
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
    private  VBox SolInfoBox;
    private VBox pivVbox;
    private Label pivotalRole;
    private VBox innerVBox;
    private int prevPL;
    private int prevpoints;
    private Label solPlLabel;
    private Label solPointsLabel;

    private int originalPL;
    private int originalPoints;

    private Label ElitesPlLabel;
    private Label ElitesPointsLabel;

    public Solitaire(){ // Solitaire Constructor
        Map<String, FactionFactory.MyObject> ourfactionMap = Utils.getFactionMap();
        this.solitaire = ourfactionMap.get("Solitaire");
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
        this.innerVBox.getChildren().remove(this.pivVbox);

        if (this.pivotalRoleSelection != 0) {

            if (this.pivotalRoleSelection == 1) {
                this.pivotalRole.setText("Prince of Sins");
            } else if (this.pivotalRoleSelection == 2) {
                this.pivotalRole.setText("Spectre of Despair");
            } else if (this.pivotalRoleSelection == 3) {
                this.pivotalRole.setText("Thirsting Darkness");
            }

            this.pivVbox.getChildren().add(this.pivotalRole);
            this.innerVBox.getChildren().add(this.pivVbox);
        }
        updatePLAndPointCostLabels();
    }

    public FactionFactory.MyObject getSolitaire() {
        return this.solitaire;
    }

    public TitledPane getUnitTitledPane(Label delete){
        this.solPlLabel = new Label();
        this.solPointsLabel = new Label();
        this.solPlLabel.setText(this.PL + " PL,");
        this.solPointsLabel.setText(this.pointcost + " pts");
        return Utils.getUnitTitledPane(this.name,this.solPlLabel,this.solPointsLabel,delete,"\u2715");

    }
    public TitledPane getSolitaireRoleTitledPane(){

        return (TitledPane) this.roleUtils[0];
    }
    public Label getElitesPlLabel(){
        return  (Label) this.roleUtils[1];
    }
    public Label getElitesPointsLabel() {
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
        if(this.solitaire!= null) {
            this.name = this.solitaire.getName();
            this.PL = this.solitaire.getPL();
            this.pointcost = this.solitaire.getPointCost();
            this.role = this.solitaire.getRole();
            this.roleUtils = Utils.getRoleTitledPane(this.role);
            this.pivotalRole = new Label();
            this.pivVbox = new VBox();
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

        this.solPlLabel.setText(this.PL + " PL,");
        this.solPointsLabel.setText(this.pointcost + " pts");

        this.originalPL = this.PL;
        this.originalPoints = this.pointcost;
    }


    public void setObjTitledPane(TitledPane titledPane, StringProperty totalPlProperty, StringProperty totalPointsProperty) {
        this.objTitledPane = titledPane;
        this.innerVBox = new VBox();
        this.objTitledPane.setContent(this.innerVBox);
        createSolitaireInfoBox();

        this.totalPlProperty = totalPlProperty;
        this.totalPointsProperty = totalPointsProperty;

        this.objTitledPane.setOnMouseClicked(e -> {
            if(this.objTitledPane != null) {
                if (this.objTitledPane.isExpanded()) {
                    this.rightSplitPaneVbox.getChildren().clear();
                    this.rightSplitPaneVbox.getChildren().add(SolInfoBox);
                } else {
                    this.rightSplitPaneVbox.getChildren().remove(SolInfoBox);
                }
            }else {
                this.rightSplitPaneVbox.getChildren().clear();
            }
        });
    }
    public void removeObjTitledPane(){
        this.objTitledPane = null;
    }

    public void createSolitaireInfoBox() {
        SolInfoBox = new VBox(10);
        SolInfoBox.setPadding(new Insets(10));
        SolInfoBox.setAlignment(Pos.TOP_LEFT);

        FactionFactory.MyObject sSeer = this.getSolitaire();

        String pivRoles = sSeer.getUpgrades();
        String[] lines = pivRoles.split("\n");
        String[] output = new String[3];

        for (int i = 0; i < lines.length; i += 3) {
            String name = lines[i].replace(";", "");
            String value1 = lines[i+1];
            String value2 = lines[i+2].replace(";", "");
            output[i/3] = name + " \u2022 " + value1 + "pts " + value2 + " PL";
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
                    this.pointcost = this.pointcost + 20;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            20)));
                    this.prevPL = 1;
                    this.prevpoints = 20;
                }
                if (index == 2) {
                    this.PL = this.PL - this.prevPL;
                    this.totalPlProperty.set(Integer.toString(Utils.subPL(this.totalPlProperty,this.prevPL)));
                    this.pointcost = this.pointcost - this.prevpoints;
                    this.totalPointsProperty.set(Integer.toString(Utils.subPoints(this.totalPointsProperty,
                            this.prevpoints)));
                    this.PL++;
                    this.totalPlProperty.set(Integer.toString(Utils.addPL(this.totalPlProperty,1)));
                    this.pointcost = this.pointcost + 15;
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            15)));
                    this.prevPL = 1;
                    this.prevpoints = 15;
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
                    this.totalPointsProperty.set(Integer.toString(Utils.addPoints(this.totalPointsProperty,
                            20)));
                    this.prevPL = 1;
                    this.prevpoints = 20;
                }
                this.setPivotalRoleSelection(index);
            });
        }
        SolInfoBox.getChildren().addAll(pivRoleBP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solitaire").append("\n");
        sb.append("Power Level: ").append(getPL()).append(" Point Cost: ").append(getPointcost()).append("\n");

        //Selected options
        sb.append("Pivotal Roles:\n");
        if(getPivotalRoleSelection() == 0){
            sb.append("No Pivotal Role Selected").append("\n");
        } else if (getPivotalRoleSelection() == 1) {
            sb.append("Prince of Sins").append("\n");
        } else if (getPivotalRoleSelection() == 2) {
            sb.append("Spectre of Despair").append("\n");
        } else if (getPivotalRoleSelection() == 3) {
            sb.append("Thirsting Darkness").append("\n");
        }

        return sb.toString();
    }
}

