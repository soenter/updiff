package com.sand.updiff.examples.assembly;

import com.sand.updiff.common.DiffItem;
import com.sand.updiff.common.DiffReader;
import org.dom4j.DocumentException;

import java.io.*;
import java.util.List;

/**
 * @author : sun.mt
 * @date : 2015/9/8 12:35
 * @since 1.0.0
 */
public class ChangeList {


	public void print (){

		InputStream in = getClass().getClassLoader().getResourceAsStream("META-INF/updiff-examples-assembly-1.0.4.diff");

		if(in == null){
			System.out.println("change list is empty!");
			return;
		} else {
			try {
				DiffReader reader = new DiffReader(in);

				List<DiffItem> list = reader.readAll();

				for (DiffItem item: list){
					System.out.println(item.toString());
				}

			} catch (DocumentException e) {
				System.out.println("read change list file error!");
			}
		}
		System.out.println("done");

	}


}
