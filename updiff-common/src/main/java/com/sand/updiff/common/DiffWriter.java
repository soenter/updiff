/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/3 18:36
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/3        Initailized
 */
package com.sand.updiff.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @ClassName ：com.sand.updiff.common.DiffWriter
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/3 18:36
 * @version 1.0.0
 *
 */
public class DiffWriter {

	private XMLWriter writer;

	private Document document;

	private Element rootElement;

	private Map<String, Element> groupMap = new HashMap<String, Element>();

	private List<DiffItem> otherElement = new LinkedList<DiffItem>();

	private Element filters;

	public DiffWriter(File savePath, String fileName, String packaging) throws IOException, DocumentException {
		OutputFormat format = OutputFormat.createPrettyPrint();

		File file = new File(savePath, fileName + FileType.DIFF.getType());

		if(file.exists()){
			SAXReader saxReader = new SAXReader();
			rootElement = document.getRootElement();
			document = saxReader.read(file);
		} else {
			document = DocumentFactory.getInstance().createDocument("utf-8");
			rootElement = document.addElement("diffs");
			rootElement.addAttribute("packaging", packaging);
		}
		writer = new XMLWriter(new FileWriter(file), format);
	}

	public void addElement(FilterItem item){

		if(filters == null){
			filters = rootElement.element("filters");
			if(filters == null){
				filters = rootElement.addElement("filters");
			}
		}

		if(FilterItem.Type.INCLUDE == item.getType()){
			Element include = filters.addElement("include");
			include.setText(item.getValue());
		}
		if(FilterItem.Type.EXCLUDE == item.getType()){
			Element include = filters.addElement("exclude");
			include.setText(item.getValue());
		}
	}
	public void addElement(DiffItem element){
		String newGroupName = element.getNewGroupName();
		String oldGroupName = element.getNewGroupName();
		if(newGroupName == null || "".equals(newGroupName)){
			element.setNewGroupName("other");
			otherElement.add(element);
			return;
		}

		Element group = groupMap.get(newGroupName);
		if(group == null){
			group = rootElement.addElement("group");
			group.addAttribute("name", newGroupName);
			groupMap.put(newGroupName, group);
		}

		Element node = group.addElement("file");

		node.addAttribute("change", element.getChangeName());
		String newPath = element.getNewPath();
		if(newPath != null && !"".equals(newPath)){
			node.addAttribute("path", newPath);
		}
		if(oldGroupName != null && !oldGroupName.equals(newGroupName)){
			node.addAttribute("oldGroup", oldGroupName);
		}
		String oldPath = element.getOldPath();
		if(oldPath != null && !oldPath.equals(newPath)){
			node.addAttribute("oldPath", oldPath);
		}
	}

	public void write() throws IOException {

		for (DiffItem element: otherElement){
			addElement(element);
		}

		writer.write(document);
	}



	/**
	 * 关闭
	 */
	public void close(){
		if(writer != null){
			try {
				writer.close();
			} catch (IOException e) {
				//忽略
			}
		}
	}

}
