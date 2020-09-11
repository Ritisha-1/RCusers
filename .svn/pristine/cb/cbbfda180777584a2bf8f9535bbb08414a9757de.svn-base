package com.mexel.frmk.pdf;

import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Body extends PdfContentList {

	private int mByteOffsetStart;
	private int mObjectNumberStart;
	private int mGeneratedObjectsCount;
	private ArrayList<IndirectObject> mObjectsList;
	private final int objectCount=0;
	private RandomAccessFile buffer;
	private final PDFContext pdfContext;
	private File tempFile;
	
	public Body(PDFContext pdfContext) {
		super();
		this.pdfContext = pdfContext;
		clear();
	}

	public int getObjectNumberStart() {
		return mObjectNumberStart;
	}

	public void setObjectNumberStart(int Value) {
		mObjectNumberStart = Value;
	}

	public int getByteOffsetStart() {
		return mByteOffsetStart;
	}

	public void setByteOffsetStart(int Value) {
		mByteOffsetStart = Value;
	}

	public int getObjectsCount() {
		return objectCount;
	}

	private int getNextAvailableObjectNumber() {
		return ++mGeneratedObjectsCount + mObjectNumberStart;
	}

	public IndirectObject getNewIndirectObject() {
		return getNewIndirectObject(getNextAvailableObjectNumber(), 0, true);
	}

	public IndirectObject getNewIndirectObject(int Number, int Generation,
			boolean InUse) {
		IndirectObject iobj = new IndirectObject();
		iobj.setNumberID(Number);
		iobj.setGeneration(Generation);
		iobj.setInUse(InUse);
		return iobj;
	}

	public IndirectObject getObjectByNumberID(int Number) {
		IndirectObject iobj;
		int x = 0;
		while (x < mObjectsList.size()) {
			iobj = mObjectsList.get(x);
			if (iobj.getNumberID() == Number)
				return iobj;
			x++;
		}
		return null;
	}
	private int newObjects = 0;

	public void includeIndirectObject(IndirectObject iobj) {
		mObjectsList.add(iobj);
		newObjects++;
	}
	
	int lastBufferedPos = 0;
	
	public void bufferIfRequired()throws Exception{
		
		if(mObjectsList.size()- lastBufferedPos <20){
			return ;
		}
		if(buffer==null){
			tempFile = new File(this.pdfContext.getBufferDir(),"temp_"+hashCode());
			buffer = new RandomAccessFile(tempFile,"rw");
		}
		System.out.println("Buffering "+newObjects);
		int x=lastBufferedPos;
		while (x < mObjectsList.size()) {
			IndirectObject iobj = getObjectByNumberID(++x);
			if(iobj.isSerializable() ){
				String s = "";
				s = iobj.getStreamContent() ;
				iobj.setPosition(buffer.getFilePointer());
				byte[] bytes = s.getBytes(this.pdfContext.getEncoding());
				iobj.setBufferLength(bytes.length);
				buffer.write(bytes);
				newObjects--;
				iobj.setStreamContent(null);
			}
		}
		lastBufferedPos = mObjectsList.size();
	}

	public long render(CrossReferenceTable crossRef, OutputStream io) throws Exception{
		int x = 0;
		int offset = mByteOffsetStart;
		
		while (x < mObjectsList.size()) {
			IndirectObject iobj = getObjectByNumberID(++x);
			 
			if(iobj.getBufferLength() >0){
				System.out.println("Reading buffer");
				buffer.seek(iobj.getPosition());
				byte[] bytes = new byte[iobj.getBufferLength()];
				buffer.read(bytes);
				iobj.setStreamContent(new String(bytes, this.pdfContext.getEncoding()));
			}
			
			String s = iobj.toPDFString() ;	
			
			iobj.setByteOffset(offset);
			 PDFDocument.writeToIo(io, s, this.pdfContext.getEncoding());
			 
//			offset++;
//			mList.add(s);
			
			offset += iobj.getLength()+1;
			crossRef.addObjectXRefInfo(iobj.getByteOffset(), iobj.getGeneration(), iobj.getInUse());
			iobj.clear();
		}
		if(buffer != null){
			buffer.close();
			tempFile.delete();
		}
		return offset-mByteOffsetStart;
//		String str = renderList();
//		mList.clear();
//		this.mByteOffsetStart = offset;
//		return str;
	}

	@Override
	public String toPDFString() {
		throw new RuntimeException();
	}

	@Override
	public void clear() {
		super.clear();
		mByteOffsetStart = 0;
		mObjectNumberStart = 0;
		mGeneratedObjectsCount = 0;
		mObjectsList = new ArrayList<IndirectObject>();
	}
}
