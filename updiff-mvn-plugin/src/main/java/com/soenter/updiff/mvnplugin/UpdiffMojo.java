package com.soenter.updiff.mvnplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 增量升级
 *
 * @goal updiff
 * @phase process-sources
 */
public class UpdiffMojo extends AbstractMojo {

	//工程项目根路径 + /.git = Git仓库路径
	private String projectRootPath;

	//Git旧版本号：SHA-1全称，或简称
	private String oldGitVersion;

	//Git新版本号：SHA-1全称，或简称
	private String newGitVersion = "HEAD";

	/**
	 * @parameter expression="${project.basedir}"
	 * @required
	 * @readonly
	 */
	private File baseDir;



	/**
	 * Location of the file.
	 *
	 * @parameter expression="${project.build.directory}"
	 * @required
	 * @readonly
	 */
	private File outputDirectory;

	public void execute () throws MojoExecutionException {
		File f = outputDirectory;

		if (!f.exists()) {
			f.mkdirs();
		}

		File updiff = new File(f, "updiff.txt");

		FileWriter w = null;
		try {
			w = new FileWriter(updiff);

			w.write("updiff.txt\n");
			w.write(baseDir.getAbsolutePath());
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + updiff, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}


}
