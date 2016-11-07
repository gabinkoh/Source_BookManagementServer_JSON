package com.cjon.users.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class UserDAO {


	public boolean insert(String id, String pw, String gender) {

		System.out.println("insert id " + id + " pw " + pw + " gender " + gender);
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;
		int gend;
		
		try {
			String sql = "insert into users values (?, ?, ?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			if (gender.equals("female"))
				gend = 1;
			else
				gend = 2;
			pstmt.setInt(3, gend);
			
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

	public String confirm(String id, String pw) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		String result = null;
		ResultSet rs = null;
		
		try {
			String sql = "select id, pw "
					   + "from users where id = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			JSONObject obj = new JSONObject();
			while(rs.next()) {
				if (pw.equals(rs.getString("pw"))) {
						obj.put("id", id);
						obj.put("pw", pw);
						result = obj.toJSONString();
						
				} else
					result = "false";
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

}
















