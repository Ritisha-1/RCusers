package com.mexel.frmk.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class SqlReader {

	
	public void read(InputStream xmlFile, DataBrokerService register ) throws Exception{
		SAXBuilder builder = new SAXBuilder();
	 
		  try {
	 
			Document document = builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("service");
	 
			for(Element node:list){
			   String service =  node.getAttributeValue("name");
			   String mapper = node.getChildText("mapper");
			   String sql = node.getChildText("sql");
			   register.register(service.toLowerCase(), sql,mapper);
			}
	 
		  } catch (IOException io) {
			 throw new ServiceException("Fail to Read Sql files ", io); 
		  } catch (JDOMException jdomex) {
			  throw new ServiceException("Fail to Read Sql files", jdomex);
		  }
		}
	
	public void printService(File xmlFile, DataBrokerService register)throws Exception{
		Map<String,SQLQueryInfo> sqls =  register.sqlService;
        Document doc = new Document();
        doc.setRootElement(new Element("sqls"));
		
		for(Map.Entry<String, SQLQueryInfo> entry:sqls.entrySet()){
			String service = entry.getKey();
			SQLQueryInfo q = entry.getValue();
			Element node = new Element("service");
			node.setAttribute("name",service.toLowerCase());
			node.addContent(new Element("sql").setText(q.getOrignalSql()));
			node.addContent(new Element("mapper").setText(q.getMapper()));
			doc.getRootElement().addContent(node);
		}
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        //output xml to console for debugging
        //xmlOutputter.output(doc, System.out);
        xmlOutputter.output(doc, new FileOutputStream(xmlFile));
		
	}
	
}
