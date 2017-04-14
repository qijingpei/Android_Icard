package com.example.yasin.thisme.model;

public class User {
	private String uid;	//�û����
	private String phoneNumber; //�ֻ��ţ����ڵ�¼
	private String name; //����
	private String password; //����
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String uid, String phoneNumber, String name, String password) {
		super();
		this.uid = uid;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", phoneNumber=" + phoneNumber + ", name="
				+ name + ", password=" + password + "]";
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
