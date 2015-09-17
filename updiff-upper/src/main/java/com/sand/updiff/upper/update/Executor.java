package com.sand.updiff.upper.update;

import com.sand.updiff.upper.scan.Scanned;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/10 17:05
 * @since 1.0.0
 *
 */
public interface Executor {


	boolean backup(Scanned scanned);

	boolean execute(Scanned scanned);

	boolean recovery();

}
