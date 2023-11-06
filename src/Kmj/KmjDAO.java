package Kmj;

import java.sql.SQLException;
import java.util.ArrayList;

import Main.MemberDTO;
import Main.InterfaceDAO.AboutPlay;
import Main.InterfaceDAO.Connector;

public class KmjDAO extends Connector implements AboutPlay  {

	@Override
	public void printRandomRecipe() {
	 connect();
	
		
	}

	@Override
	public void userInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkAnswer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void printCorrect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean printInCorrect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int minusScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printRemain() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateScore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameEnd() {
		// TODO Auto-generated method stub
		
	}

	
	
	
 
}
