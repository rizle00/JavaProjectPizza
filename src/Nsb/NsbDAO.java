package Nsb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.InterfaceDAO.Connector;
import Main.MemberDTO;
import Main.RecipeDTO;

public class NsbDAO extends Connector{
	
	// 레시피 목록 출력
	public ArrayList<RecipeDTO> printRecipe(){
		ArrayList<RecipeDTO> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(" SELECT * FROM RECIPE_INFO ");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RecipeDTO dto = new RecipeDTO(rs.getInt("recipeNo"),rs.getInt("cookNum"), rs.getString("ingredient"));
				list.add(dto);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
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
    
    // 레시피 중복
    public boolean addRecipe(RecipeDTO dto) {
    	// exists 서브쿼리를 통해 조건에 해당하는 컬럼이 존재하는지를 반환하는 형태
    	// 특정 조건의 일치 여부에 대해 true / false 반환
    	// exists = 해당 서브쿼리에서 값이 있는지만 확인하는
        boolean exists = false; 
        try (
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM RECIPE WHERE ingredient = ?")) {
        	ps.setInt(1, dto.getRecipeNo());
        	ps.setInt(1, dto.getCookNum());       	
        	ps.setString(1, dto.getIngredient());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    exists = count > 0; // 해당 조건에 해당하는 레코드가 있으면 true 할당 . 중복확인
                }else {
                	System.out.println("중복된 레시피 입니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    
    // 레시피 수정
    public void updateRecipe(RecipeDTO dto) {
        try (
             PreparedStatement ps = conn.prepareStatement("UPDATE RECIPE SET cooknum = ?, recipeno = ?, ingredient = ? WHERE id = ?")) {
            	 ps.setInt(1, dto.getCookNum());
            	 ps.setInt(1, dto.getRecipeNo());
            	 ps.setString(1,dto.getIngredient());
            	 ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 레시피 삭제
    public void deleteRecipe(RecipeDTO dto) {
        try (
        	PreparedStatement ps = conn.prepareStatement("DELETE FROM RECIPE WHERE id = ?")) {
        	ps.setInt(1, dto.getRecipeNo());
        	ps.setInt(1, dto.getCookNum());
        	ps.setString(1, dto.getIngredient());
        	ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 레시피 db 땡겨와서 인덱스에 점수 입력 처리
    public void inputData(RecipeDTO recipedto , MemberDTO memberdto) {
        try (
             PreparedStatement ps = conn.prepareStatement("")) {
        	ps.setInt(1, recipedto.getRecipeNo());
        	ps.setInt(1, recipedto.getCookNum());
        	ps.setString(1, recipedto.getIngredient());
        	ps.setInt(1, memberdto.getScore());
        	ps.executeUpdate();
            System.out.println("점수입력 : " + memberdto.getScore());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

	

