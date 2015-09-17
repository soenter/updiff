package com.sand.updiff.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/10 15:47
 * @since 1.0.0
 *
 */
public class DiffReader {

	private XMLReader reader;

	private Document document;

	private Element rootElement;


	public DiffReader (File diffFile) throws DocumentException {

		try {
			init(new FileInputStream(diffFile));
		} catch (FileNotFoundException e) {
			throw new DocumentException(e);
		}
	}
	public DiffReader (InputStream inputStream) throws DocumentException {
		init(inputStream);
	}

	private void init(InputStream in) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		document = saxReader.read(in);
		rootElement = document.getRootElement();
	}

	public List<DiffItem> readAll(){

		List<DiffItem> allEl = new ArrayList<DiffItem>();

		List<Element> group = rootElement.elements();

		String packaging = rootElement.attributeValue("packaging");
		String mainJavaGroup = rootElement.attributeValue("mainJavaGroup");
		String mainResourceGroups = rootElement.attributeValue("mainResourceGroups");

		for (Element g: group){
			List<Element> filesEl = g.elements();
			String gName = g.attributeValue("name");
			for (Element f: filesEl){
				boolean isExclued = Boolean.valueOf(f.elementText("isExcluded"));
				allEl.add(new DiffItem(
								gName,
								f.elementText("change"),
								isExclued,
								f.elementText("path"),
								packaging,
								mainJavaGroup,
								mainResourceGroups.split(",")
						)
				);
			}
		}

		return allEl;
	}

	public String getPackaging (){
		return rootElement.attributeValue("packaging");
	}

	public String getMainJavaGroup(){
		return rootElement.attributeValue("mainJavaGroup");
	}

	public String[] getMainResourceGroups(){
		return rootElement.attributeValue("mainResourceGroups").split(",");
	}
}
