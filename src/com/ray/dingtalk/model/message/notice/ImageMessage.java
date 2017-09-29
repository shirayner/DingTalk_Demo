package com.ray.dingtalk.model.message.notice;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 下午1:48:44
 */
public class ImageMessage extends Message {
	
	private Media image;

	
	public Media getImage() {
		return image;
	}

	public void setImage(Media image) {
		this.image = image;
	}

}
