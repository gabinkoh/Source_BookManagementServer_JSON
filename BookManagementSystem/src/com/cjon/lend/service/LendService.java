package com.cjon.lend.service;

import com.cjon.book.dao.BookDAO;
import com.cjon.comment.dao.CommentDAO;
import com.cjon.lend.dao.LendDAO;

public class LendService {

	
	public boolean insertComment(String title, String author, String text, String isbn) {
		// TODO Auto-generated method stub
		CommentDAO dao = new CommentDAO();
		boolean result = dao.insert(title, author, text, isbn);	
		return result;
	}

	public String cAllList(String keyword) {
		CommentDAO dao = new CommentDAO();
		String result = dao.cAllList(keyword);	
		return result;
	}


	public boolean deleteComment(String cid) {
		CommentDAO dao = new CommentDAO();
		boolean result = dao.delete(cid);	
		return result;
	}


	public String searchMyBook(String isbn) {
		LendDAO dao = new LendDAO();
		String result = dao.searchMyBook(isbn);	
		return result;
	}

	public String searchStateByBook(String isbn) {
		LendDAO dao = new LendDAO();
		String result = dao.searchStateByBook(isbn);	
		return result;
	}
	
	public boolean searchStateByBook(String isbn, String uid) {
		LendDAO dao = new LendDAO();
		boolean result = dao.searchStateByBook(isbn, uid);	
		return result;
	}

	public boolean deleteLend(String id, String isbn) {
		LendDAO dao = new LendDAO();
		boolean result = dao.delete(id, isbn);	
		return result;
	}

	public boolean insert(String isbn, String id) {
		LendDAO dao = new LendDAO();
		boolean result = dao.insert(isbn, id);	
		return result;
	}

}












