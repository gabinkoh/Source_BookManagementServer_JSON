package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		System.out.println("dao select [" + keyword + "]");
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
			String sql = "select b.bisbn, b.bimgurl, b.btitle, b.bauthor, b.bprice, b.bdate, b.bpage, b.bpublisher, l.state, l.uid from book b left outer join lendbook l on b.bisbn = l.bisbn where b.btitle like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("b.bisbn"));
				obj.put("img", rs.getString("b.bimgurl"));
				obj.put("title", rs.getString("b.btitle"));
				obj.put("author", rs.getString("b.bauthor"));
				obj.put("price", rs.getString("b.bprice"));
				obj.put("date", rs.getString("b.bdate"));
				obj.put("page", rs.getString("b.bpage"));
				obj.put("publisher", rs.getString("b.bpublisher"));
				obj.put("state", rs.getString("l.state"));
				obj.put("uid", rs.getString("l.uid"));
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

	public boolean update(String title, String author, String price, String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;

		boolean result = false;
		try {
			String sql = "update book set btitle = ?, bauthor = ?, bprice = ? where bisbn = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setInt(3, Integer.parseInt(price));
			pstmt.setString(4, isbn);

			int count = pstmt.executeUpdate();
			System.out.println("try " + count);
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

	public boolean delete(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;

		boolean result = false;
		try {
			String sql = "delete from book where bisbn = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);

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

	public String detailInfo(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bdate, bpage, bpublisher, bimgurl "
					+ "from book where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();
			rs.next();
			JSONObject obj = new JSONObject();
			obj.put("date", rs.getString("bdate"));
			obj.put("page", rs.getString("bpage"));
			obj.put("publisher", rs.getString("bpublisher"));
			obj.put("img", rs.getString("bimgurl"));

			result = obj.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean insert(String isbn, String title, String date, String page, String price, String author,
			String translator, String publisher, String img) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			String sql = "insert into book (bisbn, btitle, bdate, bpage, bprice, bauthor, btranslator, bpublisher, bimgurl) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setInt(4, Integer.parseInt(page));
			pstmt.setInt(5, Integer.parseInt(price));
			pstmt.setString(6, author);
			pstmt.setString(7, translator);
			pstmt.setString(8, publisher);
			pstmt.setString(9, "http://image.hanbit.co.kr/cover/_m_1063m.gif");

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
















