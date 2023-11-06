package Jgh;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JunitTest {


	@DisplayName("로그인 or 종료 선택 ")
	@Test
	void test() {
		Jgh.JghDAO dao = new JghDAO();
		dao.idao.startGame();
	}
	
	
	@DisplayName("메뉴 선택")
	@Test
	void selectTest() {
		JghDAO dao = new JghDAO();
		dao.idao.selectMode();
		
	}

}
