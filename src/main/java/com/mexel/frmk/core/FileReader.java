package com.mexel.frmk.core;

import java.io.IOException;

public interface FileReader {
	
	public String[] readNext() throws IOException ;
	
	public void close() throws IOException ;
}
