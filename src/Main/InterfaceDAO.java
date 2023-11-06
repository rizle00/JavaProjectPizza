package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InterfaceDAO {
//	 Connection conn; // 연결 객체
//	PreparedStatement ps; // 실행 객체
//	ResultSet rs ; //결과 객체(표 sql dev 밑에 질의결과)
	public static class connector{
		static Connection conn;// 상속했는데 스태틱이 필요함?
		protected static void connect() {
			String url = "jdbc:oracle:thin:@118.40.91.135:1521:xe";
			String user = "ATEAM";
			String password = "ATEAM1";
			try {
				conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement("");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}// db 접속
		protected void disconnect() {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}// db 접속 해제
		
	}
	public interface main{
		public void select();//로그인, 종료 선택 + 시작화면임을 표시필요
		public int selectMode();// 로그인 후 모드 선택,로그아웃, 점수출력, 레시피추가, 게임시작 
		public void rogout();// 로그아웃
		public void printScore();// db에있는 점수 정보 출력 , 등수, 계급, 점수, 닉네임
	}
	public interface login{
		public boolean idCheck(); // 아이디 있는지 체크
		public void resister(); //회원가입
		public boolean login(); // 로그인
		public void modifyInfo(); // 개인정보 수정
		public void deleteId(); // 회원탈퇴
	}
	
	public interface aboutRecipe{
		public void printRecipe();// 있는 레시피 출력
		public void addRecipe();// 중복 예외 처리 필요, db에서 기본키지정
		public void modifyRecipe();// 레시피 수정
		public void deleteRecipe();// 레시피 삭제
		public void inputData();// 레시피 db에서 불러와서 인덱스에 점수 입력 처리
		
	}
	
	public interface aboutPlay{
		public void startGame();// 게임 시작
		public int selectLevel();// 난이도 선택 상 중 하+ 최상?
		public void printRandomRecipe();// 배열중 랜덤하게, 순서 섞어서 레시피 출력, 두개로 나눌수도?,(=문제출제) 화면 올려버리기 필요
		public void userInput();// 입력값받기
		public boolean checkAnswer();// 정답체크
		public void printCorrect();//맞을때 점수 누적 및 출력사항
		public boolean printInCorrect();// 틀렸음을 알림, 3번 중 몇번째 틀렸는지 3번 틀리면 사망
		public int minusScore();// 틀릴때마다 획득 점수 감소
		public void printRemain();//3번까지 틀린경우, 목숨 잃음 알림, 남은목숨 알림, 누적점수 알림
		public void updateScore();// 접속, 누적점수 업데이트, 해제
		public void gameEnd();// 게임종료 알림, 점수 출력, 다시 셀렉트모드로
		
	}
	
	
	
	
	
}
