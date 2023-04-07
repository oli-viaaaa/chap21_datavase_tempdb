package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 카테고리가 "전자제품"인 상품들의 정보를 출력하세요
 * - 출력할 컬럼 : 상품 ID, 상품명, 가격, 입고일, 카테고리 ID, 카테고리 명
 * - 가격 순 정렬
 */

public class Database03_Select {
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

			// 2. 데이터베이스 커넥션(연결)
			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("2. 커넥션 객체 생성 성공!");

			// 3. 조회
			String categoryName = "전자제품"; 
			
			// 4. 생성한 statment 객체를 통해서 쿼리하기 위한 SQL 쿼리 문장 만들기(삽입, 수정, 삭제, 조회)
			String sql = "select P.PRODUCT_ID, P.PRODUCT_NAME, P.PRICE, P.RECEIPT_DATE, C.CATEGORY_ID, C.CATEGORY_NAME";
				   sql += " from product P, category C";
				   sql += " where p.category_id = c.category_id";
				   sql += " and C.CATEGORY_NAME = ?";
				   sql += " ORDER by p.price desc";
				   
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, categoryName);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getInt("PRODUCT_ID")+"\t"
								  + rs.getString("PRODUCT_NAME")+"\t"
								  + rs.getInt("PRICE")+"\t"
								  + rs.getDate("RECEIPT_DATE")+"\t"
								  + rs.getInt("CATEGORY_ID")+"\t"
								  + rs.getString("CATEGORY_NAME"));
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR! :" + e.getMessage());
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
