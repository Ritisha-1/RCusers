package com.mexel.frmk.util;

public class PDFHelper {
	private static final char[] CHARACTERS_THAT_MUST_BE_ESCAPED = { '(', ')','\\' };
	
	public static String escape(String str) {
		if (indexOfAny(str, CHARACTERS_THAT_MUST_BE_ESCAPED) > 0) {
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<str.length();i++){
				char c = str.charAt(i);
				if(appendEscape(str, c, i)){
					sb.append("\\");
				}
				sb.append(c);
			}
			return sb.toString();
		}
		return str;
	}
	
	private static boolean appendEscape(String str, char c, int pos){
		if(c == '(' || c==')' ){
			if(pos>0 && str.charAt(pos-1) == '\\'){
				return false;
			}
			return true;
		}
		else if(c=='\\' ){
			if( pos <str.length() &&  (str.charAt(pos+1) == '(' || str.charAt(pos+1) == ')' || str.charAt(pos+1) == '\\')){
				return false;
			}
			return true;
		}
		return false;
	}

	public static int indexOfAny(String str, char... strs) {
		for (char s : strs) {
			int index = str.indexOf(s);
			if (index > 0) {
				return index;
			}
		}
		return -1;
	}
	
	



}
