dd.config({
	agentId : _config.agentId,
	corpId : _config.corpId,
	timeStamp : _config.timeStamp,
	nonceStr : _config.nonceStr,
	signature : _config.signature,
	jsApiList : [                           //需要调用的借口列表 
		'runtime.info',
		'biz.contact.choose',            //选择用户接口  
		'device.notification.confirm', 
		'device.notification.alert',   //confirm,alert,prompt都是弹出小窗口的接口     
		'device.notification.prompt', 
		'biz.ding.post',
		'biz.util.openLink' ]
});

dd.ready(function() {  

	document.getElementById("yanzheng").innerHTML = "验证成功";  

	document.querySelector('#ceshi').onclick = function () {
		alert("ceshiaaa");
	};

	/* 1.获取容器信息
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



	/* 2.获取免登授权码
	 *获得免登授权码，需要的参数为corpid，也就是企业的ID 
	 *成功调用时返回onSuccess,返回值在function的参数info中，具体操作可以在function中实现 
	 *返回失败时调用onFail 
	 */  
	dd.runtime.permission.requestAuthCode({  
		corpId : _config.corpId,  
		onSuccess : function(info) {                                                   //成功获得code值,code值在info中  
    		alert('authcode: ' + info.code);  
			/* 
			 *$.ajax的是用来使得当前js页面和后台服务器交互的方法 
			 *参数url:是需要交互的后台服务器处理代码，这里的userinfo对应WEB-INF -> classes文件中的UserInfoServlet处理程序 
			 *参数type:指定和后台交互的方法，因为后台servlet代码中处理Get和post的doGet和doPost 
			 *原本需要传输的参数可以用data来存储的，格式为data:{"code":info.code,"corpid":_config.corpid} 
			 *其中success方法和error方法是回调函数，分别表示成功交互后和交互失败情况下处理的方法 
			 */  
			$.ajax({  
				type : "POST",
				url : "http://p65s3p.natappfree.cc/DingTalk_Demo/userInfoServlet",
				data : {
					code : info.code
				},
				success : function(data, status, xhr) {
					alert(data);
					var userInfo = JSON.parse(data);

					document.getElementById("userName").innerHTML = userInfo.name;
					document.getElementById("userId").innerHTML = userInfo.userid;

					// 图片
					if(info.avatar.length != 0){
						var img = document.getElementById("userImg");
						img.src = info.avatar;
						img.height = '200';
						img.width = '200';
					}

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


//在dd.config函数验证失败时执行 dd.error
dd.error(function(err) {                                             //验证失败  
	alert("进入到error中");  
	document.getElementById("userName").innerHTML = "验证出错";  
	alert('dd error: ' + JSON.stringify(err));  
});  