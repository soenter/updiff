<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
    String rootPathInHeader = request.getContextPath();
%>
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/font-awesome/4.4.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/ionicons/2.0.1/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet" href="<%=rootPathInHeader%>/dist/css/skins/skin-blue-light.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/plugins/iCheck/flat/blue.css">
<!-- Morris chart -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/plugins/morris/morris.css">
<!-- jvectormap -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<!-- Date Picker -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/plugins/datepicker/datepicker3.css">
<!-- Daterange picker -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/plugins/daterangepicker/daterangepicker-bs3.css">
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet" href="<%=rootPathInHeader%>/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="<%=rootPathInHeader%>/js/html5shiv.min.js"></script>
<script src="<%=rootPathInHeader%>/js/respond.min.js"></script>
<![endif]-->
<!-- jQuery 2.1.4 -->
<script src="<%=rootPathInHeader%>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="<%=rootPathInHeader%>/js/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.5 -->
<script src="<%=rootPathInHeader%>/bootstrap/js/bootstrap.min.js"></script>
<!-- Morris.js charts -->
<script src="<%=rootPathInHeader%>/js/raphael-min.js"></script>
<script src="<%=rootPathInHeader%>/plugins/morris/morris.min.js"></script>
<!-- Sparkline -->
<script src="<%=rootPathInHeader%>/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script src="<%=rootPathInHeader%>/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="<%=rootPathInHeader%>/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- jQuery Knob Chart -->
<script src="<%=rootPathInHeader%>/plugins/knob/jquery.knob.js"></script>
<!-- daterangepicker -->
<script src="<%=rootPathInHeader%>/js/moment.min.js"></script>
<script src="<%=rootPathInHeader%>/plugins/daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="<%=rootPathInHeader%>/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="<%=rootPathInHeader%>/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="<%=rootPathInHeader%>/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=rootPathInHeader%>/plugins/fastclick/fastclick.min.js"></script>


<script src="<%=rootPathInHeader%>/js/sdrift.js"></script>