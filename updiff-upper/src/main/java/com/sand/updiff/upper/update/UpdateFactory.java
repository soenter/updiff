/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/10 16:53
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/10        Initailized
 */
package com.sand.updiff.upper.update;

import com.sand.updiff.common.FileType;
import com.sand.updiff.upper.scan.Scaned;
import com.sand.updiff.upper.update.impl.ClassUpdate;
import com.sand.updiff.upper.update.impl.DefaultUpdate;
import com.sand.updiff.upper.update.impl.JarUpdate;

import java.io.IOException;

/**
 *
 * @ClassName ：com.sand.updiff.upper.update.UpdateFactory
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/10 16:53
 * @version 1.0.0
 *
 */
public class UpdateFactory {


	public static Update create(Scaned scaned, String backpath) throws IOException {
		String newFileName = scaned.getNewFile().getName();
		 if(scaned.isJar()){
			return new JarUpdate(scaned, backpath);
		} else if(newFileName.endsWith(FileType.CLASS.getType())){
			 return new ClassUpdate(scaned, backpath);
		 } else {
			return new DefaultUpdate(scaned, backpath);
		}
	}
}
