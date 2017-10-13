package com.ray.dingtalk.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.ray.dingtalk.config.Env;
import com.ray.dingtalk.util.HttpHelper;



/**
 * 钉钉相关配置参数的获取工具类
 * @desc  : AccessToken和jsticket的获取封装
 * 
 * @author: shirayner
 * @date  : 2017年9月27日 下午5:00:25
 */
public class AuthHelper {
	//private static Logger log = LoggerFactory.getLogger(AuthHelper.class);  
	//获取access_token的接口地址,有效期为7200秒
	private static final String GET_ACCESSTOKEN_URL="https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=CORPSECRET"; 

	//获取getJsapiTicket的接口地址,有效期为7200秒 
	private static final String GET_JSAPITICKET_URL="https://oapi.dingtalk.com/get_jsapi_ticket?access_token=ACCESSTOKE"; 


	/** 1.获取access_token 
	 * @desc ： 
	 *  
	 * @param corpId
	 * @param corpSecret
	 * @return
	 * @throws Exception String
	 */
	public static String getAccessToken(String corpId,String corpSecret) throws Exception {
		//1.获取请求url
		String url=GET_ACCESSTOKEN_URL.replace("CORPID", corpId).replace("CORPSECRET", corpSecret);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpGet(url);

		//3.解析结果，获取accessToken
		String accessToken="";  
		if (null != jsonObject) {  
			accessToken=jsonObject.getString("access_token");

			//4.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}  


		return accessToken;
	}

	/**
	 * 2、获取JSTicket, 用于js的签名计算
	 * 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
	 * @throws Exception 
	 */
	public static String getJsapiTicket(String accessToken) throws Exception  {
		//1.获取请求url
		String url=GET_JSAPITICKET_URL.replace("ACCESSTOKE", accessToken);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpGet(url);

		//3.解析结果，获取ticket
		String ticket="";  
		if (null != jsonObject) {  
			ticket=jsonObject.getString("ticket");

			//4.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}  

		return ticket;
	}


	/**
	 * @desc ： 3.生成签名的函数 
	 *  
	 * @param ticket jsticket
	 * @param nonceStr 随机串，自己定义
	 * @param timeStamp 生成签名用的时间戳 
	 * @param url 需要进行免登鉴权的页面地址，也就是执行dd.config的页面地址 
	 * @return
	 * @throws Exception String
	 */
	
	public static String getSign(String jsTicket, String nonceStr, Long timeStamp, String url) throws Exception {  
		String plainTex = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp + "&url=" + url;
		System.out.println(plainTex);
		try {  
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(plainTex.getBytes("UTF-8"));
			return byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {  
			throw new Exception(e.getMessage());  
		} catch (UnsupportedEncodingException e) {  
			throw new Exception(e.getMessage());  
		}  
	}  

	//将bytes类型的数据转化为16进制类型  
	private static String byteToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	
	


	/**
	 * @desc ：获取前端jsapi需要的配置参数（已弃用，请用getConfig(HttpServletRequest)）
	 *  
	 * @param request      request:在钉钉中点击微应用图标跳转的url地址 
	 * @return Map<String,Object>  将需要的参数存入map,并返回
	 */
	public static Map<String, Object> getDDConfig(HttpServletRequest request){  

		Map<String, Object> configMap = new HashMap<String, Object>();

		//1.准备好参与签名的字段
		/* 
		 *以http://localhost/test.do?a=b&c=d为例 
		 *request.getRequestURL的结果是http://localhost/test.do 
		 *request.getQueryString的返回值是a=b&c=d 
		 */  
		String urlString = request.getRequestURL().toString();
		String queryString = request.getQueryString();

		String queryStringEncode = null;
		String url;
		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}


		String nonceStr=UUID.randomUUID().toString();      //随机数
		long timeStamp = System.currentTimeMillis() / 1000;     //时间戳参数  

		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;   	//签名

		//2.进行签名，获取signature
		try {  
			accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);  

			ticket=AuthHelper.getJsapiTicket(accessToken);  
			signature=getSign(ticket,nonceStr,timeStamp,signedUrl);  


		} catch (Exception e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}  

		System.out.println("accessToken:"+accessToken);
		System.out.println("ticket:"+ticket);
		System.out.println("nonceStr:"+nonceStr);
		System.out.println("timeStamp:"+timeStamp);
		System.out.println("signedUrl:"+signedUrl);
		System.out.println("signature:"+signature);
		System.out.println("agentId:"+Env.AGENTID);
		System.out.println("corpId:"+Env.CORP_ID);
		
		
		
		//3.将配置参数存入Map
		configMap.put("agentId", Env.AGENTID);
		configMap.put("corpId", Env.CORP_ID);
		configMap.put("timeStamp", timeStamp);
		configMap.put("nonceStr", nonceStr);
		configMap.put("signature", signature);

		return configMap;  
	}  

	public static String getConfig(HttpServletRequest request){  

		//1.准备好参与签名的字段
		/* 
		 *以http://localhost/test.do?a=b&c=d为例 
		 *request.getRequestURL的结果是http://localhost/test.do 
		 *request.getQueryString的返回值是a=b&c=d 
		 */  
		String urlString = request.getRequestURL().toString();
		String queryString = request.getQueryString();

		String queryStringEncode = null;
		String url;
		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}


		String nonceStr=UUID.randomUUID().toString();      //随机数
		long timeStamp = System.currentTimeMillis() / 1000;     //时间戳参数  

		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;   	//签名

		//2.进行签名，获取signature
		try {  
			accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);  

			ticket=AuthHelper.getJsapiTicket(accessToken);  
			signature=getSign(ticket,nonceStr,timeStamp,signedUrl);  


		} catch (Exception e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}  

		System.out.println("accessToken:"+accessToken);
		System.out.println("ticket:"+ticket);
		System.out.println("nonceStr:"+nonceStr);
		System.out.println("timeStamp:"+timeStamp);
		System.out.println("signedUrl:"+signedUrl);
		System.out.println("signature:"+signature);
		System.out.println("agentId:"+Env.AGENTID);
		System.out.println("corpId:"+Env.CORP_ID);
		
		
		
		   String configValue = "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
	                + timeStamp + "',corpId:'" + Env.CORP_ID + "',agentId:'" + Env.AGENTID + "'}";
	        System.out.println(configValue);

		return configValue;  
	}  
	
}

