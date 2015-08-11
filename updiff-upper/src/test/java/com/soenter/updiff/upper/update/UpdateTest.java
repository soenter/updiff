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
package com.soenter.updiff.upper.update;

import com.soenter.updiff.common.DiffElement;
import com.soenter.updiff.upper.scan.Scaned;
import com.soenter.updiff.upper.scan.Scanner;
import com.soenter.updiff.upper.scan.impl.ScandDiffImpl;
import com.soenter.updiff.upper.scan.impl.ScannerDiffImpl;
import com.soenter.updiff.upper.scan.impl.ScannerImpl;
import com.soenter.updiff.upper.update.Executor;
import com.soenter.updiff.upper.update.Update;
import com.soenter.updiff.upper.update.UpdateFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @ClassName ��com.soenter.updiff.update.impl.UpdateTest
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
		Scanner<DiffElement> scanner = new ScannerDiffImpl(diffFile);

		Iterator<DiffElement> it = scanner.iterator();

		File newDir = new File("D:\\0000_test\\updiff\\abacus");
		File oldDir = new File("D:\\0000_test\\updiff\\abacus_up");
		String backupDif = "D:\\0000_test\\updiff\\abacus_backup_" + System.currentTimeMillis();

		Executor executor = new Executor();
		while(it.hasNext()){
			DiffElement diffElement = it.next();
			try {
				File newFile = new File(newDir.getAbsolutePath() + File.separator + diffElement.getCompiledNewPath());
				File oldFile = new File(oldDir.getAbsolutePath() + File.separator + diffElement.getCompiledNewPath());

				Scaned scaned = new ScandDiffImpl(newFile, oldFile, diffElement.getCompiledNewPath(), diffElement);
				Update update = UpdateFactory.create(scaned, backupDif);

				executor.execute(update);


			} catch (Exception e){

				executor.recovery();

				throw new IOException(e);
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

		Scanner<Scaned> scanner = new ScannerImpl(newDir, oldDir);
		Iterator<Scaned> it = scanner.iterator();

		String backupDif = "D:\\0000_test\\updiff\\abacus_backup_" + System.currentTimeMillis();

		Executor executor = new Executor();
		while(it.hasNext()){
			try {
				Update update = UpdateFactory.create(it.next(), backupDif);
				executor.execute(update);

			} catch (Exception e){

				executor.recovery();

				throw new IOException(e);
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
