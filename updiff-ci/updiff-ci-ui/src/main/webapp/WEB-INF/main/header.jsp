<%--
  User: sun.mt
  Date: 2015/9/18
  Time: 10:14
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="main-header">
    <!-- Logo -->
    <a href="/" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><img src="/image/ico/logo_32-32.ico" style="width: 36px;height: 36px;"></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><img src="/image/ico/logo_32-32.ico" style="width: 30px;height: 30px;"><b
                style="vertical-align: middle">drift</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>

        <div class="navbar-custom-menu">
            <%--<form class="navbar-form navbar-left" role="search">--%>
            <%--<div class="form-group">--%>
            <%--<input type="text" class="form-control" id="navbar-search-input" placeholder="搜索">--%>
            <%--</div>--%>
            <%--</form>--%>
            <c:choose>
                <c:when test="${sessionScope.containsKey('session.user.model')}">
                    <%@include file="header-navbar-login.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@include file="header-navbar.jsp" %>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</header>
