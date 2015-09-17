package com.sand.updiff.upper.dom;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.FileType;
import org.dom4j.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/14 15:49
 * @since 1.0.0
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
