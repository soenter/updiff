<%@ taglib prefix="e" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@include file="../main/frame-header.jsp" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            客户端
            <small>接入列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-television"></i>客户端与服务</a></li>
            <li><a href="/client">客户端</a></li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <form role="form" method="POST" action="/client">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header">
                            <a class="btn btn-sm btn-info btn-flat" href="/client/add">添加客户端</a>
                            <div class="box-tools">
                                <div class="input-group" style="width: 150px;">
                                    <input type="text" name="search" class="form-control input-sm pull-right"
                                           placeholder="Search" value="${clientModel.search}">
                                    <div class="input-group-btn">
                                        <button type="submit" class="btn btn-sm btn-default"><i class="fa fa-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <tr>
                                    <th width="10px">&nbsp;</th>
                                    <th width="20%">ID</th>
                                    <th width="15%">别名</th>
                                    <th width="5%">状态</th>
                                    <th width="14%">最近通信地址</th>
                                    <th width="14%">最近通信时间</th>
                                    <th width="20%">操作</th>
                                    <th width="12%">备注</th>
                                </tr>
                                <e:forEach items="${list}" var="item">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="checkboxs" value="${item.id}">
                                        </td>
                                        <td>${item.name}-${item.no}</td>
                                        <td>${item.alias}</td>
                                        <td>
                                            <e:choose>
                                                <e:when test="${item.status == '05'}">
                                                    <span class="label label-danger">异常</span>
                                                </e:when>
                                                <e:when test="${item.status == '10'}">
                                                    <span class="label label-default">禁用</span>
                                                </e:when>
                                                <e:otherwise>
                                                    <span class="label label-success">正常</span>
                                                </e:otherwise>
                                            </e:choose>
                                        </td>
                                        <td>${item.lastCommIp}</td>
                                        <td>
                                            <fmt:parseDate value="${item.lastCommTime}" var="lastCommTime" pattern="yyyyMMddHHmmss"></fmt:parseDate>
                                            <fmt:formatDate value="${lastCommTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                        </td>
                                        <td>
                                            <a href="#" onclick="submitForm(this, '/client/modify/?id=${item.id}')" class="btn btn-xs btn-default">修改</a>
                                            <e:choose>
                                                <e:when test="${item.status == '10'}">
                                                    <a href="#" onclick="submitForm(this, '/client/enable?id=${item.id}')" class="btn btn-xs btn-info">启用</a>
                                                </e:when>
                                                <e:otherwise>
                                                    <a href="#" onclick="submitForm(this, '/client/disable?id=${item.id}')" class="btn btn-xs btn-warning">禁用</a>
                                                </e:otherwise>
                                            </e:choose>
                                            <a href="#" onclick="submitForm(this, '/client/topic?clientId=${item.id}')" class="btn btn-xs btn-link">订阅通知</a>
                                        </td>
                                        <td>${item.comments}</td>
                                    </tr>
                                </e:forEach>
                            </table>
                        </div>
                        <div class="box-footer clearfix">
                            <input type="checkbox" id="check-all" value="${item.id}">&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="#" id="deletes-btn" class="btn btn-sm btn-danger btn-flat">删除</a>
                            <%@include file="/WEB-INF/includes/paging-bar.jsp"%>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </section>
</div>
<script>
    $(function(){
        $("#check-all").change(function(){
            $("input[name='checkboxs']").prop("checked", this.checked);
        });
        $("#deletes-btn").on("click", function(){
            if($("input[name='checkboxs']:checked").length > 0){
                submitForm(this, '/client/deletes/')
            }
        });
    });
</script>
<%@include file="../main/frame-footer.jsp" %>

