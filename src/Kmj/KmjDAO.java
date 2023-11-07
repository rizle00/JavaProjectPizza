package Kmj;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Main.CookDTO;
import Main.InterfaceDAO.AboutPlay;
import Main.InterfaceDAO.Common;
import Main.MemberDTO;
import Main.RecipeDTO;

public class KmjDAO extends Common implements AboutPlay  {
	CookDTO cookDto= new CookDTO();
	MemberDTO mDto= new MemberDTO();
	ArrayList<CookDTO> cDtos=  new ArrayList<>();
//	int[] tempArr = makeRandom();
//	int randomNum= tempArr[tempArr.length-1];
	
	
	public ArrayList<CookDTO> loadRecipe(){
		
//		cDto[].recipeList = new ArrayList<>();
	 connect();
	 try {
		PreparedStatement ps = conn.prepareStatement("select c.cook_no , c.cook_name , c.CREATE_BY, c.score,"
				+ " (SELECT COUNT(RECIPE_NO)"
				+ "  FROM RECIPE_INFO R WHERE C.COOK_NO = R.COOK_NO)CNT from cook_info c");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
				cDtos.add(new CookDTO(rs.getString("COOK_NAME"),rs.getString("CREATE_BY")
						,rs.getInt("cook_no"),rs.getInt("cnt"), rs.getInt("score")));
				// 각 객체 생성
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		disconnect();
		return cDtos;
	}

	public void printRecipes() {
//		ArrayList<CookDTO> cDtos = cookDto.getcDto();
		for (int i = 0; i < cDtos.size(); i++) {
			System.out.println("레시피 이름 : "+cDtos.get(i).getCookName()+
					", 재료 개수 : "+cDtos.get(i).getCount()+",  제작자 : "+cDtos.get(i).getCreatedBy());
		}
	}
	public int[] makeRandom(ArrayList<CookDTO> cDtos) {
		r = new Random();
		int randomNum = r.nextInt(cDtos.size());
		int [] iArr = new int [cDtos.get(randomNum).recipeList.size()+1];
		iArr[iArr.length-1] = randomNum;
		for (int i = 0; i < iArr.length-1; i++) {
			iArr[i] = r.nextInt(iArr.length-1);
			for (int j = 0; j < i; j++) {
				if(iArr[i] == iArr[j]) {
					i--; 	}
			}
		}return iArr;
		
	} 
	@Override
	public int[] printRandomRecipe() {
		int[] tempArr = makeRandom(cDtos);
		int randomNum = tempArr[tempArr.length-1];
		System.out.print("요리 이름 : "+cDtos.get(randomNum).getCookName());
		System.out.println(", 재료 개수 : "+cDtos.get(randomNum).getCount());
		for (int i = 0; i < cDtos.get(randomNum).recipeList.size()-1; i++) {
			System.out.print(" 재료"+(i+1)+" : "+cDtos.get(randomNum).recipeList.get(tempArr[i]).getIngredient());
		}System.out.println();
		return tempArr;
		}
		
		public ArrayList<CookDTO> loadLowRecipe(){
			int level1 = 6;
			connect();
			for (int i = 0; i < cDtos.size()-1; i++) {
				 if(cDtos.get(i).getCount()<=level1) {
					 try {
							PreparedStatement ps = conn.prepareStatement("SELECT recipe_no, cook_no, "
									+ "INGREDIENT FROM "
									+ "RECIPE_INFO WHERE COOK_NO = ?");
							ps.setInt(1, cDtos.get(i).getCookNum());
							ResultSet rs = ps.executeQuery();
							cDtos.get(i).recipeList = new ArrayList<>(); // 초기화 필요
							while(rs.next()) {
								cDtos.get(i).recipeList.add(new RecipeDTO(rs.getInt("recipe_no"), 
										rs.getInt("cook_no"), rs.getString("INGREDIENT")));
							}
						}
					 catch (SQLException e) {
						e.printStackTrace();
					} 
				 }
			}
			disconnect();
			return cDtos;
				 
		}
		public ArrayList<CookDTO> loadMidRecipe(int level){
			connect();
			int level2 = 8;
			for (int i = 0; i < cDtos.size()-1; i++) {
			if (cDtos.get(i).getCount()<=level2) {
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
		}disconnect();
			}return cDtos;
		}
		public ArrayList<CookDTO> loadHighRecipe(){
			connect();
			int level3 = 10;
			for (int i = 0; i < cDtos.size()-1; i++) {
				if (cDtos.get(i).getCount()<=level3) {
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
				}disconnect();
			}return cDtos;
		}
	@Override
	public void checkAnswer() {
		int[] tempArr = printRandomRecipe();
		int randomNum = tempArr[tempArr.length-1];
		for (int i = 0; i < cDtos.get(randomNum).recipeList.size()-1; i++) {
			System.out.println("답을 입력하세요");
			int userScore = mDto.getScore();
			int score = cDtos.get(randomNum).getScore();
			String str = userInput();
			if(str.equals(cDtos.get(randomNum).recipeList.get(tempArr[i]).getIngredient())) {
				int count = 0;
				count++;
				if(count==cDtos.get(randomNum).recipeList.size()-1) {
					userScore += score;
					System.out.println("지금까지 "+score+"점 획득하셨습니다");
					System.out.println("다음 레시피를 출력합니다");
					mDto.setScore(userScore);
				}
			} else {
				int count =3;
				count--;
				System.out.println("오답입니다, "+count+"번 남았습니다");
				score -= 10;
				if(count==1) {
					printRemain();
				}
			}
		}
		
	}

//	@Override
//	public void printCorrect() {
////		int[] tempArr = printRandomRecipe(cDtos);
////		int randomNum = tempArr[tempArr.length-1];
//		System.out.println("정답입니다");
//		int count = 0;
//		count++;
//		if(count==cDtos.get(randomNum).recipeList.size()-1) {
//			int score = mDto.getScore();
//			score += cDtos.get(randomNum).getScore();
//			System.out.println("지금까지 "+score+"점 획득하셨습니다");
//		}
//	}

//	@Override
//	public boolean printInCorrect() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int minusScore() {
//		
//		return 0;
//	}

	@Override
	public void printRemain() {
		int count = mDto.getChance();
		count--;
		mDto.setChance(count);
		System.out.println(count+"번 남았습니다");
		System.out.println("지금까지 "+mDto.getScore()+"점 획득하셨습니다");
		System.out.println("다음 레시피를 출력합니다");
		
	}

	@Override
	public void updateScore(String id, int score) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement("update member set score =? where id = ?");
			ps.setString(1, id );
			ps.setInt(2, score );
		} catch (SQLException e) {
			e.printStackTrace();
		}disconnect();
		
	}

	@Override
	public void gameEnd(int chance) {
		if(chance==0) {
			System.out.println("게임이 끝났습니다");
			System.out.println("지금까지 "+mDto.getScore()+"점 획득하셨습니다");
		}
	}

	
	
	
	
 
}
