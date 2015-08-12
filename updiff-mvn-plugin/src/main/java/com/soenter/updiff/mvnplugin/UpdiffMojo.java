package com.soenter.updiff.mvnplugin;

import com.soenter.updiff.common.DiffElement;
import com.soenter.updiff.common.DiffWriter;
import com.soenter.updiff.common.GitRep;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 增量升级
 *
 * @goal updiff
 * @phase process-sources
 */
public class UpdiffMojo extends AbstractMojo {

	/**
	 * Git旧版本号：SHA-1全称，或简称
	 * @parameter expression="${updiff.oldGitVersion}"
	 * @required
	 * @readonly
	 *
	 */
	private String oldGitVersion;

	/**
	 * Git新版本号：SHA-1全称，或简称
	 * @parameter expression="${updiff.newGitVersion}" efault-value="HEAD"
	 * @readonly
	 *
	 */
	private String newGitVersion;

	/**
	 *
	 * @parameter expression="${session.executionRootDirectory}"
	 * @required
	 * @readonly
	 *
	 */
	private File rootDir;

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

	/**
	 * Location of the file.
	 *
	 * @parameter expression="${project.build.outputDirectory}"
	 * @required
	 * @readonly
	 */
	private File classOutputDirectory;

	/**
	 * 源文件路径.
	 *
	 * @parameter expression="${project.build.sourceDirectory}"
	 * @required
	 * @readonly
	 */
	private File sourceDirectory;

	/**
	 * 测试源文件路径.
	 *
	 * @parameter expression="${project.build.testSourceDirectory}"
	 * @required
	 * @readonly
	 */
	private File testSourceDirectory;



	/**
	 * 脚本文件路径.
	 *
	 * @parameter expression="${project.build.scriptSourceDirectory}"
	 * @required
	 * @readonly
	 */
	private File scriptSourceDirectory;

	/**
	 * 资源文件路径.
	 *
	 * @parameter expression="${project.build.resources}"
	 * @required
	 * @readonly
	 */
	private Resource[] mainResources;


	/**
	 * 测试源文件路径.
	 *
	 * @parameter expression="${project.build.testResources}"
	 * @required
	 * @readonly
	 */
	private Resource[] testResources;

	/**
	 * 最终名称
	 *
	 * @parameter expression="${project.build.finalName}"
	 * @required
	 * @readonly
	 */
	private String finalName;

	/**
	 * 打包方式
	 *
	 * @parameter expression="${project.packaging}"
	 * @required
	 * @readonly
	 */
	private String packaging;

	public void execute () throws MojoExecutionException {

		if("pom".equals(packaging)){
			return;
		}

		File outputDif = new File(classOutputDirectory.getAbsolutePath() + File.separator + "META-INF");

		if (!outputDif.exists()) {
			outputDif.mkdirs();
		}

		DiffWriter diffWriter = null;
		try {
			GitRep gitRep = new GitRep(
					rootDir.getAbsolutePath(),
					oldGitVersion,
					newGitVersion,
					baseDir.getAbsolutePath());

			List<File> sourceStructFiles = new ArrayList<File>();
			sourceStructFiles.add(sourceDirectory);
			sourceStructFiles.add(scriptSourceDirectory);
			sourceStructFiles.add(testSourceDirectory);
			for (Resource resource: mainResources){
				sourceStructFiles.add(new File(resource.getDirectory()));
			}
			for (Resource resource: testResources){
				sourceStructFiles.add(new File(resource.getDirectory()));
			}

			List<DiffElement> diffs = gitRep.getDiffElements(sourceStructFiles);

			if(diffs != null && diffs.size() > 0){
				diffWriter = new DiffWriter(outputDif, finalName, packaging);
				for (DiffElement element: diffs){
					diffWriter.addElement(element);
				}
				diffWriter.write();
			}
		} catch (Exception e) {
			throw new MojoExecutionException("生成diff文件异常 ", e);
		} finally {
			if (diffWriter != null) {
				diffWriter.close();
			}
		}
	}


}
