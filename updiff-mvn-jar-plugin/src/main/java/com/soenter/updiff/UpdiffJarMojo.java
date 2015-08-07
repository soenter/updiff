package com.soenter.updiff;

import com.soenter.updiff.common.DiffWriter;
import com.soenter.updiff.common.FilterElement;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 *
 * @goal jar
 * @phase package
 * @requiresProject
 * @threadSafe
 * @requiresDependencyResolution runtime
 */
public class UpdiffJarMojo extends JarMojo {
	/**
	 * 打包方式
	 *
	 * @parameter expression="${project.packaging}"
	 * @required
	 * @readonly
	 */
	private String packaging;

	public void execute () throws MojoExecutionException {

		if (!"jar".equals(packaging)) {
			getLog().info("跳过非jar的打包方式");
			return;
		}
		if (skipIfEmpty && !getClassesDirectory().exists()) {
			getLog().info("Skipping packaging of the test-jar");
			return;
		}
		//调用父类
		super.execute();


		DiffWriter writer = null;
		try {

			writer = new DiffWriter(this.outputDirectory, finalName, "jar");

			String[] includes = getIncludes();

			for (String include : includes) {
				writer.addElement(new FilterElement(FilterElement.Type.INCLUDE, include));
			}
			String[] excludes = getExcludes();
			for (String exclude : excludes) {
				writer.addElement(new FilterElement(FilterElement.Type.EXCLUDE, exclude));
			}

			writer.write();
		} catch (Exception e) {
			throw new MojoExecutionException("生成diff文件异常 ", e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}


	}
}
