package com.sand.updiff.upper.scan.impl;

import com.sand.updiff.common.DiffItem;
import com.sand.updiff.common.DiffReader;
import com.sand.updiff.upper.scan.Scanned;
import com.sand.updiff.upper.scan.Scanner;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author : sun.mt
 * @date : 2015/8/10 15:36
 * @since 1.0.0
 *
 */
public class DiffScanner implements Scanner<Scanned> {

	private File oldDir;

	private File newDir;

	private List<DiffReader> diffReaders;

	private List<Scanned> scanFiles;

	private ConstaintJarDirScanner constaintJarDirScanner;

	public DiffScanner (File oldDir, File newDir, List<File> diffFiles) throws IOException {
		this.oldDir = oldDir;
		this.newDir = newDir;

		if(diffFiles != null){
			diffReaders = new ArrayList<DiffReader>(diffFiles.size());
			for(File file: diffFiles){
				try {

					DiffReader diffReader = new DiffReader(file);
					diffReaders.add(diffReader);
				} catch (DocumentException e) {
					throw new IOException("diff 文件格式错误");
				}
			}
		}

		constaintJarDirScanner = new ConstaintJarDirScanner(this.oldDir, this.newDir);

	}

	public Iterator<Scanned> iterator () {

		if(scanFiles == null){
			scanFiles = new LinkedList<Scanned>();
			if(diffReaders != null){
				scanByDiffReaders();
			}

			//加入包含jar文件的文件夹scanned
			Iterator<Scanned> constaintJarDirIt = constaintJarDirScanner.iterator();
			while(constaintJarDirIt.hasNext()){
				Scanned scanned = constaintJarDirIt.next();
				scanFiles.add(scanned);
			}
		}

		return scanFiles.iterator();
	}

	private void scanByDiffReaders(){
		for(DiffReader reader: diffReaders){
			Iterator<DiffItem> diffIt = reader.readAll().iterator();

			String packing = reader.getPackaging();
			boolean isWar = "war".equalsIgnoreCase(packing);
			Map<String, Boolean> needCompileMap = null;
			if(isWar){
				needCompileMap = new HashMap<String, Boolean>();
				needCompileMap.put(reader.getMainJavaGroup(), true);
				String[] mainResources = reader.getMainResourceGroups();
				for(String resource: mainResources){
					needCompileMap.put(resource, true);
				}
			}

			while(diffIt.hasNext()){
				DiffItem item = diffIt.next();

				String compliedPath = item.getCompiledNewPath();
				File oldFile;
				File newFile;
				if(isWar && needCompileMap.containsKey(item.getGroupName())){
					oldFile = new File(oldDir, "WEB-INF/classes/" + compliedPath);
					newFile = new File(newDir, "WEB-INF/classes/" + compliedPath);
				} else {
					oldFile = new File(oldDir, compliedPath);
					newFile = new File(newDir, compliedPath);
				}

				Scanned scanned = new DiffScanned(oldFile, newFile, compliedPath, item);
				scanFiles.add(scanned);
			}
		}
	}

}
