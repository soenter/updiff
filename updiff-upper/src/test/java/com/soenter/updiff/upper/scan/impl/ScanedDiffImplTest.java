/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/11 9:21
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/11        Initailized
 */
package com.soenter.updiff.upper.scan.impl;

import com.soenter.updiff.common.DiffElement;
import com.soenter.updiff.upper.scan.Scaned;
import com.soenter.updiff.upper.scan.Scanner;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @ClassName ：com.soenter.updiff.upper.scan.impl.ScanedDiffImplTest
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/11 9:21
 * @version 1.0.0
 *
 */
public class ScanedDiffImplTest {

	@Test
	public void test_call() throws IOException {
		File diffFile = new File("D:\\0005_git\\abacus\\abacus-product-bootstrap\\target\\abacus-product-bootstrap-1.0.0.diff");
		Scanner<DiffElement> scanner = new ScannerDiffImpl(diffFile);

		Iterator<DiffElement> it = scanner.iterator();

		while(it.hasNext()){
			System.out.println(it.next());
		}

	}
}
