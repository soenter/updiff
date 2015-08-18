/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/14 16:01
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/14        Initailized
 */
package com.sand.updiff.common.utils;

import org.dom4j.Element;

import java.io.File;
import java.util.List;

/**
 *
 * @ClassName ：com.sand.updiff.common.utils.DomUtils
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/14 16:01
 * @version 1.0.0
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
