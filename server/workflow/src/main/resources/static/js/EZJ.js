


var EZJ = new function ()
{
    this.version = "v1.0";
    this.plugin = new EZJ_Plugin();
}


//加载插件时：
//IE 把 404 等错误内容也当作 JavaScript 代码加载，只是这些内容是错误的 JavaScript 代码。
//Firefox 不会加载 404 等状态的内容。
//所以请确保插件地址是正确的，以使能够正确加载，以避免在不同浏览器中发生不同的错误。
//属性 location，字符串。插件所在文件夹的地址。
//事件 onComplete，函数。加载完成一个插件后触发该函数，并将加载完成的插件名称作为参数传递进入事件处理函数。
//方法 load。加载一个或多个插件。
//load(pluginInfo1[, pluginInfo2[, pluginInfo3[, ...[, pluginInfoN]]]])
//参数 pluginInfo 字符串或对象。
//pluginInfo 可以是插件名称，此时 location + pluginName + ".js" 组成插件地址。
//pluginInfo 也可以是含有 name、url 属性的对象。name 表示插件名称，url 表示插件地址，此时插件地址不受 location、插件名称等的影响。
function EZJ_Plugin()
{
    this.location = "";
    this.onComplete = null;
    this.load = function ()
    {
        var pluginName, pluginUrl;
        for (var i = 0; i < arguments.length; i++)
        {
            if (typeof(arguments[i]) == "string")
            {
                pluginName = arguments[i];
                pluginUrl = this.location + pluginName + ".js";
            }
            else
            {
                pluginName = arguments[i].name;
                pluginUrl = arguments[i].url;
            }
            
            var script = $C("script", [{name:"type", value:"text/javascript"}]);
            if (this.onComplete)
            {
                var onComplete = this.onComplete;
                
                //Firefox 一类响应 onload，并且无 readyState。
                script.onload = function ()
                {
                    onComplete(pluginName);
                }
                
                //IE 一类浏览器。
                script.onreadystatechange = function ()
                {
                    if (script.readyState == 4 || script.readyState == "loaded" || script.readyState == "complete")
                    {
                        onComplete(pluginName);
                    }
                }
            }
            script.setAttribute("src", pluginUrl);
            $C(script, null, document.body);
        }
    }
}


//================================================================================


//根据元素 Id，获取一个或多个元素对象。
//语法：$(element1[, element2[, element3[, ...[, elementN]]]])
//参数 element，字符串或对象。控件 Id 或控件对象。
//如果只有一个参数，则返回元素对象。
//如果有多个参数，则以数组的形式返回元素对象。
function $obj()
{
    var element = null;
    var elements = [];

    for (var i = 0; i < arguments.length; i++)
    {
        if (typeof(arguments[i]) == "string")
        {
            element = document.getElementById(arguments[i]);
        }
        else
        {
            element = arguments[i];
        }
        elements.push(element);
    }
    
    if (arguments.length <= 1)
    {
        return element
    }
    
    return elements;
}


//获取、设置控件的值。将控件的 value、src、innerHTML 属性值作为值。
//语法：$V(control[, value])
//参数 control，字符串或对象。要获取和/或设置值的控件。
//参数 value，字符串。控件的新值。
//返回控件的值或新值。
function $V(control)
{
    var result = null;
    
    var e = $obj(control);
    if (typeof(e.value) != "undefined")
    {
        if (arguments.length >= 2)
        {
            e.value = arguments[1];
        }
        result = e.value;
    }
    else if (typeof(e.src) != "undefined")
    {
        if (arguments.length >= 2)
        {
            e.src = arguments[1];
        }
        result = e.src;
    }
    else if (typeof(e.innerHTML) != "undefined")
    {
        if (arguments.length >= 2)
        {
            e.innerHTML = arguments[1];
        }
        result = e.innerHTML;
    }
    
    return result;
}


//设置控件是否可用。
//语法：$E(control, enable)
//参数 control，字符串或对象。
//参数 enable，布尔。
function $E(control, enable)
{
    $obj(control).disabled = !enable;
}


//设置控件是否显示。
//语法：$D(control, display)
//参数 control，字符串或对象。
//参数 display，布尔或字符串。通过 true、false 或 block、none 来设置是否显示。
function $D(control, display)
{
    if (display || display == "block")
    {
        $obj(control).style.display = "block";
    }
    else if (!display || display == "none")
    {
        $obj(control).style.display = "none";
    }
}


//创建、设置、追加控件。支持 text、textarea、radio、checkbox、button、submit、reset 以及其它控件名称。
//语法：$C(control[, attributes[, parentControl]])
//参数 control，字符串或对象。要创建的控件名称或控件对象。
//参数 attributes，对象数组。含有 name、value 属性的对象数组。
//参数 parentControl，字符串或对象。创建的控件存放的位置。
//返回新创建或新设置的控件。
function $C(control)
{
    var e = null;
    if (typeof(control) == "string")
    {
        switch (control)
        {
            case "text":
                e = document.createElement("input");
                e.type = "text";
                break;
            case "textarea":
                e = document.createElement("textarea");
                break;
            case "radio":
                e = document.createElement("input");
                e.type = "radio";
                break;
            case "checkbox":
                e = document.createElement("input");
                e.type = "checkbox";
                break;
            case "button":
                e = document.createElement("input");
                e.type = "button";
                break;
            case "submit":
                e = document.createElement("input");
                e.type = "submit";
                break;
            case "reset":
                e = document.createElement("input");
                e.type = "reset";
                break;
            default:
                e = document.createElement(control);
                break;
        }
    }
    else
    {
        e = control;
    }
    
    
    //设置控件
    if (arguments.length >= 2)
    {
        var attributes = arguments[1];
        if (attributes)
        {
            for (var i = 0; i < attributes.length; i++)
            {
                e.setAttribute(attributes[i].name, attributes[i].value);
            }
        }
    }
    
    
    //追加控件
    if (arguments.length >= 3)
    {
        var parentControl = arguments[2];
        if (parentControl)
        {
            $obj(parentControl).appendChild(e);
        }
    }
    
    return e;
}


//================================================================================


//String

String.prototype.left = function (length)
{
    return this.substr(0, length);
}


String.prototype.right = function (length)
{
    return this.substr(this.length - length, length);
}


String.prototype.trimLeft = function ()
{
    return this.replace(/^[ 　]+/gi, "");
}


String.prototype.trimRight = function ()
{
    return this.replace(/[ 　]+$/gi, "");
}


String.prototype.trim = function ()
{
    return this.trimLeft().trimRight();
}


String.prototype.padLeft = function (totalWidth, chr)
{
    var str = "";
    for (var i = 0; i < totalWidth - this.length; i++)
    {
        str += chr;
    }
    
    return str + this;
}


String.prototype.padRight = function (totalWidth, chr)
{
    var str = "";
    for (var i = 0; i < totalWidth - this.length; i++)
    {
        str += chr;
    }
    
    return this + str;
}


//Array

Array.prototype.max = function ()
{
   var max = this[0];
   for (var i = 1; i < this.length; i++)
   {
       if (max < this[i])
       {
           max = this[i];
       }
   }
   
   return max;
}


Array.prototype.min = function ()
{
   var min = this[0];
   for (var i = 1; i < this.length; i++)
   {
       if (min > this[i])
       {
           min = this[i];
       }
   }
   
   return min;
}


Array.prototype.checkRepeat = function ()
{
    for (var i = 0; i < this.length - 1; i++)
    {
        for (var j = i + 1; j < this.length; j++)
        {
            if (this[i] == this[j])
            {
                return true;
            }
        }
    }
    
    return false;
}