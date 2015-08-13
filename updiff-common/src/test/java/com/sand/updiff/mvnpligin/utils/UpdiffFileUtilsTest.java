/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/12 13:13
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/12        Initailized
 */
package com.sand.updiff.mvnpligin.utils;

import com.sand.updiff.common.utils.UpdiffFileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 *
 * @ClassName ：com.sand.updiff.utils.UpdiffFileUtilsTest
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/12 13:13
 * @version 1.0.0
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
