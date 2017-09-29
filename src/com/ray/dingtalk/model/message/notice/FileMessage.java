package com.ray.dingtalk.model.message.notice;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 下午1:48:44
 */
public class FileMessage extends Message {
	
	private Media file;

	public Media getFile() {
		return file;
	}

	public void setFile(Media file) {
		this.file = file;
	}

	
	
}
