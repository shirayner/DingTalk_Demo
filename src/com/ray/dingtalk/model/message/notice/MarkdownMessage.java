package com.ray.dingtalk.model.message.notice;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 下午2:19:43
 */
public class MarkdownMessage extends Message{

	private Markdown markdown;
	

	public Markdown getMarkdown() {
		return markdown;
	}

	public void setMarkdown(Markdown markdown) {
		this.markdown = markdown;
	}
	
	
}
