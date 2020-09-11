package com.mexel.frmk.exception;

public class FunctionalException extends Exception {
	private static final long serialVersionUID = 1L;

	public FunctionalException(String tag, Throwable ex) {
		super(tag, ex);
	}

	public FunctionalException(String tag) {
		super(tag);
	}
}
