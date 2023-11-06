package Kmj;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import Main.InterfaceDAO.connector;

class test{

	
	void test() {
		KmjDAO dao = new KmjDAO();
		dao.printRandomRecipe();
	}

	@Test
	void test1() {
		Connection conn1;
		String url = "jdbc:oracle:thin:@118.40.91.135:1521:xe";
		String user = "ATEAM";
		String password = "ATEAM1";
			try {
				conn1 = DriverManager.getConnection(url, user, password);
				System.out.println(conn1.isClosed());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
	}
}
