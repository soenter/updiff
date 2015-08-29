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

import com.sand.updiff.common.utils.FilePathUtils;
import org.apache.maven.model.Resource;
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
import java.util.*;
import java.util.regex.Pattern;

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

		log.info(String.format("Git 根目录:%s, Git 新版本号:%s, Git 旧版本号:%s, 模块目录:%s", rootDir, newGitVersion, oldGitVersion, basePath));

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
			log.info(String.format("Git 路径过滤:%s", pathFilter));
			command.setPathFilter(PathFilter.create(pathFilter));
		}
		this.diffs = command.call();
	}

	public List<DiffItem> getDiffItems(List<Resource> sourceStructFiles){

		Map<String, Resource> resourceMap = new HashMap<String, Resource>();
		int index = 0;
		for (Resource resource: sourceStructFiles){
			String prefix = createDiffPrefixByBasePath(resource.getDirectory());
			resourceMap.put(prefix, resource);
			log.info(String.format("要更新的目录结构有: %s", prefix));
		}

		List<DiffItem> diffEls = new ArrayList<DiffItem>(diffs.size());

		//模块文件夹名称
		String modelDirName = createDiffPrefixByRootDir(this.basePath);
		for (DiffEntry entry: diffs){
			String path = null;
			switch (entry.getChangeType()) {
				case ADD:
					path = entry.getNewPath();
					break;
				case COPY:
					path = entry.getNewPath();
					break;
				case DELETE:
					path = entry.getOldPath();
					break;
				case MODIFY:
					path = entry.getOldPath();
					break;
				case RENAME:
					path = entry.getNewPath();
					break;
			}

			//先模块文件夹名称
			path = removePrefix(modelDirName, path);
			//在去除其他前缀
			String newPathProfix = getPrefixByGitPath(resourceMap, path);
			boolean isNeedUpdate = false;
			if(newPathProfix != null){
				path = removePrefix(newPathProfix, path);

				Resource resource = resourceMap.get(newPathProfix);
				if(!FilePathUtils.isFiltered(resource, path)){
					diffEls.add(new DiffItem(newPathProfix, entry.getChangeType().name(), path));
					isNeedUpdate = true;
				}
			}

			if(isNeedUpdate){
				log.info(String.format("需要更新的文件：%s", path));
			} else {
				log.info(String.format("不需要更新的文件：%s", path));
			}

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
		if(path.indexOf(basePath) == 0){
			path = path.substring(basePath.length()).replace("\\", "/");
		}
		if(path.indexOf("/") == 0){
			path = path.substring(1);
		}
		return path;
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

	private static String getPrefixByGitPath(Map<String, Resource> resourceMap, String gitPath){
		if(gitPath == null) return null;

		Set<String> keys = resourceMap.keySet();

		for(String key: keys){
			if(gitPath.indexOf(key) == 0){
				return key;
			}
		}
		return null;
	}



}
