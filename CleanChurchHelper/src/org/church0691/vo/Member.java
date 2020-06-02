package org.church0691.vo;

public class Member {
	@Override
	public String toString() {
		return "Member [memNo=" + memNo + ", memPassword=" + memPassword + ", memId=" + memId + ", memName=" + memName
				+ ", memPhone=" + memPhone + ", memAddress=" + memAddress + ", memRemark=" + memRemark + ", memDel="
				+ memDel + ", authName=" + authName + "]";
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getMemPassword() {
		return memPassword;
	}
	public void setMemPassword(String memPassword) {
		this.memPassword = memPassword;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}
	public String getMemAddress() {
		return memAddress;
	}
	public void setMemAddress(String memAddress) {
		this.memAddress = memAddress;
	}
	public String getMemRemark() {
		return memRemark;
	}
	public void setMemRemark(String memRemark) {
		this.memRemark = memRemark;
	}
	public String getMemDel() {
		return memDel;
	}
	public void setMemDel(String memDel) {
		this.memDel = memDel;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	private String memNo;
	private String memPassword;
	private String memId;
	private String memName;
	private String memPhone;
	private String memAddress;
	private String memRemark;
	private String memDel;
	private String authName;
	
}
