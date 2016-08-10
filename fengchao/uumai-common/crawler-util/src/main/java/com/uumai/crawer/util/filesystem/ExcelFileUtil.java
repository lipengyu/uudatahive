package com.uumai.crawer.util.filesystem;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rock on 7/8/15.
 */
public class ExcelFileUtil {
    Workbook wb;
    Sheet sheet;
    public String filename;
    int cellrowid=0;
    public ExcelFileUtil(String filename) throws IOException {
        this.filename=filename;
        File file=new File(filename);
        if(file.exists()){
            FileInputStream fsIP= new FileInputStream(file);
            this.wb = new HSSFWorkbook(fsIP);

            sheet = wb.getSheetAt(0); //Access the worksheet, so that we can update / modify it.


            this.cellrowid=sheet.getPhysicalNumberOfRows();
//            Iterator rowIter=sheet.rowIterator();
//            while(rowIter.hasNext()){
//              Row row= ((Row)rowIter.next());
//                if(row.getCell(0)!=null){
//                    cellrowid=cellrowid+1;
//                }else{
//                    break;
//                }
//            }
//            if(this.cellrowid>0)
//                cellrowid=cellrowid-1;

        }else{

            //创建excel工作簿
            this.wb = new HSSFWorkbook();
            //创建第一个sheet（页），命名为 new sheet
            this.sheet = wb.createSheet();
        }



    }

    public void writeLine(List<String> inputStr){
        //Row 行
        //Cell 方格
        // Row 和 Cell 都是从0开始计数的

        // 创建一行，在页sheet上
        Row row = sheet.createRow(cellrowid);
        cellrowid=cellrowid+1;
        // 在row行上创建一个方格
        for(int i=0;i<inputStr.size();i++){
            String s=inputStr.get(i);
            Cell cell = row.createCell(i);
             //设置方格的显示
            cell.setCellValue(s);
        }

    }

    public void writeLine(String... inputStr){

        //Row 行
        //Cell 方格
        // Row 和 Cell 都是从0开始计数的

        // 创建一行，在页sheet上
        Row row = sheet.createRow(cellrowid);
        cellrowid=cellrowid+1;
        // 在row行上创建一个方格
        for(int i=0;i<inputStr.length;i++){
            Cell cell = row.createCell(i);
            //设置方格的显示
            cell.setCellValue(inputStr[i]);

        }

    }
    public void createWorkBook() throws IOException {
        //创建一个文件 命名为workbook.xls
        FileOutputStream fileOut = new FileOutputStream(this.filename);

        // 把上面创建的工作簿输出到文件中
        wb.write(fileOut);
        //关闭输出流
        fileOut.close();
    }
    public static  void  main(String[] args){
        try {
            ExcelFileUtil util=new ExcelFileUtil("a.xls");
            util.writeLine("Column1","Column2","Column3");
            util.writeLine("1","2","3");
            util.createWorkBook();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
