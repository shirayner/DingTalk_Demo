package com.ray.dingtalk.model.message.notice;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 下午12:52:17
 */
public class TextMessage extends Message {
	private Text  text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}




}
