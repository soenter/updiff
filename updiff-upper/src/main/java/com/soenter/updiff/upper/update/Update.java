package com.soenter.updiff.upper.update;

import java.io.IOException;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName £ºcom.soenter.updiff.upper.doup.Update
 * @Description :
 * @Date : 2015/8/7 16:28
 */
public interface Update {

	void backup() throws IOException;

	void recovery() throws IOException;

	void execute() throws IOException;

	enum FileType{
		CLASS(".class"),
		JAR(".jar");

		String type;
		FileType(String type){
			this.type = type;
		}

		public String getType(){
			return type;
		}
	}
}
