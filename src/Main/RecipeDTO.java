package Main;

public class RecipeDTO {
	private int recipeNo, cookNum; // 레시피 번호 , 
	private String ingredient; // 재료
	
	public RecipeDTO(int recipeNo, int cookNum, String ingredient) {
		this.recipeNo = recipeNo;
		this.cookNum = cookNum;
		this.ingredient = ingredient;
	}

	

	public int getRecipeNo() {
		return recipeNo;
	}
	public void setRecipeNo(int recipeNo) {
		this.recipeNo = recipeNo;
	}
	public int getCookNum() {
		return cookNum;
	}
	public void setCookNum(int cookNum) {
		this.cookNum = cookNum;
	}
	

	
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	
	
}
