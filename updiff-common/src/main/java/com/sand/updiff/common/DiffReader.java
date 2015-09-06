/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/10 15:47
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/10        Initailized
 */
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
 * @ClassName ：com.sand.updiff.common.DiffReader
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 15:47
 * @version 1.0.0
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
								mainJavaGroup,
								mainResourceGroups.split(",")
						)
				);
			}
		}

		return allEl;
	}

}
