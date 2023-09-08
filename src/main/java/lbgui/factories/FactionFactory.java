package lbgui.factories;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FactionFactory {

    public static Map<String, MyObject> createObjectsFromFactionXlsx(File file, String sheetName) {
        Map<String, MyObject> objectMap = new HashMap<>();

        Workbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (workbook != null) {
            Sheet sheet = workbook.getSheet(sheetName);
            DataFormatter formatter = new DataFormatter();

            Row headerRow = sheet.getRow(0);
            Map<String, Integer> columnMap = new HashMap<>();
            for (int columnIndex = 0; columnIndex < headerRow.getLastCellNum(); columnIndex++) {
                Cell headerCell = headerRow.getCell(columnIndex);
                if (headerCell != null) {
                    String columnName = formatter.formatCellValue(headerCell).toLowerCase();
                    columnMap.put(columnName, columnIndex);
                }
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell nameCell = row.getCell(columnMap.get("name"));
                    if (nameCell != null && nameCell.getStringCellValue() != null) {
                        MyObject object = new MyObject();
                        for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                            String columnName = entry.getKey();
                            int columnIndex = entry.getValue();
                            Cell cell = row.getCell(columnIndex);
                            if (cell != null) {
                                switch (columnName) {
                                    case "pointcost" -> object.setPointCost((int) cell.getNumericCellValue());
                                    case "pl" -> object.setPL((int) cell.getNumericCellValue());
                                    case "cp" -> object.setCP((int) cell.getNumericCellValue());
                                    case "name" -> object.setName(cell.getStringCellValue());
                                    case "unit size" -> object.setUnitSize((int) cell.getNumericCellValue());
                                    case "m" -> object.setM(cell.getStringCellValue());
                                    case "ws" -> object.setWS(cell.getStringCellValue());
                                    case "bs" -> object.setBS(cell.getStringCellValue());
                                    case "s" -> object.setS((int) cell.getNumericCellValue());
                                    case "t" -> object.setT((int) cell.getNumericCellValue());
                                    case "w" -> object.setW((int) cell.getNumericCellValue());
                                    case "a" -> object.setA((int) cell.getNumericCellValue());
                                    case "ld" -> object.setLD((int) cell.getNumericCellValue());
                                    case "sv" -> object.setSv(cell.getStringCellValue());
                                    case "base" -> object.setBase(cell.getStringCellValue());
                                    case "wargear" -> object.setWargear(cell.getStringCellValue());
                                    case "optional wargear" -> object.setOpWargear(cell.getStringCellValue());
                                    case "casts" -> object.setCasts((int) cell.getNumericCellValue());
                                    case "denies" -> object.setDenies((int) cell.getNumericCellValue());
                                    case "faction keywords" ->
                                            object.setFactionKeywords(cell.getStringCellValue().split(";"));
                                    case "keywords" -> object.setKeywords(cell.getStringCellValue().split(";"));
                                    case "wargear limit" ->
                                            object.setWargearLimit(cell.getStringCellValue().split(";"));
                                    case "abilities" -> object.setAbilities(cell.getStringCellValue().split(";"));
                                    case "upgrades" -> object.setUpgrades(cell.getStringCellValue());
                                    case "spells" -> object.setSpells(cell.getStringCellValue().split(";"));
                                    case "role" -> object.setRole(cell.getStringCellValue());
                                }
                            }
                        }
                        objectMap.put(object.getName(), object);
                    }
                }
            }
        }
        return objectMap;
    }

    public static class MyObject {
        private String name;
        private int pointCost;
        private int pL;
        private int cP;
        private int unitSize;
        private String M;
        private String WS;
        private String BS;
        private int S;
        private int T;
        private int W;
        private int A;
        private int lD;
        private String Sv;
        private String base;
        private String wargear;
        private String opWargear;
        private int casts;
        private int denies;
        private String[] abilities;
        private String upgrades;
        private String[] factionKeywords;
        private String[] keywords;
        private String[] wargearLimit;
        private String[] spells;
        private String role;


        // Getter and setter for Name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        // Getter and setter for pointCost
        public int getPointCost() {
            return pointCost;
        }

        public void setPointCost(int pointCost) {
            this.pointCost = pointCost;
        }
        // Getter and setter for pointCost
        public int getCP() {
            return cP;
        }

        public void setCP(int cP) {
            this.cP = cP;
        }
        // Getter and setter for pL
        public int getPL() {
            return pL;
        }

        public void setPL(int pL) {
            this.pL = pL;
        }

        // Getter and setter for unitSize
        public int getUnitSize() {
            return unitSize;
        }

        public void setUnitSize(int unitSize) {
            this.unitSize = unitSize;
        }

        // Getter and setter for M
        public String getM() {
            return M;
        }

        public void setM(String M) {
            this.M = M;
        }

        // Getter and setter for WS
        public String getWS() {
            return WS;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        // Getter and setter for BS
        public String getBS() {
            return BS;
        }

        public void setBS(String BS) {
            this.BS = BS;
        }

        // Getter and setter for S
        public int getS() {
            return S;
        }

        public void setS(int S) {
            this.S = S;
        }

        // Getter and setter for T
        public int getT() {
            return T;
        }

        public void setT(int T) {
            this.T = T;
        }

        // Getter and setter for W
        public int getW() {
            return W;
        }

        public void setW(int W) {
            this.W = W;
        }

        // Getter and setter for A
        public int getA() {
            return A;
        }

        public void setA(int A) {
            this.A = A;
        }

        // Getter and setter for lD
        public int getLD() {
            return lD;
        }

        public void setLD(int lD) {
            this.lD = lD;
        }

        // Getter and setter for Sv
        public String getSv() {
            return Sv;
        }

        public void setSv(String Sv) {
            this.Sv = Sv;
        }

        // Getter and setter for base
        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        // Getter and setter for wargear
        public String getWargear() {
            return wargear;
        }
        public void setWargear(String wargear) {
            this.wargear = wargear;
        }
        // Getter and setter for opWargear
        public String getOpWargear() {
            return opWargear;
        }

        public void setOpWargear(String opWargear) {
            this.opWargear = opWargear;
        }

        // Getter and setter for casts
        public int getCasts() {
            return casts;
        }

        public void setCasts(int casts) {
            this.casts = casts;
        }

        // Getter and setter for denies
        public int getDenies() {
            return denies;
        }

        public void setDenies(int denies) {
            this.denies = denies;
        }

        // Getter and setter for abilities
        public String[] getAbilities() {
            return abilities;
        }

        public void setAbilities(String[] abilities) {
            this.abilities = abilities;
        }

        // Getter and setter for upgrades
        public String getUpgrades() {
            return upgrades;
        }

        public void setUpgrades(String upgrades) {
            this.upgrades = upgrades;
        }

        // Getter and setter for factionKeywords
        public String[] getFactionKeywords() {
            return factionKeywords;
        }

        public void setFactionKeywords(String[] factionKeywords) {
            this.factionKeywords = factionKeywords;
        }

        // Getter and setter for keywords
        public String[] getKeywords() {
            return keywords;
        }

        public void setKeywords(String[] keywords) {
            this.keywords = keywords;
        }

        // Getter and setter for wargearLimit
        public String[] getWargearLimit() {
            return wargearLimit;
        }

        public void setWargearLimit(String[] wargearLimit) {
            this.wargearLimit = wargearLimit;
        }

        // Getter and setter for spells
        public String[] getSpells() {
            return spells;
        }

        public void setSpells(String[] spells) {
            this.spells = spells;
        }

        public void setRole(String role) {
            this.role = role;
        }
        public String getRole(){
            return role;
        }
    }

}
