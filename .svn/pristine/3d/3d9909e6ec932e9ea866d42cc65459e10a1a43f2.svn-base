var currentPathArray = [];
$(function() {
	//初始化数据
	init();
	initCheckBox();
	bindEvent();
});

function init(){
//	列表
	initFileList();
}

function bindEvent(){
//	提交任务
	$("#anaLog").click(function(){
	    var btn = $(this).button('loading')
		anaLog(btn);
	});

}
//列表
function initFileList(filePath){
	// console.log(filePath);
	$('#allCheckBox').iCheck('uncheck');
	$.ajax({
		type : "GET",
		url : "../../admin/mission/queryLogFiles",
		data : {
			filePath : filePath
		},
		dataType:"json",
		success:function(data){
			ogniseFileList(data);
		}
	});
}

function ogniseFileList(data){
	var html = '';
	$.each(data,function(index,obj){
		// console.log(obj);
		var tempSize = obj.size + 'KB';
		var tempFileName = '';
		if(obj.isDirectory){
			//是文件夹
			tempSize = '-';
			tempFileName = '<a href = "javascript:openFile(\''+obj.name+'\');"><img src="../../img/file.png" class="file_img">'+obj.name+'</a>';
		}else{
			var sep = obj.name.substring(obj.name.lastIndexOf('.') + 1);
			// console.log(sep);
			if(sep == 'zip'){
				tempFileName += '<img src="../../img/file-zip.png" class="file_img">';
			}else{
				tempFileName += '<img src="../../img/ofile.png" class="file_img">';
			}
			tempFileName += obj.name;
		}
		html += '<tr><td><input type="checkbox" name = "fileCheckBox" value="'+obj.name+'"></td>' +
			'<td>'+tempFileName+'</td>' +
			'<td>'+tempSize+'</td>' +
			'<td>'+obj.date+'</td>' +
			'</tr>';
	});
	$('#fileList').html(html);
	initCheckBox();
}
function initCheckBox(){
	$("input[type='checkbox']").iCheck({
		checkboxClass: 'icheckbox_square-blue',
		radioClass: 'iradio_square-blue',
		increaseArea: '20%'
	});
	$("#allCheckBox").on('ifChecked', function(){
		//所有的 都选中
		$('input').iCheck('check');
	});
	$("#allCheckBox").on('ifUnchecked', function(){
		//所有的都 不选中
		$('input').iCheck('uncheck');
	});

}
function checkBoxChecked(event){

}
//打开文件夹
function openFile(file){
	$('#fileBreadcrumb').html('<li><a href="javascript:jumpFile();"> 全部文件</a></li>');
	var path = '';
	var length = currentPathArray.length;
;	for (var i = 0;i < length;i++){
		path += currentPathArray[i] +'|';
		$('#fileBreadcrumb').append('<li class="active"><a href="javascript:jumpFile(\''+ currentPathArray[i] + '\');">' + currentPathArray[i] + '</a></li>');
	}
	$('#fileBreadcrumb').append('<li class="active">' + file + '</li>');
	currentPathArray[currentPathArray.length] = file;
	initFileList(path+file);
}
//目录导航跳转
function jumpFile(file){
	var tempArray = [];
	if(file){
		for(var i = 0;i < currentPathArray.length; i++){
			if(currentPathArray[i] == file){
				//移除 至此之后的
				break;
			}
			tempArray[i] = currentPathArray[i];
		}
		currentPathArray = tempArray;
		openFile(file);
	}else{
		currentPathArray = [];
		$('#fileBreadcrumb').html('<li><a href="javascript:jumpFile();"> 全部文件</a></li>');
		initFileList();
	}
}

function anaLog(btn){
	var flag = false;
	var fileNames = '';
	$("input[name='fileCheckBox']:checkbox").each(function(){
		if($(this).context.checked){
			fileNames += $(this).val() + '|';
			flag = true;
		}
	});
	if(!flag){
		alert("未选择任何文件!");
		btn.button('reset');
		return;
	}
	console.log(fileNames);
	var path = '';
	var length = currentPathArray.length;
	for (var i = 0;i < length;i++){
		path += currentPathArray[i] +'|';
	}
	console.log(path);
	$.post("../../admin/mission/analysisLog", {
		path : path ,
		names :fileNames
	}, function(data) {
		if (data.success == true) {
			alert("解析完毕");
		} else {
			alert(data.errorMsg);
		}
		btn.button('reset');
	}, "json");
}





