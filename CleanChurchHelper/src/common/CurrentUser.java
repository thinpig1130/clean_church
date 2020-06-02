package common;

public class CurrentUser {
	
	@Override
	public String toString() {
		return "CurrentUser [memNo=" + memNo + ", id=" + id + ", name=" + name + ", authName=" + authName + "]";
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	private String memNo;
	private String id;
	private String name;
	private String authName;
	
}
