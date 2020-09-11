package com.mexel.frmk.util;


public class TestUtils {
	private static String parseTime(String text){
		if (text == null) {
			return "1000";
		}

		text = text.trim();
		text = text.replace(":", "");
		text = StringUtils.leftPad(text, 4, "0");

		if(text.toUpperCase().contains("AM")){
			return text.replace("AM", "");
		}
		if(!text.toUpperCase().contains("PM")){
			return text;
		}
		

		Integer hh = CommonUtils.toInt(text.substring(0, 2));
		Integer mm = CommonUtils.toInt(text.substring(2));
		if(hh ==null || mm==null){
			return "1000";
		}
		if(hh>=12){
			return ""+hh+""+mm;
		}
		hh += 12;
		if(hh >=24){
			return "1000";	
		}
		return ""+hh+""+mm;
		
	}
	
	public static void main(String[] args){
		System.out.println(parseTime("0:12"));
	}
}
