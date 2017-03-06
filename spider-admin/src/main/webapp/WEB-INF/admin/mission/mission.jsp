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
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.css"
	rel="stylesheet">

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
    /*overflow: auto;*/
}

.c_left {
	width: 220px;
	float: left;
    overflow: auto;
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

.x_long_button {
	width: 100px;
}

#addNewMission{
 	display: none; 
}

#missionDetail{
	display: none; 
}
.margin-left-220{
	margin-left: 220px;
}
.margin-top-15{
	margin-top:15px;
}
    #modifyMission{
        display: none;
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
				<li class="active"><a href="#">首页</a></li>
				<li><a href="<%=request.getContextPath()%>/admin/mission/initMissionLog">日志</a></li>
			</ul>
		</div>
	</div>
</nav>
	<div class="container-fluid">
		<div class="header "></div>
		<div class="c_left">
			<!-- 			<button type="button" class="btn btn-default btn-lg btn-block x_add">新建任务</button> -->
			<div id="tree" class="treeview"></div>
		</div>
		<div class="c_right" id="content">
			<div id="dashBord">
				<div class="jumbotron"> 
			        <h1>DashBord</h1>
	 		        <p>待建设</p> 
 	      		</div>
			</div>
			
			<!--任务明细 begin -->
			<div id="missionDetail">
			
			<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title" >任务状态控制</h3>
					</div>
					
					
					<div class="panel-body" style="height: 124px;">
						<h4 >当前状态：<span class="label label-info " id="statuName" ></span></h4>
						<div class="btn-group margin-top-15" role="group" aria-label="..." id="controlButtons">
						</div>
					</div>
				</div>
				
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title" >明细</h3>
<!-- 						<div class="tools"> -->
<!-- 											<a href="#box-config" data-toggle="modal" class="config"> -->
<!-- 												<i class="fa fa-cog"></i> -->
<!-- 											</a> -->
<!-- 											<a href="javascript:;" class="reload"> -->
<!-- 												<i class="fa fa-refresh"></i> -->
<!-- 											</a> -->
<!-- 											<a href="javascript:;" class="collapse"> -->
<!-- 												<i class="fa fa-chevron-up"></i> -->
<!-- 											</a> -->
<!-- 											<a href="javascript:;" class="remove"> -->
<!-- 												<i class="fa fa-times"></i> -->
<!-- 											</a> -->
<!-- 										</div> -->
						
					</div>
					<div class="panel-body">

						<form class="form-horizontal xform" role="form" >
							<input type="hidden" value="" id="dType" />
							
							<div class="form-group">
								<label  class="col-sm-2 control-label">名称</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dTypeName"></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label">URL</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dURL"></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label">参数</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;height: 100px;overflow: auto;" id="dPams"></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label">睡眠时间</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dSleepTime"></label>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label">并发数</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dThreadNum"></label>
							</div>

                            <div class="form-group">
                                <label  class="col-sm-2 control-label">是否循环执行</label>
                                <label  class="col-sm-10 control-label"
                                        style="text-align: left;" id="dIsInterval"></label>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">定时器参数</label>

                                <label  class="col-sm-10 control-label"
                                        style="text-align: left;" id="dCronExpression"></label>
                            </div>

							<div class="form-group">
								<label  class="col-sm-2 control-label">描述</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dDesc"></label>
							</div>

							<div class="form-group">
								<label  class="col-sm-2 control-label">逻辑处理类型</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dParseType"></label>
							</div>

							<div class="form-group">
								<label  class="col-sm-2 control-label">开始时间</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dStartTime"></label>
							</div>

							<div class="form-group">
								<label  class="col-sm-2 control-label">结束时间</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dFinishTime"></label>
							</div>
							<div class="form-group x_row_height" style="height: 70px;">
								<label  class="col-sm-2 control-label">JS脚本</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="dJs"></label>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!--任务明细 end -->
			<!--增加信任务 begin -->
			<div id="addNewMission">
				<div class="alert alert-warning alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<strong>提醒!</strong> 该分类尚未创建任务！
				</div>
				
				
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title" >新增任务</h3>
					</div>
					<div class="panel-body">

						<form class="form-horizontal xform" role="form" id="missionForm">
							<input type="hidden" value="" id="mType" />
							<div class="form-group">
								<label  class="col-sm-2 control-label">名称</label>
								<label  class="col-sm-10 control-label"
									style="text-align: left;" id="mTypeName"></label>

							</div>


							<div class="form-group">
								<label  class="col-sm-2 control-label">URL</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="mURL"
										placeholder="URL">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label">参数</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="mParams"
										placeholder="参数">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label">睡眠时间</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="mSleepTime"
										placeholder="单位毫秒">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label">并发数</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="mConcurrent"
										placeholder="任务执行并发数">
								</div>
							</div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">是否循环执行</label>
                                <div class="col-sm-10">
                                    否：<input type="radio" name="isInterval" value="0" checked="checked" >
                                    是：<input type="radio" name="isInterval" value="1">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">定时器参数</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="cronExpression"
                                           placeholder="如：0/5 * * * * ?">
                                </div>
                            </div>

<!-- 							<div class="form-group"> -->
<!-- 								<label  class="col-sm-2 control-label">开始页</label> -->
<!-- 								<div class="col-sm-10"> -->
<!-- 									<input type="text" class="form-control" id="mStartPage" -->
<!-- 										placeholder="开始页"> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label  class="col-sm-2 control-label">截止页</label> -->
<!-- 								<div class="col-sm-10"> -->
<!-- 									<input type="text" class="form-control" id="mEndPage" -->
<!-- 										placeholder="截止页"> -->
<!-- 								</div> -->
<!-- 							</div> -->

							<div class="form-group">
								<label  class="col-sm-2 control-label">描述</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="mDescribe"
										placeholder="描述,长度不超过128个字">
								</div>
							</div>

							<div class="form-group">
								<label  class="col-sm-2 control-label">逻辑处理类型</label>
								<div class="col-sm-10">
									<div class="dropdown status_dropdown">
										<button class="btn btn-default dropdown-toggle" type="button"
											id="dropdownMenu1" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="true">
											<span id="mParseType" data="">请选择</span> <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
											<li><a href="javascript:void(0)"
												onclick="change(this,100001)">分页类型1</a></li>
											<li><a href="javascript:void(0)"
												onclick="change(this,100002)">分页类型2</a></li>
                                            <li><a href="javascript:void(0)"
                                                   onclick="change(this,100032)">分页类型3</a></li>
                                            <li><a href="javascript:void(0)"
                                                   onclick="change(this,1000346)">百度类型</a></li>
										</ul>
									</div>
								</div>
							</div>


							<div class="form-group x_row_height" style="height: 70px;">
								<label  class="col-sm-2 control-label">JS脚本</label>
								<div class="col-sm-10">
									<textarea class="form-control" rows="3" id="mJs"></textarea>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-4">
									<button type="button" class="btn btn-primary x_long_button"
										id="submitButton">提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!--增加信任务 end -->
            <!-- 修改任务 begin-->
            <div id="modifyMission">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title" >修改任务</h3>
                    </div>
                    <div class="panel-body">

                        <form class="form-horizontal xform" role="form" id="moMissionForm">
                            <input type="hidden" value="" id="moType" />
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">名称</label>
                                <label  class="col-sm-10 control-label"
                                        style="text-align: left;" id="moTypeName"></label>

                            </div>


                            <div class="form-group">
                                <label  class="col-sm-2 control-label">URL</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="moURL"
                                           placeholder="URL">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">参数</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="moParams"
                                           placeholder="参数">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">睡眠时间</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="moSleepTime"
                                           placeholder="单位毫秒">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">并发数</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="moConcurrent"
                                           placeholder="任务执行并发数">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">是否循环执行</label>
                                <div class="col-sm-10">
                                    否：<input type="radio" name="moIsInterval" value="0">
                                    是：<input type="radio" name="moIsInterval" value="1">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">定时器参数</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="moCronExpression"
                                           placeholder="如：0/5 * * * * ?">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">描述</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="moDescribe"
                                           placeholder="描述,长度不超过128个字">
                                </div>
                            </div>

                            <div class="form-group">
                                <label  class="col-sm-2 control-label">逻辑处理类型</label>
                                <div class="col-sm-10">
                                    <div class="dropdown status_dropdown">
                                        <button class="btn btn-default dropdown-toggle" type="button"
                                                id="dropdownMenu2" data-toggle="dropdown"
                                                aria-haspopup="true" aria-expanded="true">
                                            <span id="moParseType" data="">请选择</span> <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                            <li><a href="javascript:void(0)"
                                                   onclick="moChange(this,100001)">分页类型1</a></li>
                                            <li><a href="javascript:void(0)"
                                                   onclick="moChange(this,100002)">分页类型2</a></li>
                                            <li><a href="javascript:void(0)"
                                                   onclick="moChange(this,100032)">分页类型3</a></li>
                                            <li><a href="javascript:void(0)"
                                                   onclick="moChange(this,1000346)">百度类型</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>


                            <div class="form-group x_row_height" style="height: 70px;">
                                <label  class="col-sm-2 control-label">JS脚本</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control" rows="3" id="moJs"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-4">
                                    <button type="button" class="btn btn-primary x_long_button"
                                            id="modify" onclick="modfiySubmitMission()">修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- 修改任务end-->
		</div>
		<!-- 		右侧 end -->

	</div>
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-treeview.js"></script>
	<script src="<%=request.getContextPath()%>/js/mission/missionInit.js"></script>
</body>

</html>
