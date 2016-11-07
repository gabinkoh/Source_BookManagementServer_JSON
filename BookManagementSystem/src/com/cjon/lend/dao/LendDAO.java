package com.cjon.lend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class LendDAO {


	public boolean insert(String isbn, String id) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;

		Date d = new Date();
		DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT);
		String date = f.format(d);
		System.out.println("isbn "+isbn+" , id "+id);
		
		try {
			String sql = "insert into lendbook (bisbn, uid, ldate, state) values (?, ?, ?, ?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, id);
			pstmt.setString(3, date);
			pstmt.setInt(4, 1);
			
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

	public boolean delete(String id, String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		System.out.println("dao delete, id:"+id+",isbn:"+isbn);
		boolean result = false;
		try {
			String sql = "update lendbook set state = 0 where uid = ? and bisbn = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, isbn);
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

	public String searchMyBook(String bisbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select lid, bisbn, uid, ldate, state from lendbook where bisbn = ?";
			pstmt= con.prepareStatement(sql);
//			pstmt.setString(1, id);
			pstmt.setString(1, bisbn);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			System.out.println("searchMyBook check.." +rs.getString("lid") );
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("lid", rs.getString("lid"));
				obj.put("bisbn", rs.getString("bisbn"));
				obj.put("uid", rs.getString("uid"));
				obj.put("ldate", rs.getString("ldate"));
				obj.put("state", rs.getString("state"));
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

	public String searchStateByBook(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select lid, bisbn, uid, ldate, state from lendbook where bisbn = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();
//			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			rs.next();
//			while(rs.next()) {
//				JSONObject obj = new JSONObject();
				obj.put("lid", rs.getString("lid"));
				obj.put("bisbn", rs.getString("bisbn"));
				obj.put("uid", rs.getString("uid"));
				obj.put("ldate", rs.getString("ldate"));
				obj.put("state", rs.getString("state"));
				System.out.println("..... "+rs.getString("uid"));
//				arr.add(obj);
//			}
			result = obj.toJSONString();//arr.toJSONString();
		} catch (Exception e) {
			result = "none";
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	public boolean searchStateByBook(String isbn, String uid) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		boolean rst = false;
		try {
			String sql = "select lid, bisbn, uid, ldate, state from lendbook where bisbn = ? and uid = ? and state = 1";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, uid);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("lid", rs.getString("lid"));
				obj.put("bisbn", rs.getString("bisbn"));
				obj.put("uid", rs.getString("uid"));
				obj.put("ldate", rs.getString("ldate"));
				obj.put("state", rs.getString("state"));
				arr.add(obj);
				rst = true;
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return rst;
	}

}
















