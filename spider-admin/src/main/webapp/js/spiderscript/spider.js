//异步加载脚本
function loadScript(url, callback){
    var script = document.createElement("script")
    script.type = "text/javascript";
    if (script.readyState){ //IE
        script.onreadystatechange = function(){
            if (script.readyState == "loaded" ||
                script.readyState == "complete"){
                script.onreadystatechange = null;
                callback();
            }
        };
    } else { //Others: Firefox, Safari, Chrome, and Opera
        script.onload = function(){
            callback();
        };
    }
    script.src = url;
    document.body.appendChild(script);
}

loadScript("http://code.jquery.com/jquery.js",function(){console.log("加载完毕")});