package com.sand.updiff.mvnplugin;

import com.sand.updiff.common.DiffItem;
import com.sand.updiff.common.DiffWriter;
import com.sand.updiff.common.GitRep;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
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
	 * @parameter expression="${oldGitVersion}"
	 * @required
	 *
	 */
	private String oldGitVersion;

	/**
	 * Git新版本号：SHA-1全称，或简称
	 * @parameter expression="${newGitVersion}" default-value="HEAD"
	 *
	 */
	private String newGitVersion;

	/**
	 *
	 * @parameter expression="${rootDir}" default-value="${session.executionRootDirectory}"
	 * @required
	 *
	 */
	private File rootDir;

	/**
	 * @parameter expression="${baseDir}" default-value="${project.basedir}"
	 * @required
	 */
	private File baseDir;


	/**
	 * Location of the file.
	 *
	 * @parameter expression="${outputDirectory}" default-value="${project.build.directory}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * Location of the file.
	 *
	 * @parameter expression="${classOutputDirectory}" default-value="${project.build.outputDirectory}"
	 * @required
	 */
	private File classOutputDirectory;

	/**
	 * 源文件路径.
	 *
	 * @parameter expression="${sourceDirectory}" default-value="${project.build.sourceDirectory}"
	 * @required
	 */
	private File sourceDirectory;

	/**
	 * 测试源文件路径.
	 *
	 * @parameter expression="${testSourceDirectory}" default-value="${project.build.testSourceDirectory}"
	 * @required
	 */
	private File testSourceDirectory;



	/**
	 * 脚本文件路径.
	 *
	 * @parameter expression="${scriptSourceDirectory}" default-value="${project.build.scriptSourceDirectory}"
	 * @required
	 */
	private File scriptSourceDirectory;

	/**
	 * 资源文件路径.
	 *
	 * @parameter expression="${mainResources}" default-value="${project.build.resources}"
	 * @required
	 */
	private Resource[] mainResources;


	/**
	 * 测试源文件路径.
	 *
	 * @parameter expression="${testResources}" default-value="${project.build.testResources}"
	 * @required
	 */
	private Resource[] testResources;

	/**
	 * 最终名称
	 *
	 * @parameter expression="${finalName}" default-value="${project.build.finalName}"
	 * @required
	 */
	private String finalName;

	/**
	 * 打包方式
	 *
	 * @parameter expression="${packaging}" default-value="${project.packaging}"
	 * @required
	 */
	private String packaging;

	/**
	 * 扩展资源
	 *
	 * @parameter expression="${extResources}"
	 * @required
	 */
	private Resource[] extResources;

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

			List<Resource> resources = new ArrayList<Resource>();
			Resource sourceDirectoryResource = new Resource();
			sourceDirectoryResource.setDirectory(sourceDirectory.getAbsolutePath());
			resources.add(sourceDirectoryResource);

			Resource scriptSourceDirectoryResource = new Resource();
			scriptSourceDirectoryResource.setDirectory(scriptSourceDirectory.getAbsolutePath());
			resources.add(scriptSourceDirectoryResource);

			Resource testSourceDirectoryResource = new Resource();
			testSourceDirectoryResource.setDirectory(testSourceDirectory.getAbsolutePath());
			resources.add(testSourceDirectoryResource);

			for (Resource resource: mainResources){
				resources.add(resource);
			}
			for (Resource resource: testResources){
				resources.add(resource);
			}

			if(extResources != null){
				for(Resource resource: extResources){
					resources.add(resource);
				}
			}

			List<DiffItem> diffs = gitRep.getDiffItems(resources);

			if(diffs != null && diffs.size() > 0){
				diffWriter = new DiffWriter(outputDif, finalName, packaging);
				for (DiffItem element: diffs){
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
