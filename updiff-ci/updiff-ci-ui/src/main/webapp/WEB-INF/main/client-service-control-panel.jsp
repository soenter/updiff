<%--
  User: sun.mt
  Date: 2015/9/21
  Time: 10:53
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
  <!-- Content Header (Page header) -->
  <section class="content-header">
    <h1>
      客户端与服务
      <small>控制面板</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/"><i class="fa fa-television"></i> 客户端与服务</a></li>
    </ol>
  </section>

  <!-- Main content -->
  <section class="content">
    <!-- Main row -->
    <div class="row">
      <!-- Left col -->
      <section class="col-lg-7 connectedSortable">
        <!-- Custom tabs (Charts with tabs)-->
        <div class="nav-tabs-custom">
          <!-- Tabs within a box -->
          <ul class="nav nav-tabs pull-right">
            <li class="active"><a href="#client-chart-1" data-toggle="tab">近一周</a></li>
            <li class="pull-left header"><i class="fa fa-desktop"></i>客户端图表</li>
          </ul>
          <div class="tab-content no-padding bg-light-blue-gradient">
            <!-- Morris chart - Sales -->
            <div class="chart tab-pane active" id="client-chart-1" style="position: relative; height: 300px;"></div>
          </div>
        </div><!-- /.nav-tabs-custom -->
        <div class="nav-tabs-custom">
          <!-- Tabs within a box -->
          <ul class="nav nav-tabs pull-right">
            <li class="active"><a href="#ftp-chart-1" data-toggle="tab">近一周</a></li>
            <li class="pull-left header"><i class="fa fa-send-o"></i>推送服务图表</li>
          </ul>
          <div class="tab-content no-padding bg-light-blue-gradient">
            <!-- Morris chart - Sales -->
            <div class="chart tab-pane active" id="ftp-chart-1" style="position: relative; height: 300px;"></div>
          </div>
        </div><!-- /.nav-tabs-custom -->
        <div class="nav-tabs-custom">
          <!-- Tabs within a box -->
          <ul class="nav nav-tabs pull-right">
            <li class="active"><a href="#mq-chart-1" data-toggle="tab">近一周</a></li>
            <li class="pull-left header"><i class="fa fa-exchange"></i>IO 服务近图表</li>
          </ul>
          <div class="tab-content no-padding bg-light-blue-gradient">
            <!-- Morris chart - Sales -->
            <div class="chart tab-pane active" id="mq-chart-1" style="position: relative; height: 300px;"></div>
          </div>
        </div><!-- /.nav-tabs-custom -->
      </section><!-- /.Left col -->
      <section class="col-lg-5 connectedSortable">
        <!-- Chat box -->
        <div class="box box-success">
          <div class="box-header">
            <i class="fa fa-comments-o"></i>
            <h3 class="box-title">讨论区</h3>
            <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
              <div class="btn-group" data-toggle="btn-toggle" >
                <button type="button" class="btn btn-default btn-sm active"><i class="fa fa-square text-green"></i></button>
                <button type="button" class="btn btn-default btn-sm"><i class="fa fa-square text-red"></i></button>
              </div>
            </div>
          </div>
          <div class="box-body chat" id="chat-box">
            <!-- chat item -->
            <div class="item">
              <img src="dist/img/user4-128x128.jpg" alt="user image" class="online">
              <p class="messageVo">
                <a href="#" class="name">
                  <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 2:15</small>
                  Mike Doe
                </a>
                I would like to meet you to discuss the latest news about
                the arrival of the new theme. They say it is going to be one the
                best themes on the market
              </p>
              <div class="attachment">
                <h4>Attachments:</h4>
                <p class="filename">
                  Theme-thumbnail-image.jpg
                </p>
                <div class="pull-right">
                  <button class="btn btn-primary btn-sm btn-flat">Open</button>
                </div>
              </div><!-- /.attachment -->
            </div><!-- /.item -->
            <!-- chat item -->
            <div class="item">
              <img src="dist/img/user3-128x128.jpg" alt="user image" class="offline">
              <p class="messageVo">
                <a href="#" class="name">
                  <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 5:15</small>
                  Alexander Pierce
                </a>
                I would like to meet you to discuss the latest news about
                the arrival of the new theme. They say it is going to be one the
                best themes on the market
              </p>
            </div><!-- /.item -->
            <!-- chat item -->
            <div class="item">
              <img src="dist/img/user2-160x160.jpg" alt="user image" class="offline">
              <p class="messageVo">
                <a href="#" class="name">
                  <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 5:30</small>
                  Susan Doe
                </a>
                I would like to meet you to discuss the latest news about
                the arrival of the new theme. They say it is going to be one the
                best themes on the market
              </p>
            </div><!-- /.item -->
          </div><!-- /.chat -->
          <div class="box-footer">
            <div class="input-group">
              <input class="form-control" placeholder="Type messageVo...">
              <div class="input-group-btn">
                <button class="btn btn-success"><i class="fa fa-plus"></i></button>
              </div>
            </div>
          </div>
        </div><!-- /.box (chat box) -->

        <!-- TO DO List -->
        <div class="box box-primary">
          <div class="box-header">
            <i class="ion ion-clipboard"></i>
            <h3 class="box-title">待办事宜</h3>
            <div class="box-tools pull-right">
              <ul class="pagination pagination-sm inline">
                <li><a href="#">&laquo;</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">&raquo;</a></li>
              </ul>
            </div>
          </div><!-- /.box-header -->
          <div class="box-body">
            <ul class="todo-list">
              <li>
                <!-- drag handle -->
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                <!-- checkbox -->
                <input type="checkbox" value="" name="">
                <!-- todo text -->
                <span class="text">Design a nice theme</span>
                <!-- Emphasis label -->
                <small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
                <!-- General tools such as edit or delete-->
                <div class="tools">
                  <i class="fa fa-edit"></i>
                  <i class="fa fa-trash-o"></i>
                </div>
              </li>
              <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                <input type="checkbox" value="" name="">
                <span class="text">Make the theme responsive</span>
                <small class="label label-info"><i class="fa fa-clock-o"></i> 4 hours</small>
                <div class="tools">
                  <i class="fa fa-edit"></i>
                  <i class="fa fa-trash-o"></i>
                </div>
              </li>
              <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                <input type="checkbox" value="" name="">
                <span class="text">Let theme shine like a star</span>
                <small class="label label-warning"><i class="fa fa-clock-o"></i> 1 day</small>
                <div class="tools">
                  <i class="fa fa-edit"></i>
                  <i class="fa fa-trash-o"></i>
                </div>
              </li>
              <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                <input type="checkbox" value="" name="">
                <span class="text">Let theme shine like a star</span>
                <small class="label label-success"><i class="fa fa-clock-o"></i> 3 days</small>
                <div class="tools">
                  <i class="fa fa-edit"></i>
                  <i class="fa fa-trash-o"></i>
                </div>
              </li>
              <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                <input type="checkbox" value="" name="">
                <span class="text">Check your messages and notifications</span>
                <small class="label label-primary"><i class="fa fa-clock-o"></i> 1 week</small>
                <div class="tools">
                  <i class="fa fa-edit"></i>
                  <i class="fa fa-trash-o"></i>
                </div>
              </li>
              <li>
                      <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>
                <input type="checkbox" value="" name="">
                <span class="text">Let theme shine like a star</span>
                <small class="label label-default"><i class="fa fa-clock-o"></i> 1 month</small>
                <div class="tools">
                  <i class="fa fa-edit"></i>
                  <i class="fa fa-trash-o"></i>
                </div>
              </li>
            </ul>
          </div><!-- /.box-body -->
          <div class="box-footer clearfix no-border">
            <button class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add item</button>
          </div>
        </div><!-- /.box -->

      </section><!-- /.Left col -->
    </div>

  </section><!-- /.content -->

</div><!-- /.content-wrapper -->
<script src="js/main/client-service-control-panel.js"></script>
<footer class="main-footer">
  <%@include file="/WEB-INF/includes/copyright.jsp"%>
</footer>

