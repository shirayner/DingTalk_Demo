package com.ray.dingtalk.test;

import org.junit.Test;

import com.ray.dingtalk.auth.AuthHelper;
import com.ray.dingtalk.config.Env;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月27日 下午11:42:04
 */
public class AuthHelperTest {

	@Test
	public void testGetAccessToken() throws Exception {
		
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		
		System.out.println("accessToken:"+accessToken);
	}
	
	@Test
	public void testGetJsapiTicket() throws Exception {
		
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		System.out.println("accessToken:"+accessToken);
		
		String ticket=AuthHelper.getJsapiTicket(accessToken);
		System.out.println("ticket:"+ticket);
	}
	
	
	@Test
	public void testLog4j() {

		
		
		
	}
	
}
