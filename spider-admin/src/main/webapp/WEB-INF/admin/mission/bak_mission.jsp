<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<%=request.getContextPath()%>/js/bootstrap/html5shiv.min.js"></script>
      <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <![endif]-->
	    
	<style type="text/css">
	.bs-example {
	    padding: 10px 15px 15px;
	    margin: 0 -15px 15px;
	    border-color: #e5e5e5 #eee #eee;
	    border-style: solid;
	    border-width: 1px 0;
	}
	.dropdown_left_label {
	    position: relative;
	    top: 19px;
	    left: 13px;
	}
	.status_dropdown{
		margin-left: 49px;
	    top: -13px;
	}
	
	.x_search{
		float: right;
	    padding-top: 13px
	}
	</style>
  </head>
  <body>
	  <div class="container-fluid">
		<div class="row-fluid">
			<h3><strong>爬虫任务管理 </strong><small></small></h3>
			<div class="bs-example" >
			<form class="form-inline">
				<div class="form-group">
					<label for="exampleInputName2">名称</label> <input type="text"
						class="form-control" id="sName" placeholder="任务名称">
				</div>

				<div class="form-group">
					<label class="dropdown_left_label">
					状态
				</label>
					<div class="dropdown status_dropdown" >
					  <button class="btn btn-default dropdown-toggle" type="button" id="typeDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					    	<span id="curStatus" data="" >全部</span>
					    <span class="caret"></span>
					  </button>
					  <ul class="dropdown-menu" aria-labelledby="typeDropdown" id="statusContainer">
					    <li><a href="javascript:void(0)" onclick="change(this,0)">待执行</a></li>
					    <li><a href="javascript:void(0)" onclick="change(this,1)">执行中</a></li>
					    <li><a href="javascript:void(0)" onclick="change(this,2)">完成</a></li>
					    <li><a href="javascript:void(0)" onclick="change(this,3)">暂停</a></li>
					    <li><a href="javascript:void(0)" onclick="change(this,4)">终止</a></li>
	  			        <li role="separator" class="divider"></li>
					    <li><a href="javascript:void(0)" onclick="change(this)">全部</a></li>
					  </ul>
					</div>
				</div>
				
				<label class="text-right x_search">
					<button type="button" class="btn btn-primary " id="searchMission">搜索</button>
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">新增</button>
				</label>
			</form>

			<div class="view">
				<table class="table table-striped table-hover table table-bordered">
					<thead>
						<tr>
							<th>编号</th>
							<th>名称</th>
							<th>类型</th>
							<th>状态</th>
							<th>并发进程数</th>
							<th>创建时间</th>
							<th>结束时间</th>
							<th style="width: 180px; text-align: center;">操作</th>
						</tr>
					</thead>
					<tbody id="missionList">
						
						
					</tbody>
				</table>

			</div>
		</div>
		</div>
	</div>

	<!-- 新增任务Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增任务</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input type="email" class="form-control" id="inputEmail3"
									placeholder="Email">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="inputPassword3"
									placeholder="Password">
							</div>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
  </body>
<script type="text/javascript">
	var currentPage = 1;
	var pageSize = 10;
	var statusMap = {
			"0":"待执行",
			"1":"执行中",
			"2":"完成",
			"3":"暂停",
			"4":"终止"
	};
	$(function() {
// 		初始化事件
		bindEvent();
// 		初始化数据
		initData();
	})
	function initData() {
// 		getMissiConTypes();
		getMissions();
	}
	function bindEvent(){
		$("#searchMission").click(function(){
			searchMission();
		});
	}
	function searchMission(){
		currentPage = 1;
		getMissions();
	}
	function getMissions() {
		var sName = $("#sName").val();
		var curStatus = $("#curStatus").attr("data");
		$.post("../../admin/mission/queryMission", {
			"name" : sName,
			"status":curStatus,
			"currentPage":currentPage,
			"pageSize":pageSize
		}, function(data) {
			assembleMissionList(data);
		}, "json");

	}

	function getMissionTypes() {
		$.post("../../admin/mission/queryMissionTypes", {}, function(data) {
			console.log(data);
		}, "json");
	}

	function assembleMissionList(data) {
// 		console.log(data.resultList);
		$("#missionList").html("");					
		$.each(data.resultList, function(index,item){
			var html = "<tr>";
			html += "<td>"+item.ID+"</td>";
			html += "<td>"+item.NAME+"</td>";
			html += "<td>"+item.SCNAME+"</td>";
			html += "<td>"+statusMap[item.STATUS]+"</td>";
			html += "<td>"+item.CONCURRENT+"</td>";
			html += "<td>"+item.CREATE_TIME+"</td>";
			if(item.FINISH_TIME){
				html += "<td>"+item.FINISH_TIME+"</td>";
			}else{
				html += "<td></td>";
			}
			//0刚发布、1开始执行、2执行结束、3暂停、4终止
// 			"0":"待执行",
// 			"1":"执行中",
// 			"2":"完成",
// 			"3":"暂停",
// 			"4":"终止"
			var status = parseInt(item.STATUS);
			switch(status){
				case 0:
					html += "<td><button type='button' class='btn btn-default btn-small' aria-label='Left Align'><span class='glyphicon glyphicon-play' aria-hidden='true'></span>开始</button></td>"; 
					break;
				case 1:	
					html += "<td><button type='button' class='btn btn-default btn-small' aria-label='Left Align'><span class='glyphicon glyphicon-play' aria-hidden='true'></span>开始</button></td>"; 
					break;
				case 3:	
					html += "<td><button type='button' class='btn btn-default btn-small' aria-label='Left Align'><span class='glyphicon glyphicon-play' aria-hidden='true'></span>开始</button></td>"; 
					break;
				default:
					html += ""; 
			}
			html += "</tr>";
			$("#missionList").append(html);
		});
	}
	
	function change(obj,value){
		var name = $(obj).html();
		$("#curStatus").html(name).attr("data",value);
	}
</script>

</html>
