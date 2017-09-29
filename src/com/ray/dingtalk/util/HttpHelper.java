package com.ray.dingtalk.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.dingtalk.auth.AuthHelper;
import com.ray.dingtalk.config.Env;

/**
 * HTTP请求封装，建议直接使用sdk的API
 */
public class HttpHelper {

	/**
	 * @desc ：1.发起GET请求
	 *  
	 * @param url
	 * @return JSONObject
	 * @throws Exception 
	 */
	public static JSONObject httpGet(String url) throws Exception {
		//1.创建httpClient 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//2.生成一个请求
		HttpGet httpGet = new HttpGet(url);
		//3.配置请求的属性
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpGet.setConfig(requestConfig);
        
		//4.发起请求，获取响应信息
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());

			//如果返回结果的code不等于200，说明出错了  
			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
						+ ", url=" + url);
				return null;
			}
			//5.解析请求结果
			HttpEntity entity = response.getEntity();      //reponse返回的数据在entity中 
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");  //将数据转化为string格式  

				JSONObject result = JSON.parseObject(resultStr);    //将String转换为 JSONObject
				if (result.getInteger("errcode") == 0) {
					return result;
				} else {
					System.out.println("request url=" + url + ",return value=");
					System.out.println(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}


	/** 2.发起POST请求
	 * @desc ：
	 *  
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject httpPost(String url, Object data) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().
				setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json");

		try {
			StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
			httpPost.setEntity(requestEntity);

			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
						+ ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				if (result.getInteger("errcode") == 0) {
					result.remove("errcode");
					result.remove("errmsg");
					return result;
				} else {
					System.out.println("request url=" + url + ",return value=");
					System.out.println(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	
	



	
	
	
	
}
