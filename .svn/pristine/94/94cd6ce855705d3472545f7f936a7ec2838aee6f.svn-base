package com.mexel.frmk.util;

public class AgeValue {
	private final String type;
	private final int value;
	

	public AgeValue(String type, int value) {
		super();
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getTypeStr() {
		if ("Y".equals(getType())) {
			return appendS(this.value, "Yr");
		}
		if ("M".equals(getType())) {
			return appendS(this.value, "Month");
		}
		if ("D".equals(getType())) {
			return appendS(this.value, "Day");
		}
		return  type;
	}

	public int getValue() {
		return value;
	}
	
	

	@Override
	public String toString() {
		return this.value + " "+ getTypeStr();
	}

	private static String appendS(int value, String text) {
		return value == 1 ? text : text + "s";
	}

}
