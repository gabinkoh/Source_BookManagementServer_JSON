package com.cjon.comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class CommentDAO {

	public String cListForABook(String keyword, String content) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		String sql = null;

		try {
			if (keyword.equals("btitle")) {
				sql = "select b.bisbn, b.bimgurl, c.cid, c.ctitle, c.cauthor, c.cdate, c.ctext from book b join book_comment c on b.bisbn = c.bisbn where b.btitle like ?";
			} else if (keyword.equals("cauthor")) {
				sql = "select b.bisbn, b.bimgurl, c.cid, c.ctitle, c.cauthor, c.cdate, c.ctext from book b join book_comment c on b.bisbn = c.bisbn where c.cauthor = ?";
			} else if (keyword.equals("bisbn")) {
				sql = "select b.bisbn, b.bimgurl, c.cid, c.ctitle, c.cauthor, c.cdate, c.ctext from book b join book_comment c on b.bisbn = c.bisbn where c.bisbn = ?";
			}
			
			System.out.println("dao");
			pstmt= con.prepareStatement(sql);
			if (keyword.equals("btitle")) {
				pstmt.setString(1, "%"+content+"%");
			} else {
				pstmt.setString(1, content);
			}
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("b.bisbn"));
				obj.put("title", rs.getString("c.ctitle"));
				obj.put("author", rs.getString("c.cauthor"));
				obj.put("date", rs.getString("c.cdate"));
				obj.put("text", rs.getString("c.ctext"));
				obj.put("img", rs.getString("b.bimgurl"));
				obj.put("cid", rs.getString("c.cid"));
				arr.add(obj);
				System.out.println("dao.." + rs.getString("b.bisbn"));
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	public String cAllList(String keyword) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select b.bisbn, b.bimgurl, c.cid, c.ctitle, c.cauthor, c.cdate, c.ctext from book b join book_comment c on b.bisbn = c.bisbn";
			pstmt= con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("ctitle"));
				obj.put("author", rs.getString("cauthor"));
				obj.put("date", rs.getString("cdate"));
				obj.put("text", rs.getString("ctext"));
				obj.put("img", rs.getString("bimgurl"));
				obj.put("cid", rs.getString("cid"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

//	public boolean update(String title, String author, String price, String isbn) {
//		Connection con = DBTemplate.getConnection();
//		PreparedStatement pstmt = null;
//		
//		boolean result = false;
//		try {
//			String sql = "update book set btitle = ?, bauthor = ?, bprice = ? where bisbn = ?";
//			pstmt= con.prepareStatement(sql);
//			pstmt.setString(1, title);
//			pstmt.setString(2, author);
//			pstmt.setInt(3, Integer.parseInt(price));
//			pstmt.setString(4, isbn);
//			
//			int count = pstmt.executeUpdate();
//			System.out.println("try " + count);
//			// 결과값은 영향을 받은 레코드의 수
//			if( count == 1 ) {
//				result = true;
//				// 정상처리이기 때문에 commit
//				DBTemplate.commit(con);
//			} else {
//				DBTemplate.rollback(con);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DBTemplate.close(pstmt);
//			DBTemplate.close(con);
//		} 
//		return result;
//	}

	public boolean insert(String title, String author, String text, String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;

		Date d = new Date();
		DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT);
		String date = f.format(d);
		
		try {
			String sql = "insert into book_comment (bisbn, ctitle, cauthor, cdate, ctext) values (?, ?, ?, ?, ?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setString(4, date);
			pstmt.setString(5, text);
			
			int count = pstmt.executeUpdate();

			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean delete(String cid) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			String sql = "delete from book_comment where cid = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, cid);
			System.out.println("deleted comment is.." + cid);
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

}
















