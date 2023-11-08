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
		loadCooks();
		printCooks();
		while (mDto.isLogin()) {
			
			System.out.println("---------------------------------------------------------");
				System.out.println("메뉴를 선택해주세요!");
				System.out.println("1 - 회원정보관리 / 2 - 점수출력 / 3- 레시피 관리 / 4 -  게임시작  /  5 - 로그아웃");
				int tempN = userNum();
				switch (tempN) {
				case 1: 
					selectUserInfo();
					break;
				case 2:
					printScore();
					break;
				case 3:
					aboutRecipe();
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
			System.out.println("---------------------------------------------------------");
			System.out.println("아이디를 입력해주세요");
			String existId = userInput();
			try {
				PreparedStatement ps = conn.prepareStatement("select * from member where id=?");
				ps.setString(1, existId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					mDto = new MemberDTO(rs.getString("id"), rs.getString("pw"), rs.getString("nickname"),
							rs.getString("gender"), rs.getString("created_Day"), rs.getString("address"),
							rs.getInt("score"));

					System.out.println("비밀번호를 입력해 주세요");
					String pw = userInput();
					return pw;
				} else {
					System.out.println("---------------------------------------------------------");
					System.out.println("존재하지않는 아이디!");
					while(true) {
						System.out.println("회원가입을 원하시면 1, 종료는 0을 눌러주세요");
						String uInput = userInput();
						if(uInput.equals("1")) {
							resister();
							break;
						} else if(uInput.equals("0")){
							break;
						} else {
							System.out.println("잘못된 입력입니다");
						}
					}// 내부 와일 끝
				}// 엘스문
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
				System.out.println("---------------------------------------------------------");
				System.out.println("로그인 완료!!!");
				mDto.setLogin(true);
				disconnect();
				selectMode();
				break;
			} else {
				System.out.println("---------------------------------------------------------");
				System.out.println("아이디&비번오류!! 다시 로그인해주세요!!!");
				continue;
			}
		} return pw;
	}
	public void selectUserInfo() {
		connect();
		while(mDto.isLogin()) {
			System.out.println("---------------------------------------------------------");
			System.out.println("메뉴를 선택해주세요");
			System.out.println("1 - 회원 정보 수정 / 2 - 회원 탈퇴 / 3 - 이전화면으로");
			int choice = userNum();
			switch(choice) {
			case 1:
				modifyInfo();
				break;
			case 2:
				deleteId();
				select();
				break;
			case 3:
				System.out.println("---------------------------------------------------------");
				System.out.println("이전화면으로 돌아갑니다");
				choice =3;
				break;
			default :
				System.out.println("---------------------------------------------------------");
				System.out.println("잘못 된 입력입니다");
			}
			if(choice==3) {
				disconnect();
				break;
			}
		}
	}
	public void resister() {
		System.out.println("---------------------------------------------------------");
		System.out.println("회원가입합니다");
		System.out.println("아이디 입력 : ");
		String inId = userInput();
		System.out.println("---------------------------------------------------------");
		System.out.println("비밀번호 입력 : ");
		String inPw = userInput();
		System.out.println("---------------------------------------------------------");
		System.out.println("닉네임 입력 : ");
		String inNickName = userInput();
		System.out.println("---------------------------------------------------------");
		System.out.println("성별 입력 : ");
		String inGender = userInput();
		System.out.println("---------------------------------------------------------");
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
			 System.out.println("---------------------------------------------------------");
			 System.out.println("회원 가입 되었습니다");
//			System.out.println(conn.getAutoCommit());

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	
	public void modifyInfo() {// 개인정보수정

		while (mDto.isLogin()) {
			System.out.println("---------------------------------------------------------");
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
				System.out.println("---------------------------------------------------------");
				System.out.println("비밀번호 변경 :");
				change = "pw";
				str = userInput();
				break;

			case "2":
				System.out.println("---------------------------------------------------------");
				System.out.println("닉네임 변경 :");
				change = "nickname";
				str = userInput();
				break;
			case "3":
				System.out.println("---------------------------------------------------------");
				System.out.println("성별 변경 :");
				change = "gender";
				str = userInput();
				break;
			case "4":
				System.out.println("---------------------------------------------------------");
				System.out.println("주소 변경 :");
				change = "address";
				str = userInput();
				break;
			case "5":
				System.out.println("---------------------------------------------------------");
				System.out.println("이전화면으로 돌아갑니다");
				break;
			default:
				System.out.println("---------------------------------------------------------");
				System.out.println("잘못된 입력입니다");
				break;
			}
			if(choice.equals("5")){
				break;
			}
			try {
				PreparedStatement ps = conn.prepareStatement("UPDATE MEMBER SET "+change+" = ? WHERE ID=?");
//				ps.setString(0, change); => '' 가 붙음 set은..
				ps.setString(1, str);
				ps.setString(2, mDto.getId());    
				ps.executeUpdate();
//				System.out.println(conn.getAutoCommit());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("---------------------------------------------------------");
			System.out.println("회원정보가 변경되었습니다.");
			
		}
	}
	
	public void deleteId() {//삭제
		while(mDto.isLogin()) {
			System.out.println("---------------------------------------------------------");
		System.out.println("회원 탈퇴를 하시려면 y를 취소하시려면 n을 입력하세요");
		String iData = userInput();
		iData.toLowerCase();
		if(iData.equals("y")) {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM MEMBER WHERE ID = ? AND PW = ?");
				ps.setString(1, mDto.getId());
				ps.setString(2, mDto.getPw());
				ps.executeUpdate();
				System.out.println("---------------------------------------------------------");
				System.out.println(mDto.getId()+"님 탈퇴되었습니다");
				mDto.setLogin(false);
				break;
			} catch (SQLException e) {
				e.printStackTrace();
				}
		}else if(iData.equals("n")) {
			
			System.out.println("---------------------------------------------------------");
			System.out.println("이전 화면으로 돌아갑니다");
			break;
		} else {
			System.out.println("---------------------------------------------------------");
			System.out.println("잘못된 입력입니다");
			continue;
		}
		}
	}
	
	public void aboutRecipe() {
		printCooks();
		connect();
		while(mDto.isLogin()) {
			System.out.println("---------------------------------------------------------");
			System.out.println("메뉴를 선택해 주세요");
			System.out.println("1 - 레시피 추가 / 2 - 레시피 수정 / 3 - 레시피 삭제 / 4 - 이전화면으로");
			int choice = userNum();
			switch(choice) {
			case 1:
				addRecipe();
				break;
			case 2:
				modifyRecipe();
				break;
			case 3:
				deleteRecipe();
				break;
			case 4:
				System.out.println("---------------------------------------------------------");
				System.out.println("이전화면으로 돌아갑니다");
				choice = 4;
				break;
			default :
				System.out.println("---------------------------------------------------------");
				System.out.println("잘못 된 입력입니다");
			}
			if(choice==4) {
				break;
			}
		}
	}
	
	public int addCook() {
		String cookName = null;
		int score;
		int count = 0;
		while (mDto.isLogin()) {
			System.out.println("---------------------------------------------------------");
			System.out.println("레시피를 추가합니다");
			System.out.println("추가하실 레시피 이름을 입력해주세요.");
			cookName = userInput();
			for (int i = 0; i < cDtos.size() - 1; i++) {
				if (cDtos.get(i).getCookName().equals(cookName)) {
					System.out.println("---------------------------------------------------------");
					System.out.println("중복된 레시피 이름입니다.");
					System.out.println("다른 레시피 이름을 입력해주세요.");
				} else {
					count++;
				}
			}
			if (count == cDtos.size() - 1) {
				break;
			}

		}
		System.out.println("---------------------------------------------------------");
		System.out.println("몇 개의 재료를 입력하실건가요? (최소 4개 이상 선택) ");
		score = userNum();
		while (score < 4) {
			System.out.println("---------------------------------------------------------");
			System.out.println("재료의 개수를 4개이상 입력해주세요.");
			break;
		}

		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO COOK_INFO ( COOK_NO, COOK_NAME , SCORE, create_by) "
							+ "SELECT MAX(COOK_NO) +1 , ?  , ? , ?  FROM COOK_INFO");
			ps.setString(1, cookName);
			ps.setInt(2, (score * 20) + 20);
			ps.setString(3, mDto.getNickname());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}return score;
	}

	public void addRecipe() {
		int inputNum = addCook();
		for (int i = 0; i < inputNum; i++) {
			System.out.println("---------------------------------------------------------");
			System.out.println(i+1+"번 재료를 입력해주세요.");
			String str = userInput();
			try {
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO RECIPE_INFO (COOK_NO, RECIPE_NO, INGREDIENT) "
								+ " SELECT MAX(COOK_NO)  , ? , ? from cook_info");
				ps.setInt(1, i+1);
				ps.setString(2, str);
				ps.executeUpdate();
				System.out.println("---------------------------------------------------------");
				System.out.println(i+1+"번 재료 입력되었습니다");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		System.out.println("---------------------------------------------------------");
		System.out.println(cDtos.get(cDtos.size()-1).getCookName()+"이 추가되었습니다.");
		
	}
	
	public void modifyRecipe() {
		String select = null;// 음식이냐 재료냐
		String select2 =null;// 음식이냐 재료냐2
		String inputStr =null;
		String select3 =null;// 음식번호냐 재료번호냐
		String select4 =null;// 음식번호냐 재료번호냐
		int inputNum =0 ;
		while(mDto.isLogin()) {
			System.out.println("---------------------------------------------------------");
			 System.out.println("수정할 음식의 번호를 입력하세요, 0 - 이전화면으로");
			 inputNum = userNum();
			 if(inputNum ==0) {
				 break;
			 }
			 int cookNum =cDtos.get(inputNum-1).getCookNum();
			 inputNum = cookNum;
			System.out.println("1 - 음식 이름 수정 / 2 - 음식 재료 수정 ");
			int choice = userNum();
			switch(choice) {
			 case 1:
				 System.out.println("---------------------------------------------------------");
				 System.out.println("수정할 이름을 입력하세요");
				 inputStr = userInput();
				select = "cook_info";
				select2 = "cook_name";
				select3 = "cook_no";
				select4 = "";
				break;
			 case 2 :
				 System.out.println("---------------------------------------------------------");
				 System.out.println("수정할 재료의 번호를 입력하세요");
				 inputNum = userNum();
				 select = "RECIPE_INFO";
				 select2 = "INGREDIENT";
				 select3 = "Cook_NO";
				 select4 = "and recipe_no = "+inputNum;
				 System.out.println("---------------------------------------------------------");
				 System.out.println("수정할 재료를 입력하세요");
				 inputStr = userInput();
				 break;
				 
			default :
				System.out.println("---------------------------------------------------------");
				System.out.println("잘못된 입력입니다");
				
			}
			try (PreparedStatement ps = conn
					.prepareStatement("update "+ select + " set "+ select2 +" = ? where "+ select3 +" = ? "+select4)) {
//				ps.setString(1, select);
//				ps.setString(2, select2);
				ps.setString(1, inputStr);
//				ps.setString(4, select3);
				ps.setInt(2, inputNum);
				ps.executeUpdate();
				System.out.println("수정 되었습니다");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public void deleteRecipe() {
		printCooks();
		
		while(mDto.isLogin()) {
			System.out.println("---------------------------------------------------------");
			System.out.println("1 - 레시피 삭제  2 - 이전화면으로");
			int choice = userNum();
			switch(choice) {
			 case 1:
				 System.out.println("---------------------------------------------------------");
				 System.out.println("삭제할 레시피의 번호를 입력하세요");
				 int inputNum = userNum();
				 int recipeNum =cDtos.get(inputNum-1).getCookNum();
				try (PreparedStatement ps = conn
						.prepareStatement("delete from cook_info where cook_no = ?")) {
					ps.setInt(1, recipeNum);
					ps.executeUpdate();
					System.out.println("---------------------------------------------------------");
					 System.out.println(inputNum+"번 레시피 삭제되었습니다");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			 case 2 :
				 System.out.println("---------------------------------------------------------");
				 System.out.println("이전화면으로 돌아갑니다");
				 break;
			default :
				System.out.println("---------------------------------------------------------");
				System.out.println("잘못된 입력입니다");
			}
			if(choice==2) {
				break;
			}
		}
		
	}
	
	public void startGame() { //게임시작
		System.out.println("---------------------------------------------------------");
		System.out.println("게임을 시작합니다!");
		
		selectLevel();
		loadRecipe(mDto.getLevel());
		checkAnswer();
	}

	public void selectLevel() {// 난이도 선택 상 중 하+ 최상?
		while (mDto.isLogin()) {
			
				System.out.println("---------------------------------------------------------");
				System.out.println("난이도 1단계 ~ 3단계 중 선택해주세요!");
				int tempN = userNum();
				if (tempN >0 && tempN <= 3) {
					mDto.setLevel(tempN);
					break;
				}else {
					System.out.println("---------------------------------------------------------");
					System.out.println("유효한 값이 아닙니다.");
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
		System.out.println("---------------------------------------------------------");
		System.out.println("레시피 리스트 입니다");
		for (int i = 0; i < cDtos.size(); i++) {
			System.out.println(i+1+"번 레시피 이름 : "+cDtos.get(i).getCookName()+
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

	public int[] makeRandom() {
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
		int[] tempArr = makeRandom();
		int randomNum = tempArr[tempArr.length-1];
		System.out.println("---------------------------------------------------------");
		System.out.println("문제 나갑니다");
		System.out.print("요리 이름 : "+cDtos.get(randomNum).getCookName());
		System.out.println(", 재료 개수 : "+cDtos.get(randomNum).getCount());
		for (int i = 0; i < cDtos.get(randomNum).recipeList.size(); i++) {
			System.out.println(" 재료"+(i+1)+" : "+cDtos.get(randomNum).recipeList.get(tempArr[i]).getIngredient());
		}
		try {
			Thread.sleep(5000);
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
		int count = 0;
		int chance =3;
		int userScore = mDto.getScore();
		int score = cDtos.get(randomNum).getScore();
		for (int i = 0; i < cDtos.get(randomNum).recipeList.size(); i++) {
			System.out.println("---------------------------------------------------------");
			System.out.println("답을 입력하세요");
			String str = userInput();
			if(str.equals(cDtos.get(randomNum).recipeList.get(tempArr[i]).getIngredient())) {
				System.out.println("정답입니다");
				count++;
				if(count==cDtos.get(randomNum).recipeList.size()) {
					userScore += score;
					System.out.println("---------------------------------------------------------");
					System.out.println(cDtos.get(randomNum).getCookName()+", 정답입니다");
					System.out.println(score+"점 획득하셨습니다");
					System.out.println("지금까지 "+userScore+"점 획득하셨습니다");
					System.out.println("다음 레시피를 출력합니다");
					mDto.setScore(userScore);
				}
			} else {
				i--;
				
				chance--;
				System.out.println("---------------------------------------------------------");
				System.out.println("오답입니다, 10점 감점됩니다\r\n"+chance+"번 남았습니다");
				score -= 10;
				if(chance==0) {
					printRemain();
					break;
				}
			}
		}if(mDto.getChance()==0) {
			gameEnd();
			updateScore(mDto.getId(), mDto.getScore());
			printScore();
			break;
		}
		}
	}
	
	public void printRemain() {
		int count = mDto.getChance();
		count--;
		mDto.setChance(count);
		if(count!=0) {
			System.out.println("---------------------------------------------------------");
			System.out.println("3번 틀리셨습니다, 이번 레시피는 실패입니다");
			System.out.println("기회는 "+count+"번 남았습니다");
			System.out.println("지금까지 "+mDto.getScore()+"점 획득하셨습니다");
			System.out.println("다음 레시피를 출력합니다");
		}
		
		
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
		System.out.println("---------------------------------------------------------");
			System.out.println("게임이 끝났습니다");
			System.out.println("지금까지 "+mDto.getScore()+"점 획득하셨습니다");
			
	}
	public void rogout() {
		mDto.setLogin(false);
		System.out.println("---------------------------------------------------------");
		System.out.println("로그아웃되었습니다!");
		select();
	}
	
	public void printScore() {
		System.out.println("---------------------------------------------------------");
			System.out.println("점수를 출력합니다");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void endProgram() {
		System.out.println("---------------------------------------------------------");
		System.out.println("프로그램을 종료합니다");
	}
}
