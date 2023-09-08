package lbgui.listbuilder;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lbgui.dataModels.*;
import lbgui.factories.DetachmentFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class EventHandlers implements EventHandler<MouseEvent> {

    private final DetachmentFactory.MyObject ourDetachment;
    private final ArrayList<StringProperty> detachmentSlotLabels;
    private final VBox v0;
    private final StringProperty totalPlProperty;
    private final StringProperty totolPointsProperty;
    private int totalPL;
    private int totalPoints;

    private ArrayList<Shadowseer> ourShadowseers;
    private TitledPane HQ;
    boolean isHqCreated = false;
    private int totalSS;
    private int totalHQUnits;
    private VBox hqBox;
    private Label HQPLLabel;
    private Label HQPOINTSLabel;


    private ArrayList<TroupeMaster> ourTroupeMasters;
    private int totalTM;
    private int totalInfantry;

    private ArrayList<DeathJester> ourDeathJesters;
    private TitledPane Elites;
    boolean isElitesCreated = false;
    private int totalDJ;
    private int totalEliteUnits;
    private VBox eliteBox;

    private ArrayList<Solitaire> ourSolitaires;
    private int totalSol;
    private Label ELITEPLLABEL;
    private Label ELITEPOINTSLABEL;

    private ArrayList<Voidweavers> ourVoidweavers;
    private TitledPane HeavySupport;
    boolean isHeavySupportCreated = false;
    private int totalVoidweavers;
    private int totalHeavySupportUnits;
    private VBox hsBox;
    private Label HSPLLABEL;
    private Label HSPOINTSLABEL;

    private ArrayList<Skyweavers> ourSkyweavers;
    private TitledPane FastAttack;
    boolean isFastAttackCreated = false;
    private int totalSkyweavers;
    private int totalFastAttackUnits;
    private VBox faBox;
    private Label FAPLLABEL;
    private Label FAPOINTSLABEL;

    private ArrayList<Troupe> ourTroupes;
    private TitledPane Troops;
    boolean isTroopsCreated = false;
    private int totalTroupes;
    private VBox troopBox;
    private Label TROOPLLABEL;
    private Label TROOPPOINTSLABEL;

    private ArrayList<WebwayGate> ourWebwaygate;
    private TitledPane Fortification;
    boolean isFortificationCreated = false;
    private int totalWG;
    private VBox fortBox;

    private ArrayList<Starweaver> ourStarweavers;
    private TitledPane DedicatedTransport;
    boolean isDTCreated = false;
    private int totalDT;
    private VBox DTBox;
    private Label DTPLLABEL;
    private Label DTPOINTSLABEL;


    public EventHandlers(VBox RHVbox, ArrayList<StringProperty> detachmentSlotLabels,
                         DetachmentFactory.MyObject ourDetachment,StringProperty totalPlProperty,
                         StringProperty totolPointsProperty) { // Constructor
        this.v0 = RHVbox;
        this.detachmentSlotLabels = detachmentSlotLabels;
        this.ourDetachment = ourDetachment;
        this.totalPlProperty = totalPlProperty;
        this.totolPointsProperty = totolPointsProperty;
        initializeUnitObjects();
    }

    @Override
    public void handle(MouseEvent event) {

        Label clickedLabel = (Label) event.getSource();
        if (clickedLabel.getId().equals("addShadowseer")) {
            if(!isHqCreated){
                Shadowseer newShadowseer = new Shadowseer();
                HQ = newShadowseer.getShadowseerRoleTitledPane();
                HQ.setId("HQ");

                hqBox = new VBox();
                v0.getChildren().add(HQ);
                hqBox.setPadding(new Insets(10, 10, 10, 10));
                HQ.setContent(hqBox);
                this.isHqCreated = true;

                Label deleteShadow1 = new Label();
                TitledPane SS1 = newShadowseer.getUnitTitledPane(deleteShadow1);
                newShadowseer.setObjTitledPane(SS1, totalPlProperty, totolPointsProperty); // THIS IS WHAT I ADDED
                HQPLLabel = newShadowseer.getHqPlLabel();
                HQPOINTSLabel = newShadowseer.getHqPointsLabel();
                newShadowseer.setHqPlLabel(this.HQPLLabel);
                newShadowseer.setHqPointsLabel(this.HQPOINTSLabel);

                SS1.setExpanded(false);
                hqBox.getChildren().add(SS1);

                totalSS ++;
                totalHQUnits++;
                totalInfantry++;
                totalPL = totalPL + newShadowseer.getPL();
                totalPoints = totalPoints + newShadowseer.getPointcost();
                this.detachmentSlotLabels.get(0).set(Integer.toString(totalSS+totalTM));
                this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));


                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        newShadowseer.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        newShadowseer.getPointcost())));

                deleteShadow1.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            newShadowseer.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            newShadowseer.getPointcost())));
                    hqBox.getChildren().remove(SS1);
                    newShadowseer.removeObjTitledPane();
                    totalSS--;
                    totalHQUnits--;
                    totalInfantry--;
                    totalPL = totalPL - newShadowseer.getPL();
                    totalPoints = totalPoints - newShadowseer.getPointcost();
                    this.detachmentSlotLabels.get(0).set(Integer.toString(totalSS+totalTM));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    ourShadowseers.remove(newShadowseer);
                    updateHQpoints();



                    if(totalSS == 0 && totalHQUnits == 0) {
                        v0.getChildren().remove(HQ);
                        this.isHqCreated = false;

                    }
                });
                ourShadowseers.add(newShadowseer);
                updateHQpoints();
            } else {
                if(totalSS < 3 && totalHQUnits < ourDetachment.getHq()) {
                    Shadowseer newShadowseer = new Shadowseer();
                    Label deleteShadow = new Label();
                    TitledPane SS = newShadowseer.getUnitTitledPane(deleteShadow);
                    newShadowseer.setObjTitledPane(SS, totalPlProperty, totolPointsProperty);
                    SS.setExpanded(false);
                    hqBox.getChildren().add(SS);

                    newShadowseer.setHqPlLabel(this.HQPLLabel);
                    newShadowseer.setHqPointsLabel(this.HQPOINTSLabel);

                    totalSS ++;
                    totalHQUnits++;
                    totalInfantry++;
                    totalPL = totalPL + newShadowseer.getPL();
                    totalPoints = totalPoints + newShadowseer.getPointcost();
                    this.detachmentSlotLabels.get(0).set(Integer.toString(totalSS+totalTM));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    deleteShadow.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                newShadowseer.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                newShadowseer.getPointcost())));
                        hqBox.getChildren().remove(SS);
                        newShadowseer.removeObjTitledPane();
                        totalSS--;
                        totalHQUnits--;
                        totalInfantry--;
                        totalPL = totalPL - newShadowseer.getPL();
                        totalPoints = totalPoints - newShadowseer.getPointcost();
                        this.detachmentSlotLabels.get(0).set(Integer.toString(totalSS+totalTM));
                        this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                        ourShadowseers.remove(newShadowseer);
                        updateHQpoints();

                        if(totalSS == 0 && totalHQUnits == 0) {
                            v0.getChildren().remove(HQ);
                            this.isHqCreated = false;
                        }
                    });
                    ourShadowseers.add(newShadowseer);
                    updateHQpoints();

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            newShadowseer.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            newShadowseer.getPointcost())));
                }
            }
        }
        if (clickedLabel.getId().equals("addTroupeMaster")) {
            if(!isHqCreated) {
                TroupeMaster troupeMaster = new TroupeMaster();
                HQ = troupeMaster.getTroupeMasterRoleTitledPane();
                HQ.setId("HQ");

                hqBox = new VBox();
                v0.getChildren().add(HQ);
                hqBox.setPadding(new Insets(10, 10, 10, 10));
                HQ.setContent(hqBox);
                HQPLLabel = troupeMaster.getHqPlLabel();
                HQPOINTSLabel = troupeMaster.getHqPointsLabel();

                troupeMaster.setHqPlLabel(this.HQPLLabel);
                troupeMaster.setHqPointsLabel(this.HQPOINTSLabel);

                this.isHqCreated = true;

                Label deleteTroupeMaster1 = new Label();
                TitledPane TM1 = troupeMaster.getUnitTitledPane(deleteTroupeMaster1);
                troupeMaster.setObjTitledPane(TM1, totalPlProperty, totolPointsProperty);
                TM1.setExpanded(false);
                hqBox.getChildren().add(TM1);

                totalTM ++;
                totalHQUnits++;
                totalInfantry++;
                totalPL = totalPL + troupeMaster.getPL();
                totalPoints = totalPoints + troupeMaster.getPointcost();
                this.detachmentSlotLabels.get(0).set(Integer.toString(totalTM+totalSS));
                this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));

                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        troupeMaster.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        troupeMaster.getPointcost())));


                deleteTroupeMaster1.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            troupeMaster.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            troupeMaster.getPointcost())));
                    hqBox.getChildren().remove(TM1);
                    troupeMaster.removeTitledPane();
                    totalTM--;
                    totalHQUnits--;
                    totalInfantry--;
                    totalPL = totalPL - troupeMaster.getPL();
                    totalPoints = totalPoints - troupeMaster.getPointcost();
                    this.detachmentSlotLabels.get(0).set(Integer.toString(totalTM+totalSS));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    ourTroupeMasters.remove(troupeMaster);
                    updateHQpoints();

                    if(totalTM == 0 && totalHQUnits == 0) {
                        v0.getChildren().remove(HQ);
                        this.isHqCreated = false;

                    }
                });
                ourTroupeMasters.add(troupeMaster);
                updateHQpoints();
            } else {

                if(totalTM < 3 && totalHQUnits < ourDetachment.getHq()) {
                    TroupeMaster troupeMaster = new TroupeMaster();
                    Label deleteTM = new Label();
                    TitledPane TM = troupeMaster.getUnitTitledPane(deleteTM);
                    troupeMaster.setObjTitledPane(TM,totalPlProperty, totolPointsProperty);
                    TM.setExpanded(false);
                    hqBox.getChildren().add(TM);

                    troupeMaster.setHqPlLabel(this.HQPLLabel);
                    troupeMaster.setHqPointsLabel(this.HQPOINTSLabel);

                    totalTM ++;
                    totalHQUnits++;
                    totalInfantry++;
                    totalPL = totalPL + troupeMaster.getPL();
                    totalPoints = totalPoints + troupeMaster.getPointcost();
                    this.detachmentSlotLabels.get(0).set(Integer.toString(totalTM+totalSS));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    deleteTM.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                troupeMaster.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                troupeMaster.getPointcost())));
                        hqBox.getChildren().remove(TM);
                        troupeMaster.removeTitledPane();
                        totalTM--;
                        totalHQUnits --;
                        totalInfantry--;
                        totalPL = totalPL - troupeMaster.getPL();
                        totalPoints = totalPoints - troupeMaster.getPointcost();
                        this.detachmentSlotLabels.get(0).set(Integer.toString(totalTM+totalSS));
                        this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                        ourTroupeMasters.remove(troupeMaster);
                        updateHQpoints();

                        if(totalTM == 0 && totalHQUnits == 0) {
                            v0.getChildren().remove(HQ);
                            this.isHqCreated = false;
                        }
                    });
                    ourTroupeMasters.add(troupeMaster);
                    updateHQpoints();

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            troupeMaster.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            troupeMaster.getPointcost())));
                }

            }
        }
        if (clickedLabel.getId().equals("addTroupe")) {
            if(!isTroopsCreated) {
                Troupe troupe = new Troupe();
                Troops = troupe.getTroupeRoleTitledPane();
                Troops.setId("Troops");

                troopBox = new VBox();
                v0.getChildren().add(Troops);
                troopBox.setPadding(new Insets(10, 10, 10, 10));
                Troops.setContent(troopBox);
                TROOPLLABEL = troupe.getTroopPlLabel();
                TROOPPOINTSLABEL = troupe.getTroopPointsLabel();
                this.isTroopsCreated = true;

                Label deleteTroupe1 = new Label();
                TitledPane Troupe1 = troupe.getUnitTitledPane(deleteTroupe1);
                troupe.setObjTitledPane(Troupe1, totolPointsProperty);
                Troupe1.setExpanded(false);
                troopBox.getChildren().add(Troupe1);

                totalTroupes++;
                totalInfantry++;
                totalPL = totalPL + troupe.getPL();
                totalPoints = totalPoints + troupe.getPointcost();
                this.detachmentSlotLabels.get(1).set(Integer.toString(totalTroupes));
                this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));

                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        troupe.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        troupe.getPointcost())));


                deleteTroupe1.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            troupe.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            troupe.getPointcost())));
                    troopBox.getChildren().remove(Troupe1);
                    troupe.removeObjTitledPane();
                    totalTroupes--;
                    totalInfantry--;
                    totalPL = totalPL - troupe.getPL();
                    totalPoints = totalPoints - troupe.getPointcost();
                    this.detachmentSlotLabels.get(1).set(Integer.toString(totalTroupes));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    ourTroupes.remove(troupe);
                    updateTroopPoints();

                    if(totalTroupes == 0) {
                        v0.getChildren().remove(Troops);
                        this.isHqCreated = false;
                    }
                });
                ourTroupes.add(troupe);
                updateTroopPoints();
            } else {

                if(totalTroupes < ourDetachment.getTroops()) {
                    Troupe troupe = new Troupe();
                    Label deleteTroupe = new Label();
                    TitledPane Tr = troupe.getUnitTitledPane(deleteTroupe);
                    troupe.setObjTitledPane(Tr, totolPointsProperty);
                    Tr.setExpanded(false);
                    troopBox.getChildren().add(Tr);

                    totalTroupes++;
                    totalInfantry++;
                    totalPL = totalPL + troupe.getPL();
                    totalPoints = totalPoints + troupe.getPointcost();
                    this.detachmentSlotLabels.get(1).set(Integer.toString(totalTroupes));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    deleteTroupe.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                troupe.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                troupe.getPointcost())));
                        troopBox.getChildren().remove(Tr);
                        troupe.removeObjTitledPane();
                        totalTroupes--;
                        totalInfantry--;
                        totalPL = totalPL - troupe.getPL();
                        totalPoints = totalPoints - troupe.getPointcost();
                        this.detachmentSlotLabels.get(1).set(Integer.toString(totalTroupes));
                        this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                        ourTroupes.remove(troupe);
                        updateTroopPoints();

                        if(totalTroupes == 0) {
                            v0.getChildren().remove(Troops);
                            this.isTroopsCreated = false;
                        }
                    });
                    ourTroupes.add(troupe);
                    updateTroopPoints();

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            troupe.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            troupe.getPointcost())));

                }

            }
        }
        if (clickedLabel.getId().equals("addDeathJester")) {

            if (!isElitesCreated) {
                DeathJester deathJester = new DeathJester();
                Elites = deathJester.getDeathJesterRoleTitledPane();
                Elites.setId("Elites");

                eliteBox = new VBox();
                v0.getChildren().add(Elites);
                eliteBox.setPadding(new Insets(10, 10, 10, 10));
                Elites.setContent(eliteBox);
                ELITEPLLABEL = deathJester.getElitesPlLabel();
                ELITEPOINTSLABEL = deathJester.getElitesPointsLabel();

                deathJester.setElitesPlLabel(this.ELITEPLLABEL);
                deathJester.setElitesPointsLabel(this.ELITEPOINTSLABEL);
                this.isElitesCreated = true;

                Label deleteDeathJester = new Label();
                TitledPane DJ1 = deathJester.getUnitTitledPane(deleteDeathJester);
                deathJester.setObjTitledPane(DJ1,totalPlProperty, totolPointsProperty);
                DJ1.setExpanded(false);
                eliteBox.getChildren().add(DJ1);

                totalDJ++;
                totalEliteUnits++;
                totalInfantry++;
                totalPL = totalPL + deathJester.getPL();
                totalPoints = totalPoints + deathJester.getPointcost();
                this.detachmentSlotLabels.get(3).set(Integer.toString(totalDJ + totalSol));
                this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));

                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        deathJester.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        deathJester.getPointcost())));


                deleteDeathJester.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            deathJester.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            deathJester.getPointcost())));
                    eliteBox.getChildren().remove(DJ1);
                    deathJester.removeObjTitledPane();
                    totalDJ--;
                    totalEliteUnits--;
                    totalInfantry --;
                    totalPL = totalPL - deathJester.getPL();
                    totalPoints = totalPoints - deathJester.getPointcost();
                    this.detachmentSlotLabels.get(3).set(Integer.toString(totalDJ + totalSol));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    ourDeathJesters.remove(deathJester);
                    updateElitesPoints();

                    if(totalDJ == 0 && totalEliteUnits == 0) {
                        v0.getChildren().remove(Elites);
                        this.isElitesCreated = false;
                    }
                });
                ourDeathJesters.add(deathJester);
                updateElitesPoints();

            } else {
                if(totalDJ < 3 && totalEliteUnits < ourDetachment.getElites()) {
                    DeathJester deathJester = new DeathJester();
                    Label deleteDeathJester = new Label();
                    TitledPane DJ1 = deathJester.getUnitTitledPane(deleteDeathJester);
                    deathJester.setObjTitledPane(DJ1,totalPlProperty, totolPointsProperty);
                    DJ1.setExpanded(false);
                    eliteBox.getChildren().add(DJ1);

                    deathJester.setElitesPlLabel(this.ELITEPLLABEL);
                    deathJester.setElitesPointsLabel(this.ELITEPOINTSLABEL);

                    totalDJ++;
                    this.totalEliteUnits++;
                    totalInfantry++;
                    totalPL = totalPL + deathJester.getPL();
                    totalPoints = totalPoints + deathJester.getPointcost();
                    this.detachmentSlotLabels.get(3).set(Integer.toString(totalDJ + totalSol));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));

                    deleteDeathJester.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                deathJester.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                deathJester.getPointcost())));
                        eliteBox.getChildren().remove(DJ1);
                        deathJester.removeObjTitledPane();
                        totalDJ--;
                        totalEliteUnits--;
                        totalInfantry --;
                        totalPL = totalPL - deathJester.getPL();
                        totalPoints = totalPoints - deathJester.getPointcost();
                        this.detachmentSlotLabels.get(3).set(Integer.toString(totalDJ + totalSol));
                        this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                        ourDeathJesters.remove(deathJester);
                        updateElitesPoints();

                        if(totalDJ == 0 && totalEliteUnits == 0) {
                            v0.getChildren().remove(Elites);
                            this.isElitesCreated = false;
                        }
                    });
                    ourDeathJesters.add(deathJester);
                    updateElitesPoints();

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            deathJester.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            deathJester.getPointcost())));

                }

            }
        }
        if (clickedLabel.getId().equals("addSolitaire")) {
            if(!isElitesCreated) {
                Solitaire solitaire = new Solitaire();
                Elites = solitaire.getSolitaireRoleTitledPane();
                Elites.setId("Elites");

                eliteBox = new VBox();
                v0.getChildren().add(Elites);
                eliteBox.setPadding(new Insets(10, 10, 10, 10));
                Elites.setContent(eliteBox);
                ELITEPLLABEL = solitaire.getElitesPlLabel();
                ELITEPOINTSLABEL = solitaire.getElitesPointsLabel();
                solitaire.setElitesPlLabel(ELITEPLLABEL);
                solitaire.setElitesPointsLabel(ELITEPOINTSLABEL);
                this.isElitesCreated = true;

                Label deleteSolitaire = new Label();
                TitledPane SOL1 = solitaire.getUnitTitledPane(deleteSolitaire);
                solitaire.setObjTitledPane(SOL1,totalPlProperty, totolPointsProperty);
                SOL1.setExpanded(false);
                eliteBox.getChildren().add(SOL1);

                totalSol++;
                totalEliteUnits++;
                totalInfantry++;
                totalPL = totalPL + solitaire.getPL();
                totalPoints = totalPoints + solitaire.getPointcost();
                this.detachmentSlotLabels.get(3).set(Integer.toString(totalSol+ totalDJ));
                this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));

                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        solitaire.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        solitaire.getPointcost())));


                deleteSolitaire.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            solitaire.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            solitaire.getPointcost())));
                    eliteBox.getChildren().remove(SOL1);
                    solitaire.removeObjTitledPane();
                    totalSol--;
                    totalEliteUnits--;
                    totalInfantry --;
                    totalPL = totalPL - solitaire.getPL();
                    totalPoints = totalPoints - solitaire.getPointcost();
                    this.detachmentSlotLabels.get(3).set(Integer.toString(totalSol + totalDJ));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                    ourSolitaires.remove(solitaire);
                    updateElitesPoints();

                    if(totalSol == 0 && totalEliteUnits == 0) {
                        v0.getChildren().remove(Elites);
                        this.isElitesCreated = false;
                    }
                });
                ourSolitaires.add(solitaire);
                updateElitesPoints();



            }else {
                if(totalSol < 1 && totalEliteUnits < ourDetachment.getElites()) {
                    Solitaire solitaire = new Solitaire();
                    Label deleteSolitaire = new Label();
                    TitledPane SOL1 = solitaire.getUnitTitledPane(deleteSolitaire);
                    solitaire.setObjTitledPane(SOL1, totalPlProperty, totolPointsProperty);
                    SOL1.setExpanded(false);
                    eliteBox.getChildren().add(SOL1);

                    solitaire.setElitesPlLabel(ELITEPLLABEL);
                    solitaire.setElitesPointsLabel(ELITEPOINTSLABEL);

                    totalSol++;
                    totalEliteUnits++;
                    totalInfantry++;
                    totalPL = totalPL + solitaire.getPL();
                    totalPoints = totalPoints + solitaire.getPointcost();
                    this.detachmentSlotLabels.get(3).set(Integer.toString(totalSol + totalDJ));
                    this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));

                    deleteSolitaire.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                solitaire.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                solitaire.getPointcost())));
                        eliteBox.getChildren().remove(SOL1);
                        solitaire.removeObjTitledPane();
                        totalSol--;
                        totalEliteUnits--;
                        totalInfantry --;
                        totalPL = totalPL - solitaire.getPL();
                        totalPoints = totalPoints - solitaire.getPointcost();
                        this.detachmentSlotLabels.get(3).set(Integer.toString(totalSol + totalDJ));
                        this.detachmentSlotLabels.get(10).set(Integer.toString(totalInfantry));
                        ourSolitaires.remove(solitaire);
                        updateElitesPoints();

                        if(totalSol == 0 && totalEliteUnits == 0) {
                            v0.getChildren().remove(Elites);
                            this.isElitesCreated = false;

                        }
                    });
                    ourSolitaires.add(solitaire);
                    updateElitesPoints();

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            solitaire.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            solitaire.getPointcost())));
                }
            }
        }
        if (clickedLabel.getId().equals("addskyweavers")) {
            if(!isFastAttackCreated) {
                Skyweavers skyweavers = new Skyweavers();
                FastAttack = skyweavers.getSkyweaversRoleTitledPane();
                FastAttack.setId("Fast Attack");

                faBox = new VBox();
                v0.getChildren().add(FastAttack);
                faBox.setPadding(new Insets(10, 10, 10, 10));
                FastAttack.setContent(faBox);
                FAPLLABEL = skyweavers.getFastAttackPlLabel();
                FAPOINTSLABEL = skyweavers.getFastAttackPointsLabel();
                this.isFastAttackCreated = true;

                Label deleteSkyweavers1 = new Label();
                TitledPane skyweavers1 = skyweavers.getUnitTitledPane(deleteSkyweavers1);
                skyweavers.setObjTitledPane(skyweavers1, totolPointsProperty);
                skyweavers1.setExpanded(false);
                faBox.getChildren().add(skyweavers1);

                totalSkyweavers++;
                totalFastAttackUnits++;
                totalPL = totalPL + skyweavers.getPL();
                totalPoints = totalPoints + skyweavers.getPointcost();
                this.detachmentSlotLabels.get(4).set(Integer.toString(totalSkyweavers));

                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        skyweavers.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        skyweavers.getPointcost())));


                deleteSkyweavers1.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            skyweavers.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            skyweavers.getPointcost())));
                    faBox.getChildren().remove(skyweavers1);
                    skyweavers.removeObjTitledPane();
                    totalSkyweavers--;
                    totalFastAttackUnits--;
                    totalPL = totalPL - skyweavers.getPL();
                    totalPoints = totalPoints - skyweavers.getPointcost();
                    this.detachmentSlotLabels.get(4).set(Integer.toString(totalSkyweavers));
                    ourSkyweavers.remove(skyweavers);
                    updateFastAttackPoints();

                    if(totalSkyweavers == 0) {
                        v0.getChildren().remove(FastAttack);
                        this.isFastAttackCreated = false;

                    }
                });
                ourSkyweavers.add(skyweavers);
                updateFastAttackPoints();
            } else {

                if(totalSkyweavers < 3 && totalFastAttackUnits < ourDetachment.getFastAttack() ) {
                    Skyweavers skyweavers = new Skyweavers();
                    Label deleteSkyweavers = new Label();
                    TitledPane SW = skyweavers.getUnitTitledPane(deleteSkyweavers);
                    skyweavers.setObjTitledPane(SW, totolPointsProperty);
                    SW.setExpanded(false);
                    faBox.getChildren().add(SW);

                    totalSkyweavers++;
                    totalFastAttackUnits++;
                    totalPL = totalPL + skyweavers.getPL();
                    totalPoints = totalPoints + skyweavers.getPointcost();
                    this.detachmentSlotLabels.get(4).set(Integer.toString(totalSkyweavers));
                    deleteSkyweavers.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                skyweavers.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                skyweavers.getPointcost())));
                        faBox.getChildren().remove(SW);
                        skyweavers.removeObjTitledPane();
                        totalSkyweavers--;
                        totalFastAttackUnits--;
                        totalPL = totalPL - skyweavers.getPL();
                        totalPoints = totalPoints - skyweavers.getPointcost();
                        this.detachmentSlotLabels.get(4).set(Integer.toString(totalSkyweavers));
                        ourSkyweavers.remove(skyweavers);
                        updateFastAttackPoints();

                        if(totalSkyweavers == 0) {
                            v0.getChildren().remove(FastAttack);
                            this.isFastAttackCreated = false;
                        }
                    });
                    ourSkyweavers.add(skyweavers);
                    updateFastAttackPoints();

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            skyweavers.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            skyweavers.getPointcost())));
                }
            }
        }
        if (clickedLabel.getId().equals("addVoidweavers")) {
            if(!isHeavySupportCreated) {
                Voidweavers voidweavers = new Voidweavers();
                HeavySupport = voidweavers.getVoidweaversRoleTitledPane();
                HeavySupport.setId("Heavy Support");

                hsBox = new VBox();
                v0.getChildren().add(HeavySupport);
                hsBox.setPadding(new Insets(10, 10, 10, 10));
                HeavySupport.setContent(hsBox);
                HSPLLABEL = voidweavers.getHeavySupportPlLabel();
                HSPOINTSLABEL = voidweavers.getHeavySupportPointsLabel();
                this.isHeavySupportCreated = true;

                Label deleteVoidweavers1 = new Label();
                TitledPane voidweavers1 = voidweavers.getUnitTitledPane(deleteVoidweavers1);
                voidweavers.setObjTitledPane(voidweavers1,totalPlProperty, totolPointsProperty);
                voidweavers1.setExpanded(false);
                hsBox.getChildren().add(voidweavers1);

                totalVoidweavers++;
                totalHeavySupportUnits++;
                totalPL = totalPL + voidweavers.getPL();
                totalPoints = totalPoints + voidweavers.getPointcost();
                this.detachmentSlotLabels.get(5).set(Integer.toString(totalVoidweavers));

                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        voidweavers.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        voidweavers.getPointcost())));

                deleteVoidweavers1.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            voidweavers.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            voidweavers.getPointcost())));
                    hsBox.getChildren().remove(voidweavers1);
                    voidweavers.removeObjTitledPane();
                    totalVoidweavers--;
                    totalHeavySupportUnits--;
                    totalPL = totalPL - voidweavers.getPL();
                    totalPoints = totalPoints - voidweavers.getPointcost();
                    this.detachmentSlotLabels.get(5).set(Integer.toString(totalVoidweavers));
                    ourVoidweavers.remove(voidweavers);
                    updateHeavySupportPoints();

                    if(totalVoidweavers == 0) {
                        v0.getChildren().remove(HeavySupport);
                        this.isHeavySupportCreated = false;

                    }
                });
                ourVoidweavers.add(voidweavers);
                updateHeavySupportPoints();
            } else {

                if(totalVoidweavers < 3 && totalHeavySupportUnits < ourDetachment.getHeavySupport()) {
                    Voidweavers voidweavers = new Voidweavers();
                    Label deleteVoidweavers = new Label();
                    TitledPane VW = voidweavers.getUnitTitledPane(deleteVoidweavers);
                    voidweavers.setObjTitledPane(VW,totalPlProperty, totolPointsProperty);
                    VW.setExpanded(false);
                    hsBox.getChildren().add(VW);

                    totalVoidweavers++;
                    totalHeavySupportUnits++;
                    totalPL = totalPL + voidweavers.getPL();
                    totalPoints = totalPoints + voidweavers.getPointcost();
                    this.detachmentSlotLabels.get(5).set(Integer.toString(totalVoidweavers));
                    deleteVoidweavers.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                voidweavers.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                voidweavers.getPointcost())));
                        hsBox.getChildren().remove(VW);
                        voidweavers.removeObjTitledPane();
                        totalVoidweavers--;
                        totalHeavySupportUnits--;
                        totalPL = totalPL - voidweavers.getPL();
                        totalPoints = totalPoints - voidweavers.getPointcost();
                        this.detachmentSlotLabels.get(5).set(Integer.toString(totalVoidweavers));
                        ourVoidweavers.remove(voidweavers);
                        updateHeavySupportPoints();

                        if(totalVoidweavers == 0) {
                            v0.getChildren().remove(HeavySupport);
                            this.isHeavySupportCreated = false;
                        }
                    });
                    ourVoidweavers.add(voidweavers);
                    updateHeavySupportPoints();

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            voidweavers.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            voidweavers.getPointcost())));

                }
            }
        }
        if (clickedLabel.getId().equals("addStarweaver")) {
                if(!isDTCreated && totalInfantry > 0) {
                    Starweaver starweaver = new Starweaver();
                    DedicatedTransport = starweaver.getStarweaverRoleTitledPane();
                    DedicatedTransport.setId("DedicatedTransport");

                    DTBox = new VBox();
                    v0.getChildren().add(DedicatedTransport);
                    DTBox.setPadding(new Insets(10,10,10,10));
                    DedicatedTransport.setContent(DTBox);
                    DTPLLABEL = starweaver.getDTPlLabel();
                    DTPOINTSLABEL = starweaver.getDTPointsLabel();
                    this.isDTCreated = true;

                    Label deleteStarweaver = new Label();
                    TitledPane starweaver1 = starweaver.getUnitTitledPane(deleteStarweaver);
                    starweaver.setObjTitledPane(starweaver1);
                    starweaver1.setExpanded(false);
                    DTBox.getChildren().add(starweaver1);

                    totalDT++;
                    totalPL = totalPL + starweaver.getPL();
                    totalPoints = totalPoints + starweaver.getPointcost();
                    this.detachmentSlotLabels.get(9).set(Integer.toString(totalDT));

                    totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                            starweaver.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                            starweaver.getPointcost())));

                    deleteStarweaver.setOnMouseClicked(e -> {
                        totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                starweaver.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                starweaver.getPointcost())));
                        DTBox.getChildren().remove(starweaver1);
                        starweaver.removeObjTitledPane();
                        totalDT --;
                        totalPL = totalPL - starweaver.getPL();
                        totalPoints = totalPoints - starweaver.getPointcost();
                        this.detachmentSlotLabels.get(9).set(Integer.toString(totalDT));
                        ourStarweavers.remove(starweaver);
                        updateDedicatedSupportPoints();

                        if(totalDT == 0) {
                            v0.getChildren().remove(DedicatedTransport);
                            this.isDTCreated = false;
                        }
                    });
                    ourStarweavers.add(starweaver);
                    updateDedicatedSupportPoints();
                }else {
                    if(totalDT > 0 && totalDT < totalInfantry){
                        Starweaver starweaver = new Starweaver();
                        Label deleteStarweaver = new Label();
                        TitledPane starweaver1 = starweaver.getUnitTitledPane(deleteStarweaver);
                        starweaver.setObjTitledPane(starweaver1);
                        starweaver1.setExpanded(false);
                        DTBox.getChildren().add(starweaver1);

                        totalDT++;
                        totalPL = totalPL + starweaver.getPL();
                        totalPoints = totalPoints + starweaver.getPointcost();
                        this.detachmentSlotLabels.get(9).set(Integer.toString(totalDT));
                        deleteStarweaver.setOnMouseClicked(e -> {
                            totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                                    starweaver.getPL())));
                            totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                                    starweaver.getPointcost())));
                            DTBox.getChildren().remove(starweaver1);
                            starweaver.removeObjTitledPane();
                            totalDT --;
                            totalPL = totalPL - starweaver.getPL();
                            totalPoints = totalPoints - starweaver.getPointcost();
                            this.detachmentSlotLabels.get(9).set(Integer.toString(totalDT));
                            ourStarweavers.remove(starweaver);
                            updateDedicatedSupportPoints();

                            if(totalDT == 0) {
                                v0.getChildren().remove(DedicatedTransport);
                                this.isDTCreated = false;
                            }
                        });
                        ourStarweavers.add(starweaver);
                        updateDedicatedSupportPoints();

                        totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                                starweaver.getPL())));
                        totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                                starweaver.getPointcost())));
                    }
                }
        }
        if (clickedLabel.getId().equals("addWebwayGate")) {
            if(totalWG < ourDetachment.getFortification() && totalWG < 1) {
                WebwayGate webwayGate = new WebwayGate();
                Fortification = webwayGate.getWebwayGateRoleTitledPane();
                Fortification.setId("Fortification");
                Label fortificationPlLabel = webwayGate.getFortificationPlLabel();
                Label fortificationPointsLabel = webwayGate.getFortificationPointsLabel();

                int plCost = webwayGate.getPL();
                int pointCost = webwayGate.getPointcost();
                fortificationPlLabel.setText((plCost) + " Pl,");
                fortificationPointsLabel.setText((pointCost) + " pts ");

                fortBox = new VBox();
                v0.getChildren().add(Fortification);
                fortBox.setPadding(new Insets(10, 10, 10, 10));
                Fortification.setContent(fortBox);
                this.isFortificationCreated = true;

                Label deleteFortification1 = new Label();
                TitledPane Fort1 = webwayGate.getUnitTitledPane(deleteFortification1);
                webwayGate.setObjTitledPane(Fort1);
                Fort1.setExpanded(false);
                fortBox.getChildren().add(Fort1);

                totalWG++;
                totalPL = totalPL + webwayGate.getPL();
                totalPoints = totalPoints + webwayGate.getPointcost();
                this.detachmentSlotLabels.get(8).set(Integer.toString(totalWG));

                totalPlProperty.set(Integer.toString(Utils.addPL(totalPlProperty,
                        webwayGate.getPL())));
                totolPointsProperty.set(Integer.toString(Utils.addPoints(totolPointsProperty,
                        webwayGate.getPointcost())));

                deleteFortification1.setOnMouseClicked(e -> {
                    totalPlProperty.set(Integer.toString(Utils.subPL(totalPlProperty,
                            webwayGate.getPL())));
                    totolPointsProperty.set(Integer.toString(Utils.subPL(totolPointsProperty,
                            webwayGate.getPointcost())));
                    fortBox.getChildren().remove(Fort1);
                    webwayGate.removeObjTitledPane();
                    totalWG--;
                    totalPL = totalPL - webwayGate.getPL();
                    totalPoints = totalPoints - webwayGate.getPointcost();
                    this.detachmentSlotLabels.get(8).set(Integer.toString(totalWG));
                    ourWebwaygate.remove(webwayGate);


                    if(totalWG== 0) {
                        v0.getChildren().remove(Fortification);
                        this.isFortificationCreated = false;
                    }
                });
                ourWebwaygate.add(webwayGate);

            }



        }
    } // end of handle method
    private void initializeUnitObjects(){
        this.totalSS = 0;
        this.totalTM = 0;
        this.totalDJ = 0;
        this.totalWG = 0;
        this.totalSol = 0;
        this.totalDT = 0;
        this.totalVoidweavers = 0;
        this.totalSkyweavers = 0;
        this.totalTroupes = 0;
        this.ourShadowseers = new ArrayList<>();
        this.ourTroupeMasters = new ArrayList<>();
        this.ourTroupes = new ArrayList<>();
        this.ourDeathJesters = new ArrayList<>();
        this.ourSolitaires = new ArrayList<>();
        this.ourSkyweavers = new ArrayList<>();
        this.ourVoidweavers = new ArrayList<>();
        this.ourWebwaygate = new ArrayList<>();
        this.ourStarweavers = new ArrayList<>();


    }
    private void updateHQpoints(){
        int ssCount = this.ourShadowseers.size();
        int ssPlCost = 0;
        int ssPointsCost = 0;

        int tmCount = this.ourTroupeMasters.size();
        int tmPlCost = 0;
        int tmPointsCost = 0;

        if(ssCount > 0) {
            ssPlCost = this.ourShadowseers.get(0).getPL();
            ssPointsCost = this.ourShadowseers.get(0).getPointcost();
        }
        if(tmCount > 0) {
            tmPlCost = this.ourTroupeMasters.get(0).getPL();
            tmPointsCost = this.ourTroupeMasters.get(0).getPointcost();
        }


        int accumulatedPL = (ssPlCost * ssCount) + (tmPlCost * tmCount);
        int accumulatedpoints = (ssPointsCost * ssCount) + (tmPointsCost * tmCount);

        HQPLLabel.setText((accumulatedPL) + " PL,");
        HQPOINTSLabel.setText((accumulatedpoints) + " pts");
    }
    private void updateElitesPoints() {
        int djCount = this.ourDeathJesters.size();
        int djPointCost = 0;
        int djPLCost = 0;
        int solCount = this.ourSolitaires.size();
        int solPointCost = 0;
        int solPLCost = 0;

        if(djCount > 0) {
            djPointCost = this.ourDeathJesters.get(0).getPointcost();
            djPLCost = this.ourDeathJesters.get(0).getPL();
        }
        if(solCount > 0) {
            solPointCost = this.ourSolitaires.get(0).getPointcost();
            solPLCost = this.ourSolitaires.get(0).getPL();
        }
        int accumulatedPL = (djPLCost * djCount) + (solPLCost * solCount);
        int accumulatedPoints = (djPointCost * djCount) + (solPointCost * solCount);
        ELITEPLLABEL.setText((accumulatedPL) + " PL,");
        ELITEPOINTSLABEL.setText((accumulatedPoints) + " pts");
    }

    private void updateTroopPoints(){
        int troupeCount = this.ourTroupes.size();
        int troupePointCost = 0;
        int troupePLCost = 0;

        if(troupeCount > 0) {
            troupePointCost = this.ourTroupes.get(0).getPointcost();
            troupePLCost = this.ourTroupes.get(0).getPL();
        }
        int accumulatedPL = (troupePLCost * troupeCount);
        int accumulatedPoints = (troupePointCost * troupeCount);

        TROOPLLABEL.setText((accumulatedPL) + " Pl,");
        TROOPPOINTSLABEL.setText((accumulatedPoints) + " pts ");


    }
    private void updateFastAttackPoints(){
        int skyweaverCount = this.ourSkyweavers.size();
        int skyweaverPointCost = 0;
        int skyweaverPLCost = 0;

        if(skyweaverCount > 0) {
            skyweaverPointCost = this.ourSkyweavers.get(0).getPointcost();
            skyweaverPLCost = this.ourSkyweavers.get(0).getPL();
        }
        int accumulatedPL = (skyweaverPLCost * skyweaverCount);
        int accumulatedPoints = (skyweaverPointCost * skyweaverCount);
        FAPLLABEL.setText((accumulatedPL) + " Pl,");
        FAPOINTSLABEL.setText((accumulatedPoints) + " pts ");

    }
    private void updateHeavySupportPoints(){
        int voidweaverCount = this.ourVoidweavers.size();
        int voidweaverPointCost = 0;
        int voidweaverPLCost = 0;

        if(voidweaverCount > 0) {
            voidweaverPointCost = this.ourVoidweavers.get(0).getPointcost();
            voidweaverPLCost = this.ourVoidweavers.get(0).getPL();
        }
        int accumulatedPL = (voidweaverPLCost * voidweaverCount);
        int accumulatedPoints = (voidweaverPointCost * voidweaverCount);
        HSPLLABEL.setText((accumulatedPL) + " Pl,");
        HSPOINTSLABEL.setText((accumulatedPoints) + " pts ");
    }
    private void updateDedicatedSupportPoints(){
        int starvearerCount = this.ourStarweavers.size();
        int startweaverPointCost = 0;
        int startweaverPLCost = 0;

        if(starvearerCount > 0 ) {
            startweaverPLCost = this.ourStarweavers.get(0).getPL();
            startweaverPointCost = this.ourStarweavers.get(0).getPointcost();
        }
        int accumulatedPL = (startweaverPLCost * starvearerCount);
        int accumulatedPoints = (startweaverPointCost * starvearerCount);
        DTPLLABEL.setText((accumulatedPL) + " PL,");
        DTPOINTSLABEL.setText((accumulatedPoints) + " pts");
    }

    public void printListToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("WARHAMMER 40K List:\n\n");
            writer.write("-Faction: Harlequins\n");
            writer.write("-Detachment: Arks of Omen\n");
            writer.write("-Total PL: " + totalPlProperty.get() + "\n");
            writer.write("-Total Points: " + totolPointsProperty.get() + "\n\n\n");

            if(ourShadowseers.size() != 0 || ourTroupeMasters.size() != 0) {
                writer.write("|-HQ-|\n");
                // Shadowseers
                for (Shadowseer shadowseer : ourShadowseers) {
                    writer.write(shadowseer.toString() + "\n");
                }

                // TroupeMasters
                for (TroupeMaster troupeMaster : ourTroupeMasters) {
                    writer.write(troupeMaster.toString() + "\n");
                }
            }

            if(ourTroupes.size() != 0) {
                writer.write("|-Troops-|\n");
                // Troupes
                for (Troupe troupe : ourTroupes) {
                    writer.write(troupe.toString() + "\n");
                }
            }

            if(ourDeathJesters.size() != 0 || ourSolitaires.size() != 0) {
                writer.write("|-Elites-|\n");
                // DeathJesters
                for (DeathJester deathJester : ourDeathJesters) {
                    writer.write(deathJester.toString() + "\n");
                }

                // Solitaires
                for (Solitaire solitaire : ourSolitaires) {
                    writer.write(solitaire.toString() + "\n");
                }
            }

            if(ourVoidweavers.size() != 0) {
                writer.write("|-Heavy Support-|\n");
                // Voidweavers
                for (Voidweavers voidweaver : ourVoidweavers) {
                    writer.write(voidweaver.toString() + "\n");
                }
            }

            if(ourSkyweavers.size() != 0) {
                writer.write("|-Fast Attack-|\n");
                // Skyweavers
                for (Skyweavers skyweaver : ourSkyweavers) {
                    writer.write(skyweaver.toString() + "\n");
                }
            }

            if(ourStarweavers.size() != 0) {
                writer.write("|-Dedicated Transport-|\n");
                // Starweavers
                for (Starweaver starweaver : ourStarweavers) {
                    writer.write(starweaver.toString() + "\n");
                }
            }

            if(ourWebwaygate.size() != 0) {
                writer.write("|-Fortification-|\n");
                // WebwayGates
                for (WebwayGate webwaygate : ourWebwaygate) {
                    writer.write(webwaygate.toString() + "\n");
                }
            }
            writer.flush();
            writer.close();

            System.out.println("Army list have been saved to the file: " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while saving army list to the file: " + e.getMessage());
        }
    }

}