package com.sand.updiff.upper.update;

import java.io.IOException;

/**
 * @author : sun.mt
 * @since 1.0.0
 * @date : 2015/8/7 16:28
 */
public interface Update {

	void backup() throws IOException;

	void execute() throws IOException;

	void recovery() throws IOException;

}
