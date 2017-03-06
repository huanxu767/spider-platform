<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>更新 查询</title>

<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="<%=request.getContextPath()%>/js/bootstrap/html5shiv.min.js"></script>
      <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<br>
	sql：<textarea id="sql"></textarea>
	<br>
	<br>
	<a href="javascript:updateMission();">更新</a>
    <a href="javascript:queryMission();">查询</a>

    <br>
    result:<textarea id="result"></textarea>
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>

</body>
<script >
function updateMission() {
	$.ajax({
		type : "POST",
		url : "../../admin/mission/testUpdateSql",
		data : {
			sql:$('#sql').val(),
		},
		dataType:"text",
		success:function(data){
            $("#result").val(data);
		}
	});
}
function queryMission() {
    $.ajax({
        type : "POST",
        url : "../../admin/mission/testQuerySql",
        data : {
            sql:$('#sql').val(),
        },
        dataType:"text",
        success:function(data){
            $("#result").val(data);
        }
    });
}
</script>

</html>
