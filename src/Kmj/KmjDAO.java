package Kmj;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Main.MemberDTO;
import Main.RecipeDTO;
import Main.CookDTO;
import Main.InterfaceDAO.AboutPlay;
import Main.InterfaceDAO.Connector;

public class KmjDAO extends Connector implements AboutPlay  {
	CookDTO cookDto = new CookDTO();
	
	public ArrayList<CookDTO> loadRecipe(){
		ArrayList<CookDTO> cDtos =  new ArrayList<>();
//		cDto[].recipeList = new ArrayList<>();
	 connect();
	 try {
		PreparedStatement ps = conn.prepareStatement("select c.cook_no , c.cook_name , c.CREATE_BY,"
				+ " (SELECT COUNT(RECIPE_NO)"
				+ "  FROM RECIPE_INFO R WHERE C.COOK_NO = R.COOK_NO)CNT from cook_info c");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
				cDtos.add(new CookDTO(rs.getString("COOK_NAME"),rs.getString("CREATE_BY")
						,rs.getInt("cook_no"),rs.getInt("cnt")));
				// 각 객체 생성
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		disconnect();
		return cDtos;
	}

	public void printRecipe(ArrayList<CookDTO> cDtos ) {
//		ArrayList<CookDTO> cDtos = cookDto.getcDto();
		for (int i = 0; i < cDtos.size(); i++) {
			System.out.println("레시피 이름 : "+cDtos.get(i).getCookName()+
					"재료 개수 : "+cDtos.get(i).getCount()+"제작자 : "+cDtos.get(i).getCreatedBy());
		}
	}
	public int[] makeRandom() {
		
		int [] iArr = new int [10];
		
		 return iArr;
	}
	@Override
	public ArrayList<CookDTO> printRandomRecipe(ArrayList<CookDTO> cDtos) {
//		ArrayList<CookDTO> cDtos = cookDto.getcDto();
		connect();
		int level1 = 6;
		int level2 = 8;
		int level3 = 10;
		for (int i = 0; i < cDtos.size(); i++) {
		 if(cDtos.get(i).getCount()<=level1) {
			 try {
					PreparedStatement ps = conn.prepareStatement("SELECT recipe_no, cook_no, "
							+ "INGREDIENT FROM "
							+ "RECIPE_INFO WHERE COOK_NO = ?");
					ps.setInt(1, cDtos.get(i).getCookNum());
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						cDtos.get(i).recipeList.add(new RecipeDTO(rs.getInt("recipe_no"), 
								rs.getInt("cook_no"), rs.getString("INGREDIENT")));
					}
				}
			 catch (SQLException e) {
				e.printStackTrace();
			} 
			 
		 }else if (cDtos.get(i).getCount()<=level2) {
			 try {
					PreparedStatement ps = conn.prepareStatement("SELECT recipe_no, cook_no, "
							+ "INGREDIENT FROM "
							+ "RECIPE_INFO WHERE COOK_NO = ?");
					ps.setInt(1, cDtos.get(i).getCookNum());
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						cDtos.get(i).recipeList.add(new RecipeDTO(rs.getInt("recipe_no"), 
								rs.getInt("cook_no"), rs.getString("INGREDIENT")));
					}
				}
			 catch (SQLException e) {
				e.printStackTrace();
			}
		 }else {
			 try {
					PreparedStatement ps = conn.prepareStatement("SELECT recipe_no, cook_no, "
							+ "INGREDIENT FROM "
							+ "RECIPE_INFO WHERE COOK_NO = ?");
					ps.setInt(1, cDtos.get(i).getCookNum());
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						cDtos.get(i).recipeList.add(new RecipeDTO(rs.getInt("recipe_no"), 
								rs.getInt("cook_no"), rs.getString("INGREDIENT")));
					}
				}
			 catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		}return cDtos; 
		
			
		
		
//		
//		 try {
//			PreparedStatement ps = conn.prepareStatement("SELECT INGREDIENT FROM "
//					+ "RECIPE_INFO WHERE COOK_NO = ?");
//			ps.setInt(1, 0);
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				cDtos.add(new CookDTO(rs.getString("COOK_NAME"),rs.getString("CREATE_BY")
//						,rs.getInt("cook_no"),rs.getInt("cnt")));
//				// 각 객체 생성
//		}
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//		disconnect();
//			
		
	}

	@Override
	public String userInput() {
		Scanner sc =  new Scanner(System.in);
		System.out.println("답을 입력하세요");
		String inputStr = sc.nextLine();
		return inputStr;
	}

	@Override
	public boolean checkAnswer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void printCorrect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean printInCorrect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int minusScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printRemain() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateScore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printRandomRecipe() {
		// TODO Auto-generated method stub
		
	}

	
	
	
 
}
