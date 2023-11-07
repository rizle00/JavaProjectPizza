package Jgh;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Main.InterfaceDAO.Common;
import Main.InterfaceDAO.Main;


public class JghDAO extends Common implements Main {
	Scanner sc = new Scanner(System.in);
		@Override 
		public void startGame() { //게임시작
			System.out.println("게임을 시작합니다!");
			//?? 게임진행메소드 들어가면 끝인가
		}

		@Override
		public int selectMode() {// 로그인 후 모드 선택,로그아웃, 점수출력, 레시피추가, 게임시작
			System.out.println("메뉴를 선택해주세요!");

			
			while (true) {
				try {
					System.out.println("1 - 모드선택 / 2 - 점수출력 / 3- 레시피 추가 / 4 -  게임시작  /  5 - 로그아웃");
					int tempN = Integer.parseInt(sc.nextLine());
					switch (tempN) {
					
					case 1: 
						selectLevel();
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
					}
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
				System.out.println();
				try {
					System.out.println("난이도 1~3 중 선택해주세요!");
					int tempN = Integer.parseInt(sc.nextLine());
					if (tempN >0 && tempN <= 3) {
						selectMode();
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
			Scanner sc = new Scanner(System.in);
			System.out.println("hello world! 로그인하려면 1, 종료하려면 0을 입력해주세요!");
			while (true) {
				String tempStr = sc.nextLine();
				if (tempStr.equals("1")) { // 로그인 창으로
					selectMode();
					break;
				} else if (tempStr.equals("0")) { // 게임종료
					System.out.println("게임이 종료됩니다!");
					endProgram();
					break;
				} else {
					System.out.println("유효한 값이 아닙니다!");
				}
			}
			sc.close();
		}

		@Override
		public void rogout() {
			System.out.println("로그아웃되었습니다!");
			select();
		}
		
		@Override
		public void printScore() {// db에있는 점수 정보 출력 , 등수, 계급, 점수, 닉네임
			try {
				connect();
				PreparedStatement ps = conn.prepareStatement("SELECT NICKNAME, SCORE FROM MEMBER WHERE SCORE IS NOT NULL ORDER BY SCORE DESC");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					System.out.println("Nickname : " + rs.getString("nickname") + "\t Score : " + rs.getString("Score"));
				}
				selectMode();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		private void endProgram() {
			System.out.println("프로그램을 종료합니다");
		}
	};
