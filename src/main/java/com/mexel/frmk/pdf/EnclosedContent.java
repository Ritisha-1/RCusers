package com.mexel.frmk.pdf;

public class EnclosedContent extends Base {

	private String mBegin;
	private String mEnd;
	protected final StringBuilder mContent;

	public EnclosedContent() {
		mContent = new StringBuilder();
	}

	public void setBeginKeyword(String Value, boolean NewLineBefore,
			boolean NewLineAfter) {
		if (NewLineBefore)
			mBegin = "\n" + Value;
		else
			mBegin = Value;
		if (NewLineAfter)
			mBegin += "\n";
	}

	public void setEndKeyword(String Value, boolean NewLineBefore,
			boolean NewLineAfter) {
		if (NewLineBefore)
			mEnd = "\n" + Value;
		else
			mEnd = Value;
		if (NewLineAfter)
			mEnd += "\n";
	}

	public boolean hasContent() {
		return mContent.length() > 0;
	}

	public void setContent(String Value) {
		clear();
		if(Value != null){
			mContent.append(Value);	
		}
		
	}

	public String getContent() {
		return mContent.toString();
	}

	public void addContent(String Value) {
		mContent.append(Value);
	}

	public void addNewLine() {
		mContent.append("\n");
	}

	public void addSpace() {
		mContent.append(" ");
	}

	@Override
	public void clear() {
		mContent.setLength(0);
	}

	@Override
	public String toPDFString() {
		return mBegin + mContent.toString() + mEnd;
	}

}
