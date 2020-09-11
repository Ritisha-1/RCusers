package com.mexel.frmk.pdf;

import java.util.ArrayList;
import java.util.List;

public class Page {

	private final PDFDocument mDocument;
	private final IndirectObject mIndirectObject;
	private final ArrayList<IndirectObject> mPageFonts;
//	private final ArrayList<XObjectImage> mXObjects;
	private final IndirectObject mPageContents;
	private final List<String> fonts = new ArrayList<String>();
	int currentFont; 
	
	
	
	public Page(PDFDocument document) {
		mDocument = document;
		mIndirectObject = mDocument.newIndirectObject();
		mPageFonts = new ArrayList<IndirectObject>();
//		mXObjects = new ArrayList<XObjectImage>();
		setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN,
				StandardFonts.WIN_ANSI_ENCODING);
		mPageContents = mDocument.newIndirectObject();
		mDocument.includeIndirectObject(mPageContents);
	}

	public IndirectObject getIndirectObject() {
		return mIndirectObject;
	}

	private String getFontReferences() {
		String result = "";
		if (!mPageFonts.isEmpty()) {
			result = "    /Font <<\n";
			int x = 0;
			for (IndirectObject lFont : mPageFonts) {
				result += "      /F" + Integer.toString(++x) + " "
						+ lFont.getIndirectReference() + "\n";
			}
			result += "    >>\n";
		}
		return result;
	}

	private String getXObjectReferences() {
		String result = "";
//		if (!mXObjects.isEmpty()) {
//			result = "    /XObject <<\n";
//			for (XObjectImage xObj : mXObjects) {
//				result += "      " + xObj.asXObjectReference() + "\n";
//			}
//			result += "    >>\n";
//		}
		return result;
	}

	public void render(String pagesIndirectReference) {
//		StringBuilder sb = new StringBuilder();
		mIndirectObject.setDictionaryContent("  /Type /Page\n  /Parent "
				+ pagesIndirectReference + "\n" + "  /Resources <<\n"
				+ getFontReferences() + getXObjectReferences() + "  >>\n"
				+ "  /Contents " + mPageContents.getIndirectReference() + "\n");
		
//		sb.append(mIndirectObject.toPDFString()).append("\n");
//		for(IndirectObject f:mPageFonts){
//			sb.append(f.toPDFString());
//		}
//		sb.append("\n");
//		sb.append(mPageContents.toPDFString());
//		return sb.toString();
	}

//	public void setFont(String subType, String baseFont) {
//		IndirectObject lFont = mDocument.newIndirectObject();
//		mDocument.includeIndirectObject(lFont);
//		lFont.setDictionaryContent("  /Type /Font\n  /Subtype /" + subType
//				+ "\n  /BaseFont /" + baseFont + "\n");
//		mPageFonts.add(lFont);
//	}

	public void setFont(String subType, String baseFont, String encoding) {
		currentFont =  fonts.indexOf(subType+"-"+baseFont);
		if(currentFont ==-1){
			IndirectObject lFont = mDocument.newIndirectObject();
			mDocument.includeIndirectObject(lFont);
			lFont.setDictionaryContent("  /Type /Font\n  /Subtype /" + subType
					+ "\n  /BaseFont /" + baseFont + "\n  /Encoding /" + encoding
					+ "\n");
			mPageFonts.add(lFont);

			fonts.add(subType+"-"+baseFont);
			currentFont = fonts.size();
		}
		else{
			currentFont = currentFont+1;
		}
	}

	private void addContent(String content) {
		mPageContents.addStreamContent(content);
		String streamContent = mPageContents.getStreamContent();
		mPageContents.setDictionaryContent("  /Length "
				+ Integer.toString(streamContent.length()) + "\n");
		mPageContents.setStreamContent(streamContent);
	}

	public void addRawContent(String rawContent) {
		addContent(rawContent);
	}

	public void addText(int leftPosition, int topPositionFromBottom,
			int fontSize, String text) {
		addText(leftPosition, topPositionFromBottom, fontSize, text,
				Transformation.DEGREES_0_ROTATION);
	}

	public void addText(int leftPosition, int topPositionFromBottom,
			int fontSize, String text, String transformation) {
		addContent("BT\n" + transformation + " "
				+ Integer.toString(leftPosition) + " "
				+ Integer.toString(topPositionFromBottom) + " Tm\n" + "/F"
				+ Integer.toString(currentFont) + " "
				+ Integer.toString(fontSize) + " Tf\n" + "(" + text + ") Tj\n"
				+ "ET\n");
	}

	public void addLine(int fromLeft, int fromBottom, int toLeft, int toBottom) {
		addContent(Integer.toString(fromLeft) + " "
				+ Integer.toString(fromBottom) + " m\n"
				+ Integer.toString(toLeft) + " " + Integer.toString(toBottom)
				+ " l\nS\n");
	}

	public void addRectangle(int fromLeft, int fromBottom, int toLeft,
			int toBottom) {
		addContent(Integer.toString(fromLeft) + " "
				+ Integer.toString(fromBottom) + " " + Integer.toString(toLeft)
				+ " " + Integer.toString(toBottom) + " re\nS\n");
	}

//	private String ensureXObjectImage(XObjectImage xObject) {
//		for (XObjectImage x : mXObjects) {
//			if (x.getId().equals(xObject.getId())) {
//				return x.getName();
//			}
//		}
//		mXObjects.add(xObject);
//		xObject.appendToDocument();
//		return xObject.getName();
//	}ss

//	public void addImage(int fromLeft, int fromBottom, int width, int height,
//			XObjectImage xImage, String transformation) {
//		final String name = ensureXObjectImage(xImage);
//		final String translate = "1 0 0 1 " + fromLeft + " " + fromBottom;
//		final String scale = "" + width + " 0 0 " + height + " 0 0";
//		final String rotate = transformation + " 0 0";
//		addContent("q\n" + translate + " cm\n" + rotate + " cm\n" + scale
//				+ " cm\n" + name + " Do\n" + "Q\n");
//	}
}
