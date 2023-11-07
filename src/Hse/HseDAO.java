package Hse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Main.InterfaceDAO.Common;
import Main.InterfaceDAO.Login;
import Main.MemberDTO;

public class HseDAO extends Common implements Login {

	MemberDTO mDto = new MemberDTO();

	@Override
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
					disconnect();
					return uInput;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean login() {//로그인
	
		while (true) {
			String pw = idCheck();
			if (pw.equals(mDto.getPw())) {
				System.out.println("로그인 완료!!!");
				// selectMode method
				return true;
			} else {
				System.out.println("아이디&비번오류!! 다시 로그인해주세요!!!");
			}
		}
	}

	@Override
	public void resister() {
		// 회원가입
	
		connect();
		String id,  pw,  nickName,  gender, adress;
		System.out.println("회원가입!!!");
		System.out.println("아디디 입력 : ");
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

			int result = ps.executeUpdate();
			System.out.println(conn.getAutoCommit());

			System.out.println(result);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void modifyInfo() {// 개인정보수정

		System.out.println("변경하실 회원정보를 입력해주세요");
		
		System.out.println("1. 비밀번호 변경");
		System.out.println("2. 닉네임 변경");
		System.out.println("3. 성별 변경");
		System.out.println("4. 주소 변경");
		
		String change = null;		//변경할 행의 컬럼이름!
		String str = null;          //변경될 컬럼의값
		String choice = userInput();

		while (true) {

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

			default:
				System.out.println("다시 입력해주세요");
				break;
			}
			
			
			try {
				PreparedStatement ps = conn
						.prepareStatement("UPDATE MEMBER SET ? = ? WHERE ID=?");
				ps.setString(1, change);
				ps.setString(2, str);
				ps.setString(3, mDto.getId());    //기존의것!(변경 컬럼의 내용)

				int result = ps.executeUpdate();
				System.out.println(conn.getAutoCommit());

				System.out.println(result);

			} catch (SQLException e) {

				e.printStackTrace();
			}
			
			
			
			System.out.println("회원정보가 변경되었습니다.");
		}

	}

	@Override
	public void deleteId() {//삭제
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM MEMBER WHERE ID = ? AND PW = ?");

			ps.setString(1, mDto.getId());
			ps.setString(2, mDto.getPw());
			int result = ps.executeUpdate();
			System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}




		
	}
