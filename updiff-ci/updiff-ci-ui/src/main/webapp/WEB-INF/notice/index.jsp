<%@ taglib prefix="e" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@include file="../main/frame-header.jsp" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            通知服务
            <small>列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-television"></i>客户端与服务</a></li>
            <li><a href="/notice">通知服务</a></li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <form role="form" method="POST" action="/notice">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header">
                            <a class="btn btn-sm btn-info btn-flat" href="/notice/add">添加通知服务</a>
                            <a class="btn btn-sm btn-primary btn-flat" href="/notice/log">查看通知日志</a>
                            <div class="box-tools">
                                <div class="input-group" style="width: 150px;">
                                    <input type="text" name="search" class="form-control input-sm pull-right"
                                           placeholder="Search" value="${noticeModel.search}">
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
                                    <th width="15%">名称</th>
                                    <th width="15%">地址</th>
                                    <th width="5%">状态</th>
                                    <th width="5%">协议</th>
                                    <th width="7.5%">超时时间</th>
                                    <th width="7.5%">心跳时间</th>
                                    <th width="15%">可选参数</th>
                                    <th width="10%">备注</th>
                                    <th width="20%">操作</th>
                                </tr>
                                <e:forEach items="${list}" var="item">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="checkboxs" value="${item.id}">
                                        </td>
                                        <td>${item.name}</td>
                                        <td>${item.host}:${item.port}</td>
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
                                        <td>${item.protocol}</td>
                                        <td>${item.timeout}(秒)</td>
                                        <td>${item.keepAlive}(秒)</td>
                                        <td>${item.options}</td>
                                        <td>${item.comments}</td>
                                        <td>
                                            <a href="#" onclick="submitForm(this, '/notice/modify?id=${item.id}')" class="btn btn-xs btn-default">修改</a>
                                            <e:choose>
                                                <e:when test="${item.status == '10'}">
                                                    <a href="#" onclick="submitForm(this, '/notice/enable?id=${item.id}')" class="btn btn-xs btn-info">启用</a>
                                                </e:when>
                                                <e:otherwise>
                                                    <a href="#" onclick="submitForm(this, '/notice/disable?id=${item.id}')" class="btn btn-xs btn-warning">禁用</a>
                                                </e:otherwise>
                                            </e:choose>
                                        </td>
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
                submitForm(this, '/notice/deletes')
            }
        });
    });
</script>
<%@include file="../main/frame-footer.jsp" %>

