package com.sand.updiff.upper.dom;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/13 14:29
 * @since 1.0.0
 *
 */
public class BackupItem  implements Item{

	private String fromPath;

	private String toPath;

	public BackupItem (String fromPath, String toPath) {
		this.fromPath = fromPath;
		this.toPath = toPath;
	}

	public String getFromPath () {
		return fromPath;
	}

	public String getToPath () {
		return toPath;
	}
}
