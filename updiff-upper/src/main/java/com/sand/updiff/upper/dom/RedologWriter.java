package com.sand.updiff.upper.dom;

import com.sand.updiff.common.FileType;
import com.sand.updiff.common.utils.DomUtils;
import org.dom4j.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/14 15:21
 * @since 1.0.0
 *
 */
public class RedologWriter extends AbstractWriter{

	private static final Map<String, RedologWriter> writerMap = new HashMap<String, RedologWriter>();

	private RedologWriter (String backupDir) throws IOException {
		super(backupDir, FileType.REDOLOG,  "redolog");
	}

	public static synchronized RedologWriter getInstance(String backupDir) throws IOException {
		if(writerMap.containsKey(backupDir)){
			return writerMap.get(backupDir);
		} else {
			RedologWriter redologWriter = new RedologWriter(backupDir);
			writerMap.put(backupDir, redologWriter);
			return redologWriter;
		}
	}

	@Override
	public void addItem (Item item) {
		RedologItem redologItem = (RedologItem)item;
		Element log = rootElement.addElement("log");

		//
		DomUtils.setElementText(log.addElement("isDir"), redologItem.isDir() ? "true" : "false");
		DomUtils.setElementText(log.addElement("change"), redologItem.getChange().name());
		DomUtils.setElementText(log.addElement("from"), redologItem.getFromPath());
		DomUtils.setElementText(log.addElement("to"), redologItem.getToPath());
		DomUtils.setElementText(log.addElement("backup"), redologItem.getBackupPath());
	}

}

