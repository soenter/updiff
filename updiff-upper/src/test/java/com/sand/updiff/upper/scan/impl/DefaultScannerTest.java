/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/7 15:33
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/7        Initailized
 */
package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.Scanner;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;

/**
 *
 * @ClassName ：com.sand.updiff.upper.scan.impl.DefaultScannerTest
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/7 15:33
 * @version 1.0.0
 *
 */
public class DefaultScannerTest {

	@Test
	public void test_call(){

		Scanner scanner = new DefaultScanner(
				new File("D:\\0000_test\\abacus-annotation"),
				new File("D:\\0005_git\\abacus\\abacus-annotation")
		);

		Iterator<Scanned> it = scanner.iterator();

		while(it.hasNext()){
			System.out.println(it.next());
		}

	}


}
