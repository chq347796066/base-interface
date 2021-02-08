// JScript 文件

function M(id){
    return document.getElementById(id);//用M()方法代替document.getElementById(id)
}
function MC(t){
   return document.createElement(t);//用MC()方法代替document.createElement(t)
}

//取得页面的高宽
function getBodySize(){
   var bodySize = [];
   with(document.documentElement) {
    bodySize[0] = (scrollWidth>clientWidth)?scrollWidth:clientWidth;//如果滚动条的宽度大于页面的宽度，取得滚动条的宽度，否则取页面宽度
    bodySize[1] = (scrollHeight>clientHeight)?scrollHeight:clientHeight;//如果滚动条的高度大于页面的高度，取得滚动条的高度，否则取高度
   }
   return bodySize;
}
//判断浏览器是否为IE
function isIE(){
      return (document.all && window.ActiveXObject && !window.opera) ? true : false;
} 

//创建遮盖层
function popCoverDiv(){
   if (M("cover_div")) {
    if (isIE()) {
      M("cover_div").style.filter = "Alpha(Opacity=0)";//IE逆境
     } else {
      M("cover_div").style.opacity = 0;
     }
   //如果存在遮盖层，则让其显示 
    M("cover_div").style.display = 'block';
   } else {
   //否则创建遮盖层
    var coverDiv = MC('div');
    document.body.appendChild(coverDiv);
    coverDiv.id = 'cover_div';
    with(coverDiv.style) {
     position = 'absolute';
     background = '#CCCCCC';
     left = '0px';
     top = '0px';
     var bodySize = getBodySize();
     width = bodySize[0] + 'px'
     height = bodySize[1] + 'px';
     zindex = 0;
     if (isIE()) {
      filter = "Alpha(Opacity=0)";//IE逆境
     } else {
      opacity = 0;
     }
    }
   }
    showBackground(M("cover_div"),50);
}

//让背景渐渐变暗 
function showBackground(obj,endInt) 
{ 
 if(isIE()) 
 { 
 obj.filters.alpha.opacity+=1; 
 if(obj.filters.alpha.opacity<endInt) 
 { 
 setTimeout(function(){showBackground(obj,endInt)},5); 
 } 
 }else{ 
 var al=parseFloat(obj.style.opacity);al+=0.01; 
 obj.style.opacity=al; 
 if(al<(endInt/100)) 
 {setTimeout(function(){showBackground(obj,endInt)},5);} 
 } 
} 

function close(){
    M("cover_div").style.display='none';
}

//// 实现可拖动的div
//var px = 0;
//var py = 0;
//var begin = false;
////是否要开启透明效果
//var enableOpacity = false; // 默认不允许
//var myDragDiv;
//function down(oDiv) {
//    myDragDiv = oDiv;
//    begin = true;
//    oDiv.style.cursor = "default";
//    //oDiv.focus();
//    //event.srcElement.setCapture();
//    px = oDiv.style.pixelLeft - event.x;
//    py = oDiv.style.pixelTop - event.y;
//}
//function document.onmousemove() {
//    if (myDragDiv != null && typeof (myDragDiv) != "undefined") {
//        if (begin) {
//            if (enableOpacity) { myDragDiv.style.filter = "Alpha(opacity=30)"; }  // 滤镜 
//            myDragDiv.style.pixelLeft = px + event.x;
//            myDragDiv.style.pixelTop = py + event.y;
//        }
//    }
//}
//function document.onmouseup() {
//    if (myDragDiv != null && typeof (myDragDiv) != "undefined") {
//        begin = false;
//        if (enableOpacity) { myDragDiv.style.filter = "Alpha(opacity=100)"; } // 滤镜 
//        myDragDiv.style.cursor = "default";
//        //event.srcElement.releaseCapture();
//        myDragDiv = null;
//    }
//}

//div居中
var divobj;
function changeIt(obj) {
    divobj=obj;
    var myobj=document.getElementById(obj);
    var  bodyhh=document.documentElement.clientHeight;
    var  bodyww=document.documentElement.clientWidth;
    //var bodySize=getBodySize();
    var objhh=myobj.clientHeight;
    var objww=myobj.clientWidth;
    myobj.style.top=(bodyhh-objhh)/2+"px";
    myobj.style.left=(bodyww-objww)/2+"px";
    myobj.style.visibility ='visible';
}





