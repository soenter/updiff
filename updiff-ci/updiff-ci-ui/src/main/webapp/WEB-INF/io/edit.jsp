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
            IO服务
            <small><span style="color: red">*</span>为必填项</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/"><i class="fa fa-send-o"></i>客户端与服务</a></li>
            <li><a href="/io">IO服务</a></li>
            <li><a href="#">
                <c:choose>
                    <c:when test="${ioModel.id == null}">
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
                <form role="form" data-toggle="validator" method="POST" action="/io/save">
                    <input type="hidden" name="id" value="${ioModel.id}">
                    <div class="row">
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>服务名<span style="color: red">*</span></label>
                                <input type="text" name="name" class="form-control" id="inputName" maxlength="30"
                                       placeholder="自定义名称" value="${ioModel.name}" required>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>协议<span style="color: red">*</span></label>
                                <select class="form-control" name="protocol" id="inputProtocol" required>
                                    <option <c:if test="${ioModel.protocol == 'FTP'}">selected="selected"</c:if> value="FTP">FTP</option>
                                    <option <c:if test="${ioModel.protocol == 'AMQP'}">selected="selected"</c:if> value="AMQP">AMQP</option>
                                </select>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>IP地址<span style="color: red">*</span></label>
                                <input type="text" name="host" class="form-control" id="inputHost" host="30"
                                       placeholder="IP地址" value="${ioModel.host}" data-inputmask="'alias': 'ip'" data-mask required>

                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>主机端口<span style="color: red">*</span></label>
                                <input type="number" min="1" name="port" class="form-control" id="inputPort"
                                       placeholder="大于0" value="${ioModel.port}" required>
                                <div class="help-block with-errors">
                                    <c:if test="${code != null}">
                                        <span style="color: red">${message}</span>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>连接用户</label>
                                <input type="text" name="userName" class="form-control" id="inputUserName" maxlength="30"
                                       placeholder="连接时用户名" value="${ioModel.userName}">
                            </div>
                        </div>
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>用户密码</label>
                                <input type="password" name="userPwd" class="form-control" id="inputUserPwd" maxlength="500"
                                       placeholder="连接时用户密码" value="${ioModel.userPwd}" autocomplete="new-password">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>超时时间</label>
                                <input type="number" min="1" name="timeout" class="form-control" id="inputTimeout"
                                       placeholder="单位秒" value="${ioModel.timeout}">
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>心跳时间</label>
                                <input type="number" min="1" name="keepAlive" class="form-control" id="inputKeepAlive"
                                       placeholder="单位秒" value="${ioModel.keepAlive}" required>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>参数选项</label>
                                <textarea name="options" class="form-control" id="inputOptions" maxlength="1000"
                                       placeholder="Json格式">${ioModel.options}</textarea>
                            </div>
                        </div>
                        <div class="form-group" style="margin-bottom: 0px">
                            <div class="col-xs-6">
                                <label>备注</label>
                                <textarea name="comments" class="form-control" id="inputComments" maxlength="200">${ioModel.comments}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style="padding-top: 10px;">
                        <button type="submit" class="btn btn-primary pull-right" id="save-btn">保存</button>
                    </div>
                </form>
            </div><!-- /.box-body -->
        </div><!-- /.box -->
    </section>
<script src="../../js/validator.min.js"></script>
<!-- InputMask -->
<script src="../../plugins/input-mask/jquery.inputmask.js"></script>
<script src="../../plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="../../plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function(){
        $("[data-mask]").inputmask();
    });
</script>
<%@include file="../main/frame-footer.jsp" %>