
/** 
 * _config 这个参数是在前台的H5文件中我定义的，它的值是通过调用步骤6中封装好的参数来获得的 
 */  

dd.config({                                                //dd.config方法会对参数进行验证  
	agentId :'${agentId}',  
	corpId : '${corpId}',  
	timeStamp :_timeStamp,  
	nonceStr : '${nonceStr }',  
	signature : '${signature }',  
	jsApiList : [                              //需要调用的借口列表    
		'runtime.info',            
		'biz.contact.choose',              //选择用户接口  
		'device.notification.confirm',     //confirm,alert,prompt都是弹出小窗口的接口     
		'device.notification.alert',  
		'device.notification.prompt',  
		'biz.util.openLink' ]  
});  


/* 
 *在dd.config()验证通过的情况下，就会执行ready()函数， 
 *dd.ready参数为回调函数，在环境准备就绪时触发，jsapi的调用需要保证在 
 *该回调函数触发后调用，否则无效,所以你会发现所有对jsapi接口的调用都会在 
 *ready的回调函数里面 
 */  

dd.ready(function() {  

	$("#ceshi").click(function() {
		alert("ceshi11111111");
	});
	
	alert("欢迎");
	/* 
	 *获取容器信息，返回值为ability:版本号，也就是返回容器版本 
	 *用来表示这个版本的jsapi的能力，来决定是否使用jsapi 
	 */  
	dd.runtime.info({  
		onSuccess : function(info) {  
			logger.e('runtime info: ' + JSON.stringify(info));  
		},  
		onFail : function(err) {  
			logger.e('fail: ' + JSON.stringify(err));  
		}  
	});  

	/* 
	 *获得免登授权码，需要的参数为corpid，也就是企业的ID 
	 *成功调用时返回onSuccess,返回值在function的参数info中，具体操作可以在function中实现 
	 *返回失败时调用onFail 
	 */  
	dd.runtime.permission.requestAuthCode({  
		corpId : _config.corpId,  
		onSuccess : function(info) {                                                   //成功获得code值,code值在info中  
//			alert('authcode: ' + info.code);  
			/* 
			 *$.ajax的是用来使得当前js页面和后台服务器交互的方法 
			 *参数url:是需要交互的后台服务器处理代码，这里的userinfo对应WEB-INF -> classes文件中的UserInfoServlet处理程序 
			 *参数type:指定和后台交互的方法，因为后台servlet代码中处理Get和post的doGet和doPost 
			 *原本需要传输的参数可以用data来存储的，格式为data:{"code":info.code,"corpid":_config.corpid} 
			 *其中success方法和error方法是回调函数，分别表示成功交互后和交互失败情况下处理的方法 
			 */  
			$.ajax({  
				url : 'userinfo?code=' + info.code + '&corpid='                //userinfo为本企业应用服务器后台处理程序  
				+ _config.corpId,  
				type : 'GET',  
				/* 
				 *ajax中的success为请求得到相应后的回调函数，function(response,status,xhr) 
				 *response为响应的数据，status为请求状态，xhr包含XMLHttpRequest对象 
				 */  
				success : function(data, status, xhr) {                                  
					var info = JSON.parse(data);  

					alert("用户"+info.name+"登录成功");  

				},  
				error : function(xhr, errorType, error) {  
					logger.e("yinyien:" + _config.corpId);  
					alert(errorType + ', ' + error);  
				}  
			});  

		},  
		onFail : function(err) {                                                       //获得code值失败  
			alert('fail: ' + JSON.stringify(err));  
		}  
	});  
	
	
});  


/* 
 *在dd.config函数验证没有通过下执行这个函数 
 */  
dd.error(function(err) {  
	alert('dd error: ' + JSON.stringify(err));  
});  


/* 
dd中借口的约定： 
所有接口都为异步 
接受一个object类型的参数,function在js中也是一个object 
成功回调 onSuccess(某些异步接口的成功回调，将在事件触发时被调用，具体详情请查看相关onSuccess回调时机，未做描述的即为同步接口) 
失败回调 onFail 

模板如下： 
dd.命名空间.功能.方法({ 
    参数1: '', 
    参数2: '', 
    onSuccess: function(result) { 
    //成功回调 
  //{ 
        //所有返回信息都输出在这里 
  //} 
    }, 
    onFail: function(){ 
    //失败回调 
    } 
}) 
 */  

/**************************************结束********************************/  