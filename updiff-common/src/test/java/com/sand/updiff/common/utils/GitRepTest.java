package com.sand.updiff.common.utils;

import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName : com.sand.updiff.common.utils.GitRepTest
 * @Description :
 * @Author : sun.mt@sand.com.cn
 * @Date : 2015/9/12 23:42
 */
public class GitRepTest {

	@Test
	public void test_git_tag() throws IOException, GitAPIException {

		String rootDir = "D:\\0010_git\\light-task-scheduler_github\\.git";
		String oldGitVersion = "1.5.4x";
		String newGitVersion = "1.5.5";

		System.out.println(String.format("Git 根目录:%s, Git 新版本号:%s, Git 旧版本号:%s", rootDir, newGitVersion, oldGitVersion));

		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File(rootDir))
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
		List<DiffEntry> diffs = command.call();
		for (DiffEntry diffEntry: diffs){
			System.out.println(diffEntry);
		}

	}
}
