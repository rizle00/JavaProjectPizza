package Nsb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NsbDAO {
private Connection conn;
	
	public boolean isConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hanul";
		String password = "0000";
		try {
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 통신 닫기
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 있는 레시피 출력 , 레시피디티오 목록정보를 다 불러 , 레시피 목록 조회
	public ArrayList<RecipeDTO> printRecipe() {
		ArrayList<RecipeDTO> list = new ArrayList<>(); // 레시피 리스트
		if(isConnection()) {
			try {
				PreparedStatement ps = conn.prepareStatement(" SELECT * FROM RECIPE ");// db연결
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					RecipeDTO dto = new RecipeDTO(0,0,rs.getString("ingredient"));
					list.add(dto);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 중복 예외 처리 필요 , db에서 기본키 지정
	public void addRecipe() {
		
	}
	
	// 레시피 수정
	public void modifyRecipe(RecipeDTO dto) {
		if(isConnection()) {
			try {
				PreparedStatement ps = conn.prepareStatement(""); //db 연결 후 수정
				ps.setString(0, null);//레시피 수정 재료 이름만 가능.
				int result = ps.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeConnection();
			}
		}
	}
	
	// 레시피 삭제
	public void deleteRecipe(RecipeDTO dto) {
		if (isConnection()) {
			try {
				PreparedStatement ps = conn.prepareStatement(""); // db 연결 후 수정
				ps.setString(0, null);// 레시피 재료이름만 삭제
				int result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}
	}
	// 레시피 db에서 불러와서 인덱스에 점수 입력 처리
	public void inputData() {
		
	}
}
