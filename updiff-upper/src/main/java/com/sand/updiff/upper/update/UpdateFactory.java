package com.sand.updiff.upper.update;

import com.sand.updiff.common.FileType;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.update.impl.ClassUpdate;
import com.sand.updiff.upper.update.impl.DefaultUpdate;
import com.sand.updiff.upper.update.impl.JarUpdate;

import java.io.IOException;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/10 16:53
 * @since 1.0.0
 *
 */
public class UpdateFactory {


	public static Update create(Scanned scanned, String backpath) throws IOException {
		 if(scanned.isJar()){
			return new JarUpdate(scanned, backpath);
		} else if(scanned.getNewFile() != null && scanned.getNewFile().getName().endsWith(FileType.CLASS.getType())){
			 return new ClassUpdate(scanned, backpath);
		 } else {
			return new DefaultUpdate(scanned, backpath);
		}
	}
}
