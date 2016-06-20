var rootDir="/uploadfile"; //上传下载的全局变量
/**
 * 复制一个对象，返回一个新的对象
 */
function clone(myObj){
  if(isArray(myObj)){ //复制数组
  var myNewArr = new Array();
  	for(var i=0;i<myObj.length;i++){
  		myNewArr[i]=clone(myObj[i]);
  	}
  	return myNewArr;
  }
  if(typeof(myObj) != 'object') return myObj;
  if(myObj == null) return myObj;
  
  var myNewObj = new Object();
  //复制对象
  for(var i in myObj)
     myNewObj[i] = clone(myObj[i]);
  
  return myNewObj;
}

//值复制一级属性 ,不支持属性
function cloneOne(myObj){
	/*
	  if(isArray(myObj)){ //复制数组
	  var myNewArr = new Array();
	  	for(var i=0;i<myObj.length;i++){
	  		myNewArr[i]=clone(myObj[i]);
	  	}
	  	return myNewArr;
	  }
	  */
	  if(typeof(myObj) != 'object') return myObj;
	  
	  var myNewObj = new Object();
	  //复制对象
	  for(var i in myObj){
		  try{
			  if(myObj[i]){
				  if(typeof(myObj[i])!= 'object'){
						  myNewObj[i] = myObj[i];
				  }
			  }
		  } catch (e) {
		}
	  }	  
	  return myNewObj;
	}

/**
 * 清除页面上的【...】和 @【...】
 */
function clearObjectByHtml(obj){
	var ht = $(obj).html();
	ht=ht.replace(new RegExp('@【(.*?)】','g'),"");
	ht=ht.replace(new RegExp('【(.*?)】','g'),"");
	$(obj).html(ht);
}

/**
 * subdom 是否在parentdom 元素内，即subdom 是否parentdom的子元素
 * @param subdom
 * @param parentdom
 * @returns {Boolean}
 */
function isInNode(subdom ,parentdom){
 // alert(parentdom.id);
  var s= $(parentdom).find(subdom);
  if(s[0])
	  return true;
  else
	  return false;
  
  /*var s1=subdom.outerHTML;
  var s2=parentdom.outerHTML;
  var p1=subdom.parentNode;
  var p2= parentdom.parentNode;
  if(s1==s2){
    if( p1.outerHTML&&p2.outerHTML){
    	var s3= p1.outerHTML;
  		var s4= p2.outerHTML;
  		 if(s3==s4)
  		   return true;
    }
  }
 
   if(parentdom.hasChildNodes()){
   	    var c=parentdom.childNodes;
   	  for(var i=0;i<c.length;i++){
   	  	  var b= isInNode(subdom,parentdom.childNodes[i]);
   	  	  if(b)
   	  	  	return true;
   	  }
   }
 return false;*/
}
/**
 * 获取元素的outerText，兼容IE和firefox
 * @param obj
 * @returns
 */
function getOuterText(obj){
	var s= $g(obj);
	if(s){
		if(s.outerText)
			return s.outerText;
		else{
		 	return $('<div/>').append($(s).clone()).text();		
		}
	}
}

/**
 * 获取 页面上的 元素兼容 all,getById, getByName
 * 最加判断只获取某个对象内的数据
 * @param element 所需获取的元素的ID，或者name 属性
 * @param parentId 指定获取的element 元素，是在某个根对象下面
 */
function $g(element ,parentId){
	if(parentId&&parentId!=""){//如果父类Id存在
		var gg=$g(parentId);
		if(gg){ //父对象比较存在

			  if (typeof element == 'string'){
			    var s=null;
			    if(element.charAt(0)=='#'){
			    	 s= $(gg).find(element);
			      	 return s[0];
			    }else{
			    	s=$(gg).find('#'+element);//优先获取ID类的对象
			    	if(s[0])
			    		return s[0];
			    	else{ //再获取 name 属性的对象
			    		var ss=$(gg).find("[name='"+element+"']");
			    		if(ss.length==0){
			    			return null;
			    		}
			    		if(ss.length==1)
			    			return ss[0];
			    		else if(ss.length>1){ //多个name属性相同的值
			    			s=new Array();
			    			for(var i=0;i<ss.length;i++){
			    				s[i]=ss[i];
			    			}
			    		return s;
			    		}
			    			
			    	}
			    }
				return s;
			    }
			
//		  var s= $g(element);
//		  if(s){
//			  if(s.length&&!s.tagName){ //防止是具有 length 属性的htmldom 对象
//			   var rs=new Array();
//			   var j=0;
//			     for(var i=0;i<s.length;i++){
//			  	 	if(isInNode(s[i],gg)){
//			  	 		rs[j]=s[i];
//			  	 		j++;
//			  	 	};
//			  	 }
//			  	 if(j==0)
//			  	 	return null;
//			  	 else if(j==1){
//			  	 	return rs[0];
//			  	 }else{
//			  	 	return rs;
//			  	 }
//			  	 
//			}else{
//			  if(isInNode(s,gg)) //如果在指定的 parentId 之内
//			  	 return s;
//			}
//		  }
//		  
		}else{
		  	alert("找不到"+parentId+"的ID 对象!");
		  	return null;
		}
	  
	 }else{
	  if (typeof element == 'string'){
	    var s=null;
	    if(element.charAt(0)=='#'){
	    	 s= $(element);
	      	 return s[0];
	    }else{
	    	s=$('#'+element);//优先获取ID类的对象
	    	if(s[0])
	    		return s[0];
	    	else{ //再获取 name 属性的对象
	    		var ss=$("[name='"+element+"']");
	    		if(ss.length==0){
	    			return null;
	    		}
	    		if(ss.length==1)
	    			return ss[0];
	    		else if(ss.length>1){ //多个name属性相同的值
	    			s=new Array();
	    			for(var i=0;i<ss.length;i++){
	    				s[i]=ss[i];
	    			}
	    		return s;
	    		}
	    			
	    	}
	    }
		return s;
	    } else {
	    	return element;
	    }
	 
	 }
		return null;
 }


//判断某个对象是否为数组,不能对使用$g() 之类的方法得到的dom对象使用
function isArray(o) {   
  return Object.prototype.toString.call(o) === '[object Array]';    
} 
/**
 * 获取下拉框选中像的text文本
 * @param element 下拉框name 或id属性
 * @param selectedValue 获取指定的选中值的文本描述，如果为空，则默认获取当前所有选中下拉框中的值对应的中文
 * @param isnull 如果没有找到对应的值，则返回是否返回空字符串，默认返回 selectedValue 的值
 * @returns 如果多选，则返回值以,号分割
 */
function getSelectText(element,selectedValue,isnull){
if(typeof element == 'string'){
     element=$g(element);
}
  if(!element) //如果对象不存在，直接返回原始值
	  return selectedValue;
//选定select的option
  var opt = element.options;	
  if(opt == undefined){
   return "";
  };
  var text="";
  for(var i = 0;i<opt.length;i++){
	  if(selectedValue){ //单选值
	    if(opt[i].value==selectedValue){
	    	//alert(opt[i].innerHTML);
	    	if(opt[i].innerText){
	    		return opt[i].innerText;
	    	}else{
	    		return opt[i].innerHTML;
	    		
	    	}
	     }
	  }else{//默认所有选中值的文本
		  if(opt[i].selected){
			  if(opt[i].innerText){
				  text+= opt[i].innerText+",";
			  }else{
				  text+= opt[i].innerHTML+",";
			  }
		     }
	  }
  }
  if(text.length>1)
	  text=text.substring(0,text.length-1);
  else {
	  if(isnull)
	  text=null;
  	else
	  text=selectedValue;
  }
  // 如果没有任何匹配，则返回原值
  return text;
}
/**
 * 获取指定的 checkbox ,radio 的选中项的值
 * @param element : 指定的 checkbox ,radio 对象，可传入ID，或者dom 对象
*/
function getCheckedValue(element){
if (typeof element == 'string'){
      element=$g(element);
 }
   var s=element;
   var v="";
	if(s.length){
	    for(var i=0;i<s.length;i++){
	    	if(s[i].checked)
	    	   v+=s[i].value+",";
	    }
	    if(v.length>1)
	      v=v.substring(0,v.length-1);
	}else{
		if(s.checked)
		  v=s.value;
	}
	return v;
}

/**
 * 获取指定的 checkbox ,radio 的选中项的dom对象
 * @param element : 指定的 checkbox ,radio 对象，可传入ID，或者dom 对象
 * @returns dom 对象数组
*/
function getCheckedDom(element){
if (typeof element == 'string'){
      element=$g(element);
 };
 var list=new Array();
   var s=element;
	if(s.length){
	    for(var i=0;i<s.length;i++){
	    	if(s[i].checked)
	    	   list.push(s[i]);
	    }
	}else{
		if(s.checked)
			list.push[s];
	}
	return list;
}

/**
 *获取checkbox ,radio 的选中项对应的中文描述
 * @param element : 指定的 checkbox ,radio 对象，可传入ID，或者dom 对象
 * @param value: 可为空，或指定的value 值对应的中文描述 
 */
function getCheckedText(element,value){
if (typeof element == 'string'){
      element=$g(element);
 }
   var s=element;
   var v="";
 if(value){ // 如果指定值则返回指定的value 对应的中文描述
		if(s.length){
		    for(var i=0;i<s.length;i++){
		    	if(s[i].value==value)
		    	   if(s[i].nextSibling){
		    			  return getOuterText(s[i].nextSibling);
		    	   }else{
		    		   return s[i].nextSibling.nodeValue;
		    	   }
		    }
		    
		}else{
			if(s.value==value){
				if(s.nextSibling)
		    	    return getOuterText(s.nextSibling);
				v=s.nextSibling.nodeValue;
			}
			 
		}
	}else{ //否则默认获取所有选中值的中文说明
	  var v2=getCheckedValue(s);
	  if(v2=="")
	  	 return "";
	//  alert(v2);
	  if(v2.toString().indexOf(",")>0){ //默认多个数值之间用,号分隔
	      var vv=v2.split(",");
	      for(var i=0;i<vv.length;i++){
	   //   	alert(getCheckedText(s,vv[i])+",vi="+vv[i]);
	       		v+=getCheckedText(s,vv[i])+",";
	       		}
	      if(v.length>1)
	      v=v.substring(0,v.length-1);
	  }else{
	  	 v=getCheckedText(s,v2);
	  }
	}
	return v;
}
/**
 * 获取values 对应的值， 支持多个值之间用 , 号分隔。
 */ 
function getCheckedTextByValues(element,values){
if(values==null)
	return "";
 var value;
if(isArray(values)){ //暂不支持数组类数据的回填
	 value=values;
}
if (typeof element == 'string'){
      element=$g(element);
 }
    value= values.split(",");
	var s="";
	for(var i=0;i<value.length;i++){
		if("select"==element.tagName.toLowerCase()){
			s+=getSelectText(element,value[i])+",";
		}else{
			s+=getCheckedText(element,value[i])+",";
		}
	}
	if(s.length>1)
		s=s.substring(0,s.length-1);
	return s;	
}

/**
 * 从下拉框或者ul标签中获取数据字典的值对应文本
 * 可用select
 * 需要加样式时使用ul
 * @param element 对应下拉框或者 ul标签元素，包含属性value或者values，例如：
 * 	<ul>
 * 		<li values="001">申请中</li>
 *		<li values="default">默认</li>	默认的选项，即无匹配值时，默认此项，可选
 * 	</ul>
 * 
 * @param values 需要转换的 value 值，多个之间以 ,号分割 
 * @returns {String} 返回对应的 下拉框文本或者 li 里面的 html 
 */
function transValueToText(element, values){
	if(values==null)
		return "";
	values = ""+values;
	var vals = values.split(",");
	var km = "";
	for (var j=0; j<vals.length; j++){
		var value= $(element).find("[value='"+vals[j]+"']").html();
		if (!value){
			value= $(element).find("[values='"+vals[j]+"']").html();
		}
		if (value){
			km += value+" ";
		}
	}
	if (km==""){	//取默认
		var value = $(element).find("[value='default']").html();
		if (!value){
			value= $(element).find("[values='default']").html();
		}
		if (value){
			km += value+" ";
		}
	}
	return km;
}
//调试用类，测试某个对象有哪些属性
function alertObjcet(o){
 if (typeof o == 'string'){
      return o;
 }
 var s="";
  for(var name in o){
     s+=name+"="+o[name]+"\n";
  }
  return s;
}

/**
 * 设置 某个页面元素的 属性值,  支持普通输入域, 单选，多选 框等
 * @param element 为 表单域 的id 或者是 name 属性
 * @param value  可以为 字符串 或 字符串数组
 */
function $setVal(element , value,parentId){
// alert(element+","+value);
if(typeof element == 'string'){
 	var s= $g(element,parentId);
  }else{
 	s=element;
}
if(s)
	if(s.length&&!s.tagName){ //如果是数组
		// 判断是否 单选, 多选 
		var tagName = s[0].tagName.toLowerCase();
		//alert(element+","+tagName+","+value);
	    if("input"==tagName){
			var tagNametype = s[0].type.toLowerCase();
			//alert(tagNametype+"-000;"+value+";"+isArray(value));
	      	if(tagNametype=="checkbox"||tagNametype=="radio"){
	      	//判断 value 是否数组, 如果是，则逐一比较填写
	      	 if(isArray(value)){
				 for(var i=0;i< s.length;i++){	
				 	for(var j=0;j< value.length;j++)
						if(s[i].value==value[j])
							s[i].checked=true;
				  }
	      	  }else{
		      	  for(var i=0;i< s.length;i++){	
					if(s[i].value==value)
						s[i].checked=true;
				  }
			   }
	      	}else{ //普通form 表单对象
			  	for(var i=0;i< s.length;i++)
      		 		s[i].value=value;
      		 }
      	}else{
      		//Form.Element.setValue(s,value);
      		 for(var i=0;i< s.length;i++)
      		 	s[i].value=value;
      	}
	}else{ // 单个对象
	    var tagName = s.tagName.toLowerCase();
	    if("input"==tagName){
		   	var tagNametype = s.type.toLowerCase();
		    if(tagNametype=="checkbox"||tagNametype=="radio"){
	    		if(s.value==value)
					s.checked=true;
			}else
				s.value=value;
	  	 }else { //暂不考虑多选下拉框
				s.value=value;
				//Form.Element.setValue(element,value);
		}
	}
}

/**
 * 设置 json 对象到 表单元素中, 映射规则按照 json对象的属性名 追加前缀进行对应
 * @param s : json 对象
 * @param prefix : 映射到 表单域中的前缀字符串 ,可为空
 * @param splitfix:  如果json 中有 多选等字段, 该值自动转换成数组的 分隔符，默认为 , 号
 * @param parentId:  指定只更新某个id 以内的数据
 */  
function setJosnToForm(s,prefix,splitfix,parentId){
if(isArray(s)){ //暂不支持数组类数据的回填
	alert("数据格式错误，不能回填");
	return ;
}
	if(!prefix)
		prefix="";
	if(!splitfix)
		splitfix=",";	
	for(var name in s){
	   var value=s[name];
	  // alert(name+"="+value+","+value.indexOf(splitfix));
	   if(value==null)
	   		value="";
	   if(value.toString().indexOf(splitfix)>0) //多选数组
	   		value=value.split(splitfix);
	   var wfname=prefix+name;
	  //  alert(wfname);
	  var elem=$g(wfname,parentId);
	   if(elem){
	   // alert($g(wfname).value);
	      $setVal(elem,value,parentId);
	  /*   if($g(wfname).value){
	      	alert(3);
	   	   	  $setVal(wfname,value);
	   	  } else{ alert(4);
	       	  $g(wfname).innerHTML=value;	
	      }
	      */
	   } 
	   	   
	}
}			

/**
 * 设置 json 对象到 表单title元素中, 并改变背景色为红色
 * @param s : json 对象
 * @param prefix : 映射到 表单域中的前缀字符串 ,可为空
 * @param splitfix:  如果json 中有 多选等字段, 该值自动转换成数组的 分隔符，默认为 , 号
 * @param parentId:  指定只更新某个id 以内的数据
 */  
function setJosnToTitle(s,prefix,splitfix,parentId){
if(isArray(s)){ //暂不支持数组类数据的回填
	alert("数据格式错误，不能回填");
	return ;
}
	if(!prefix)
		prefix="";
	if(!splitfix)
		splitfix=",";	
	for(var name in s){
	   var value=s[name];
	 //  alert(name+"="+value);
	 //  alert(value);
	   if(value==null)
	   		value="";
	   if(value.toString().indexOf(splitfix)>0) // 暂不处理多选数组
	   		value=value.split(splitfix);
	 //  	 alert(2);
	   var wfname=prefix+name;
	   var obj=$g(wfname,parentId);
	   if(obj){
	   // alert($g(wfname).value);
	      $setTitle(obj,value,parentId);
 
	   } 
	   	   
	}
}			

/**
 * 设置 某个页面元素的 title属性值,  并将背景色设置为红色 
 * @param element 为 表单域 的id 或者是 name 属性
 * @param value  可以为 字符串 或 字符串数组
 */
function $setTitle(element , value,parentId){
// alert(element+","+value);
if(typeof element == 'string'){
 	var s= $g(element,parentId);
  }else{
 	s=element;
}
if(s)
	if(s.length&&!s.tagName){ //如果是数组
		// 判断是否 单选, 多选 
		var tagName = s[0].tagName.toLowerCase();
		//alert(element+","+tagName+","+value);
	    if("input"==tagName){
			var tagNametype = s[0].type.toLowerCase();
			//alert(tagNametype+"-000;"+value+";"+isArray(value));
	      	if(tagNametype=="checkbox"||tagNametype=="radio"){
	      	//判断 value 是否数组, 如果是，则逐一比较填写
	      	 if(isArray(value)){
				 for(var i=0;i< s.length;i++){
				 	for(var j=0;j< value.length;j++)
						if(s[i].value==value[j]){
							//s[i].checked=true;
							s[i].title= getCheckedText(s[i],value[j]);
							s[i].style.backgroundColor="lightpink";
						}
				  }
	      	  }else{
		      	  for(var i=0;i< s.length;i++){	
					if(s[i].value==value){
						//s[i].checked=true;
							s[i].title= getCheckedText(s[i],value);
							s[i].style.backgroundColor="lightpink";
					}
				  }
			   }
	      	}else{ //普通form 表单对象
			  	for(var i=0;i< s.length;i++){
      		 		s[i].title= value;
      		 		s[i].style.backgroundColor="lightpink";
      		 	}
      		 }
      	}else{
      		// select 对象
      		 for(var i=0;i< s.length;i++){
      		 	//s[i].title=value;
      		 	s[i].title= getCheckedText(s[i],value);
      		 	s[i].style.backgroundColor="lightpink";
      		 }
      	}
	}else{ // 单个对象
	    var tagName = s.tagName.toLowerCase();
	   // alert(element+","+tagName+","+value);
	    if("input"==tagName){
		   	var tagNametype = s.type.toLowerCase();
		    if(tagNametype=="checkbox"||tagNametype=="radio"){
	    		if(s.value==value){
				 	//s.checked=true;
				 	s.title= value;
					s.style.backgroundColor="lightpink";
				}
			}else{
				s.title=value;
				s.style.backgroundColor="lightpink";
			 }
	  	 }else if(tagName=="select"){ //暂不考虑多选下拉框
				s.title= getSelectText(s,value);
				s.style.backgroundColor="lightpink";
		 }else{
		 	s.title= value;
			s.style.backgroundColor="lightpink";
		 }
	}
}
/**
 * 根据指定的ID 和值 从 array 中获取对应的对象
 */
function $getObjectById(array , id ,value){
if(isArray(array)) {//必须是数组
	for(var i=0;i<array.length;i++){
		 if(array[i][id]==value)
		 	return array[i];
	}
 }
	return null;
}  
 
//清除 背景颜色和title属性
function clearbgcolor(s,parentId){
 for(var name in s){
	var s= $g(name,parentId);
	if(s)
		if(s.length&&!s.tagName){ //如果是数组
 		  for(var i=0;i< s.length;i++){
      		 s[i].title= "";
      		 s[i].style.backgroundColor="";
      	  }
	   }else{
	   	  	s.title= "";
			s.style.backgroundColor="";
	   } 
	   	   
 }
}			

function CreateHttpRequest(){
    try{
		return new ActiveXObject('MSXML2.XMLHTTP.4.0');
	}
    catch(e){
         try{
			 return new ActiveXObject('MSXML2.XMLHTTP.3.0');
			 }
             catch(e){
                   try{return new ActiveXObject('MSXML2.XMLHTTP.2.6');
					 }
					 catch(e){
							try{return new ActiveXObject('MSXML2.XMLHTTP');
							}
							catch(e){
								try{return new ActiveXObject('Microsoft.XMLHTTP');
								}
								 catch(e){
										try{return new XMLHttpRequest();
										}
										catch(e){return null;
										}
								}
					         }
			          }
			 }
	     }
} 
//简单的异步提交
function xmlhttp(path)
{
	var http=CreateHttpRequest();
	http.open("POST",path,false);
	http.send();
	return http.responseText;
}


//Achieving enter key submit form on button.实现键盘提交代码 
/**
 * 页面中enter事件发生在id "#searchForm" 下时，此id下的按钮：queryBtn，searchbtn，searchBtn，将被触发其点击事件
 */
$(document).keydown(function(event){             
	//var event = getEvent();
	var eventKeyCode = event.keyCode;
	if (!eventKeyCode) { 
		eventKeyCode = event.which;
	}              
	if(eventKeyCode == 13 && isInNode(event.target, $("#searchForm")))
	{   
		//var button = document.getElementById("queryBtn"); //queryBtn为触发提交的button id.
		var button = $("#searchForm").find("#queryBtn")[0];
		//alert(button);
		if(!button){
			button= $("#searchForm").find("#searchbtn")[0]; 
			if(!button)
				button= $("#searchForm").find("#searchBtn")[0]; 
		}
		
		if(button){
			button.click();
			event.returnValue = false;
			return false;
		}
	}
});
 	   
function getEvent() //get event on ie and firefox 
{  
	if(document.all)  return window.event;    
	func=getEvent.caller;        
	while(func!=null){  
	var arg0=func.arguments[0]; 
	if(arg0) 
	{ 
		if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)) 
		{  
			return arg0; 
		} 
	} 
	func=func.caller; 
	} 
	return null; 
}

// 判断是否有全角字符
function chkHalf(str){
      for(var i=0;i<str.length;i++){      
             strCode=str.charCodeAt(i);      
             if((strCode>65248)||(strCode==12288)){
                      //alert("有全角字符");   
                      return true;
             }    
     }
     return false;     
}

//获取工作日和班次（JSON格式）
function getWorkDateAndBanciWithJson(element) {
	if (typeof element == 'string') {
		element = $g(element);
	}
	var s = element;
	var v = "[";
	if (s.length) {
		for (var i = 0; i < s.length; i++) {
			if (i == s.length - 1) {
				v += "{banci:" + s[i].value + ",date:" + s[i].id + "}]";
			} else {
				v += "{banci:" + s[i].value + ",date:" + s[i].id + "},";
			}
		}
		/*
		 * if(v.length>1) v=v.substring(0,v.length-1);
		 */
	} else {
		/*
		 * if(s.checked) v=s.value;
		 */
		v = "{}]";
	}
	return v;
};

/**
 * 将本身的日期对象按格式化的方式输出成字符串
 * @param formatter 格式化类型： 默认yyyy-MM-dd
 * @returns {String}
 */
Date.prototype.format = function(formatter)
{
    if(!formatter || formatter == "")
    {
        formatter = "yyyy-MM-dd";
    }
    var year = this.getFullYear().toString();
    var month = (this.getMonth() + 1).toString();
    var day = this.getDate().toString();
    var hour=this.getHours().toString();     
    var minute=this.getMinutes().toString();     
    var second=this.getSeconds().toString();   
    var returnDate = formatter;
    
    var yearMarker = formatter.replace(/[^y|Y]/g,'');
    if(yearMarker.length > 1){
	    if(yearMarker.length == 2)
	    {
	        year = year.substring(2,4);
	    }    
	    returnDate = returnDate.replace(yearMarker,year);
    }
    var monthMarker = formatter.replace(/[^M]/g,'');
    if(monthMarker.length > 1)
    {
        if(month.length == 1) 
        {
            month = "0" + month;
        }
        returnDate = returnDate.replace(monthMarker,month);
    }    
    var dayMarker = formatter.replace(/[^d]/g,'');
    if(dayMarker.length > 1)
    {
        if(day.length == 1) 
        {
            day = "0" + day;
        }
        returnDate = returnDate.replace(dayMarker,day);
    }
    var hourMarker = formatter.replace(/[^h|H]/g,'');
    if(hourMarker.length > 1)
    {
    	if(hour.length == 1) 
    	{
    		hour = "0" + hour;
    	}
    	returnDate = returnDate.replace(hourMarker,hour);
    }
    var minuteMarker = formatter.replace(/[^m]/g,'');
    if(minuteMarker.length > 1)
    {
    	if(minute.length == 1) 
    	{
    		minute = "0" + minute;
    	}
    	returnDate = returnDate.replace(minuteMarker,minute);
    }
    var secondMarker = formatter.replace(/[^s|S]/g,'');
    if(secondMarker.length > 1)
    {
    	if(second.length == 1) 
    	{
    		second = "0" + second;
    	}
    	returnDate = returnDate.replace(secondMarker,second);
    }
    return returnDate;    
};

/**
 * 将字符串，格式化为日期对象
 * @param dateString 日期字符串
 * @param formatter 格式化类型： 默认yyyy-MM-dd
 * @returns {Date}
 */
Date.parseString = function(dateString,formatter)
{
    if(!dateString || dateString == "")
    {
    	var today = new Date();
        return today;
    }
    if(!formatter || formatter == "")
    {
        formatter = "yyyy-MM-dd";
    }  
    var yearMarker = formatter.replace(/[^y|Y]/g,'');   
    var monthMarker = formatter.replace(/[^M]/g,'');   
    var dayMarker = formatter.replace(/[^d]/g,'');
    var yearPosition = formatter.indexOf(yearMarker);
    var yearLength = yearMarker.length;
    var year =  dateString.substring(yearPosition ,yearPosition + yearLength) * 1;
    if( yearLength == 2)
    {
        if(year < 50 )
        {
            year += 2000;
        }
        else
        {
            year += 1900;
        }
    }
    var monthPosition = formatter.indexOf(monthMarker);
    var month = dateString.substring(monthPosition,monthPosition + monthMarker.length) * 1 - 1;
    var dayPosition = formatter.indexOf(dayMarker);
    //alert(dayPosition);
    
    var HHMarker = formatter.replace(/[^H]/g,'');
    var mmMarker = formatter.replace(/[^m]/g,'');
    var ssMarker = formatter.replace(/[^s]/g,'');
    
    var HHPosition = formatter.indexOf(HHMarker);
    var HH = dateString.substring(HHPosition, HHPosition+HHMarker.length)*1;
    
    var mmPosition = formatter.indexOf(mmMarker);
    var mm = dateString.substring(mmPosition, mmPosition+mmMarker.length)*1;
    
    var ssPosition = formatter.indexOf(ssMarker);
    var ss = dateString.substring(ssPosition, ssPosition+ssMarker.length)*1;
    
    if(dayPosition>0){
    	var day = dateString.substring( dayPosition,dayPosition + dayMarker.length )* 1;
    	var d = new Date(year,month,day);
    	if (0<=HH && HH<24){
    		d.setHours(HH, 0, 0, 0);
    		
    		if (0<=mm && mm<60){
    			d.setMinutes(mm, 0, 0);
    			
    			if (0<=ss && ss<60){
    				d.setSeconds(ss, 0);
    			}
    		}
    	}
    	return d;
    }else{
    	var d = new Date(year,month,1);
    	if (0<=HH && HH<24){
    		d.setHours(HH, 0, 0, 0);
    		
    		if (0<=mm && mm<60){
    			d.setMinutes(mm, 0, 0);
    			
    			if (0<=ss && ss<60){
    				d.setSeconds(ss, 0);
    			}
    		}
    	}
    	return d;
    }
};

/**
 * 两个日期相减，得到天数（无时分秒时，得到的是整数）
 * @param date
 * @returns {Number}
 */
Date.prototype.diff = function(date){
	  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
	};
	
/**
 * 月份增加或修改
 * @param date 日期对象
 * @param addMonthNum 增加的月份，或者减少的月份，以正数或者负伤数处理
 * @returns Date 日期对象
 */
function addMonth(date,addMonthNum){
	var d2=null;
	var mon=date.getMonth()+addMonthNum*1;
	if(mon<0){
		d2=new Date(date.getFullYear()*1-1,11,1);
	}else if(mon>11){
		d2=new Date(date.getFullYear()*1+1,0,1);
	}else{
		d2=new Date(date.getFullYear(),mon,1);
	}
	return d2;
}
/**
 * 验证只输入数字
 * @param obj
 * @param value
 */
function ValidateNumber(e,value){
		if (!/^\d+/.test(this.value)){
			$(e).val(/^\d+/.exec($(e).val()));
		} 
		return false;
}
/**
 * 自动补全表格到15行，如果小于15行的则自动补充空行，否则不补充
 * @param tableId 表格ID
 * @param thlen th长度，即td数量，每行输出多少个td
 */
function fillListTable(tableId, thlen){
	var trs=$('#'+tableId).find("tr");
	if (!thlen) {
		thlen=$('#'+tableId+' tr:first th').length;
	}
	if(trs.length<=10){
		var trtmp="<tr>";
		for(var i=0;i<thlen;i++){
			trtmp+="<td> </td>";
		}
		trtmp+="</tr>";
		for(var i=trs.length;i<=10;i++){
			$('#'+tableId+' tbody').append(trtmp);
		};
	};
}

/**
 * 自动补全表格行，自定义行数，td数固定为表头th数量
 * @param tableId
 * @param num
 */
function fillListTableAutoNum(tableId,num){
	var trs=$('#'+tableId).find("tr");
	var thlen=$('#'+tableId+' thead ').find("th").length;
	if(trs.length<=num){
		var trtmp="<tr>";
		for(var i=0;i<thlen;i++){
			trtmp+="<td> </td>";
		}
		trtmp+="</tr>";
		for(var i=trs.length;i<=num;i++){
			$('#'+tableId+' tbody').append(trtmp);
		};
	};
}

/**
 * 根据模板将数据添加到某个元素中
 * 指定的 tmplateId 中 以 【】 包括的字符串都是会被自动替换的对象内容
 * 【= =】或【::】包括的字符为 数据字典，状态类的 转换输出 
 * ob_select 为需要数据字典转换的下拉框id（某个字段），或者ul标签，如需要使用特殊样式，可使用ul
 * 
 * 需要特别处理的字段，根据状态判断、特别循环等处理，可使用自定义js方法，格式如下：
 * @【getIschaosong(item.chaoSongUserId, item.handlePersonId, item.status);】
 * 
 * 说明：以@【】包围；里面是js方法名（自定义），以及要传递的参数，参数统一“item.字段名”
 * 此方法需返还字符串，用于替换自定义方法这一串：
 * @【getIschaosong(item.chaoSongUserId, item.handlePersonId, item.status);】 将被 getIschaosong 方法的返回值替换！
 * 
 * 列表显示序号：【_i】
 * 
 * @param tmplateId	模板元素的jquery对象
 * @param appendId	追加到的元素的jquery对象
 * @param datas	list对象数组数据
 * @param options 扩展参数
 */
function rendListByTemplate(tmplateId, appendId, datas, options){
	//分页开始行获取
	/*var start = $(".pageblock input[name='start']").val();
	if (start==null || start==""){
		start=0;
	}
	start = parseInt(start);*/
	
	var html = $(tmplateId).html();
	var funStr = html.match(/@【(.*?)】/g);
	//var cache=initTemplateCache(html, datas);
	
	/*for(var i=0;i<datas.length;i++){
		var ht = html;
		var item = datas[i];
		
		//alert(funStr);
		if (funStr){
			for (var j=0; j<funStr.length; j++){
				//alert(funStr[j]);
				var fun = funStr[j].match(/@【(.*?)】/);
				
				var rs = eval(fun[1]);
				
				//alert(rs);
				ht=ht.replace(funStr[j],rs==null?"":rs);
			}
		}
		
		ht=ht.replace('【_i】',  start+i + 1);
		
		var flag = false;
		for(var ob in item){
			if (ht.indexOf("【="+ob+"=】")>=0){
				var obs = $g(ob+"_select");
				if (obs){
					var value=transValueToText(obs,item[ob]);
					flag = true;
					ht=ht.replace(new RegExp('【='+ob+'=】','g'),value==null?"":value);
				}
			}
			if (ht.indexOf("【:"+ob+":】")>=0){
				var obs = $g(ob+"_ul");
				if (obs){
					var value=transValueToText(obs,item[ob]);
					flag = true;
					ht=ht.replace(new RegExp('【:'+ob+':】','g'),value==null?"":value);
				}
			}
			ht=ht.replace(new RegExp('【'+ob+'】','g'),item[ob]==null?"":item[ob]);
		}
		if (flag){
			for(var ob in item){
				ht=ht.replace(new RegExp('【'+ob+'】','g'),item[ob]==null?"":item[ob]);
			}
		}
		$(appendId).append(ht);
	}*/
	var cache= initTemplateCache(html,datas);
	var resHtml=""; // 结果集
	var ap= $(appendId);
	for(var i=0;i<datas.length;i++){
		//var ht = html;
		var item = datas[i];
		cache.index=i;
		//debugger;
//		ap.append( renderRedseaTempltCore(html, cache, funStr, item));
		
		resHtml+= renderRedseaTempltCore(html, cache, funStr, item);
	}
	ap.append(resHtml); // 拼接完成之后，再放入dom 会更快
}

/**
 * 循环输出模板 返回html
 * @param tmplateId	模板元素的jquery对象
 * @param datas	list对象数组数据
 * @param options 扩展参数
 */
function rendListByTemplateHtml_old(tmplateId, datas, options){
	//分页开始行获取
	var start = $(".pageblock input[name='start']").val();
	if (start==null || start==""){
		start=0;
	}
	start = parseInt(start);
	
	var html = $(tmplateId).html();
	var funStr = html.match(/@【(.*?)】/g);
	var rsHtml = "";
	for(var i=0;i<datas.length;i++){
		var ht = html;
		var item = datas[i];
		
		//alert(funStr);
		if (funStr){
			for (var j=0; j<funStr.length; j++){
				//alert(funStr[j]);
				var fun = funStr[j].match(/@【(.*?)】/);
				
				var rs = eval(fun[1]);
				
				//alert(rs);
				ht=ht.replace(funStr[j],rs==null?"":rs);
			}
		}
		
		ht=ht.replace('【_i】',  start+i + 1);
		
		var flag = false;
		for(var ob in item){
			if (ht.indexOf("【="+ob+"=】")>=0){
				var obs = $g(ob+"_select");
				if (obs){
					var value=transValueToText(obs,item[ob]);
					flag = true;
					ht=ht.replace(new RegExp('【='+ob+'=】','g'),value==null?"":value);
				}
			}
			if (ht.indexOf("【:"+ob+":】")>=0){
				var obs = $g(ob+"_ul");
				if (obs){
					var value=transValueToText(obs,item[ob]);
					flag = true;
					ht=ht.replace(new RegExp('【:'+ob+':】','g'),value==null?"":value);
				}
			}
			ht=ht.replace(new RegExp('【'+ob+'】','g'),item[ob]==null?"":item[ob]);
		}
		if (flag){
			for(var ob in item){
				ht=ht.replace(new RegExp('【'+ob+'】','g'),item[ob]==null?"":item[ob]);
			}
		}
		rsHtml += ht;
	}
		return rsHtml;
}

/**
 * 初始化 模板缓存
 * @param html
 * @param datas
 */
function initTemplateCache(html,datas){
	var cache=new Object();
	var allCache=new Object(); // 缓存对象 属性 ob 是否有对应的 模板元素
	var cache0=new Object(); // 缓存对象【'+ob+'】 是否存在
	var cache1=new Object(); // 缓存对象【='+ob+'=】
	var cache2=new Object(); // 缓存对象【:"+ob+":】
	var hasStart=false;
	
	var item = null;
	if (isArray(datas)){
		item = datas[0];
	}else {
		item = datas;
	}
	if(item!=null){
		for(var ob in item){
			if (html.indexOf("【"+ob+"】")>=0){
				var obj=new RegExp('【'+ob+'】','g'); 
				allCache[ob]=true;
				cache0[ob]=obj;
			}else{
				cache0[ob]=false;
			}
			if (html.indexOf("【="+ob+"=】")>=0){
				var obs = $g(ob+"_select");
				allCache[ob]=true;
				if (obs){
					cache1[ob]=obs;
				}
			}else{
				cache1[ob]=false;
			}
			if (html.indexOf("【:"+ob+":】")>=0){
				allCache[ob]=true;
				var obs = $g(ob+"_ul");
				if (obs){
					cache2[ob]=obs;
				}
			}else{
				cache2[ob]=false;
			}
		}
	}
	
	var hasStart=false;
	var start=0;
	if(html.indexOf("【_i】")>=0){
		//分页开始行获取
		start = $(".pageblock input[name='start']").val();
		if (start==null || start==""){
			start=0;
		}
		start = parseInt(start);
		hasStart=true;
	}
	
	cache.allCache=allCache;
	cache.cache0=cache0;
	cache.cache1=cache1;
	cache.cache2=cache2;
	cache.hasStart=hasStart;
	cache.start=start;
	return cache;
}
// 模板渲染内核 , 模板引擎专用
var regTest = new RegExp('【(.*?)】','g');
function renderRedseaTempltCore(htmlTemplat,cache,funStr,item){
	//var item = datas[i];
	var ht=htmlTemplat;
	var allCache=cache.allCache; // 缓存对象 属性 ob 是否有对应的 模板元素
	var cache0=cache.cache0; // 缓存对象【'+ob+'】 是否存在
	var cache1=cache.cache1; // 缓存对象【='+ob+'=】
	var cache2=cache.cache2; // 缓存对象【:"+ob+":】
	var hasStart= cache.hasStart;
	var start= cache.start;
	var i=cache.index;
	if (funStr){
		for (var j=0; j<funStr.length; j++){
			//alert(funStr[j]);
			var fun = funStr[j].match(/@【(.*?)】/);
			var rs = eval(fun[1]);
			ht=ht.replace(funStr[j],rs==null?"":rs);
		}
		//重新检测是否包含【】、【::】、【==】，原因是调用自定义方法后，可能又包含有需要替换的数据字段；而此时却不会有自定义方法标记
		if (regTest.test(ht)){
			cache = initTemplateCache(ht, item);
			
			allCache=cache.allCache; // 缓存对象 属性 ob 是否有对应的 模板元素
			cache0=cache.cache0; // 缓存对象【'+ob+'】 是否存在
			cache1=cache.cache1; // 缓存对象【='+ob+'=】
			cache2=cache.cache2; // 缓存对象【:"+ob+":】
			hasStart= cache.hasStart;
			//start= cache.start;
			//i=cache.index;
		}
	}
	
	if(hasStart)
		ht=ht.replace('【_i】',  start+i + 1);
	
	for(var ob in allCache){
		
		if (cache1[ob]){
			//var obs = $g(ob+"_select");
			var obs = cache1[ob];
			var value=transValueToText(obs,item[ob]);
			ht=ht.replace(new RegExp('【='+ob+'=】','g'),value==null?"":value);
		}
		if (cache2[ob]){
//			var obs = $g(ob+"_ul");
			var obs = cache2[ob];
			var value=transValueToText(obs,item[ob]);
			ht=ht.replace(new RegExp('【:'+ob+':】','g'),value==null?"":value);
		}
		if(cache0[ob])
			ht=ht.replace(cache0[ob],item[ob]==null?"":item[ob]);
		//ht=ht.replace(new RegExp('【'+ob+'】','g'),item[ob]==null?"":item[ob]);
	}
	
	if (cache.cacheSub || regTest.test(ht)){
		if (!cache.cacheSub){
			var cacheSub = initTemplateCache(ht, item);
			cache.cacheSub= cacheSub;
		}
		ht = renderRedseaTempltCore(ht,cache.cacheSub,false,item);
	}
	
	return ht;
}

/**
 * 循环输出模板 返回html
 * @param tmplateId	模板元素的jquery对象
 * @param datas	list对象数组数据
 * @param options 扩展参数
 */
function rendListByTemplateHtml(tmplateId, datas, options){
	var start=0;
	var html = $(tmplateId).html();
	var funStr = html.match(/@【(.*?)】/g);
	var cache= initTemplateCache(html,datas);
	var resHtml=""; // 结果集
	for(var i=0;i<datas.length;i++){
		//var ht = html;
		var item = datas[i];
		cache.index=i;
		//debugger;
		resHtml += renderRedseaTempltCore(html, cache, funStr, item);
		 
	}
	
	return resHtml;
}

/**
 * 根据模板将数据添加到某个元素中
 * 
 * 指定的 tmplateId 中 以 【】 包括的字符串都是会被自动替换的对象内容
 * 【= =】或【::】包括的字符为 数据字典，状态类的 转换输出 
 * 
 * ob_select 为需要数据字典转换的下拉框id（某个字段），或者ul标签，如需要使用特殊样式，可使用ul
 * 
 * 需要特别处理的字段，根据状态判断、特别循环等处理，可使用自定义js方法，格式如下：
 * @【getIschaosong(item.chaoSongUserId, item.handlePersonId, item.status);】
 * 
 * 说明：以@【】包围；里面是js方法名（自定义），以及要传递的参数，参数统一“item.字段名”
 * 此方法需返还字符串，用于替换自定义方法这一串：
 * @【getIschaosong(item.chaoSongUserId, item.handlePersonId, item.status);】 将被 getIschaosong 方法的返回值替换！
 * 
 * 
 * @param tmplateId	模板元素的jquery对象
 * @param appendId	追加到的元素的jquery对象
 * @param obj	对象数据
 * @param options 扩展参数
 */
function rendObjectByTmplateId(tmplateId, appendId, obj, options){
	var html = $(tmplateId).html();
	var item = obj;
	var funStr = html.match(/@【(.*?)】/g);
	
	var cache= initTemplateCache(html,item);
	
	var resHtml = renderRedseaTempltCore(html, cache, funStr, item);
	
	$(appendId).append(resHtml);
	hideDataContentLoadding($(appendId));
}

/**
 * 将数据添加到某个元素中
 * 指定的 appendId 中 以 【】 包括的字符串都是会被自动替换的对象内容
 * 【= =】或【::】包括的字符为 数据字典，状态类的 转换输出
 *  
 * ob_select 为需要数据字典转换的下拉框id（某个字段），或者ul标签，如需要使用特殊样式，可使用ul ob_ur
 * 
 * 需要特别处理的字段，根据状态判断、特别循环等处理，可使用自定义js方法，格式如下：
 * @【getIschaosong(item.chaoSongUserId, item.handlePersonId, item.status);】
 * 
 * 说明：以@【】包围；里面是js方法名（自定义），以及要传递的参数，参数统一“item.字段名”
 * 此方法需返还字符串，用于替换自定义方法这一串：
 * @【getIschaosong(item.chaoSongUserId, item.handlePersonId, item.status);】 将被 getIschaosong 方法的返回值替换！
 * 
 * @param appendId	追加到的元素的jquery对象
 * @param obj	对象数据
 * @param options 扩展参数
 */
function rendObjectByElement(appendId, obj, options){
	var html = $(appendId).html();
	var item = obj;
	var funStr = html.match(/@【(.*?)】/g);
	
	var cache= initTemplateCache(html,item);
	
	var resHtml = renderRedseaTempltCore(html, cache, funStr, item);
	
	$(appendId).html(resHtml);
	hideDataContentLoadding($(appendId));
}

/**
 * 单个对象渲染，返回html
 * @param ht html
 * @param obj 数据
 * @param options
 * @returns
 */
function rendObjectByHtml(html, obj, options){
	var item = obj;
	var funStr = html.match(/@【(.*?)】/g);
	
	var cache= initTemplateCache(html,item);
	
	var resHtml = renderRedseaTempltCore(html, cache, funStr, item);

	return resHtml;
}



/**
 * 将数据填到某个元素中对应id的标签内
 * 
 * @param htmlId	某元素的jquery对象，包含result属性的标签，例如：<td result="name">
 * @param result	json对象数据
 * @param options 	扩展参数
 */
function rendResultForID(htmlId, result, options){
	
	$(htmlId).find("*[result]").each(function(){
		if (result==null){
			$(this).html("");
		}else {
			for (var i in result){
				var item = $(this).attr("result");
				if (item==i){
					var value = result[i]==null?"":result[i];
					$(this).html(value);
				}
			}
		}
	});
}

/**
 * 截取字符串
 */
function cutText(text, size){
	if (text==null){
		return "";
	}
	if (text.length<=size){
		return text;
	}
	var rs = "";
	
	if (size>text.length){
		size = text.length;
	}
	
	rs = text.substring(0, size);	
	return rs;
}


/**
 * 截取字符串，超长用省略号表示，可用@【cutTextWithComma(item.text,3,”个”)】调用
 * 
 * 一个逗号分隔的字符串，如果超过size个，则超出部分将用"..."替换；同时显示“共length个”，长度后面的为单位，即unit
 * 
 * 例如：text= 精整乙班,精整丙班,金川集团信息与自动化工程有限公司,精整丁班,精整甲班
 * size=3
 * unit="个"
 * 
 * 返回：精整乙班,精整丙班,金川集团信息与自动化工程有限公司...共5个
 * 
 * 另外，可在标签中加title属性，这样鼠标移到此标签上时显示全部的内容
 * 
 * @param text	逗号分隔的字符串
 * @param size	显示多少个
 * @param unit	单位
 * @returns
 */
function cutTextWithComma(text, size, unit){
	if (text==null){
		return "";
	}
	
	text = text.replace(new RegExp('，','g'),",");
	
	var strs = text.split(",");
	
	if (strs.length<=size){
		return text;
	}
	var rs = "";
	for (var j=0; j<strs.length; j++){
		if (j<size){
			rs += strs[j]+" , ";
		}
	}
	rs = rs.substring(0, rs.length-2);
	
	rs += "...共<span style=' color:#f60; padding: 0 3px'>"+strs.length+"</span>"+unit;
	
	return rs;
}

/**
 * 截取字符串，超长用省略号表示，可用@【cutTextWithSize(item.text,3)】调用
 * 
 * 如果超过size个，则超出部分将用"..."替换；
 * 
 * 例如：text= 精整乙班精整丙班金川集团信息与自动化工程有限公司,精整丁班精整甲班
 * size=4
 * 
 * 返回：精整乙班...
 * 
 * 另外，可在标签中加title属性，这样鼠标移到此标签上时显示全部的内容
 * 
 * @param text	逗号分隔的字符串
 * @param size	显示多少个
 * @param unit	单位
 * @returns
 */
function cutTextWithSize(text, size){
	if (text==null){
		return "";
	}
	if (text.length<=size){
		return text;
	}
	var rs = "";
	
	if (size>text.length){
		size = text.length;
	}
	
	rs = text.substring(0, size);
	
	rs += "...";
	
	return rs;
}

/**
 * 用于在渲染列表时，返回序号
 * @param i
 * @returns
 */
function getIndex(i){
	//分页开始行获取
	var start = $(".pageblock input[name='start']").val();
	if (start==null || start==""){
		start=0;
	}
	start = parseInt(start);
	return start+i+1;
}

/**
 * 获取url参数值
 * @param name
 * @returns
 */
function getUrlParam(name){  
    //构造一个含有目标参数的正则表达式对象  
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");  
    //匹配目标参数  
    var r = window.location.search.substr(1).match(reg);  
    //返回参数值  
    if (r!=null) return unescape(r[2]);  
    return null;  
}

/**
 * 模板自定义方法，格式化时间，传入json数据中的时间值
 * 
 * @param time 1448847928000，或"2015-11-30 09:45:28.0"；
 * @param formatter 返回的时间格式
 * @returns
 */
function timeFormatter(time, formatter){
	if (time==null){
		return "";
	}
	time = time+"";
	if (time.indexOf(".")!=-1){
		time = time.substring(0, time.indexOf("."));
	}
	var day = new Date(Date.parse(time.replace(/-/g,"/")));
	
	if (day=="Invalid Date"){
		day = new Date(time*1);
		
		if (day=="Invalid Date"){
			return "";
		}
	}
	if (formatter==null || formatter == ""){
		formatter = "yyyy-MM-dd";
	}
	
	return day.format(formatter);
}

$(function(){
	showDataContentLoadding($("[role='data-content']"));
});

function getCidFunction() {
	var i = 1;
	return function() {
		return i++;
	}
}

/**
 * 采用ajax异步渲染时，隐藏元素，显示loading
 * @param $obj
 * @returns
 */
function showDataContentLoadding($obj) {
	var dataContents = $obj;
	if (dataContents.size() > 0 && $("link[href='/RedseaPlatform/skins/library/bootstrap-3.3.0/css/font-awesome.min.css']").size() == 0) {
		$('body').append('<link href="/RedseaPlatform/skins/library/bootstrap-3.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />');
	}
	dataContents.each(function(){
		var dataContent = $(this);
		if (dataContent.attr("loading-id") != null) {
			return;
		}
		dataContent.css("visibility", "hidden");
		var offset = dataContent.offset();
		var c_id = showDataContentLoadding.getLoadingId();
		var html = '<div id="loading-id-' + c_id + '" role="loading" style="background-color:#fff; z-index: 5; width: '+ dataContent.width() +'px; height: ' + dataContent.height() + 'px; position: absolute; top: '+ offset.top +'px;left: ' + offset.left + 'px;">';
		html += '<div style="position: absolute; top: 50%; left: 50%; margin: -33px 0 0 -115px;z-index: 998;">';
		html += '<span class=" icon-spinner icon-spin" style="font-size:64px" ></span> <span style="font-size:28px" >数据加载中...</span>';
		html += '</div>';
		html += '</div>';
		dataContent.attr("loading-id",  "loading-id-" + c_id);
		$('body').append(html);
	});
}

/**
 * 显示showDataContentLoadding隐藏的元素，隐藏loading，然后移除
 */
function hideDataContentLoadding ($obj) {
	$obj.css({visibility: "visible"});
	var loadingId = $obj.attr("loading-id");
	$obj.removeAttr("loading-id");
	$("#" + loadingId).remove();
}

showDataContentLoadding.getLoadingId = getCidFunction();

$.ajaxSetup({
	dataFilter : function (tx) {
		try {
			var obj = JSON.parse(tx);
			if (obj && obj.state == "Nosession") {
				top.hideload && top.hideload();
				showLoginModal();
				return '{"state": 0,"meg": "会话失效"}';
			}
		} catch(ex) {

		}
		
		return tx;
	},
	complete : function(a,b,c){
		if(a.status == 0 && a.responseText == ""){
			window.top.rsMessageBox.hideNow();
		}
	}
});



/**
 * 会话失效时，弹出的登录框
 * @param reload
 */
function showLoginModal (reload) {
	var reloadValue = 1;
	if (top == window) {
		reload = true;
		reloadValue = 2;
	}
	if ($(top.document).find("body #login-content").size() <= 0) {
		//回话失效时新版登录界面
		var html = '<div class="login-inline-bg" id="login-content"' + (reload ? "reload=\"" + reloadValue + "\"" : "") + ' style="background-color: rgba(0, 0, 0, .5);position: fixed;z-index: 99990;top: 0px;left: 0px;right: 0px;bottom: 0px;">'
			+'<div class="login-inline-box" style="background-color: #fff;border-radius: 10px;width: 400px;height: 260px;position: fixed;z-index: 99999;left: calc(50% - 200px);top: calc(50% - 130px);">'
			+'<div class="login-inline-form" style="position: relative;overflow: hidden;">'
			+'<div class="login-inline-close" style="position: absolute;width: 20px;height: 20px;top: 5px;right: 0px;">'
			+'<span class="glyphicon glyphicon-remove" id="closeLogin" style="cursor: pointer;" onclick="$(\'#login-content\').remove();"></span>'
			+'</div>'
			+'<div class="login-inline-tt" id="login-info" style="text-align: center;margin-top: 30px;margin-bottom: 10px;font-size: 20px;color: #9c9c9c;">用户登录</div>'
			+'<div class="login-inline-bd" style="margin: 0px 40px;border: #aeaeae solid 1px;padding: 10px 20px 5px 20px;color: #64646e;">'
			+'<form id="login_form">'
			+'<input type="hidden" name="MENU_TYPE" value="oa">'
			+'<ul style="list-style: none;padding: 0px;margin: 0px;">'
			+'<li style="list-style: none;padding: 0px;margin: 0px;border-bottom: #d3d3d3 solid 1px;padding-bottom: 5px;">'
			+'<span class="icon-user" style="text-align: center;width: 30px;float: left;display: block;line-height: 30px;font-size: 18px;"></span>'
			+'<em style="margin-left: 30px;display: block;">'
			+'<input type="text" name="j_username" placeholder="用户名" validate="required" style="width: 100%;line-height: 30px;border: none;box-shadow: none;font-style: normal;"/>'
			+'</em></li>'
			+'<li style="list-style: none;padding: 0px;margin: 0px;padding-top: 10px;">'
			+'<span class="icon-lock" style="text-align: center;width: 30px;float: left;display: block;line-height: 30px;font-size: 18px;"></span>'
			+'<em style="margin-left: 30px;display: block;">'
			+'<input type="password" name="j_password" placeholder="密码" validate="required" style="width: 100%;line-height: 30px;border: none;box-shadow: none;font-style: normal;"/>'
			+'</em></li></ul>'
			+'</form></div>'
			+'<div class="login-inline-fd" style="margin: 0px 40px;margin-top: 20px;cursor:pointer;">'
			+'<a onclick="login()" style="display: block;background-color: #00a0f0;text-align: center;font-size: 16px;color: #fff;line-height: 35px;border-radius: 5px;">登录</a>'
			+'</div>'
			+'</div></div></div>';
		
		//当有模态框遮罩时，要把元素append到模态框里面
		
		//解决模态框遮罩问题
		if($(".modal.fade").length >= 1){
			if($(".modal.fade.in").length >= 1){
				$(".modal.fade.in").append(html);
			}else{
				$('.modal.fade').on('shown.bs.modal', function (e) {
					$(".modal.fade.in").append(html);
				});
				
				$('.modal.fade').on('hidden.bs.modal', function (e) {
					$('.modal.fade').unbind("shown.bs.modal");
				});
			}	
			
		}else{
			$(top.document).find("body").append(html);
		}
	}
}


/**
 * 登录框对应的登录方法
 */
function login() {
	var validate = true;
	if ($("#login_form").validate) {
		validate = $("#login_form").validate()
	}
	var current_sys="/RedseaPlatform";
	try {// 防止没有上下文变量 
		if (window.contextPath)
			current_sys = contextPath;
	} catch (e) { }
	if (validate) {
		$.ajax({
			url : current_sys+"/j_red_security_check/PW",
			type : "post",
			data : $("#login_form").serialize(),
			success: function(rs) {
				if (rs.state == "1") {
					if ($("#login-content").attr("reload") == "1") {
						Main_ifr.window.location.reload();
					} else if($("#login-content").attr("reload") == "2") {
						window.location.reload();
					}
					$("#login-content").remove();
				} else {
					$("#login-info").html(rs.meg).css("color", "red");
				}
			}
		});
	}
}

var Page={};
/**
 * 分页处理，页面需要include分页jsp
 * @param id 分页所在的form的id，分页要在一个form内
 * @param result 列表数据
 * @param fn 回调方法，获取数据、分页、显示的方法，用在切换页数时调用
 * @param show 暂时无用
 */
Page.createPage = function(id,result,fn,show){
	var show = show||false;
	var count = result.count;
	var pageNo = result.pageNo;
	var pageSize = result.pageSize;
	var pageCount = result.pageCount;
	
	$("#"+id+" input[name='start']").val(result.start);
	$("#"+id+" input[name='pageNo']").val(pageNo);
	$("#"+id+" #totalPage").html(count);
	$("#"+id+" #pageSize").val(pageSize);
	
	var html = "";
	if (pageNo == 1) {
		html = "<li  class='disabled'><a href='javascript:void(0)' class='disabled'>«</a></li>";
	} else {
		html = "<li><a href='javascript:void(0)'  pageNum='1'>«</a></li>";
	}
	
	var start = pageNo - 2 >= 1 ? pageNo -2 : 1;
	for (var i = start; i <= pageCount && i - start < 5; i++) {
		if ( i == pageNo) {
			html += "<li class='active'><a href='javascript:void(0)' pageNum='" + i + "'>" + i + "</a></li>";
		} else {
			html += "<li><a  href='javascript:void(0)' pageNum='" + i + "'>" + i + "</a></li>";
		}
	}
	
	if (pageNo == pageCount || pageCount < 1 || pageNo < 1) {
		html += "<li class='disabled'><a  class='disabled'>»</a></li>";
	} else {
		html += "<li><a href='javascript:void(0)' pageNum='"+ pageCount +"'>»</a></li>";
	}
    
    $("#"+id+" #pageNum").html(html);
    
    $("#"+id+" #pageNum li a[pageNum]").click(function(){
    	/*if (top.showload) {
    		top.showload();
    	}*/
    	var pageNo = $(this).attr("pageNum");
    	$("#"+id+" #pageNo").val(pageNo);
    	$("#"+id+" #start").val((parseInt(pageNo)-1)*pageSize);
    	fn();
    });
    
    //防止chang事件递归调用：2的n次方
    $("#"+id+" #pageSize").unbind("change");
    
    $("#"+id+" #pageSize").on("change",function(){
    	/*if (top.showload) {
    		top.showload();
    	}*/
    	$("#"+id+" #pageNo").val(1);
    	$("#"+id+" #start").val(0);
    	fn();
    });
    
};

/**
 * show loading
 */
function showload(){
	if($g('redsea_loading'))
		$("#redsea_loading").show();
	else if($g('loading'))
		$("#loading").show();
}

/**
 * hide loading
 */
function hideload(){
	if($g('redsea_loading'))
		$("#redsea_loading").hide();
	if($g('loading'))
		$("#loading").hide();
}

/**
 * 登录退出、注销方法，多点退出
 * 上下文，无则默认当前，当前无则默认RedseaPlatform
 */
var othercontextlist=["/RedseaPlatform","/OutWork","/LeanLabor","/RedseaSA","/RedseaOA","/redseaCMS","cms"]; //全部系统上下文退出
function dologout(current_sys){ //多点退出
 	$.ajaxSetup({
	    async : false
	});
 	if(!current_sys){ //设置默认的上下文
 		 if(contextPath)
 			current_sys=contextPath;
 		 else
 			current_sys="/RedseaPlatform";
 	}
	for(var i=0;i<othercontextlist.length;i++){
		debugger;
		if (current_sys!=othercontextlist[i]){	//不是当前，就退出
			if(othercontextlist[i] == "/redseaCMS"||othercontextlist[i] == "/cms"){
				$.get(othercontextlist[i]+"/admin/cms/logout.do");
			}else{
				$.get(othercontextlist[i]+"/Logout/?onlyLogout=true");
			}
		}
	}
 	$.ajaxSetup({
	    async : true
	});
 	//最后退出当前
	window.location.href=current_sys+"/Logout/";
}

//激活排序
function activeSorting(){
	//排序
	$(".sorting, .sorting_desc, .sorting_asc").unbind("click");
	$(".sorting, .sorting_desc, .sorting_asc").click(function(){
		var sortName = $(this).attr("rowName");
		var sortType = $(this).attr("class");
		
		if (sortType == "sorting_asc") {
			$("#sortName").val(sortName);
			$("#sortType").val("desc");
		}  else {
			$("#sortName").val(sortName);
			$("#sortType").val("asc");
		}
		
		$("#tableHead th[rowName=" + sortName + "]").attr("class", "sorting_" + $("#sortType").val());
		
		if ($("#searchbtn").size() > 0) {
			$("#searchbtn").click();
		} else {
			if (window.searchData) {
				searchData();
			}
		}
	});
}

/**
 * 金额格式化，保留n位数，四舍五入；s==null || s==""，返回""；
 * 整数部分默认千分位分隔
 * @param n:保留几位，0<=n<=20，非此范围n=2；n==null则 n=2
 * @param s:"555.555"或者555.555
 */
function fmoney(s, n)   
{   
   if(s==null||s==""){
	   return "";
   }
   if (n==null){
	   n = 2;
   }else {
	   n = n >= 0 && n <= 20 ? n : 2;   
   }
   var dd = (s + "").replace(/[^\d\.-]/g, "");
   
   s = (parseFloat(dd)+0.000001).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse();  
   var r = s.split(".")[1];   
   var t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
} 
/**
 * 四舍五入，保留两位小数后，转为百分比；
 * 如果是含有“.00”，则去掉
 */
function  toPercent(number){
	if(number==null&&number==''||number=='null'){
		return "";
	}
	number=Number(number*100).toFixed(2);
	number=number.toString();
	if(number.indexOf(".00")>0){
		number=number.substring(0,number.indexOf(".00"));
	}
	return number+'%';
}
/**两个数相除**/
function accDiv(arg1,arg2){ 
	if(arg1==null||arg1==''||arg2==null||arg2==''||arg1=='undefined'||arg2=='undefined'){
		return 0;
	}
    var t1=0,t2=0,r1,r2; 
    try{t1=arg1.toString().split(".")[1].length}catch(e){} 
    try{t2=arg2.toString().split(".")[1].length}catch(e){} 
    with(Math){ 
        r1=Number(arg1.toString().replace(".","")); 
        r2=Number(arg2.toString().replace(".","")); 
        return (r1/r2)*pow(10,t2-t1); 
    } 
   }
/**两个数相乘**/
function accMul(arg1,arg2) 
{ 
	if(arg1==null||arg1==''||arg2==null||arg2==''||arg1=='undefined'||arg2=='undefined'){
		return 0;
	}
    var m=0,s1=arg1.toString(),s2=arg2.toString(); 
    try{m+=s1.split(".")[1].length}catch(e){} 
    try{m+=s2.split(".")[1].length}catch(e){} 
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m); 
}
//加法函数，用来得到精确的加法结果
//说明：javascript的加法 结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
	if(!arg1 || arg1 == ""){
		arg1 = 0.0;
	}
	if(!arg2 || arg2 == ""){
		arg2 = 0.0;
	}
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2));
	return (accMul(arg1,m)+accMul(arg2,m))/m;
}
//减法函数  
function subtr(arg1, arg2) { 
	if(arg1==null||arg1==''||arg2==null||arg2==''||arg1=='undefined'||arg2=='undefined'){
		return 0;
	}
    var r1, r2, m, n;  
    try {  
        r1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r1 = 0;  
    }  
    try {  
        r2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r2 = 0;  
    }  
    m = Math.pow(10, Math.max(r1, r2));  
     //last modify by deeka  
     //动态控制精度长度  
    n = (r1 >= r2) ? r1 : r2;  
    return ((arg1 * m - arg2 * m) / m).toFixed(n);  
}
/**
 * 截取区分数字 字符 中文 
 * 一个中文长度为2，其他则为1；
 * 例：text="我和你223我和他",displayLength=7，返回“我和你2...”
 */
function  displayPart(text,displayLength) {
        var displayLength =displayLength==null?100:displayLength;
        if (!text) return "";
        text = text.replace(/<\/?[^>]*>/g,'');//返回前端内容默认移除html标记 
		text = text.replace('&nbsp;','');//移除空格
        var result = "";
        var count = 0;
        for (var i = 0; i < displayLength; i++) {
            var _char = text.charAt(i);
            if (count >= displayLength)  break;
            if (/[^x00-xff]/.test(_char))  count++;  //双字节字符，//[u4e00-u9fa5]中文
            result += _char;
            count++;
        }
        if (result.length < text.length) {
            result += "...";
        }
        return result;
}

/**
 * 未知
 * @param str
 * @returns
 */
function getStrLength(str) { 
	if(!str){
		return 0;
	}
    var cArr = str.match('/[^\x00-\xff]/ig');  
    return str.length + (cArr == null ? 0 : cArr.length);  
}  

$(function(){
	//排序
	//activeSorting();
});

/**
 * 取第一个字符，取名字第一个字
 * @param name
 * @returns
 */
function getFirstName(name){
	if(name==null &&name!=""){
		return name.trim().substring(0,1);
	}
}

/**
 * 取最后两个字符，取名字后面两个字
 * @param name
 * @returns
 */
function getFamilyName(name){
	if(name && name.trim().length>=2){
		return name.trim().substring(name.trim().length-2,name.trim().length);
	}else{
		return name.trim();
	}
}


/**
 * 默认头像处理；
 * 头像未找到、出错，隐藏头像，显示下一个元素
 * @param obj
 */
function notfind(obj){
	$(obj).hide();
	$(obj).next().show();
}

/**
 * 默认头像处理：头像载入后，调整宽高，使其显示正确（可能圆圈里还有蓝色背景）
 * @param obj
 * @param height
 */
function adaptPhoto(obj, height){
	var imgWidth = $(obj).width();
	var imgHeight = $(obj).height();
	if(imgHeight!=0 &&imgWidth>imgHeight){
		
		if (height!=null){
			$(obj).css({"width":"auto","height":"70px"});
		}else {
			$(obj).css({"width":"auto","height":height+"px"});
		}
	}
}

