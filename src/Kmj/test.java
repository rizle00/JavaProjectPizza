package Kmj;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Main.CookDTO;
import Main.InterfaceDAO.Connector;

class test{

	
	
		public ArrayList<CookDTO> loadRecipe(){
			ArrayList<CookDTO> cDtos =  new ArrayList<>();
//			cDto[].recipeList = new ArrayList<>();
			Connection conn = null;
			String url = "jdbc:oracle:thin:@118.40.91.135:1521:xe";
			String user = "ATEAM";
			String password = "ATEAM1";
//			try {
				try {
					conn = DriverManager.getConnection(url, user, password);
				} catch (Exception e) {
					e.printStackTrace();}
		 
		 try {
			PreparedStatement ps = conn.prepareStatement("select c.cook_no , c.cook_name , c.CREATE_BY,"
					+ " (SELECT COUNT(RECIPE_NO)"
					+ "  FROM RECIPE_INFO R WHERE C.COOK_NO = R.COOK_NO)CNT from cook_info c");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
					cDtos.add(new CookDTO(rs.getString("COOK_NAME"),rs.getString("CREATE_BY")
							,rs.getInt("cook_no"),rs.getInt("cnt")));
					
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			System.out.println(cDtos.get(0).getCookName());
		 return cDtos;
		}
	

	@Test
	void test1() {
		
		ArrayList<CookDTO> cDtos = loadRecipe();
		System.out.println(cDtos.size());
//		for (int i = 0; i < cDtos.size(); i++) {
//			
//			System.out.println("레시피 이름 : "+cDtos.size());
		
	}
}
