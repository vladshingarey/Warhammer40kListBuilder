package lbgui.factories;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DetachmentFactory {
    public static Map<String, MyObject> createObjectsFromDetachmentXlsx(File file, String sheetName) {
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
                                    case "name" -> object.setName(cell.getStringCellValue());
                                    case "required" -> object.setRequired((int) cell.getNumericCellValue());
                                    case "hq" -> object.setHq((int) cell.getNumericCellValue());
                                    case "troops" -> object.setTroops((int) cell.getNumericCellValue());
                                    case "elites" -> object.setElites((int) cell.getNumericCellValue());
                                    case "fast attack" -> object.setFastAttack((int) cell.getNumericCellValue());
                                    case "heavy support" -> object.setHeavySupport((int) cell.getNumericCellValue());
                                    case "flyer" -> object.setFlyer((int) cell.getNumericCellValue());
                                    case "transport" -> object.setTransport(cell.getStringCellValue());
                                    case "lord of war" -> object.setLordOfWar((int) cell.getNumericCellValue());
                                    case "fortification" -> object.setFortification((int) cell.getNumericCellValue());
                                    case "elite character" -> object.setEliteCharacter((int) cell.getNumericCellValue());
                                    case "command points" -> object.setCP((int) cell.getNumericCellValue());
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
        private int troops;
        private int elites;
        private int fastAttack;
        private int heavySupport;
        private int lordOfWar;
        private int hq;
        private int flyer;
        private int fortification;
        private int eliteCharacter;
        private int CP;

        // Getter and setter for Name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRequired(int required) {
        }

        // Getter and setter for Troops
        public int getTroops() {
            return troops;
        }

        public void setTroops(int troops) {
            this.troops = troops;
        }

        // Getter and setter for Elites
        public int getElites() {
            return elites;
        }

        public void setElites(int elites) {
            this.elites = elites;
        }

        // Getter and setter for Fast Attack
        public int getFastAttack() {
            return fastAttack;
        }

        public void setFastAttack(int fastAttack) {
            this.fastAttack = fastAttack;
        }

        // Getter and setter for Heavy Support
        public int getHeavySupport() {
            return heavySupport;
        }

        public void setHeavySupport(int heavySupport) {
            this.heavySupport = heavySupport;
        }

        // Getter and setter for Lord of War
        public int getLordOfWar() {
            return lordOfWar;
        }

        public void setLordOfWar(int lordOfWar) {
            this.lordOfWar = lordOfWar;
        }

        // Getter and setter for HQ
        public int getHq() {
            return hq;
        }

        public void setHq(int hq) {
            this.hq = hq;
        }

        // Getter and setter for Flyer
        public int getFlyer() {
            return flyer;
        }

        public void setFlyer(int flyer) {
            this.flyer = flyer;
        }

        // Getter and setter for Fortification
        public int getFortification() {
            return fortification;
        }

        public void setFortification(int fortification) {
            this.fortification = fortification;
        }

        // Getter and setter for Elite Character
        public int getEliteCharacter() {
            return eliteCharacter;
        }

        public void setEliteCharacter(int eliteCharacter) {
            this.eliteCharacter = eliteCharacter;
        }

        public void setTransport(String transport) {
        }

        // Getter and setter for CP
        public int getCP() { return CP; }

        public void setCP(int CP) {
            this.CP = CP;
        }

    }

}

