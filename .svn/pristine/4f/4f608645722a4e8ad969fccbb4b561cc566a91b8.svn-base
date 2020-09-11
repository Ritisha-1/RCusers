package com.mexel.frmk.pdf;

import java.io.IOException;
import java.io.OutputStream;

public class PDFDocument extends Base {

	private final Header mHeader;
	private final Body mBody;
	private final CrossReferenceTable mCRT;
	private final Trailer mTrailer;
	private final OutputStream io;
	private static final String encoding = "ISO-8859-1";
	private final PDFContext pdfContext;
	
	private final Pages mPages;
	private Page currentPage;
	int byteCount = 0;
	int objectCount = 0;
	
	public PDFDocument(PDFContext pdfContext ) {
		this.pdfContext = pdfContext;

		mHeader = new Header();
		mBody = new Body(pdfContext);
		mBody.setByteOffsetStart(mHeader.getPDFStringSize());
		mBody.setObjectNumberStart(0);
		mCRT = new CrossReferenceTable();
		mTrailer = new Trailer();
		this.io = pdfContext.getIo();
		this.mPages = new Pages(this, pdfContext.getPageWidth(), pdfContext.getPageHeight());
		
		currentPage = mPages.newPage();

	}
	

	public void newPage() throws Exception {
		mBody.bufferIfRequired();
		currentPage = mPages.newPage();
	}

	public IndirectObject newIndirectObject() {
		return mBody.getNewIndirectObject();
	}

	public void includeIndirectObject(IndirectObject iobj) {
		mBody.includeIndirectObject(iobj);
	}

	public static  int writeToIo(OutputStream io, String data, String encoding)
			throws IOException {
		if (data == null || data.isEmpty()) {
			return 0;
		}
		io.write(data.getBytes(encoding));
		return data.length();
	}
	
	public void addObjectXRefInfo(IndirectObject ob){
		mCRT.addObjectXRefInfo(ob.getByteOffset(),
				ob.getGeneration(), ob.getInUse());
		
	}
	
	public void write() throws Exception {
		mPages.render();
		int byteCount = 0;
		mCRT.setObjectNumberStart(mBody.getObjectNumberStart());
		
		byteCount += writeToIo(io, mHeader.toPDFString(), encoding);
		byteCount+= mBody.render(mCRT, io);
//		byteCount += writeToIo(io, mBody.render(mCRT), encoding);
		
		mTrailer.setObjectsCount(mBody.getObjectsCount());
		mTrailer.setCrossReferenceTableByteOffset(byteCount);
		mTrailer.setId(Indentifiers.generateId());
		writeToIo(io, mCRT.toPDFString(), encoding);
		writeToIo(io, mTrailer.toPDFString(), encoding);
		io.close();
	}	




	@Override
	public String toPDFString() {
		throw new RuntimeException();
	}

	@Override
	public void clear() {
		mHeader.clear();
		mBody.clear();
		mCRT.clear();
		mTrailer.clear();
	}

	public Page getCurrentPage() {
		return currentPage;
	}
	

}
