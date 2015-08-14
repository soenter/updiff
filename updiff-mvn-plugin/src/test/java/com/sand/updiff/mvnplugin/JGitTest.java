package com.sand.updiff.mvnplugin;

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
 * Created by sun on 2015/8/2.
 */
public class JGitTest{


	@Test
	public void test_call() throws IOException, GitAPIException {

		FileRepositoryBuilder builder = new FileRepositoryBuilder();


		Repository repository = builder.setGitDir(new File("D:\\0005_git\\updiff\\.git"))
				.readEnvironment()
				.findGitDir()
				.build();

		Git git = new Git(repository);

		ObjectReader reader = git.getRepository().newObjectReader();

		CanonicalTreeParser newTreeIterator = new CanonicalTreeParser();
		ObjectId newTree = git.getRepository().resolve("HEAD^{tree}");
		newTreeIterator.reset(reader, newTree);

		CanonicalTreeParser oldTreeIterator = new CanonicalTreeParser();
		ObjectId oldTree = git.getRepository().resolve("8b5adeb^{tree}");
		oldTreeIterator.reset(reader, oldTree);

		List<DiffEntry> diffs = git.diff()
				.setNewTree(newTreeIterator)
				.setOldTree(oldTreeIterator)
				.call();

		for (DiffEntry diffEntry: diffs){
			System.out.println(diffEntry);
		}

	}
}
