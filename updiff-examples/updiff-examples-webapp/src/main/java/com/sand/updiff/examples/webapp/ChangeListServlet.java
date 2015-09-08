package com.sand.updiff.examples.webapp;

import com.sand.updiff.common.DiffItem;
import com.sand.updiff.common.DiffReader;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @ClassName : com.sand.updiff.examples.ChangeListServlet
 * @Description :
 * @Author : sun.mt@sand.com.cn
 * @Date : 2015/8/29 12:11
 */
public class ChangeListServlet extends HttpServlet{

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

				for (DiffItem item: list){
					out.println(item.toString());
				}

			} catch (DocumentException e) {
				out.println("read change list file error!");
			}
		}
		out.println("done");

	}
}
