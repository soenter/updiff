<%--
  User: sun.mt
  Date: 2015/9/18
  Time: 16:04
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<ul class="nav navbar-nav">
    <li>
        <a href="#"><i class="fa fa-user"></i>&nbsp;${sessionScope.get('session.user.model').nick} </a>
    </li>
    <li>
        <a href="/users/logout"><i class="fa fa-sign-out"></i>&nbsp;退出</a>
    </li>
</ul>