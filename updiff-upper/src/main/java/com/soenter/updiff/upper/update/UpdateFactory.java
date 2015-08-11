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
package com.soenter.updiff.upper.update;

import com.soenter.updiff.common.FileType;
import com.soenter.updiff.upper.scan.Scaned;
import com.soenter.updiff.upper.update.impl.UpdateClassImpl;
import com.soenter.updiff.upper.update.impl.UpdateImpl;
import com.soenter.updiff.upper.update.impl.UpdateJarImpl;

import java.io.IOException;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.update.UpdateFactory
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
			return new UpdateJarImpl(scaned, backpath);
		} else if(newFileName.endsWith(FileType.CLASS.getType())){
			 return new UpdateClassImpl(scaned, backpath);
		 } else {
			return new UpdateImpl(scaned, backpath);
		}
	}
}
