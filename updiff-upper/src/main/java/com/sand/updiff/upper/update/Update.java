package com.sand.updiff.upper.update;

import java.io.IOException;

/**
 * @author : sun.mt@sand.com.cn
 * @version 1.0.0
 * @ClassName ：com.sand.updiff.upper.doup.Update
 * @Description :
 * @Date : 2015/8/7 16:28
 */
public interface Update {

	void backup() throws IOException;

	void execute() throws IOException;

	void recovery() throws IOException;

}
