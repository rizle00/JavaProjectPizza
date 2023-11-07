package Jgh;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JunitTest {
//
//	@Disabled
//	@DisplayName("로그인 or 종료 선택 ")
//	@Test
//	void test() {
//		Jgh.JghDAO dao = new JghDAO();
//		dao.idao.startGame();
//	}
//	
	@Disabled
	@DisplayName("메뉴 선택")
	@Test
	void selectTest() {
		JghDAO dao = new JghDAO();
		dao.selectMode();
		
		}
//	
	@Disabled
	@DisplayName("점수판")
	@Test
	void printScore() {
		JghDAO dao = new JghDAO();
		dao.printScore();
	}
}
