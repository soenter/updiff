package com.sand.updiff.mvnplugin;

import com.sand.updiff.common.DiffItem;
import com.sand.updiff.common.DiffWriter;
import com.sand.updiff.common.GitRep;
import com.sand.updiff.common.utils.FilePathUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

		File outputDif = null;
		if("jar".equals(packaging)){
			outputDif = new File(classOutputDirectory,  "META-INF");
		} else if("war".equals(packaging)){
			outputDif = new File(outputDirectory.getAbsoluteFile() + File.separator + finalName,  "META-INF");
		} else {
			getLog().warn("updiff 不支持的 packaging : " + packaging);
			return;
		}

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

			String[] mainResourceGroup = new String[mainResources.length];
			int index = 0;
			for (Resource resource: mainResources){
				resources.add(resource);
				mainResourceGroup[index ++] = FilePathUtils.getDiffPrefixPath(baseDir.getAbsolutePath(), resource.getDirectory());
			}

			if(extResources != null){
				for(Resource resource: extResources){
					resources.add(resource);
				}
			}

			List<DiffItem> diffs = gitRep.getDiffItems(resources);

			if(diffs != null && diffs.size() > 0){
				String mainJavaGroup = FilePathUtils.getDiffPrefixPath(baseDir.getAbsolutePath(), sourceDirectory.getAbsolutePath());
				diffWriter = new DiffWriter(outputDif, finalName, packaging, mainJavaGroup, mainResourceGroup);
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
