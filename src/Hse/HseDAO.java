package Hse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Main.InterfaceDAO.login;

public class HseDAO {

	Scanner str = new Scanner(System.in);

	Main.DTO dto = new 
		
	Main.InterfaceDAO.login =new login() {
		
		
		@Override
		public boolean login() {
			String id = "a";
			String pw = "b";

			System.out.println("로그인 해주세요");
			System.out.println("아이디입력 : ");
			String id = str.nextLine();
			System.out.println("비밀번호입력 : ");
			String pw = str.nextLine();

			
			
			while (true) {

				if (Main.dto.getId().equals(id) && Main.dto.getId().equals(pw)) {
					System.out.println("로그인 성공!!! ");
					return true;

				} else {
					System.out.println("아이디&비밀번호 오류!다시 입력바랍니다!!!");
				}
				return false;
			}
		}
	
		
		@Override
		public void resister(Main.DTO dto) {//회원가입
			
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> ids = new ArrayList<String>();
			ArrayList<String> phones = new ArrayList<String>();
			ArrayList<String> birthdays = new ArrayList<String>();
			ArrayList<String> adressd = new ArrayList<String>();
		
			Scanner str = new Scanner(System.in);
			while (true) {
				System.out.println("회원가입은 1 !, 종료는 -1을 누르세요 ");
				int choice = sc.nextInt();
			if (getIntByScan().equals(1)) {
					
			    System.out.println("이름 입력 : ");
			    String id = sc.next();
			
				System.out.println("아이디 입력 : ");
				String id = sc.next();
			

				System.out.println("비밀번호 입력 : ");
				String pw = sc.next();
				

				System.out.println("성별 입력 : ");
				String nickname = sc.next();
				
				System.out.println("생년월일 입력 : ");
				String nickname = sc.next();
				
				System.out.println("주소 입력 : ");
				String nickname = sc.next();
								

				
			} else {
				System.out.println("");
		
		}
			
		
			
			
			
	@Override
	public void modifyInfo(Main.DTO dto) {// 개인정보 수정
		ArrayList<String> list = new ArrayList<>();
		
		list.add(1)=name;
		list.add(2)=id;
		list.add(3)=phone;       //
		list.add(4)=birthday;
		list.add(5)=adress;
		
		list.remove(0);
	
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i+ ":"+ list.get());
		}
			

	}

	


	@Override
	public boolean idCheck() {// 아이디 있는지 체크
		System.out.println("아이디를 입력해주세요");
		Scanner str = new Scanner(System.in);
		String ss = str.nextLine();

		if (Main.dto.getId().equals(ss)) {
			System.out.println("존재하는  아이디입니다");
            System.out.println("다른 아이디로 입력해주세요");
            return true;
		} else {
			System.out.println("존재하지 않는 아이디입니다.");	
		}
		return false;
	}

	@Override
	public void deleteId() {// 회원탈퇴
		// TODO Auto-generated method stub

	}
};

