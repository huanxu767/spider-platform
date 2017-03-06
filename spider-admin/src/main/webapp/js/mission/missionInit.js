var statusMap = {
			"0":"待执行",
			"1":"执行中",
			"2":"完成",
			"3":"暂停",
			"4":"终止"
	};

$(function() {
	//初始化数据
	init();
});

function init(){
//	初始化树
	initTree();
	bindEvent();
    $(".c_left").css('max-height',$(window).height()-70);
}

function bindEvent(){
//	提交任务
	$("#submitButton").click(function(){
	    var btn = $(this).button('loading');
		addMission(btn);
	});
}


var treeData = [];
function initTree(){
	$.post("../../admin/mission/queryMissionTree", {
	}, function(data) {
		treeData = eval(data);
		$('#tree').treeview({
			data : getTree(), // data is not optional
			levels : 1,
			onNodeSelected: function(event, data) {
//				console.log(data);
				selectNode(data.ID,data.text);
			}
		});
		
	}, "json");		
}

//递归组装数据  
var i = 1;
function getTree() {
	var finalTree = new Array();
	var root = treeData.shift();
	var rootId = root.ID;
	$.each(treeData, function(index, item) {
		if (item.P_CODE == rootId) {
			finalTree.push({
				ID : item.ID,
				text : item.NAME
			});
			i++;
		}
	});
	ognisze(finalTree);
	return finalTree;
}

//递归组装数据  
function ognisze(tdata) {
	if (treeData.length >= i) {
		var tempTree = [];
		$.each(tdata, function(index, fitem) {
			var temId = fitem.ID;
			// 	    	 	console.log("temId:"+temId);
			var json = [];
			$.each(treeData, function(index, sitem) {
				if (sitem.P_CODE == temId) {
					json.push({
						ID : sitem.ID,
						text : sitem.NAME
					});
					i++;
				}
			});
			tempTree = tempTree.concat(json);
			if (json.length > 0) {
				fitem["nodes"] = json;
				fitem["selectable"] = false;
			}
		});
		return ognisze(tempTree);
	}
}


//下拉列表
function change(obj,value){
	var name = $(obj).html();
	$("#mParseType").html(name).attr("data",value);
}
function moChange(obj,value){
    var name = $(obj).html();
    $("#moParseType").html(name).attr("data",value);
}

function addMission(btn){
    var mTypeName = $("#mTypeName").html();
	var myType = $("#mType").val();
	var mDescribe = $("#mDescribe").val();
	var mURL = $("#mURL").val();
	var mParams = $("#mParams").val();
	var mSleepTime = $("#mSleepTime").val();
	var mConcurrent = $("#mConcurrent").val();
	var mparseType = $("#mParseType").attr("data");
	var mJs = $("#mJs").val();
    var isInterval = $("input:radio[name=isInterval]:checked").val();
    var cronExpression = $("#cronExpression").val();
	 $.ajax({
		type : "POST",
		url : "../../admin/mission/addMission",
		data : {
            mTypeName :mTypeName,
			type : myType,
			describe : mDescribe,
			url : mURL,
			pams : mParams,
			sleepTime : mSleepTime,
			threadNum : mConcurrent,
			parseType : mparseType,
			js : mJs,
            isInterval :isInterval,
            cronExpression :cronExpression
		},
		dataType:"json",
		success:function(data){
			btn.button('reset');
			if(data.success == false){
				alert(data.errorMsg);
			}else{
				selectNode(myType);
			}
		}
	});
}


function selectNode(id,text) {
	// 该分类是否已添加记录
	$.post("../../admin/mission/queryMission", {
		id : id
	}, function(data) {
		if(data.success == true){
			showMissionDetail(data.map);
		}else if(data.success == false && data.errorCode == "100002"){
			//该分类尚未创建任务
			createMission(id,text);
		}else{
			alert(data.errorMsg);
		}
	}, "json");
}

function showMissionDetail(data){
	$("#dashBord").hide();
	$("#addNewMission").hide();
    $("#modifyMission").hide();
//	console.log(JSON.stringify(data));
	$("#dURL").html(data.URL);
	$("#dType").html(data.TYPE);
	$("#dTypeName").html(data.TYPE_NAME);
    $("#dPams").html(data.PARAMS== undefined ?"&nbsp;":data.PARAMS);
	$("#dDesc").html(data.DESCRIBE);
	$("#dSleepTime").html(data.SLEEP_TIME);
	$("#dThreadNum").html(data.THREAD_NUM);
    $("#dIsInterval").html(data.IS_INTERVAL == 1?"是":"否");
    if(data.CRON_EXPRESSION){
        $("#dCronExpression").html(data.CRON_EXPRESSION);
    }else{
        $("#dCronExpression").html("");
    }
	$("#dParseType").html(data.PARSE_NAME);
	$("#dStartTime").html(data.CREATE_TIME);
	$("#dFinishTime").html(data.FINISH_TIME == undefined ?"&nbsp;":data.FINISH_TIME);
	$("#dJs").html(data.JS);
	drawStatusContainer(data.TYPE,data.STATUS);
	$("#missionDetail").show();
}

function modifyMissionDetail(data){
    $("#dashBord").hide();
    $("#missionDetail").hide();
    $("#moURL").val(data.URL);
    $("#moType").val(data.TYPE);
    $("#moTypeName").html(data.TYPE_NAME);
    $("#moParams").val(data.PARAMS== undefined ?"&nbsp;":data.PARAMS);
    $("#moDescribe").val(data.DESCRIBE);
    $("#moSleepTime").val(data.SLEEP_TIME);
    $("#moConcurrent").val(data.THREAD_NUM);
    if(data.CRON_EXPRESSION){
        $("#moCronExpression").val(data.CRON_EXPRESSION);
    }else{
        $("#moCronExpression").val("");
    }
    $("#moParseType").html(data.PARSE_NAME).attr("data",data.PARSE_TYPE);

    $("#moStartTime").val(data.CREATE_TIME);
    $("#moFinishTime").val(data.FINISH_TIME == undefined ?"&nbsp;":data.FINISH_TIME);
    $("#moJs").val(data.JS);
    if(data.IS_INTERVAL == 0){
        $("input:radio[name=moIsInterval]").eq(0).attr("checked","checked");
    }else{
        $("input:radio[name=moIsInterval]").eq(1).attr("checked","checked");
    }
    $("#modifyMission").show();
}

function createMission(id,text){
	$("#dashBord").hide();
	$("#missionDetail").hide();
	$("#mType").val(id);
	$("#mTypeName").html(text);
	$("#addNewMission").show();
}

function drawStatusContainer(type,tempStatus){
	//	var statusMap = {
	//	"0":"待执行",
	//	"1":"执行中",
	//	"2":"完成",
	//	"3":"暂停",
	//	"4":"终止"
	//};
	
	$("#statuName").html(statusMap[tempStatus]);
	if(tempStatus == "0"){
		//待执行    执行、修改 按钮
		$("#controlButtons").html(" <button type='button' class='btn btn-default' onclick='excuteMission("+type+")'>执行</button> " +
				"<button type='button' class='btn btn-default' onclick='modifyMission("+type+")'>修改</button>");
	}else if(tempStatus == "1"){
		//执行中   暂停、
		$("#controlButtons").html(" <button type='button' class='btn btn-default' onclick='pauseMission("+type+")'>暂停</button> ");
	}else if(tempStatus == "2"){
		//完成      执行、修改
		$("#controlButtons").html(" <button type='button' class='btn btn-default' onclick='excuteMission("+type+")'>执行</button> " +
		"<button type='button' class='btn btn-default' onclick='modifyMission("+type+")'>修改</button>");
	}else if(tempStatus == "3"){
		//暂停中    执行、修改
		$("#controlButtons").html(" <button type='button' class='btn btn-default' onclick='excuteMission("+type+")'>执行</button> " +
		"<button type='button' class='btn btn-default' onclick='modifyMission("+type+")'>修改</button>");
	}else{
		
	}
}
function excuteMission(type) {
	console.log(type + "  ");
	$.post("../../spider/startMission", {
		type : type
	}, function(data) {
		if (data.success == true) {
			drawStatusContainer(type, "1");
		} else {
			alert(data.errorMsg);
		}
	}, "json");
}
function modifyMission(type){
    // 该分类是否已添加记录
    $.post("../../admin/mission/queryMission", {
        id : type
    }, function(data) {
        modifyMissionDetail(data.map);
    }, "json");
}
function modfiySubmitMission(obj){
    var btn = $(this).button('loading');

    var mTypeName = $("#moTypeName").html();
    var myType = $("#moType").val();
    var mDescribe = $("#moDescribe").val();
    var mURL = $("#moURL").val();
    var mParams = $("#moParams").val();
    var mSleepTime = $("#moSleepTime").val();
    var mConcurrent = $("#moConcurrent").val();
    var mparseType = $("#moParseType").attr("data");
    var mJs = $("#moJs").val();
    var isInterval = $("input:radio[name=moIsInterval]:checked").val();
    var cronExpression = $("#moCronExpression").val();
    $.ajax({
        type : "POST",
        url : "../../admin/mission/modifyMission",
        data : {
            mTypeName :mTypeName,
            type : myType,
            describe : mDescribe,
            url : mURL,
            pams : mParams,
            sleepTime : mSleepTime,
            threadNum : mConcurrent,
            parseType : mparseType,
            js : mJs,
            isInterval :isInterval,
            cronExpression :cronExpression
        },
        dataType:"json",
        success:function(data){
            btn.button('reset');
            if(data.success == false){
                alert(data.errorMsg);
            }else{
               selectNode(myType);
            }
        }
    });
}

function pauseMission(type){
	$.post("../../spider/pauseMission", {
		type : type
	}, function(data) {
		if (data.success == true) {
			drawStatusContainer(type, "3");
		} else {
			alert(data.errorMsg);
		}
	}, "json");
}




