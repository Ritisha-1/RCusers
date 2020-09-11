package com.mexel.frmk.pdf;


public class Pages {

	private final PDFDocument mDocument;
//	private final ArrayList<Page> mPageList;
	private final IndirectObject mIndirectObject;
	private final ContentArray mMediaBox;
	private final ContentArray mKids;
	StringBuilder pageContent = new StringBuilder();
	private Page currentPage;
	private int pageCount=0;
	private final IndirectObject mCatalog;

	public Pages(PDFDocument document, int pageWidth, int pageHeight) {
		mCatalog = document.newIndirectObject();
		document.includeIndirectObject(mCatalog);

		mDocument = document;
		mIndirectObject = mDocument.newIndirectObject();
		document.includeIndirectObject(mIndirectObject);
//		mPageList = new ArrayList<Page>();
		mMediaBox = new ContentArray();
		String content[] = { "0", "0", Integer.toString(pageWidth),
				Integer.toString(pageHeight) };
		mMediaBox.addItemsFromStringArray(content);
		mKids = new ContentArray();
		renderCatalog();
	}
	
	private void renderCatalog() {
		mCatalog.setDictionaryContent("  /Type /Catalog\n  /Pages "
				+ mIndirectObject.getIndirectReference() + "\n");
	}

	public Page newPage() {
		if(currentPage != null){
			currentPage.render(mIndirectObject.getIndirectReference());
		}
		
		pageCount++;
		currentPage = new Page(mDocument);
		mDocument.includeIndirectObject(currentPage.getIndirectObject());
//		mPageList.add(currentPage);
		mKids.addItem(currentPage.getIndirectObject().getIndirectReference());
		return currentPage;
	}
	
	public void render() {
		currentPage.render(mIndirectObject.getIndirectReference());
		mIndirectObject.setDictionaryContent("  /Type /Pages\n"
				+ "  /MediaBox " + mMediaBox.toPDFString() + "\n" + "  /Count "
				+ Integer.toString(pageCount) + "\n" + "  /Kids "
				+ mKids.toPDFString() + "\n");
		
	}


	public int pageCount(){
		return pageCount;
	}
}
