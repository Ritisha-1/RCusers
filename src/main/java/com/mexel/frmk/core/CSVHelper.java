package com.mexel.frmk.core;

import org.apache.commons.lang.StringUtils;

public class CSVHelper {
	public static String escape(String s) {
		if (s.contains(QUOTE)) {
			s = s.replace(QUOTE, ESCAPED_QUOTE);
		}

		if (StringUtils.indexOfAny(s, CHARACTERS_THAT_MUST_BE_QUOTED) > 0) {
			s = QUOTE + s + QUOTE;
		}

		return s;
	}

	public static String unEscape(String s) { 
		if (s == null) {
			return s;
		}
		if (s.startsWith(QUOTE) && s.endsWith(QUOTE)) {
			s = s.substring(1, s.length());

			if (s.contains(ESCAPED_QUOTE))
				s = s.replace(ESCAPED_QUOTE, QUOTE);
		}

		return s;
	}

	public static String[] unEscape(String[] strs) {
		String[] result = new String[strs.length];

		for (int i = 0; i < strs.length; i++) {
			result[i] = unEscape(strs[i]);
		}
		return result;
	}

	private static final String QUOTE = "\"";
	private static final String ESCAPED_QUOTE = "\"\"";
	private static final char[] CHARACTERS_THAT_MUST_BE_QUOTED = { ';', '"',
			'\n',',' };

}
