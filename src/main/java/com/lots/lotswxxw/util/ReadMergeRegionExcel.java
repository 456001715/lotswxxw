package com.lots.lotswxxw.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 用于拥有合并行的excel导入
 *
 * @author lots
 *
 * @date   2013-6-21
 */
public class ReadMergeRegionExcel {



    /**
     * 读取excel数据
     * @param is
     */
    public void readExcelToObj(InputStream is) {

        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
            readExcel(wb, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 定制 读取excel文件 返回list
     * @param  is excel文件
     * @param  tableType 表单类型
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine 去除最后读取的行
     */
//    public List<SelfcheckTwo> getList(InputStream is, int tableType, int sheetIndex, int startReadLine, int tailLine) {
//        Workbook wb = null;
//        try {
//            wb = WorkbookFactory.create(is);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Sheet sheet = wb.getSheetAt(sheetIndex);
//        Row row = null;
//        List<SelfcheckTwo> list=new ArrayList<SelfcheckTwo>();
//        for(int i=startReadLine; i<sheet.getLastRowNum()-tailLine+1; i++) {
//            row = sheet.getRow(i);
//            int index = 0; //循环下标
//            SelfcheckTwo two=new SelfcheckTwo();
//            for(Cell c : row) {
//                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
////判断是否具有合并单元格
//                if(isMerge) {
//                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
//                    switch(index){
//                        case 1:
//                            two.setSortCode(Integer.parseInt(rs));index++;continue;
//                        case 2:
//                            two.setCheckLimits(rs);index++;continue;
//                        case 3:
//                            two.setCheckContent(rs);index++;continue;
//                        case 4:
//                            two.setCheckItem(rs);index++;continue;
//                        case 5:
//                            two.setCheckKey(rs);index++;continue;
//                        case 6:
//                            two.setCheckResult(rs);index++;continue;
//                        case 7:
//                            two.setInputQuestion(rs);index++;continue;
//                        case 8:
//                            two.setInputAnswer(rs);index++;continue;
//                        case 9:
//                            two.setRemarks(rs);index++;continue;
//                    }
//
//                }else {
//                    switch(index){
//                        case 1:
//                            two.setSortCode(Integer.parseInt(c.getRichStringCellValue().getString()));index++;continue;
//                        case 2:
//                            two.setCheckLimits(c.getRichStringCellValue().getString());index++;continue;
//                        case 3:
//                            two.setCheckContent(c.getRichStringCellValue().getString());index++;continue;
//                        case 4:
//                            two.setCheckItem(c.getRichStringCellValue().getString());index++;continue;
//                        case 5:
//                            two.setCheckKey(c.getRichStringCellValue().getString());index++;continue;
//                        case 6:
//                            two.setCheckResult(c.getRichStringCellValue().getString());index++;continue;
//                        case 7:
//                            two.setInputQuestion(c.getRichStringCellValue().getString());index++;continue;
//                        case 8:
//                            two.setInputAnswer(c.getRichStringCellValue().getString());index++;continue;
//                        case 9:
//                            two.setRemarks(c.getRichStringCellValue().getString());index++;continue;
//                    }
//
//                }
//
//                index+=1;
//            }
//            two.setTableType(tableType);
//            list.add(two);
//        }
//        return list;
//    }
    /**
     * 读取excel文件
     * @param wb
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine 去除最后读取的行
     */
    private void readExcel(Workbook wb,int sheetIndex, int startReadLine, int tailLine) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        for(int i=startReadLine; i<sheet.getLastRowNum()-tailLine+1; i++) {
            row = sheet.getRow(i);
            for(Cell c : row) {
                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
//判断是否具有合并单元格
                if(isMerge) {
                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
                    System.out.print(rs + ""+"---------i---------");
                }else {
                    System.out.print(c.getRichStringCellValue()+"---------i---------");
                }
            }
            System.out.println();
        }
    }
    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }
    /**
     * 判断合并了行
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private boolean isMergedRow(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断sheet页中是否含有合并单元格
     * @param sheet
     * @return
     */
    private boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0 ? true : false;
    }

    /**
     * 合并单元格
     * @param sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    private void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell){

        if(cell == null) return "";

        if(cell.getCellType() == Cell.CELL_TYPE_STRING){

            return cell.getStringCellValue();

        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){

            return String.valueOf(cell.getBooleanCellValue());

        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){

            return cell.getCellFormula() ;

        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }
}