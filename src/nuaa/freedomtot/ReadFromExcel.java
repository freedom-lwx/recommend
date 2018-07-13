package nuaa.freedomtot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadFromExcel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  Map<String,Integer> map=readExcel("data.xls");
		System.out.println("end");
	}
	   public static Map<String,Integer> readExcel(String file) {
		   Map<String,Integer> map=new HashMap<>();
	        try {
	            // 创建输入流，读取Excel
	            InputStream is = new FileInputStream(file);
	            // jxl提供的Workbook类
	            Workbook wb = Workbook.getWorkbook(is);
	     
	            Sheet sheet = wb.getSheet(0);
	                // sheet.getRows()返回该页的总行数
	                for (int i = 2; i < sheet.getRows(); i++) {
	                    List innerList=new ArrayList();
	                    // sheet.getColumns()返回该页的总列数
	                    String name = sheet.getCell(1, i).getContents();
	                    int birth = Integer.parseInt(sheet.getCell(2, i).getContents());
	                    map.put(name, birth);
	                }
	                return map;
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (BiffException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
