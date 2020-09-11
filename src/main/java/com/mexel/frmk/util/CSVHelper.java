package com.mexel.frmk.util;

public class CSVHelper {
	public static String escape(String s) {
		if(s==null){
			return "";
		}
		if (s.contains(QUOTE)) {
			s = s.replace(QUOTE, ESCAPED_QUOTE);
		}

		if (indexOfAny(s, CHARACTERS_THAT_MUST_BE_QUOTED) > 0) {
			s = QUOTE + s + QUOTE;
		}

		return s;
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

	public static String[] unEscape(String[] strs) {
		String[] result = new String[strs.length];

		for (int i = 0; i < strs.length; i++) {
			result[i] = unEscape(strs[i]);
		}
		return result;
	}

	public static String unEscape(String s) {
		if (s == null) {
			return s;
		}
		if (s.startsWith(QUOTE) && s.endsWith(QUOTE)) {
			s = s.substring(1, s.length() - 2);

			if (s.contains(ESCAPED_QUOTE))
				s = s.replace(ESCAPED_QUOTE, QUOTE);
		}

		return s;
	}

	private static final String QUOTE = "\"";
	private static final String ESCAPED_QUOTE = "\"\"";
	private static final char[] CHARACTERS_THAT_MUST_BE_QUOTED = { ';', '"',
			'\n',',' };

}
