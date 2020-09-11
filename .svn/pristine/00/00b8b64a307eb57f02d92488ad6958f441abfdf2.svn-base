package com.mexel.frmk.core;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.mexel.frmk.exception.FunctionalException;





public class FileIterator {
	private String[] columns;
	private final Map<String, Integer> header = new HashMap<String, Integer>();
	private int columnCount = -1;

	private final String seperatorChar = ",";
	Pattern splitter = Pattern.compile(seperatorChar);
	private boolean eof = false;
	private FileReader reader;

	public FileIterator(File file) throws FunctionalException {
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(
					file)));
			init();
		} catch (Exception e) {
			throw new FunctionalException("File not Exists", e);
		}
	}

	public FileIterator(InputStream io) throws FunctionalException {
		reader = new CSVReader(new InputStreamReader(io));
		init();
	}

	public FileIterator(String fileName, InputStream io) throws FunctionalException {
		String type= StringUtils.substringAfterLast(fileName, ".");
		if("xls".equalsIgnoreCase(type)|| "xlsx".equalsIgnoreCase(type) ||"xlsx".equalsIgnoreCase(type) ){
			try {
				reader = new XLSReader(io);
			} catch (Exception e) {
				throw new FunctionalException("Error Reading File",e);
			}	
		}
		else{
			reader = new CSVReader(new InputStreamReader(io));	
		}
		
		init();
	}

	private void init() throws FunctionalException {
		if (next()) {
			for (int i = 0; i < columnCount; i++) {
				header.put(columns[i].toUpperCase().trim(), i);
			}
		} else {
			throw new FunctionalException("Empty File");
		}
	}

	public int size() throws FunctionalException {
		int count = 0;
		try {
			while (reader.readNext() != null) {
				count++;
			}
		} catch (Exception e) {
			throw new FunctionalException(getClass().getName(), e);
		} finally {
			close();
		}
		return count;
	}

	public boolean next() throws FunctionalException {
		if (eof) {
			return false;
		}
		try {
			columns = reader.readNext();

			if (columns != null) {
				if (columnCount == -1) {
					columnCount = columns.length;
					columns = CSVHelper.unEscape(columns);
				} else {
					columns = CSVHelper.unEscape(columns);
				}
				
				return true;
			} else {
				eof = true;
			}
			return false;
		} catch (Exception e) {
			throw new FunctionalException("Fail to Read file", e);
		}

	}
	
	public boolean isEmpty(){
		if(columns != null){
			for(String str:columns){
				if(StringUtils.isNotBlank(str)){
					return false;
				}
			}
		}
		return true;
	}

	public String get(int index) {
		if (columns != null) {
			String str = columns[index];
			if(str != null){
				str= str.replace("&nbsp;","");
			}
			return str != null ? str.trim() : str;
		}
		return null;
	}

//	public String get(String columnName) {
//		Integer index = header.get(columnName.toUpperCase());
//		if (index == null) {
//			return null;
//		}
//		return get(index);
//	}

	public String get(String... columnName) {
		for(String s:columnName ){
			Integer index = header.get(s.toUpperCase());
			if (index != null) {
				return get(index);
			}
		}
		return null;
	}

	public void close() {
		try {
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		;
	}
	
	public String[] getRecordWithError(String[] header, int seq, String error) {
		String[] record = new String[header.length];
		int counter = 0;
		for(String h:header){
			if(h.equalsIgnoreCase("seq")){
				record[counter] = ""+seq;
			}
			else if(h.equalsIgnoreCase("error")){
				record[counter] = ""+error;
			}
			else{
				String val = get(h);
				record[counter] = val;
			}
			counter++;
		}
		return record;
	}

	public List<String> getColumnNames() {
		Map<Integer,String> map = new HashMap<Integer,String>();
		for(Map.Entry<String,Integer> entry: header.entrySet()){
			map.put(entry.getValue(), entry.getKey());
		}
		List<String> lstHeader = new ArrayList<String>();
		for(int i=0;i<columnCount;i++){
			lstHeader.add(map.get(i).toUpperCase());
		}
		
		return new ArrayList<String>(lstHeader);
	}
}
