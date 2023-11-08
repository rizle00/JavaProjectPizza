package Nsb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.InterfaceDAO.AboutRecipe;
import Main.InterfaceDAO.Common;
import Main.CookDTO;
import Main.MemberDTO;

public class NsbDAO extends Common implements AboutRecipe {
	CookDTO cookDto = new CookDTO();
	MemberDTO mDto = new MemberDTO();
	ArrayList<CookDTO> cDtos = new ArrayList<>();

	public ArrayList<CookDTO> loadRecipe() {
//		ArrayList<RecipeDTO> list = new ArrayList<>();
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"select c.cook_no , c.cook_name , c.CREATE_BY, c.score," + " (SELECT COUNT(RECIPE_NO)"
							+ "  FROM RECIPE_INFO R WHERE C.COOK_NO = R.COOK_NO)CNT from cook_info c");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cDtos.add(new CookDTO(rs.getString("COOK_NAME"), rs.getString("CREATE_BY"), rs.getInt("cook_no"),
						rs.getInt("cnt"), rs.getInt("score")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
		return cDtos;
	}

	@Override // 레시피 출력
	public void printRecipe() {
		for (int i = 0; i < cDtos.size(); i++) {
			System.out.println("레시피 : " + cDtos.get(i).getCookName() + "들어가는 재료 개수 : " + cDtos.get(i).getCount());
		}

	}
	// 레시피 출력
//    public void printRecipe(RecipeDTO dto) {
//        try (
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM RECIPE_INFO WHERE id = ?")) {
//            ps.setInt(1, dto.getRecipeNo());
//            ps.setInt(1, dto.getCookNum());
//            ps.setString(1,dto.getIngredient());
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    System.out.println("RecipeNo: " + rs);
//                    System.out.println("CookNum: " + rs);
//                    System.out.println("Ingredient: " + rs);
//                } else {
//                    System.out.println("레시피를 찾을 수 없습니다.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

	public int addCook() {
		String cookName;
		int score;
		int count = 0;
		while (true) {
			System.out.println("레시피 이름을 입력해주세요.");
			cookName = userInput();
			for (int i = 0; i < cDtos.size() - 1; i++) {
				if (cDtos.get(i).getCookName().equals(cookName)) {
					System.out.println("중복된 레시피 이름입니다.");
				} else {
					count++;
				}
			}
			System.out.println("다른 레시피 이름을 입력해주세요.");
			if (count == cDtos.size() - 1) {
			}
			break;

		}
		System.out.println("몇 개의 재료를 입력하실건가요? (최소 4개 이상 선택) ");
		score = userNum();
		while (score < 4) {
			System.out.println("재료의 개수를 4개이상 입력해주세요.");
		}

		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO COOK_INFO ( COOK_NO, COOK_NAME , SCORE, create_by) "
							+ "SELECT MAX(COOK_NO) +1 , ?  , ? , ?  FROM COOK_INFO");
			ps.setString(1, cookName);
			ps.setInt(2, (score * 20) + 100);
			ps.setString(3, mDto.getNickname());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}return score;
	}

	// 레시피 중복
	@Override
	public void addRecipe() {
		int inputNum = addCook();
		for (int i = 0; i < inputNum-1; i++) {
			System.out.println(i+1+"재료를 입력해주세요.");
			String str = userInput();
			try {
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO RECIPE_INFO (COOK_NO, RECIPE_NO, INGREDIENT) "
								+ " SELECT MAX(COOK_NO)  , ? , ? from cook_info");
				ps.setInt(1, i+1);
				ps.setString(2, str);
				ps.executeUpdate();
				System.out.println(i+1+"번 재료 입력되었습니다");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} System.out.println(cDtos.get(cDtos.size()-1).getCookName()+"이 추가되었습니다.");
		
	}

	// 레시피 수정
	@Override
	public void deleteRecipe() {
		String select = null;// 음식이냐 재료냐
		String select2 =null;// 음식이냐 재료냐2
		String inputStr =null;
		String select3 =null;// 음식번호냐 재료번호냐
		int inputNum =0 ;
		System.out.println("음식이름을 수정하려면 1, 재료를 수정하려면 2를 입력하세요");
		int choice = userNum();
		while(choice!=-10) {
			switch(choice) {
			 case 1:
				 System.out.println("수정할 음식의 번호를 입력하세요");
				 inputNum = userNum();
				select = "cook_info";
				select2 = "cook_name";
				select3 = "cook_no";
				 System.out.println("수정할 이름을 입력하세요");
				 inputStr = userInput();
				 choice = -10;
				break;
			 case 2 :
				 System.out.println("수정할 재료의 번호를 입력하세요");
				 inputNum = userNum();
				 select = "RECIPE_INFO";
				 select2 = "INGREDIENT";
				 select3 = "RECIPE_NO";
				 System.out.println("수정할 재료를 입력하세요");
				 inputStr = userInput();
				 choice=-10;
				 break;
			default :
				System.out.println("잘못된 입력입니다");
				
			}
		}
		
		
		try (PreparedStatement ps = conn
				.prepareStatement("update ? set ? = ? where ? = ?")) {
			ps.setString(1, select);
			ps.setString(2, select2);
			ps.setString(3, inputStr);
			ps.setString(4, select3);
			ps.setInt(5, inputNum);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 레시피 삭제
	@Override
	public void modifyRecipe() {
		String select = null;
		String select2 =null;
		String inputStr =null;
		String select3 =null;
		int inputNum =0 ;
		System.out.println("---------------------------------------------------------");
		System.out.println("1 - 음식 이름 삭제  2 - 이전화면으로");
		int choice = userNum();
		while(choice!=-10) {
			switch(choice) {
			 case 1:
				 System.out.println("---------------------------------------------------------");
				 System.out.println("삭제할 음식의 번호를 입력하세요");
				 inputNum = userNum();
				select = "cook_info";
				select2 = "cook_name";
				select3 = "cook_no";
				System.out.println("---------------------------------------------------------");
				 System.out.println("삭제할 이름을 입력하세요");
				 inputStr = userInput();
				 choice = -10;
				break;
			 case 2 :
				 System.out.println("---------------------------------------------------------");
				 System.out.println("이전화면으로 돌아갑니다");
				 break;
			default :
				System.out.println("---------------------------------------------------------");
				System.out.println("잘못된 입력입니다");
				
			}
		}
		try (PreparedStatement ps = conn
				.prepareStatement("delete ? set ? = ? where ? = ?")) {
			ps.setString(1, select);
			ps.setString(2, select2);
			ps.setString(3, inputStr);
			ps.setString(4, select3);
			ps.setInt(5, inputNum);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 레시피 db 땡겨와서 인덱스에 점수 입력 처리
//    public void inputData(RecipeDTO recipedto , MemberDTO memberdto) {
//        try (
//             PreparedStatement ps = conn.prepareStatement("")) {
//        	ps.setInt(1, recipedto.getRecipeNo());
//        	ps.setInt(1, recipedto.getCookNum());
//        	ps.setString(1, recipedto.getIngredient());
//        	ps.setInt(1, memberdto.getScore());
//        	ps.executeUpdate();
//            System.out.println("점수입력 : " + memberdto.getScore());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
