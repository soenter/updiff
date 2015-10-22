/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/12 14:23
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt         2015/8/12        Initailized
 */
package com.sand.updiff.upper;

import org.junit.Test;

/**
 *
 * @ClassName ：com.sand.updiff.upper.UpperTest
 * @Description : 
 * @author : sun.mt
 * @date : 2015/8/12 14:23
 * @since 1.0.0
 *
 */
public class UpperTest {

	@Test
	public void test_call_upper(){

		String oldPath = "D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0";
		String newPath = "D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0-assembly.new.tar.gz";
		Upper.main(new String[]{"up", oldPath, newPath});
	}

	@Test
	public void test_call_backup(){

		String oldPath = "D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0";
		String newPath = "D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0-assembly.new.tar.gz";
		Upper.main(new String[]{"backup", oldPath, newPath});
	}

	@Test
	public void test_call_recovery(){

		String backupDir = "D:\\0005_git\\updiff\\updiff-upper\\abacus-product-bootstrap-1.0.0_backup_20150917152825";
		Upper.main(new String[]{"recovery", backupDir});
	}

	@Test
	public void test_call_up_webapp(){

		String oldPath = "D:\\0000_test\\updiff\\riskms";
		String newPath = "D:\\0000_test\\updiff\\riskms.war";
		Upper.main(new String[]{"up", oldPath, newPath});
	}
}
