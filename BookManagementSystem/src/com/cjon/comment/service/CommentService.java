package com.cjon.comment.service;

import com.cjon.book.dao.BookDAO;
import com.cjon.comment.dao.CommentDAO;

public class CommentService {

	// 리스트를 가져오는 일을 하는 method
	public String cListForABook(String keyword, String content) {
		// 일반적인 로직처리 나와요!!
		
		// 추가적으로 DB처리가 나올 수 있어요!
		// DB처리는 Service에서 처리는하는게 아니라..다른 객체를 이용해서
		// Database처리를 하게 되죠!!
		System.out.println("service..");
		CommentDAO dao = new CommentDAO();
		String result = dao.cListForABook(keyword, content);	
		return result;
	}
//
//	public boolean updateBook(String title, String author, String price, String isbn) {
//		CommentDAO dao = new CommentDAO();
//		boolean result = dao.update(title, author, price, isbn);	
//		return result;
//	}
//
//	public boolean deleteBook(String isbn) {
//		CommentDAO dao = new CommentDAO();
//		boolean result = dao.delete(isbn);	
//		return result;
//	}


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

}












