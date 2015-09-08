package com.sand.updiff.examples.webapp;

import com.sand.updiff.common.ChangeType;
import com.sand.updiff.common.DiffItem;
import com.sand.updiff.common.DiffReader;
import com.sand.updiff.common.FileType;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ClassName : com.sand.updiff.examples.ChangeAnalysisServlet
 * @Description :
 * @Author : sun.mt@sand.com.cn
 * @Date : 2015/8/30 1:34
 */
public class ChangeAnalysisServlet  extends HttpServlet {

	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	private void doProcess (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();

		String realRootPath = req.getRealPath("/");

		InputStream in = new FileInputStream(realRootPath + "/META-INF/updiff-examples-webapp.diff");


		if(in == null){
			out.println("change list is empty!");
			return;
		} else {
			try {
				DiffReader reader = new DiffReader(in);

				List<DiffItem> list = reader.readAll();

				int addCount = 0;
				int modifyCount = 0;
				int deleteCount = 0;
				int classCount = 0;
				int jspCount = 0;
				for (DiffItem item: list){
					if(item.getChangeName().equals(ChangeType.ADD.name())){
						addCount ++;
					}
					if(item.getChangeName().equals(ChangeType.MODIFY.name())){
						modifyCount ++;
					}
					if(item.getChangeName().equals(ChangeType.DELETE.name())){
						deleteCount ++;
					}
					if(item.getCompiledNewPath().endsWith(FileType.CLASS.getType())){
						classCount ++;
					}
					if(item.getCompiledNewPath().endsWith(".jsp")){
						jspCount ++;
					}
				}

				out.println(String.format("This incremental update has a total of %s add files", addCount));
				out.println(String.format("This incremental update has a total of %s modify files", modifyCount));
				out.println(String.format("This incremental update has a total of %s delete files", deleteCount));
				out.println(String.format("This incremental update has a total of %s class files", classCount));
				out.println(String.format("This incremental update has a total of %s jsp files", jspCount));

			} catch (DocumentException e) {
				out.println("read change list file error!");
			}
		}
		out.println("done");

	}
}
