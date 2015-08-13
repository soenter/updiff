/**
 * Copyright : http://www.sandpay.com.cn/ , 2007-2015
 * Project : updiff
 * $$Id$$
 * $$Revision$$
 * Last Changed by sun.mt at 2015/8/11 9:33
 * $$URL$$
 * <p/>
 * Change Log
 * Author      Change Date    Comments
 * -------------------------------------------------------------
 * sun.mt@sand.com.cn         2015/8/11        Initailized
 */
package com.sand.updiff.upper.update;

import com.sand.updiff.common.DiffItem;
import com.sand.updiff.upper.scan.Scanner;
import com.sand.updiff.upper.scan.impl.DiffScand;
import com.sand.updiff.upper.scan.impl.DiffScanner;
import com.sand.updiff.upper.scan.Scaned;
import com.sand.updiff.upper.scan.impl.DefaultScanner;
import com.sand.updiff.upper.update.impl.DefaultTask;
import com.sand.updiff.upper.update.impl.UpperExecutor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @ClassName ��com.sand.updiff.update.impl.UpdateTest
 * @Description : 
 * @author : sun.mt@sand.com.cn
 * @Date : 2015/8/11 9:33
 * @version 1.0.0
 *
 */
public class UpdateTest {

	@Test
	public void test_call_with_scand_diff_file() throws IOException {
		test_call_with_scand_diff_file_1(false);
	}

	@Test
	public void test_call_with_scand_diff_file_recovery() throws IOException {
		test_call_with_scand_diff_file_1(true);
	}

	public void test_call_with_scand_diff_file_1(boolean isRecovery) throws IOException {

		File diffFile = new File("D:\\0000_test\\updiff\\abacus_up\\abacus_up.diff");
		Scanner<DiffItem> scanner = new DiffScanner(diffFile);

		Iterator<DiffItem> it = scanner.iterator();

		File newDir = new File("D:\\0000_test\\updiff\\abacus");
		File oldDir = new File("D:\\0000_test\\updiff\\abacus_up");
		String backupDif = "D:\\0000_test\\updiff\\abacus_backup_" + System.currentTimeMillis();

		UpperExecutor executor = new UpperExecutor();
		while(it.hasNext()){
			DiffItem diffItem = it.next();
			File newFile = new File(newDir.getAbsolutePath() + File.separator + diffItem.getCompiledNewPath());
			File oldFile = new File(oldDir.getAbsolutePath() + File.separator + diffItem.getCompiledNewPath());

			Scaned scaned = new DiffScand(newFile, oldFile, diffItem.getCompiledNewPath(), diffItem);
			if(!executor.execute(new DefaultTask(scaned, backupDif))){
				break;
			}

		}
		if(isRecovery){
			executor.recovery();
		}

	}


	@Test
	public void test_call_with_scand_file() throws IOException {
		test_call_with_scand_file_1(false);
	}

	@Test
	public void test_call_with_scand_file_recovery() throws IOException {
		test_call_with_scand_file_1(true);
	}



	public void test_call_with_scand_file_1(boolean isRecovery) throws IOException {

		File newDir = new File("D:\\0000_test\\updiff\\abacus");
		File oldDir = new File("D:\\0000_test\\updiff\\abacus_up");

		Scanner<Scaned> scanner = new DefaultScanner(newDir, oldDir);
		Iterator<Scaned> it = scanner.iterator();

		String backupDif = "D:\\0000_test\\updiff\\abacus_backup_" + System.currentTimeMillis();

		UpperExecutor executor = new UpperExecutor();
		while(it.hasNext()){
			if(!executor.execute(new DefaultTask(it.next(), backupDif))){
				break;
			}
		}

		if(isRecovery){
			executor.recovery();
		}

	}


	@Test
	public void test_delete_file(){
		System.out.println(new File("D:\\0000_test\\updiff\\abacus\\com\\Test.txt").delete());
	}
}
