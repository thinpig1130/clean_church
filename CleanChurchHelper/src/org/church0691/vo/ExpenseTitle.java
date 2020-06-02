package org.church0691.vo;

public class ExpenseTitle {
	
	@Override
	public String toString() {
		return "(" + id + ") " + title + " ▶ " + subTitle ;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	private int id;
	private String title;
	private String subTitle;
	private String del;

}
