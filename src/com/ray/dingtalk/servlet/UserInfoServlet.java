package com.ray.dingtalk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.dingtalk.auth.AuthHelper;
import com.ray.dingtalk.config.Env;
import com.ray.dingtalk.service.contact.UserService;

/**身份认证Servlet:免登
 * 
 * 
 * Servlet implementation class AuthServlet
 */
@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public UserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
		
		//1.获取code
		String code = request.getParameter("code");
		System.out.println("code:"+code);
		
		Object result=null;
		try {
			//2.通过CODE换取身份userid
			String accessToken = AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
			UserService us = new UserService();
			String userId=us.getUserInfo(accessToken, code).getString("userid");
		
			//3.通过userid换取用户信息
			JSONObject jsonObject=us.getUser(accessToken, userId);
			result=JSON.toJSON(jsonObject);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		PrintWriter out = response.getWriter(); 
		out.print(result);  
		out.close();  
		out = null;  
	}

}
