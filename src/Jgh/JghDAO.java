package Jgh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Main.InterfaceDAO;
import Main.InterfaceDAO.connector;

public class JghDAO {
	Main.InterfaceDAO.main idao = new InterfaceDAO.main() {
		@Override // 로그인 or 종료 선택 + 시작화면임을 표시필요
		public void startGame() { //게임시작
			
		}

		@Override
		public int selectMode() {// 로그인 후 모드 선택,로그아웃, 점수출력, 레시피추가, 게임시작
			System.out.println("메뉴를 선택해주세요!");
			Scanner sc = new Scanner(System.in);
			while (true) {
				try {
					System.out.println("1 - 모드선택 / 2 - 점수출력 / 3- 레시피 추가 / 4 -  게임시작  /  5 - 로그아웃");
					int tempN = Integer.parseInt(sc.nextLine());
					if (tempN >0 && tempN <= 5) {
						sc.close();
						return tempN;
					}else {
						System.out.println("유효한 값이 아닙니다.");
					}
				} catch (NumberFormatException e) {
					System.out.println("유효한 값이 아닙니다. ");
				}
			}
		}

		@Override
		public int selectLevel() {// 난이도 선택 상 중 하+ 최상?
			while (true) {
				Scanner sc = new Scanner(System.in);
				System.out.println();
				try {
					System.out.println("난이도 1~3 중 선택해주세요!");
					int tempN = Integer.parseInt(sc.nextLine());
					if (tempN >0 && tempN <= 3) {
						sc.close();
						return tempN;
					}else {
						System.out.println("유효한 값이 아닙니다.");
					}
				} catch (NumberFormatException e) {
					System.out.println("유효한 값이 아닙니다. ");
				}
			}
		}

		@Override
		public void select() {
			//연결 어떻게 햇더라 ㅎ.ㅎ 
			
			Scanner sc = new Scanner(System.in);
			System.out.println("hello world! 로그인하려면 1, 종료하려면 0을 입력해주세요!");
			while (true) {
				String tempStr = sc.nextLine();
				if (tempStr.equals("1")) { // 로그인 창으로
					// 로그인 메소드
					break;
				} else if (tempStr.equals("0")) { // 게임종료
					System.out.println("게임이 종료됩니다!");
					break;
				} else {
					System.out.println("유효한 값이 아닙니다!");
				}
			}
			sc.close();
		}

		@Override
		public void rogout() {
			
		}

		@Override
		public void printScore() {// db에있는 점수 정보 출력 , 등수, 계급, 점수, 닉네임
			Connection conn = null; // 이거 널 들어가도되는지 모르겟는데요 
			String url = "jdbc:oracle:thin:@118.40.91.135:1521:xe";
			String user = "ATEAM";
			String password = "ATEAM1";
			try {
				ArrayList<Main.DTO> list = new ArrayList<>();
				
				conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement("select nickname, score from member where score is not null order by score desc ;");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					System.out.println(rs.getString("nickname")+ "  " + rs.getString("score") );
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	};
}
