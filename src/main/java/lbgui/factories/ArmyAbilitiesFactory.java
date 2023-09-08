package lbgui.factories;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArmyAbilitiesFactory{

    public static Map<String, MyObject> createObjectsFromArmyAbilitiesXlsx(File file, String sheetName) {

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
                    Cell nameCell = row.getCell(columnMap.get("info type"));
                    if (nameCell != null && nameCell.getStringCellValue() != null) {
                        MyObject object = new MyObject();
                        for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                            String columnName = entry.getKey();
                            int columnIndex = entry.getValue();
                            Cell cell = row.getCell(columnIndex);
                            if (cell != null) {
                                //String cellValue = formatter.formatCellValue(cell);
                                switch (columnName) {
                                    case "info type" -> object.setInfoType(cell.getStringCellValue());
                                    case "column a" -> object.setColumnA(cell.getStringCellValue());
                                    case "column b" -> object.setColumnB(cell.getStringCellValue());
                                    case "column c" -> object.setColumnC(cell.getStringCellValue());
                                    case "column d" -> object.setColumnD(cell.getStringCellValue());
                                    case "column e" -> object.setColumnE(cell.getStringCellValue());
                                    case "column f" -> object.setColumnF(cell.getStringCellValue());
                                    case "column g" -> object.setColumnG(cell.getStringCellValue());
                                    case "column h" -> object.setColumnH(cell.getStringCellValue());
                                    case "column i" -> object.setColumnI(cell.getStringCellValue());
                                    case "column j" -> object.setColumnJ(cell.getStringCellValue());
                                    case "column k" -> object.setColumnK(cell.getStringCellValue());
                                    case "column l" -> object.setColumnL(cell.getStringCellValue());
                                    case "column m" -> object.setColumnM(cell.getStringCellValue());
                                }
                            }
                        }
                        objectMap.put(object.getInfoType(), object);
                    }
                }
            }
        }
        return objectMap;
    }

    public static class MyObject {
        String infoType;
        String columnA;
        String columnB;
        String columnC;
        String columnD;
        String columnE;
        String columnF;
        String columnG;
        String columnH;
        String columnI;
        String columnJ;
        String columnK;
        String columnL;
        String columnM;
        public void setInfoType(String infoType) {
            this.infoType = infoType;
        }
        public String getInfoType(){
            return this.infoType;

        }
        public void setColumnA(String cA) {
            this.columnA = cA;
        }
        public String getColumnA(){
            return this.columnA;
        }
        public void setColumnB(String cB) {
            this.columnB = cB;
        }
        public String getColumnB(){
            return this.columnB;
        }
        public void setColumnC(String cC) {
            this.columnC = cC;
        }
        public String getColumnC(){
            return this.columnC;
        }
        public void setColumnD(String cD) {
            this.columnD = cD;
        }
        public String getColumnD(){
            return this.columnD;
        }
        public void setColumnE(String cE) {
            this.columnE = cE;
        }
        public String getColumnE(){
            return this.columnE;
        }
        public void setColumnF(String cF) {
            this.columnF = cF;
        }
        public String getColumnF(){
            return this.columnF;
        }
        public void setColumnG(String cG) {
            this.columnG = cG;
        }
        public void setColumnH(String cH) {
            this.columnH = cH;
        }
        public void setColumnI(String cI) {
            this.columnI = cI;
        }
        public void setColumnJ(String cJ) {
            this.columnJ = cJ;
        }
        public void setColumnK(String cK) {
            this.columnK = cK;
        }
        public void setColumnL(String cL) {
            this.columnL = cL;
        }
        public void setColumnM(String cM) {
            this.columnM = cM;
        }
    }

}