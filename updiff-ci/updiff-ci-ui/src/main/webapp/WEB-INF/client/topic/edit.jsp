<%--
  User: sun.mt
  Date: 2015/9/24
  Time: 13:34
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@include file="../../main/frame-header.jsp" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            通知主题
            <small><span style="color: red">*</span>为必填项</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-television"></i>客户端与服务</a></li>
            <li><a href="/client">客户端</a></li><li>
            <a href="/client/topic?clientId=${topicModel.clientId}">订阅通知</a></li>
            <li><a href="#">
                <c:choose>
                    <c:when test="${topicModel.id == null}">
                        添加
                    </c:when>
                    <c:otherwise>
                        修改
                    </c:otherwise>
                </c:choose>
            </a></li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="box box-info">
            <div class="box-body">
                <form role="form" data-toggle="validator" method="POST" action="/client/topic/save">
                    <input type="hidden" name="id" value="${topicModel.id}">
                    <input type="hidden" name="clientId" value="${topicModel.clientId}">
                    <div class="form-group">
                        <label>通知主题<span style="color: red">*</span></label>
                        <input type="text" name="topic" pattern="^[_A-z0-9]{1,}$" class="form-control" id="inputTopic"
                               placeholder="仅包含英文字母和下划线" value="${topicModel.topic}" maxlength="500" required
                                <c:if test="${topicModel.id != null}">disabled</c:if> >
                        <div class="help-block with-errors">
                            <c:if test="${code != null}">
                                <span style="color: red">${message}</span>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>处理类<span style="color: red">*</span></label>
                        <input type="text" name="handlerClass" pattern="^[\\.A-z0-9]{1,}$" class="form-control" id="inputNo"
                               placeholder="Java类名" value="${topicModel.handlerClass}"  maxlength="500" required>
                        <div class="help-block with-errors">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea type="text" name="comments" class="form-control" id="inputComments" maxlength="200">${topicModel.comments}</textarea>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary pull-right" id="save-btn">保存</button>
                    </div>
                </form>
            </div><!-- /.box-body -->
        </div><!-- /.box -->
    </section>
<script src="../../../js/validator.min.js"></script>
<%@include file="../../main/frame-footer.jsp" %>