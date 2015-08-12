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
 * sun.mt@sand.com.cn         2015/8/12        Initailized
 */
package com.soenter.updiff.upper;

import org.junit.Test;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.UpperTest
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/12 14:23
 * @version 1.0.0
 *
 */
public class UpperTest {

	@Test
	public void test_call(){

		String oldPath = "D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0";
		String newPath = "D:\\0000_test\\updiff\\abacus\\abacus-product-bootstrap-1.0.0-assembly.new.tar.gz";
		Upper upper = new Upper(oldPath, newPath);

		upper.up();
	}
}
