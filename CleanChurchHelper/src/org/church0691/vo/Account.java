package org.church0691.vo;

public class Account {
	@Override
	public String toString() {
		return "Account [계좌번호:" + no + ", 은행명:" + bank + ", 계좌별명:" + name + ", 잔액:" + price + ", 계좌정보:" + info + "]";
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	private String no;
	private String bank;
	private String name;
	private long price;
	private String info;
 }
