<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>网络爬虫管理平台</title>

<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/iCheck/all.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="<%=request.getContextPath()%>/js/bootstrap/html5shiv.min.js"></script>
      <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <![endif]-->


<style type="text/css">
#tree {
	width: 200px;
	background: #ddd;
}

.c_right {
	margin-left: 220px;
	/* 	background: yellow; */
	/* border: 1px solid #ddd;
	border-radius:2px; */
}

.c_left {
	width: 220px;
	float: left;
	/*     background: none repeat scroll 0 0 #f4f4f4; */
}

.x_add {
	width: 200px;
	margin-bottom: 20px;
}

.x_header {
	background: none repeat scroll 0 0 #337ab7;
	border: none;
	border-radius: 0;
	color: #fff;
}

.x_header_name {
	color: #fff;
	float: left;
	height: 50px;
	padding: 15px 15px;
	font-size: 18px;
	line-height: 20px;
}

.form-horizontal .form-group {
	margin-right: -15px;
	margin-left: -15px;
	height: 33px;
}
.file_img{
	height: 20px;
}
	.red_color{
		color:#337ab7;
	}
.breadcrumb{
	padding: 4px 15px;
	margin-bottom: 0px;
	list-style: none;
	background-color: #f5f5f5;
	border-radius: 4px;
}
	.bo_right{
		float: right;
		margin-top: -31px;
		/*float: left;*/
		/*margin-top: -31px;*/
		/*margin-left: 269px;*/
	}
</style>
</head>
<body>

	<nav class="navbar navbar-default x_header" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">爬虫管理平台</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
						<li ><a href="<%=request.getContextPath()%>/admin/mission/initMission">首页</a></li>
					<li class="active"><a href="#">日志</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<ol class="breadcrumb" id="fileBreadcrumb">
					<li><a href="#"> 全部文件</a></li>
					<%--<li><a href="#"> work01</a></li>--%>
					<%--<li class="active">active</li>--%>
				</ol>
				<a href="javascript:void(0);" class="btn btn-primary bo_right" role="button" id="anaLog">解析入库</a>
			</div>

			<!-- Table -->
			<table class="table">
				<table class="table">
					<thead>
					<tr>
						<th><input type="checkbox" name = 'headFileCheckBox' id="allCheckBox"></th>
						<th>文件名</th>
						<th>大小</th>
						<th>修改日期&nbsp;<span class="glyphicon glyphicon-arrow-down red_color"></span></th>
					</tr>
					</thead>
					<tbody id="fileList">
					</tbody>
				</table>
			</table>
		</div>

	</div>

	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>

	<script src="<%=request.getContextPath()%>/js/iCheck/icheck.js"></script>
	<script src="<%=request.getContextPath()%>/js/mission/missionFileProcess.js"></script>

</body>

</html>
