package com.cjon.users.service;

import com.cjon.users.dao.UserDAO;

public class UserService {


	public boolean insertUser(String id, String pw, String gender) {
		// TODO Auto-generated method stub
		UserDAO dao = new UserDAO();
		System.out.println("insertuser id " + id + " pw " + pw + " gender " + gender);
		boolean result = dao.insert(id, pw, gender);	
		return result;
	}

	public String confirm(String id, String pw) {
		UserDAO dao = new UserDAO();
		String result = dao.confirm(id, pw);	
		return result;
	}

}












