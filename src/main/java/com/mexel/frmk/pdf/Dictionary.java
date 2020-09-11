package com.mexel.frmk.pdf;

public class Dictionary extends EnclosedContent {

	public Dictionary() {
		super();
		setBeginKeyword("<<", false, true);
		setEndKeyword(">>", false, true);
	}

}
