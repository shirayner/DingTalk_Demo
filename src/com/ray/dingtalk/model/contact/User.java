package com.ray.dingtalk.model.contact;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年9月28日 上午9:38:25
 */
public class User {

	public String userid;
	public String name;     //必须  
	public boolean active;
	public String avatar;
	public List<Long> department;    //必须
	public String position;
	public String mobile;            //必须
	public String tel;
	public String workPlace;
	public String remark;
	public String email;
	public String jobnumber;
	public JSONObject extattr;
	public boolean isAdmin;
	public boolean isBoss;
	public String dingId;
	
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * @return the department
	 */
	public List<Long> getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(List<Long> department) {
		this.department = department;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the workPlace
	 */
	public String getWorkPlace() {
		return workPlace;
	}
	/**
	 * @param workPlace the workPlace to set
	 */
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the jobnumber
	 */
	public String getJobnumber() {
		return jobnumber;
	}
	/**
	 * @param jobnumber the jobnumber to set
	 */
	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}
	/**
	 * @return the extattr
	 */
	public JSONObject getExtattr() {
		return extattr;
	}
	/**
	 * @param extattr the extattr to set
	 */
	public void setExtattr(JSONObject extattr) {
		this.extattr = extattr;
	}
	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	/**
	 * @return the isBoss
	 */
	public boolean isBoss() {
		return isBoss;
	}
	/**
	 * @param isBoss the isBoss to set
	 */
	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}
	/**
	 * @return the dingId
	 */
	public String getDingId() {
		return dingId;
	}
	/**
	 * @param dingId the dingId to set
	 */
	public void setDingId(String dingId) {
		this.dingId = dingId;
	}
	
	@Override
	public String toString() {
		return "User[userid:" + userid + ", name:" + name + ", active:" + active + ", "
				+ "avatar:" + avatar + ", department:" + department +
				", position:" + position + ", mobile:" + mobile + ", email:" + email + 
				", extattr:" + extattr;
	}
	
}
