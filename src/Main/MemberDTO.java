package Main;

public class MemberDTO {

	private String id, pw, nickname, gender, createdDay, address, iData;
	private int score, chance=3, level;
	private boolean isLogin;

	public MemberDTO(String id, String pw, String nickname, String gender, String createdDay, String address, int score) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.gender = gender;
		this.createdDay = createdDay;
		this.address = address;
		this.score = score;
//		this.ranking = ranking;
	}
	public int getChance() {
		return chance;
	}


	public void setChance(int chance) {
		this.chance = chance;
	}
	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}

	public String getiData() {
		return iData;
	}


	public void setiData(String iData) {
		this.iData = iData;
	}


	public boolean isLogin() {
		return isLogin;
	}


	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public MemberDTO(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

//	public class memberDTO{
//		
//	}
//	
//	public class recipeDTO{ DAO에서 배열 생성
//		private String name;
//	}


	public MemberDTO() {
		// TODO Auto-generated constructor stub
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

//	public int getRanking() {
//		return ranking;
//	}
//
//	public void setRanking(int ranking) {
//		this.ranking = ranking;
//	}

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
