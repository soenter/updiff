/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/3 19:24
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/3        Initailized
 */
package com.sand.updiff.common;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName ：com.sand.updiff.common.GitRep
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/3 19:24
 * @version 1.0.0
 *
 */
public class GitRep {

	private static final Log log = new SystemStreamLog();

	private String rootDir;

	private String oldGitVersion;

	private String newGitVersion;

	private String basePath;

	private List<DiffEntry> diffs;

	public GitRep (String rootDir, String oldGitVersion, String newGitVersion, String basePath) throws IOException, GitAPIException {
		this.rootDir = rootDir;
		this.oldGitVersion = oldGitVersion;
		this.newGitVersion = newGitVersion;
		this.basePath = basePath;

		log.info(String.format("根目录:%s, 新版本:%s, 旧版本:%s, 模块目录:%s", rootDir, newGitVersion, oldGitVersion, basePath));

		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File(getGitDirByRootDir(rootDir)))
				.readEnvironment()
				.findGitDir()
				.build();

		Git git = new Git(repository);

		ObjectReader reader = git.getRepository().newObjectReader();

		CanonicalTreeParser newTreeIterator = new CanonicalTreeParser();
		ObjectId newTree = git.getRepository().resolve(newGitVersion + "^{tree}");
		newTreeIterator.reset(reader, newTree);

		CanonicalTreeParser oldTreeIterator = new CanonicalTreeParser();
		ObjectId oldTree = git.getRepository().resolve(oldGitVersion + "^{tree}");
		oldTreeIterator.reset(reader, oldTree);

		DiffCommand command = git.diff()
				.setNewTree(newTreeIterator)
				.setOldTree(oldTreeIterator);
		String pathFilter = createDiffPrefixByRootDir(this.basePath);
		if(!"".equals(pathFilter)){
			log.info(String.format("路径过滤:%s", pathFilter));
			command.setPathFilter(PathFilter.create(pathFilter));
		}
		this.diffs = command.call();
	}

	public List<DiffItem> getDiffItems(List<File> sourceStructFiles){

		String[] prefixes = new String[sourceStructFiles.size()];
		int index = 0;
		for (File file: sourceStructFiles){
			String prefix = createDiffPrefixByBasePath(file.getAbsolutePath());
			prefixes[index ++] = prefix;
			log.info(String.format("源文件接口目录: %s", prefix));
		}

		List<DiffItem> diffEls = new ArrayList<DiffItem>(diffs.size());

		//模块文件夹名称
		String modelDirName = createDiffPrefixByRootDir(this.basePath);
		for (DiffEntry entry: diffs){
			String newPath = DiffEntry.DEV_NULL.equals(entry.getNewPath())?null:entry.getNewPath();
			String oldPath = DiffEntry.DEV_NULL.equals(entry.getOldPath())?null:entry.getOldPath();

			//先模块文件夹名称
			newPath = removePrefix(modelDirName, newPath);
			oldPath = removePrefix(modelDirName, oldPath);
			//在去除其他前缀
			String newPathProfix = getPrefix(prefixes, newPath);
			String oldPathProfix = getPrefix(prefixes, oldPath);
			newPath = removePrefix(newPathProfix, newPath);
			oldPath = removePrefix(oldPathProfix, oldPath);

			log.info(String.format("变化文件当前路径：%s, 变化文件旧路径：%s,", newPath, oldPath));

			diffEls.add(new DiffItem(newPathProfix, oldPathProfix, entry.getChangeType().name(), newPath, oldPath));
		}

		return diffEls;
	}


	private String createDiffPrefixByRootDir (String path){
		return createDiffPrefix(this.rootDir, path);
	}

	private String createDiffPrefixByBasePath (String path){
		return createDiffPrefix(this.basePath, path);
	}

	private static String getGitDirByRootDir(String rootDir){
		return rootDir + "/.git";
	}

	private static String createDiffPrefix (String basePath, String path){
		if(path.length() > basePath.length() && path.indexOf(basePath) == 0){
			path = path.substring(basePath.length()).replace("\\", "/");
			if(path.indexOf("/") == 0){
				path = path.substring(1);
			}
			return path;
		}
		return "";
	}

	private static String removePrefix (String prefix, String gitPath){
		if(gitPath == null) return null;

		if(gitPath.indexOf(prefix) == 0){
			String path = gitPath.substring(prefix.length());
			if(path.indexOf("/") == 0){
				path = path.substring(1);
				return path;
			}
		}
		return gitPath;
	}

	private static String getPrefix(String[] prefixes, String gitPath){
		if(gitPath == null) return null;

		for (String prefix: prefixes){
			if(gitPath.indexOf(prefix) == 0){
				return prefix;
			}
		}
		return "";
	}


}
