package com.sand.updiff.common.utils;

import org.dom4j.Element;

import java.io.File;
import java.util.List;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/14 16:01
 * @since 1.0.0
 *
 */
public class DomUtils {

	public static void setElementText(Element el, String elementText){
		if(elementText == null){
			el.setText("");
		} else {
			el.setText(elementText);
		}
	}

}
