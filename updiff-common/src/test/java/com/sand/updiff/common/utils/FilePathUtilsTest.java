package com.sand.updiff.common.utils;

import org.apache.maven.model.Resource;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/17 12:10
 * @since 1.0.0
 *
 */
public class FilePathUtilsTest {



	@Test
	public void test_getSameSubfixPath(){

		String path1 = "D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0_backup_20150817141952\\lib\\abacus-business-domain-transjnl-1.0.0.jar_new.jar";
		String path2 = "D:\\a\\b\\d\\1\\2\\3\\4.java";

		String samePath = FilePathUtils.getSameSubfixPath(path1, path2);

		System.out.println(samePath);
		assert samePath.equals("1/2/3/4.java");
	}

	@Test
	public void test_resource_filted(){

		Resource resourceInclude = new Resource();
		resourceInclude.addInclude("config/*");

		assert !FilePathUtils.isFiltered(resourceInclude, "config/1.conf");
		assert !FilePathUtils.isFiltered(resourceInclude, "config/a/1.conf");
		assert FilePathUtils.isFiltered(resourceInclude, "bin/a/1.conf");
		assert FilePathUtils.isFiltered(resourceInclude, "2.conf");


		Resource resourceExclude = new Resource();
		resourceExclude.addExclude("bin/*");

		assert FilePathUtils.isFiltered(resourceExclude, "bin/a/1.conf");


		Resource resourceExclude2 = new Resource();
		resourceExclude2.addExclude("*.test");

		assert FilePathUtils.isFiltered(resourceExclude2, "1.test");
		assert FilePathUtils.isFiltered(resourceExclude2, "bin/a/1.test");
	}

	@Test
	public void test_read_file_entry(){
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(new File("E:\\0097_upload\\risk-server-bootstrap-service-2.0.0\\lib\\risk-server-service-all-2.0.0.jar"));
			Enumeration<JarEntry> entryEnumeration =  jarFile.entries();
			while (entryEnumeration.hasMoreElements()){
				JarEntry entry = entryEnumeration.nextElement();
				System.out.println("entry name: " + entry.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
