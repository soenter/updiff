package com.sand.updiff.mvnpligin;

import com.sand.updiff.common.DiffWriter;
import com.sand.updiff.common.FilterItem;
import org.apache.maven.plugin.MojoExecutionException;

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
	 * �����ʽ
	 *
	 * @parameter expression="${project.packaging}"
	 * @required
	 * @readonly
	 */
	private String packaging;

	public void execute () throws MojoExecutionException {

		if (!"jar".equals(packaging)) {
			getLog().info("������jar�Ĵ����ʽ");
			return;
		}
		if (skipIfEmpty && !getClassesDirectory().exists()) {
			getLog().info("Skipping packaging of the test-jar");
			return;
		}
		//���ø���
		super.execute();


		DiffWriter writer = null;
		try {

			writer = new DiffWriter(this.outputDirectory, finalName, "jar");

			String[] includes = getIncludes();

			for (String include : includes) {
				writer.addElement(new FilterItem(FilterItem.Type.INCLUDE, include));
			}
			String[] excludes = getExcludes();
			for (String exclude : excludes) {
				writer.addElement(new FilterItem(FilterItem.Type.EXCLUDE, exclude));
			}

			writer.write();
		} catch (Exception e) {
			throw new MojoExecutionException("����diff�ļ��쳣 ", e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}


	}
}
