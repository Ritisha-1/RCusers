package com.mexel.frmk.pdf;

public class ContentArray extends EnclosedContent {

	public ContentArray() {
		super();
		setBeginKeyword("[ ", false, false);
		setEndKeyword("]", false, false);
	}

	public void addItem(String s) {
		addContent(s);
		addSpace();
	}

	public void addItemsFromStringArray(String[] content) {
		for (String s : content) {
			addItem(s);
		}
	}
}
