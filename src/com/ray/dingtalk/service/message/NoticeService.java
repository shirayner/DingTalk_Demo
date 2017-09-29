package com.ray.dingtalk.service.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.dingtalk.config.Env;
import com.ray.dingtalk.model.message.notice.Media;
import com.ray.dingtalk.model.message.notice.FileMessage;
import com.ray.dingtalk.model.message.notice.ImageMessage;
import com.ray.dingtalk.model.message.notice.Link;
import com.ray.dingtalk.model.message.notice.LinkMessage;
import com.ray.dingtalk.model.message.notice.Markdown;
import com.ray.dingtalk.model.message.notice.MarkdownMessage;
import com.ray.dingtalk.model.message.notice.Message;
import com.ray.dingtalk.model.message.notice.Text;
import com.ray.dingtalk.model.message.notice.TextMessage;
import com.ray.dingtalk.model.message.notice.Voice;
import com.ray.dingtalk.model.message.notice.VoiceMessage;
import com.ray.dingtalk.util.HttpHelper;

/**@desc  :  企业通知消息接口
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 下午1:03:50
 */
public class NoticeService {
	private static final String SEND_NOTICE_URL="https://oapi.dingtalk.com/message/send?access_token=ACCESSTOKEN";

	/**
	 * @desc ：0.发送企业通知消息
	 *  
	 * @param accessToken
	 * @param message
	 * @throws Exception void
	 */
	public void sendNotice(String accessToken,Message message) throws Exception {
		//1.准备POST请求参数
		Object data=JSON.toJSON(message);      //将javaBean 转为 json
		System.out.println(data);

		//2.获取请求url
		String url=SEND_NOTICE_URL.replace("ACCESSTOKEN", accessToken);

		//3.发起POST请求，获取返回结果
		JSONObject jsonObject=HttpHelper.httpPost(url, data);
		System.out.println("jsonObject:"+jsonObject.toString());

		//4.解析结果，获取
		if (null != jsonObject) {  

			//5.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			}  
		}   		
	}
	/**
	 * @desc ：1.发送文本通知消息
	 *  
	 * @param accessToken
	 * @param userId
	 * @param content
	 * @throws Exception void
	 */
	public void sendTextNotice(String accessToken,String userId,String content) throws Exception {
		//1.创建消息
		TextMessage message=new TextMessage();
		message.setAgentid(Env.AGENTID);
		message.setTouser(userId);
		message.setMsgtype("text");

		Text text=new Text();
		text.setContent(content);
		message.setText(text);

		//2.发送消息
		this.sendNotice(accessToken, message);
	}


	public void sendImageNotice(String accessToken,String userId,String mediaId) throws Exception {
		//1.创建消息
		ImageMessage message=new ImageMessage();
		message.setAgentid(Env.AGENTID);
		message.setTouser(userId);
		message.setMsgtype("image");

		Media image=new Media();
		image.setMedia_id(mediaId);
		message.setImage(image);

		//2.发送消息
		this.sendNotice(accessToken, message);
	}

	public void sendVoiceNotice(String accessToken,String userId,String mediaId,String duration) throws Exception {
		//1.创建消息
		VoiceMessage message=new VoiceMessage();
		message.setAgentid(Env.AGENTID);
		message.setTouser(userId);
		message.setMsgtype("voice");

		Voice voice=new Voice();
		voice.setMedia_id(mediaId);
		voice.setDuration(duration);
		message.setVoice(voice);;

		//2.发送消息
		this.sendNotice(accessToken, message);
	}


	public void sendFileNotice(String accessToken,String userId,String mediaId) throws Exception {
		//1.创建消息
		FileMessage message=new FileMessage();
		message.setAgentid(Env.AGENTID);
		message.setTouser(userId);
		message.setMsgtype("file");

		Media file=new Media();
		file.setMedia_id(mediaId);
		message.setFile(file);

		//2.发送消息
		this.sendNotice(accessToken, message);
	}

	public void sendLinkNotice(String accessToken,String userId,String messageUrl,String picUrl,String title,String text) throws Exception {
		//1.创建消息
		LinkMessage message=new LinkMessage();
		message.setAgentid(Env.AGENTID);
		message.setTouser(userId);
		message.setMsgtype("link");

		Link link=new Link();
		link.setMessageUrl(messageUrl);
		link.setPicUrl(picUrl);
		link.setTitle(title);
		link.setText(text);
		message.setLink(link);

		//2.发送消息
		this.sendNotice(accessToken, message);
	}


	public void sendMarkdownNotice(String accessToken,String userId,String title,String text) throws Exception {
		//1.创建消息
		MarkdownMessage message=new MarkdownMessage();
		message.setAgentid(Env.AGENTID);
		message.setTouser(userId);
		message.setMsgtype("markdown");

		Markdown markdown=new Markdown();
		markdown.setTitle(title);
		markdown.setText(text);
		message.setMarkdown(markdown);

		//2.发送消息
		this.sendNotice(accessToken, message);
	}



}
