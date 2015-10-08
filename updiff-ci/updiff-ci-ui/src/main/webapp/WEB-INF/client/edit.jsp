<%--
  User: sun.mt
  Date: 2015/9/24
  Time: 13:34
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@include file="../main/frame-header.jsp" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            客户端
            <small><span style="color: red">*</span>为必填项</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-television"></i>客户端与服务</a></li>
            <li><a href="/client">客户端</a></li>
            <li><a href="#">
                <c:choose>
                    <c:when test="${clientModel.id == null}">
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
                <form role="form" data-toggle="validator" method="POST" action="/client/save">
                    <input type="hidden" name="id" value="${clientModel.id}">
                    <div class="form-group">
                        <label>名称<span style="color: red">*</span></label>
                        <input type="text" name="name" pattern="^[_A-z0-9]{1,}$" class="form-control" id="inputName"
                               placeholder="仅包含英文字母和下划线" value="${clientModel.name}" maxlength="30" required
                                <c:if test="${clientModel.id != null}">disabled</c:if> >
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label>序号<span style="color: red">*</span></label>
                        <input type="number" min="0" name="no" class="form-control" id="inputNo"
                               placeholder="只能为数字" value="${clientModel.no}" required
                               <c:if test="${clientModel.id != null}">disabled</c:if>>
                        <div class="help-block with-errors">
                            <c:if test="${code != null}">
                                <span style="color: red">${message}</span>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>默认通知服务<span style="color: red">*</span></label>
                        <select class="form-control" name="noticeServerId" id="inputPushServer" required>
                            <c:forEach var="notice" items="${noticeServers}">
                                <c:choose>
                                    <c:when test="${notice.id == clientModel.noticeServerId}">
                                        <option selected="selected" value="${notice.id}">${notice.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${notice.id}">${notice.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>默认IO服务<span style="color: red">*</span></label>
                        <select class="form-control" name="ioServerId" id="inputIoServer" required>
                            <c:forEach var="io" items="${ioServers}">
                                <c:choose>
                                    <c:when test="${io.id == clientModel.ioServerId}">
                                        <option selected="selected" value="${io.id}">${io.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${io.id}">${io.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>别名</label>
                        <input type="text" name="alias" class="form-control" id="inputAlias" value="${clientModel.alias}" maxlength="50">
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea type="text" name="comments" class="form-control" id="inputComments" maxlength="200">${clientModel.comments}</textarea>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary pull-right" id="save-btn">保存</button>
                    </div>
                </form>
            </div><!-- /.box-body -->
        </div><!-- /.box -->
    </section>
<script src="../../js/validator.min.js"></script>
<%@include file="../main/frame-footer.jsp" %>