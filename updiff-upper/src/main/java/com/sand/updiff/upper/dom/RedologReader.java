/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/14 15:49
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/14        Initailized
 */
package com.sand.updiff.upper.dom;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.FileType;
import org.dom4j.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @ClassName ：com.sand.updiff.upper.dom.RedologReader
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/14 15:49
 * @version 1.0.0
 *
 */
public class RedologReader extends AbstractReader {

	public RedologReader (String backupDir) throws IOException {
		super(backupDir, FileType.REDOLOG);
	}

	@Override
	public List<RedologItem> readAll () {
		List<RedologItem> allEl = new LinkedList<RedologItem>();

		List<Element> els = rootElement.elements();

		for (Element e: els){
			allEl.add(new RedologItem(
					"true".equals(e.elementText("isDir")),
					ChangeType.get(e.elementText("change")),
					e.elementText("from"),
					e.elementText("to"),
					e.elementText("backup")
			));
		}
		return allEl;
	}

}
