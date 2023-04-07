package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Product 테이블에 상품 추가
 * 카테고리 : 식료품, 상품Id : 21, 상품명 : 시금치, 가격 : 3500, 입고일 : 2023.02.10
 */

public class Database04_Insert {
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
		// 커넥션 객체를 통해서 데이터베이스에 쿼리(SQL)를 실행해주는 객체
		PreparedStatement pstmt = null;
		// 실행된 쿼리문의 결과를 반환 받는 객체
		ResultSet rs = null;
		
		try {
			// 1. 드라이버 로딩
			Class.forName(driver);
			System.out.println("1. 드라이버 로드 성공!");
			
			// 2. 데이터베이스 커넥션
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("2. 커넥션 객체 생성 성공!");
			
			// 3. 테이블에 ? 문자가 포함된 insert 문에 넣을 인자 생성
			int PRODUCT_ID = 21;
			String PRODUCT_NAME = "시금치";
			int PRICE = 3500;
			int CATEGORY_ID = 5;
			String RECEIPT_DATE = "20230210";
			
			// 4. 저장 SQL문 생성하기
			String sql = "insert into PRODUCT(PRODUCT_ID,PRODUCT_NAME, PRICE, CATEGORY_ID, RECEIPT_DATE)";
				   sql += " values(?,?,?,?,to_date(?,'YYYY/MM/DD'))";
				   
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, PRODUCT_ID);
			pstmt.setString(2, PRODUCT_NAME);
			pstmt.setInt(3, PRICE);
			pstmt.setInt(4, CATEGORY_ID);
			pstmt.setString(5, RECEIPT_DATE);
			
			int resultRows = pstmt.executeUpdate();
			// executeUpdate 쿼리 업데이트 한다
			if (resultRows>0) {
				System.out.println("저장 성공");
			} else {
				System.out.println("저장 실패");
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
