package com.mexel.frmk.pdf;

public class IndirectObject extends Base {

	private final EnclosedContent mContent;
	private final Dictionary mDictionaryContent;
	private final Stream mStreamContent;
	private final IndirectIdentifier mID;
	private int mByteOffset;
	private boolean mInUse;
	private long length;
	private boolean serializable;
	
	private long position=0;
	private int bufferLength=0;
	
	

	public IndirectObject() {
		mID = new IndirectIdentifier();
		mByteOffset = 0;
		mInUse = false;
		mContent = new EnclosedContent();
		mContent.setBeginKeyword("obj", false, true);
		mContent.setEndKeyword("endobj", false, true);
		mDictionaryContent = new Dictionary();
		mStreamContent = new Stream();
	}

	public void setNumberID(int Value) {
		mID.setNumber(Value);
	}

	public int getNumberID() {
		return mID.getNumber();
	}

	public void setGeneration(int Value) {
		mID.setGeneration(Value);
	}

	public int getGeneration() {
		return mID.getGeneration();
	}

	public String getIndirectReference() {
		return mID.toPDFString() + " R";
	}

	public void setByteOffset(int Value) {
		mByteOffset = Value;
	}

	public int getByteOffset() {
		return mByteOffset;
	}

	public void setInUse(boolean Value) {
		mInUse = Value;
	}

	public boolean getInUse() {
		return mInUse;
	}

	public void addContent(String Value) {
		mContent.addContent(Value);
	}

	public void setContent(String Value) {
		mContent.setContent(Value);
	}

	public String getContent() {
		return mContent.getContent();
	}

	public void addDictionaryContent(String Value) {
		mDictionaryContent.addContent(Value);
	}

	public void setDictionaryContent(String Value) {
		mDictionaryContent.setContent(Value);
	}

	public String getDictionaryContent() {
		return mDictionaryContent.getContent();
	}

	public void addStreamContent(String Value) {
		mStreamContent.addContent(Value);
	}

	public void setStreamContent(String Value) {
		mStreamContent.setContent(Value);
	}

	public String getStreamContent() {
		return mStreamContent.getContent();
	}

	protected String render() {
		StringBuilder sb = new StringBuilder();
		sb.append(mID.toPDFString());
		sb.append(" ");
		// j-a-s-d: this can be performed in inherited classes DictionaryObject
		// and StreamObject
		if (mDictionaryContent.hasContent()) {
			mContent.setContent(mDictionaryContent.toPDFString());
			if (mStreamContent.hasContent())
				mContent.addContent(mStreamContent.toPDFString());
		}
		sb.append(mContent.toPDFString());
		return sb.toString();
	}
	
	public boolean hasContent(){
		return mDictionaryContent.hasContent() || mStreamContent.hasContent(); 
	}
	

	@Override
	public void clear() {
		mStreamContent.clear();
	}

	@Override
	public String toPDFString() {
		String str = render();
		this.length = str.length();
		return str+"\n";
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public boolean isSerializable() {
		return mStreamContent.hasContent();
	}


	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public int getBufferLength() {
		return bufferLength;
	}

	public void setBufferLength(int bufferLength) {
		this.bufferLength = bufferLength;
	}

}
