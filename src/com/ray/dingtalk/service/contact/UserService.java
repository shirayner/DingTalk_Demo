package com.ray.dingtalk.service.contact;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.dingtalk.model.contact.User;
import com.ray.dingtalk.util.HttpHelper;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 上午9:53:51
 */
public class UserService {

	private static final String CREATE_USER_URL="https://oapi.dingtalk.com/user/create?access_token=ACCESSTOKEN";
	private static final String GET_USER_URL="https://oapi.dingtalk.com/user/get?access_token=ACCESSTOKEN&userid=USERID";
	private static final String GET_DEPARTMENTUSER_URL="https://oapi.dingtalk.com/user/simplelist?access_token=ACCESSTOKEN&department_id=DEPARTMENTID";
	private static final String GET_DEPARTMENTUSERDETAIL_URL="https://oapi.dingtalk.com/user/list?access_token=ACCESSTOKEN&department_id=DEPARTMENTID";
	private static final String GET_USERINFO_BYCODE_URL="https://oapi.dingtalk.com/user/getuserinfo?access_token=ACCESSTOKEN&code=CODE";

	/**1.创建用户
	 * @desc ：
	 *  
	 * @param accessToken
	 * @param user
	 * @return
	 * @throws Exception String
	 */
	public String createUser(String accessToken,User user) throws Exception {
		//1.准备POST请求参数
		Object data=JSON.toJSON(user);
		System.out.println(data);

		//2.获取请求url
		String url=CREATE_USER_URL.replace("ACCESSTOKEN", accessToken);

		//3.发起POST请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpPost(url, data);
		System.out.println("jsonObject:"+jsonObject.toString());

		//4.解析结果，获取UserId
		String userId="";
		if (null != jsonObject) {  
			userId=jsonObject.getString("userid");
			//5.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}   

		return userId;
	}


	/** 2.获取成员详情
	 * @desc ：获取成员详情
	 *   参考文档： https://open-doc.dingtalk.com/docs/doc.htm?spm=0.0.0.0.jjSfQQ&treeId=371&articleId=106816&docType=1#s0
	 * @param accessToken
	 * @param userId void
	 * @throws Exception 
	 */
	public JSONObject getUser(String accessToken, String userId) throws Exception {

		//1.获取请求url
		String url=GET_USER_URL.replace("ACCESSTOKEN", accessToken).replace("USERID", userId);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpGet(url);
		System.out.println("jsonObject:"+jsonObject.toString());
		//3.解析结果，获取User
		if (null != jsonObject) {  
			//4.请求成功,则返回jsonObject
			if (0==jsonObject.getInteger("errcode")) {
				return jsonObject;
			}
			//5.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}   

		return null;
	}

	/** 3.获取部门成员
	 * @desc ：
	 *  
	 * @param accessToken
	 * @param departmentId
	 * @throws Exception void
	 */
	public void getDepartmentUser(String accessToken, String departmentId) throws Exception {

		//1.获取请求url
		String url=GET_DEPARTMENTUSER_URL.replace("ACCESSTOKEN", accessToken).replace("DEPARTMENTID", departmentId);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpGet(url);
		System.out.println("jsonObject:"+jsonObject.toString());

		//3.解析结果，获取User
		if (null != jsonObject) {  

			//4.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}   
	}

	/** 4.获取部门成员（详情）
	 * @desc ：
	 *  
	 * @param accessToken
	 * @param departmentId
	 * @throws Exception void
	 */
	public void getDepartmentUserDetail(String accessToken, String departmentId) throws Exception {

		//1.获取请求url
		String url=GET_DEPARTMENTUSERDETAIL_URL.replace("ACCESSTOKEN", accessToken).replace("DEPARTMENTID", departmentId);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpGet(url);
		System.out.println("jsonObject:"+jsonObject.toString());

		//3.解析结果，获取User
		if (null != jsonObject) {  

			//4.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}   
	}

	/** 5.根据免登授权码Code查询免登用户userId
	 * @desc ：钉钉服务器返回的用户信息为：
	 * userid	员工在企业内的UserID
	 * deviceId	手机设备号,由钉钉在安装时随机产生
	 * is_sys	是否是管理员
	 * sys_level	级别，0：非管理员 1：超级管理员（主管理员） 2：普通管理员（子管理员） 100：老板
	 *  
	 * @param accessToken
	 * @param code
	 * @throws Exception void
	 */
	public JSONObject getUserInfo(String accessToken,String code) throws Exception {

		//1.获取请求url
		String url=GET_USERINFO_BYCODE_URL.replace("ACCESSTOKEN", accessToken).replace("CODE", code);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpGet(url);
		System.out.println("jsonObject:"+jsonObject.toString());

		//3.解析结果，获取User
		if (null != jsonObject) {  
			//4.请求成功,则返回jsonObject
			if (0==jsonObject.getInteger("errcode")) {
				return jsonObject;
			}
			//5.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}   

		return null;
	}



}
