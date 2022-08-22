package com.lms.bean;


public class User {

	private String email;
	private String pass;
	private int isAdmin;
	
	public int isAdmin() {
		return isAdmin;
	}
	public void setAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public User(String email, String pass, int v) {
		super();
		this.email = email;
		this.pass = pass;
		this.isAdmin=v;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
}




