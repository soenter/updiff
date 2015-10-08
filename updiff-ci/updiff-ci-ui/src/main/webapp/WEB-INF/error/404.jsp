<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/page-doctype.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/includes/page-meta.jsp"%>
<title>访问出错</title>
</head>
<body>
<style>
.nors {
	position: relative;
}

.norsTitle {
	font-size: 22px;
	font-family: Microsoft Yahei;
	font-weight: normal;
	color: #333;
	margin: 35px 0 25px 0;
}

.norsSuggest {
	display: inline-block;
	color: #333;
	font-family: arial;
	font-size: 13px;
	position: relative;
}

.norsSuggest li {
	list-style: decimal;
}

.norsTitle2 {
	font-family: arial;
	font-size: 13px;
	color: #666;
}

.norsSuggest li {
	margin: 13px 0;
}

.norsSuggest ol {
	margin-left: 47px;
}

#foot {
	position: fixed;
	bottom: 0;
	width: 100%;
}
</style>
	<div id="content_left">
		<div class="nors">
			<div class="norsSuggest">
				<h3 class="norsTitle">
					很抱歉，您要访问的页面不存在！
				</h3>
				<p class="norsTitle2">温馨提示：</p>
				<ol>
					<li>请检查您访问的网址是否正确</li>
					<li>如有任何意见或建议，请及时<a href="http://www.sandpay.com.cn/" target="blank">反馈给我们</a>。</li>

				</ol>
			</div>
		</div>
	</div>
</body>
</html>