package com.sand.updiff.common.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/12 13:13
 * @since 1.0.0
 *
 */
public class UpdiffFileUtilsTest {


	@Test
	public void test_readDiffFileFromJar() throws IOException {


		File jar = new File("D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0-assembly.new.tar.gz_up_source_20150812164139\\lib\\abacus-dao-transjnl-1.0.0.jar");


		File diffFile = UpdiffFileUtils.readDiffFileFromJar(jar);
		System.out.println(diffFile.getAbsolutePath());


	}
}
