package Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Main.InterfaceDAO.Common;

public class DAO extends Common{
	CookDTO cookDto= new CookDTO();
	MemberDTO mDto= new MemberDTO();
	ArrayList<CookDTO> cDtos=  new ArrayList<>();
	
	public void select() {
		System.out.println("hello world! 로그인하려면 1, 종료하려면 0을 입력해주세요!");
		while (true) {
			String tempStr = userInput();
			String iData = null;
			if (tempStr.equals("1")) { 
				iData = login();
				break;
			} else if (tempStr.equals("0") || iData.equals("0") ) { // 게임종료
				endProgram();
				break;
			} else {
				System.out.println("유효한 값이 아닙니다!");
			}
		}
	}
	
	
	public void selectMode() {// 로그인 후 모드 선택,로그아웃, 점수출력, 레시피추가, 게임시작
		System.out.println("메뉴를 선택해주세요!");
		while (mDto.isLogin()) {
				System.out.println("1 - 회원정보관리 / 2 - 점수출력 / 3- 레시피 추가 / 4 -  게임시작  /  5 - 로그아웃");
				int tempN = userNum();
				switch (tempN) {
				case 1: 
					selectUserInfo();
					break;
				case 2:
					printScore();
					break;
				case 3:
					//레시피 추가 메소드 자리
					break;
				case 4: 
					startGame();
					break;
				case 5:
					rogout();
					break;
				default :
					System.out.println("유효한 값이 아닙니다.");
				}
		}//와일문 끝
	}
	
	public String idCheck() {// 아이디 있는지 체크
		connect();
		while (true) {

			System.out.println("아이디를 입력해주세요");
			String existId = userInput();
			try {
				PreparedStatement ps = conn.prepareStatement("select * from member where id=?");
				ps.setString(1, existId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					mDto = new MemberDTO(rs.getString("id"), rs.getString("pw"), rs.getString("nickname"),
							rs.getString("gender"), rs.getString("createdDay"), rs.getString("address"),
							rs.getInt("score"));

					System.out.println("비밀번호를 입력해 주세요");
					String pw = userInput();
					disconnect();
					return pw;
				} else {
					System.out.println("존재하지않는 아이디!");
					System.out.println("회원가입을 원하시면 1, 종료는 0을 눌러주세요");
					String uInput = userInput();
					if(uInput.equals("1")) {
						resister();
					}
					disconnect();
					return uInput;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String login() {//로그인
		String pw = null;
		while (true) {
			 pw = idCheck();
			if (pw.equals(mDto.getPw())) {
				System.out.println("로그인 완료!!!");
				mDto.setLogin(true);
				break;
			} else {
				System.out.println("아이디&비번오류!! 다시 로그인해주세요!!!");
			}
		} return pw;
	}
	public void selectUserInfo() {
		while(mDto.isLogin()) {
			System.out.println("메뉴를 선택해주세요");
			System.out.println("1 - 회원 정보 수정 / 2 - 회원 탈퇴 / 3 - 이전화면으로");
			int choice = userNum();
			switch(choice) {
			case 1:
				modifyInfo();
				break;
			case 2:
				deleteId();
				break;
			case 3:
				System.out.println("이전화면으로 돌아갑니다");
				choice =3;
				break;
			default :
				System.out.println("잘못 된 입력입니다");
			}
			if(choice==3) {
				break;
			}
		}
	}
	public void resister() {
		// 회원가입
		System.out.println("회원가입합니다");
		System.out.println("아이디 입력 : ");
		String inId = userInput();

		System.out.println("비밀번호 입력 : ");
		String inPw = userInput();

		System.out.println("닉네임 입력 : ");
		String inNickName = userInput();

		System.out.println("성별 입력 : ");
		String inGender = userInput();

		System.out.println("주소 입력 : ");
		String inAdress = userInput();

		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO MEMBER (ID, PW, NICKNAME,GENDER, ADDRESS) VALUES (?,?,?,?,?)");
			ps.setString(1, inId);
			ps.setString(2, inPw);
			ps.setString(3, inNickName);
			ps.setString(4, inGender);
			ps.setString(5, inAdress);
			 ps.executeUpdate();
			 System.out.println("회원 가입 되었습니다");
//			System.out.println(conn.getAutoCommit());


		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	
	public void modifyInfo() {// 개인정보수정

		while (mDto.isLogin()) {
			System.out.println("변경하실 회원정보를 입력해주세요");
			
			System.out.print("1. 비밀번호 변경 ");
			System.out.print("2. 닉네임 변경 ");
			System.out.print("3. 성별 변경 ");
			System.out.print("4. 주소 변경 ");
			System.out.println("5. 이전 화면으로");
			String change = null;		//변경할 행의 컬럼이름!
			String str = null;          //변경될 컬럼의값
			String choice = userInput();
			switch (choice) {
			
				
			case "1":
				System.out.println("비밀번호 변경 :");
				change = "pw";
				str = userInput();
				break;

			case "2":
				System.out.println("닉네임 변경 :");
				change = "nickname";
				str = userInput();
				break;
			case "3":
				System.out.println("성별 변경 :");
				change = "gender";
				str = userInput();
				break;
			case "4":
				System.out.println("주소 변경 :");
				change = "address";
				str = userInput();
				break;
			case "5":
				System.out.println("이전화면으로 돌아갑니다");
				
			default:
				System.out.println("잘못된 입력입니다");
				break;
			}
			
			try {
				PreparedStatement ps = conn.prepareStatement("UPDATE MEMBER SET ? = ? WHERE ID=?");
				ps.setString(1, change);
				ps.setString(2, str);
				ps.setString(3, mDto.getId());    
				ps.executeUpdate();
//				System.out.println(conn.getAutoCommit());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("회원정보가 변경되었습니다.");
			if(choice.equals("5")){
				break;
			}
		}
	}
	
	public void deleteId() {//삭제
		while(mDto.isLogin()) {
		System.out.println("회원 탈퇴를 하시려면 y를 취소하시려면 n을 입력하세요");
		String iData = userInput();
		iData.toLowerCase();
		if(iData.equals("y")) {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM MEMBER WHERE ID = ? AND PW = ?");

				ps.setString(1, mDto.getId());
				ps.setString(2, mDto.getPw());
				ps.executeUpdate();
				System.out.println(mDto.getId()+", 탈퇴되었습니다");
				break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}else if(iData.equals("n")) {
			System.out.println("이전 화면으로 돌아갑니다");
			break;
		} else {
			System.out.println("잘못된 입력입니다");
			continue;
		}
		}
	}
	
	public void startGame() { //게임시작
		System.out.println("게임을 시작합니다!");
		loadCooks();
		printCooks();
		selectLevel();
		loadRecipe(mDto.getLevel());
		checkAnswer();
	}

	public void selectLevel() {// 난이도 선택 상 중 하+ 최상?
		while (mDto.isLogin()) {
			try {
				System.out.println("난이도 1~3 중 선택해주세요!");
				int tempN = userNum();
				if (tempN >0 && tempN <= 3) {
					mDto.setLevel(tempN);
				}else {
					System.out.println("유효한 값이 아닙니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("유효한 값이 아닙니다. ");
			}
		}
	}
	
	public void loadCooks() {
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
	}
	
	public void printCooks() {
//		ArrayList<CookDTO> cDtos = cookDto.getcDto();
		for (int i = 0; i < cDtos.size(); i++) {
			System.out.println("레시피 이름 : "+cDtos.get(i).getCookName()+
					", 재료 개수 : "+cDtos.get(i).getCount()+",  제작자 : "+cDtos.get(i).getCreatedBy());
		}
	}
	
	
	public void loadRecipe(int level){
		int range = 0;
		if(level ==1) {
			range = 6;
		} else if(level ==2) {
			range = 8;
		} else {
			range = 15;
		}
		connect();
		for (int i = 0; i < cDtos.size()-1; i++) {
			 if(cDtos.get(i).getCount()<=range) {
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
	
	public int[] printRandomRecipe() {
		int[] tempArr = makeRandom(cDtos);
		int randomNum = tempArr[tempArr.length-1];
		System.out.println("문제 나갑니다");
		System.out.print("요리 이름 : "+cDtos.get(randomNum).getCookName());
		System.out.println(", 재료 개수 : "+cDtos.get(randomNum).getCount());
		for (int i = 0; i < cDtos.get(randomNum).recipeList.size()-1; i++) {
			System.out.print(" 재료"+(i+1)+" : "+cDtos.get(randomNum).recipeList.get(tempArr[i]).getIngredient());
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 100; i++) {
			System.out.println();
		}
		return tempArr;
		}
	
	public void checkAnswer() {
		while(mDto.isLogin()) {
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
				System.out.println("오답입니다, 10점 감점됩니다\r\n"+count+"번 남았습니다");
				score -= 10;
				if(count==1) {
					printRemain();
				}
			}
		}if(mDto.getChance()==0) {
			gameEnd();
			updateScore(mDto.getId(), mDto.getScore());
			break;
		}
		}
	}
	
	public void printRemain() {
		int count = mDto.getChance();
		count--;
		mDto.setChance(count);
		System.out.println("3번 틀리셨습니다, 이번 레시피는 실패입니다");
		System.out.println("기회는 "+count+"번 남았습니다");
		System.out.println("지금까지 "+mDto.getScore()+"점 획득하셨습니다");
		System.out.println("다음 레시피를 출력합니다");
		
	}
	
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
	
	public void gameEnd() {
		
			System.out.println("게임이 끝났습니다");
			System.out.println("지금까지 "+mDto.getScore()+"점 획득하셨습니다");
			
	}
	public void rogout() {
		mDto.setLogin(false);
		System.out.println("로그아웃되었습니다!");
		select();
	}
	
	public void printScore() {
		try {
			connect();
			PreparedStatement ps = conn.prepareStatement("SELECT NICKNAME, SCORE FROM MEMBER WHERE SCORE IS NOT NULL ORDER BY SCORE DESC");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int i = 1;
				System.out.println(i+"등, 닉네임 : " + rs.getString("nickname") + "\t 점수 : " + rs.getInt("Score"));
				i++;
			}
			disconnect();
			selectMode();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void endProgram() {
		System.out.println("프로그램을 종료합니다");
	}
}
