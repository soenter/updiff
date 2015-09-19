<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/page-doctype.jsp" %>
<html>
<head>
<%@ include file="/WEB-INF/includes/page-meta.jsp" %>
<title>登陆出错</title>
</head>
<body>
<style>
	.nors{
		position: relative;
	}
	.norsTitle{
		font-size: 22px;
		font-family: Microsoft Yahei;
		font-weight: normal;
		color: #333;
		margin: 35px 0 25px 0;
	}
	.norsSuggest {
		display: inline-block;
		color:#333;
		font-family:arial;
		font-size: 13px;
		position: relative;
	}

	.norsSuggest li{
		list-style: decimal;
	}
	.norsTitle2 {
		font-family: arial;
		font-size: 13px;
		color: #666;	
	}
	.norsSuggest li{
		margin: 13px 0;
	}
	.norsSuggest ol{
		margin-left: 47px;
	}
	#foot{
		position: fixed;
		bottom: 0;
		width: 100%;
	}
</style>
	<div id="content_left">
		<div class="nors">
			<div class="norsSuggest">
				<h3 class="norsTitle">很抱歉，您尚未登陆！<span id="time">5</span>秒后将跳转到登陆页面...</h3>
				
				
				<p class="norsTitle2">温馨提示：</p>
				<ol>
					<li>请确认您是否有访问权限</li>
					<li>如有任何意见或建议，请及时<a href="http://www.sandpay.com.cn/">反馈给我们</a>。</li>
					<li><a href="JavaScript:doLogin()">点击登陆</a>。</li>

				</ol>
			</div>
		</div>
	</div>

<script type="text/javascript">
var num = 4;
setInterval(function(){
	var p = document.getElementById("time");
	if(num == 0){
		doLogin();
	} else {
		p.innerText = num--;
	}
}, 1000);
function doLogin(){
	window.top.location.href = "login.html";
}
</script>
</body>
</html>