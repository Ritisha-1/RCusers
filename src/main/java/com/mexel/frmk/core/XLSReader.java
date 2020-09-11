package com.mexel.frmk.core;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XLSReader implements FileReader {

	private final HSSFWorkbook workbook;

	private final HSSFSheet sheet;
	final Iterator<Row> rowIterator;
	public XLSReader(InputStream inputFile) throws Exception{
		workbook = new HSSFWorkbook(inputFile);
		sheet = workbook.getSheetAt(0);
		rowIterator = sheet.iterator();
	}

	@Override
	public String[] readNext() throws IOException {
		return rowIterator.hasNext() ? parseLine(rowIterator.next()) : null;
	}
	
	private String[] parseLine(Row row) throws IOException {
		Iterator<Cell> cellIterator = row.cellIterator();
		
		List<String> tokensOnThisLine = new ArrayList<String>();
		
		while (cellIterator.hasNext()) 
        {
			Cell  cell = cellIterator.next();
			tokensOnThisLine.add(cell.getStringCellValue());
        }
		return tokensOnThisLine.toArray(new String[tokensOnThisLine.size()]);

	}



	/**
	 * Closes the underlying reader.
	 * 
	 * @throws IOException
	 *             if the close fails
	 */
	@Override
	public void close() throws IOException {
		workbook.cloneSheet(0);
	}

}
