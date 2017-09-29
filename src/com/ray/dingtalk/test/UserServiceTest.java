package com.ray.dingtalk.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ray.dingtalk.auth.AuthHelper;
import com.ray.dingtalk.config.Env;
import com.ray.dingtalk.model.contact.User;
import com.ray.dingtalk.service.contact.UserService;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 上午10:09:34
 */
public class UserServiceTest {

	
	
	@Test
	public void testCreateUser() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		
		User user=new User();
		user.setName("王子明");
		List<Long> departmentList=new ArrayList<Long>();
		departmentList.add(new Long(1));
		user.setDepartment(departmentList);
		user.setMobile("18771419627");
		
		
		UserService us=new UserService();
		String userId=us.createUser(accessToken, user);
		System.out.println("userId"+userId);
		
	}
	
	@Test
	public void testGetUser() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		String userId="manager6777";
		
		UserService us=new UserService();
		us.getUser(accessToken, userId);
		
	}
	
	@Test
	public void testGetDepartmentUser() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		String departmentId="1";
		
		UserService us=new UserService();
		us.getDepartmentUser(accessToken, departmentId);
		
	}
	
	@Test
	public void testGetDepartmentUserDetail() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		String departmentId="1";
		
		UserService us=new UserService();
		us.getDepartmentUser(accessToken, departmentId);	
	}
	
	
	
	
}
