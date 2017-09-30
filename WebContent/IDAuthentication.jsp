<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>身份认证</title>
<script src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>

<script type="text/javascript">
	var _config =<%=com.ray.dingtalk.auth.AuthHelper.getConfig(request)%>;
</script>
<script type="text/javascript" src="js/auth.js"></script>


</head>
<body>


	<div align="center">
		<img id="userImg" alt="头像" src="">
	</div>

	<div align="center">
		<span>UserName:</span>
		<div id="userName" style="display: inline-block"></div>
	</div>

	<div align="center">
		<span>UserId:</span>
		<div id="userId" style="display: inline-block"></div>
	</div>


	<div align="center">
		<span class="desc">是否验证成功</span>
		<button class="btn btn_primary" id="yanzheng">ceshi</button>
	</div>
	<div align="center">
		<span class="desc">测试按钮</span>
		<button class="btn btn_primary" id="ceshi">ceshi</button>
	</div>


</body>
</html>