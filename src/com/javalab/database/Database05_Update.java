package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 상품 가격 변경(수정)
 * - 탱크로리 상품의 가격을 500,000원으로 수정하시오
 */

public class Database05_Update {
	public static void main(String[] args) {
		// 오라클 드라이버 로딩 문자열
		String driver = "oracle.jdbc.driver.OracleDriver";
		// 데이터베이스 연결 문자열
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		// 데이터베이스(계정명)
		String dbId = "tempdb";
		// 데이터베이스 비밀번호
		String dbPwd = "1234";
		
		// 데이터베이스 연결 객체
		Connection con = null;
		// 데이터베이스 실행 객체
		PreparedStatement pstmt = null;
		// 실행된 쿼리문 반환
		ResultSet rs = null;
		
		int resultNo = 0;
		
		try {
			Class.forName(driver);
			System.out.println("1. 드라이버 로드 성공");
			
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("2. 데이터베이스 연결 성공!");
			
			String PRODUCT_NAME ="승용차";
			int PRICE =300000;
			
			String sql = "update PRODUCT set PRICE =? where PRODUCT_NAME =?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, PRICE);
			pstmt.setString(2, PRODUCT_NAME);
			
			resultNo = pstmt.executeUpdate();
			
			if (resultNo > 0) {
				System.out.println("수정 성공");
			} else {
				System.out.println("수정 실패");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR! : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL ERR : " + e.getMessage());
		} finally {
			try { // 자원 해제(반납) 순서는 작은거에서 큰걸로 가야함
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("자원해제 ERR! : " + e.getMessage());
			}
		}
	}

}
