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
import java.util.List;

/**
 * 增量升级
 *
 * @goal updiff
 * @phase process-sources
 */
public class UpdiffMojo extends AbstractMojo {

	/**
	 * Git 仓库的版本可以为: commit短sha-1版本或Tag<br/>
	 * SVN 仓库只能为提交版本
	 * @parameter expression="${oldVersion}"
	 * @required
	 *
	 */
	private String oldVersion;

	/**
	 * Git 仓库的版本可以为: commit短sha-1版本或Tag<br/>
	 * SVN 仓库只能为提交版本<br/>
	 * 默认值为HEAD
	 * @parameter expression="${newVersion}" default-value="HEAD"
	 *
	 */
	private String newVersion;

	/**
	 * 默认值为 false
	 * @parameter expression="${updiff.disabled}" default-value="false"
	 *
	 */
	private boolean disabled;

	/**
	 * 项目根目录，默认取${session.executionRootDirectory}
	 *
	 * @parameter expression="${projectDir}" default-value="${session.executionRootDirectory}"
	 * @required
	 *
	 */
	private File projectDir;

	/**
	 * 模块路径，默认取${project.basedir}
	 * @parameter expression="${moduleDir}" default-value="${project.basedir}"
	 * @required
	 */
	private File moduleDir;


	/**
	 * 输出目录，默认取${project.build.directory}
	 *
	 * @parameter expression="${outputDirectory}" default-value="${project.build.directory}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * class 文件输出目录
	 *
	 * @parameter expression="${classOutputDirectory}" default-value="${project.build.outputDirectory}"
	 * @required
	 */
	private File classOutputDirectory;

	/**
	 * 源文件目录，默认取${project.build.sourceDirectory}
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
		if(disabled) return;

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
					projectDir.getAbsolutePath(),
					oldVersion,
					newVersion,
					moduleDir.getAbsolutePath());

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
				mainResourceGroup[index ++] = FilePathUtils.getDiffPrefixPath(moduleDir.getAbsolutePath(), resource.getDirectory());
			}

			if(extResources != null){
				for(Resource resource: extResources){
					resources.add(resource);
				}
			}

			List<DiffItem> diffs = gitRep.getDiffItems(resources);

			if(diffs != null && diffs.size() > 0){
				String mainJavaGroup = FilePathUtils.getDiffPrefixPath(moduleDir.getAbsolutePath(), sourceDirectory.getAbsolutePath());
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
