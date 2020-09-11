//
//  Android PDF Writer
//  http://coderesearchlabs.com/androidpdfwriter
//
//  by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com)
//

package com.mexel.frmk.pdf;

import com.mexel.frmk.util.PDFHelper;





public class PDFWriter {

	private PDFDocument mDocument;

	public PDFWriter(PDFContext pdfContext) throws Exception {
		newDocument(pdfContext);
	}

	private void newDocument(PDFContext pdfContext) throws Exception {
		mDocument = new PDFDocument(pdfContext);
	}

	// public void newPage()throws Exception {
	// if(mPages.pageCount() > 0){
	// flush();
	// }
	// mCurrentPage = mPages.newPage();
	// mDocument.includeIndirectObject(mCurrentPage.getIndirectObject());
	// }

	public void newPage() throws Exception {
		mDocument.newPage();
	}

	// public void newPage()throws Exception {
	// if(mPages.pageCount() > 0){
	// flush();
	// }
	// mCurrentPage = mPages.newPage();
	// mDocument.includeIndirectObject(mCurrentPage.getIndirectObject());
	// }

	public void setFont(String subType, String baseFont, String encoding) {
		mDocument.getCurrentPage().setFont(subType, baseFont, encoding);
	}

	public void addRawContent(String rawContent) {
		mDocument.getCurrentPage().addRawContent(rawContent);
	}

	public void addText(int leftPosition, int topPositionFromBottom,
			int fontSize, String text) {
		if (text == null || text.trim().isEmpty()) {
			return;
		}
		addText(leftPosition, topPositionFromBottom, fontSize,
				PDFHelper.escape(text), Transformation.DEGREES_0_ROTATION);
	}

	public void addText(int leftPosition, int topPositionFromBottom,
			int fontSize, String text, String transformation) {
		mDocument.getCurrentPage().addText(leftPosition, topPositionFromBottom,
				fontSize, PDFHelper.escape(text), transformation);
	}

	public void addLine(int fromLeft, int fromBottom, int toLeft, int toBottom) {
		mDocument.getCurrentPage().addLine(fromLeft, fromBottom, toLeft,
				toBottom);
	}

	public void addRectangle(int fromLeft, int fromBottom, int toLeft,
			int toBottom) {
		mDocument.getCurrentPage().addRectangle(fromLeft, fromBottom, toLeft,
				toBottom);
	}

//	public void addImage(int fromLeft, int fromBottom, Bitmap bitmap) {
//		addImage(fromLeft, fromBottom, bitmap,
//				Transformation.DEGREES_0_ROTATION);
//	}
//
//	public void addImage(int fromLeft, int fromBottom, Bitmap bitmap,
//			String transformation) {
//		final XObjectImage xImage = new XObjectImage(mDocument, bitmap);
//		mDocument.getCurrentPage().addImage(fromLeft, fromBottom,
//				xImage.getWidth(), xImage.getHeight(), xImage, transformation);
//	}
//
//	public void addImage(int fromLeft, int fromBottom, int toLeft,
//			int toBottom, Bitmap bitmap) {
//		addImage(fromLeft, fromBottom, toLeft, toBottom, bitmap,
//				Transformation.DEGREES_0_ROTATION);
//	}
//
//	public void addImage(int fromLeft, int fromBottom, int toLeft,
//			int toBottom, Bitmap bitmap, String transformation) {
//		mDocument.getCurrentPage().addImage(fromLeft, fromBottom, toLeft,
//				toBottom, new XObjectImage(mDocument, bitmap), transformation);
//	}
//
//	public void addImageKeepRatio(int fromLeft, int fromBottom, int width,
//			int height, Bitmap bitmap) {
//		addImageKeepRatio(fromLeft, fromBottom, width, height, bitmap,
//				Transformation.DEGREES_0_ROTATION);
//	}
//
//	public void addImageKeepRatio(int fromLeft, int fromBottom, int width,
//			int height, Bitmap bitmap, String transformation) {
//		final XObjectImage xImage = new XObjectImage(mDocument, bitmap);
//		final float imgRatio = (float) xImage.getWidth()
//				/ (float) xImage.getHeight();
//		final float boxRatio = (float) width / (float) height;
//		float ratio;
//		if (imgRatio < boxRatio) {
//			ratio = (float) width / (float) xImage.getWidth();
//		} else {
//			ratio = (float) height / (float) xImage.getHeight();
//		}
//		width = (int) (xImage.getWidth() * ratio);
//		height = (int) (xImage.getHeight() * ratio);
//		mDocument.getCurrentPage().addImage(fromLeft, fromBottom, width,
//				height, xImage, transformation);
//	}

	// public String asString() {
	//
	// return mDocument.toPDFString();
	// }

	public void write() throws Exception {

		mDocument.write();

	}
}
