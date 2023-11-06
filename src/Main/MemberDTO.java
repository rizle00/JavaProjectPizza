package Main;

public class MemberDTO {

	private String id, pw, nickname, gender, createdDay, address;
	private int score, ranking;

	
	MemberDTO(String id, String pw, String nickname, String gender, String createdDay, String address, int score, int ranking) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.gender = gender;
		this.createdDay = createdDay;
		this.address = address;
		this.score = score;
		this.ranking = ranking;
	}
//	public class memberDTO{
//		
//	}
//	
//	public class recipeDTO{ DAO에서 배열 생성
//		private String name;
//	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCreatedDay() {
		return createdDay;
	}

	public void setCreatedDay(String createdDay) {
		this.createdDay = createdDay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	
}
