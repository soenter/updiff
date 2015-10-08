<%--
  User: sun.mt
  Date: 2015/9/21
  Time: 15:10
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/WEB-INF/includes/page-doctype.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <%@include file="/WEB-INF/includes/page-meta.jsp"%>
  <title>drift</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <%@ include file="/WEB-INF/includes/ui-lib-header.jsp"%>
  <link rel="stylesheet" href="../../plugins/iCheck/square/blue.css">
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="../" class="logo">
      <span class="logo-lg"><img src="/image/ico/logo_32-32.ico" style="width: 30px;height: 30px;"><b style="vertical-align: middle">drift</b></span>
    </a>
  </div>
  <div class="login-box-body">
      <p class="login-box-msg">
      <c:choose>
          <c:when test="${code != null}">
              <span style="color:red">${message}</span>
          </c:when>
          <c:otherwise>
              请输入登陆信息
          </c:otherwise>
      </c:choose>
      </p>
    <form action="/users/session" method="post">
        <input type="hidden" name="to" value="${to}">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="login" placeholder="用户名或邮箱">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="password" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <%--<label>--%>
              <%--<input type="checkbox"> 记住密码--%>
            <%--</label>--%>
          </div>
        </div><!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登陆</button>
        </div><!-- /.col -->
      </div>
    </form>

    <%--<a href="#">忘记密码</a><br>--%>

  </div><!-- /.login-box-body -->
</div><!-- /.login-box -->

<!-- iCheck -->
<script src="../../plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>
