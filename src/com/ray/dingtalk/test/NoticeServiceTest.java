package com.ray.dingtalk.test;

import org.junit.Test;

import com.ray.dingtalk.auth.AuthHelper;
import com.ray.dingtalk.config.Env;
import com.ray.dingtalk.service.message.NoticeService;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 下午1:16:08
 */
public class NoticeServiceTest {

	@Test
	public void testSendNotice() throws Exception {
		
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		
		String userId="manager6777";
		String content="ray,你的快递到了";
	
		NoticeService  ns=new NoticeService();
		ns.sendTextNotice(accessToken, userId, content);
		
	}
	
	@Test
	public void testSendMarkdownNotice() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.CORP_SECRET);
		String userId="manager6777";
		String title="代办事项";
		String text="# 这是支持markdown的文本 \\n## 标题2  \\n* 列表1 \\n)";
		
		NoticeService  ns=new NoticeService();
		ns.sendMarkdownNotice(accessToken, userId, title, text);
	}
	
	
}
