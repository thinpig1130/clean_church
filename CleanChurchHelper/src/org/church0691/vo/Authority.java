package org.church0691.vo;

public class Authority {
	@Override
	public String toString() {
		return "Authority [authName=" + authName + ", authId=" + authId + ", authDiscription=" + authDiscription + "]";
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public long getAuthId() {
		return authId;
	}
	public void setAuthId(long authId) {
		this.authId = authId;
	}
	public String getAuthDiscription() {
		return authDiscription;
	}
	public void setAuthDiscription(String authDiscription) {
		this.authDiscription = authDiscription;
	}
	
	private String authName;
	private long authId;
	private String authDiscription;
}
