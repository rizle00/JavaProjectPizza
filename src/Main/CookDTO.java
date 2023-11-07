package Main;

import java.util.ArrayList;

public class CookDTO {
//	private ArrayList<CookDTO> cDto;
	
	private String cookName , createdDate, createdBy;
	private int cookNum, count, score;
	public ArrayList<RecipeDTO> recipeList;
	public CookDTO() {
	}
	public CookDTO(String cookName, String createdBy, int cookNum, int count, int score) {
		this.cookName = cookName;
		this.createdBy = createdBy;
		this.cookNum = cookNum;
		this.count = count;
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getCookName() {
		return cookName;
	}
	public void setCookName(String cookName) {
		this.cookName = cookName;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getCookNum() {
		return cookNum;
	}
	public void setCookNum(int cookNum) {
		this.cookNum = cookNum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int ccore) {
		this.count = ccore;
	}
//	public ArrayList<CookDTO> getcDto() {
//		return cDto;
//	}
//	public void setcDto(ArrayList<CookDTO> cDtos) {
//		this.cDto = cDtos;
//	}
	
}
