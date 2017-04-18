package org.yuantai.common.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yuantai.common.util.DateUtil;
import org.yuantai.school.pojo.Student;

/**
 * 利用开源组件POI动态导出EXCEL文档
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel {

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * @param title 表格标题名
	 * @param headers 表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public static void exportExcel(String title, String[] headers, Collection<Student> dataset, OutputStream out) {
/*		XSSFWorkbook xWorkbook = null;			 
  	  is = new FileInputStream(uploadfile);
  	  xWorkbook = new XSSFWorkbook(is);
    //第一个工作表
  	  XSSFSheet xSheet = xWorkbook.getSheetAt(0);*/
		
		
  	XSSFWorkbook workbook = new XSSFWorkbook();		// 声明一个工作薄
		XSSFSheet sheet = workbook.createSheet(title);	// 生成一个表格
		sheet.setDefaultColumnWidth((short) 15);		// 设置表格默认列宽度为15个字节
		sheet.setDefaultRowHeightInPoints(25.0f);
		
		buildTitle(workbook,sheet,title,headers.length);
		buildHeader(workbook, sheet, headers);
		buildBody(workbook, sheet, dataset);
		
		try {
			workbook.write(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void buildTitle(XSSFWorkbook workbook, XSSFSheet sheet, String title,int colspan) {
		
		// 生成标题单元格样式
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 18);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(57.0f);
		XSSFCell cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		XSSFRichTextString text = new XSSFRichTextString(title);
		cell.setCellValue(text);
		
		for(int i=1;i<=colspan;i++) {
			XSSFCell _cell = row.createCell(i);
		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colspan-1));
		//sheet.addMergedRegion(new Region(0,(short)0,0,(short)6)); 
	}

	private static void buildHeader(XSSFWorkbook workbook, XSSFSheet sheet, String[] headers) {
		
		// 生成列表头单元格样式
		XSSFCellStyle headerStyle = workbook.createCellStyle();
//		headerStyle.setFillForegroundColor(XSSFColor.SKY_BLUE.index);
//		headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		//font.setColor(XSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font);
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(1);
		row.setHeightInPoints(25.0f);
		for (short i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		int unit=300;
		sheet.setColumnWidth(0, unit*18);
		sheet.setColumnWidth(1, unit*12);
		sheet.setColumnWidth(2, unit*12);
		sheet.setColumnWidth(3, unit*12);
		sheet.setColumnWidth(4, unit*12);
		sheet.setColumnWidth(5, unit*12);
		sheet.setColumnWidth(6, unit*12);
		sheet.setColumnWidth(7, unit*12);
		sheet.setColumnWidth(8, unit*12);
		sheet.setColumnWidth(9, unit*12);
		sheet.setColumnWidth(10, unit*12);
		sheet.setColumnWidth(11, unit*12);
		sheet.setColumnWidth(12, unit*12);
	}

	private static void buildBody(XSSFWorkbook workbook, XSSFSheet sheet, Collection<Student> dataset) {
		
		// 生成列表体单位格样式
		XSSFCellStyle cellStyle = workbook.createCellStyle();
//		cellStyle.setFillForegroundColor(XSSFColor.LIGHT_YELLOW.index);
//		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		
		XSSFFont font = workbook.createFont();
		//font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 10);
		cellStyle.setFont(font);
		
/*		XSSFCellStyle decmalStyle = workbook.createCellStyle();
		decmalStyle.cloneStyleFrom(cellStyle);
		XSSFDataFormat format = workbook.createDataFormat();
		decmalStyle.setDataFormat(format.getFormat("#,###.00"));*/
		
		XSSFCellStyle dateStyle = workbook.createCellStyle();
		dateStyle.cloneStyleFrom(cellStyle);
		XSSFDataFormat format = workbook.createDataFormat();
		dateStyle.setDataFormat(format.getFormat("@"));//设为文本类型
		
		// 遍历集合数据，产生数据行
		if(dataset!=null){
			
		Iterator<Student> it = dataset.iterator();
		for (int index = 2;it.hasNext();index++) {
			
			Student p = it.next();
			XSSFRow row = sheet.createRow(index);
			row.setHeightInPoints(25.0f);
			
			XSSFCell c0 = row.createCell(0);
			c0.setCellStyle(cellStyle);
			c0.setCellValue(p.getIdcard());
			
			XSSFCell c1 = row.createCell(1);
			c1.setCellStyle(cellStyle);
			c1.setCellValue(p.getStudnetName()+"");
			
			XSSFCell c2 = row.createCell(2);
			c2.setCellStyle(cellStyle);
			c2.setCellValue(p.getClassCode());
			
			XSSFCell c3 = row.createCell(3);
			c3.setCellStyle(cellStyle);
			c3.setCellValue(p.getStudentNo());
			
			XSSFCell c4 = row.createCell(4);
			c4.setCellStyle(cellStyle);
			c4.setCellValue("");
			
			XSSFCell c5 = row.createCell(5);
			c5.setCellStyle(cellStyle);
			c5.setCellValue("");
			
			XSSFCell c6 = row.createCell(6);
			c6.setCellStyle(cellStyle);
			c6.setCellValue("");
			
			XSSFCell c7 = row.createCell(7);
			c7.setCellStyle(cellStyle);
			c7.setCellValue("");
			
			XSSFCell c8 = row.createCell(8);
			c8.setCellStyle(cellStyle);
			c8.setCellValue("");
			
			XSSFCell c9 = row.createCell(9);
			c9.setCellStyle(cellStyle);
			c9.setCellValue("");
			
			XSSFCell c10 = row.createCell(10);
			c10.setCellStyle(cellStyle);
			c10.setCellValue("");
			
			XSSFCell c11 = row.createCell(11);
			c11.setCellStyle(dateStyle);
			c11.setCellValue(DateUtil.format(new Date(), "yyyy-MM-dd"));
			
			XSSFCell c12 = row.createCell(12);
			c12.setCellStyle(cellStyle);
			c12.setCellValue("");
		}
		}
	}
}