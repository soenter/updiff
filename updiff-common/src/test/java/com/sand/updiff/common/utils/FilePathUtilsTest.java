/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/17 12:10
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/17        Initailized
 */
package com.sand.updiff.common.utils;

import org.junit.Test;

/**
 *
 * @ClassName ：com.sand.updiff.mvnplugin.utils.FilePathUtilsTest
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/17 12:10
 * @version 1.0.0
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

}
