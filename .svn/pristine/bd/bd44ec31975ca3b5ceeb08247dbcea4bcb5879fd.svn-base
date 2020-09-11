package com.mexel.frmk.pdf;

import java.io.File;
import java.io.OutputStream;

public class PDFContext {
	private final File bufferDir;
	private final OutputStream io;
	private boolean fileStream;
	private  final String encoding = "ISO-8859-1";
	private final int pageWidth;
	private final int pageHeight;
	

	public PDFContext(File bufferDir, OutputStream io, int width, int height) {
		this.bufferDir = bufferDir;
		this.io = io;
		this.pageHeight = height;
		this.pageWidth = width;

	}

	public File getBufferDir() {
		return bufferDir;
	}

	public OutputStream getIo() {
		return this.io;
	}

	public boolean isFileStream() {
		return fileStream;
	}

	public void setFileStream(boolean fileStream) {
		this.fileStream = fileStream;
	}

	public String getEncoding() {
		return encoding;
	}

	public int getPageWidth() {
		return pageWidth;
	}

	public int getPageHeight() {
		return pageHeight;
	}

}
