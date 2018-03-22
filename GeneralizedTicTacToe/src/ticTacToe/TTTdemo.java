package ticTacToe;

import java.util.Scanner;

public class TTTdemo {
	private static final int BOARD_SIZE = 3;//The size of the board.
	private static final int WIN_NUMBER = 3;//The number of needed continous 
										//same pieces in a row to win. 
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
	
		Game g = new Game("Tom","Jerry",BOARD_SIZE,WIN_NUMBER);
		while(g.checkGame()==3) {
			g.move(0, 0);
			int i = scanner.nextInt();
			int j = scanner.nextInt();
			g.move(i, j);
			AI.alphabeta(g, 3);
		}
		scanner.close();
//		System.out.println();
//		Alphabeta.minimaxAlphaBeta(g);
		//checkRow
		
		//AI.alphabeta(g, 2);
//		g.move(6, 6);
//		g.move(5, 7);
//		g.move(5, 6);
//		g.move(4, 7);
			
		//checkCol
//		g.move(0, 0);
//		g.move(0, 1);
//		g.move(1, 0);
//		g.move(0, 2);
//		g.move(2, 0);
//		g.move(1, 1);
//		
		//check Obl
//		g.move(0, 0);
//		g.move(1, 0);
//		g.move(1, 1);
//		g.move(2, 0);
//		g.move(2, 2);
//		g.move(0, 2);
		
//		//check rObl
//		g.move(1, 0);
//		g.move(2, 0);
//		g.move(0, 1);
//		g.move(0, 2);
//		g.move(2, 2);
//		g.move(1, 1);
//		g.move(0, 0);
		
		//check win before draw
//		g.move(1, 0);
//		g.move(2, 0);
//		g.move(0, 1);
//		g.move(0, 2);
//		g.move(2, 2);
//		g.move(1, 2);
//		g.move(0, 0);
//		g.move(2, 1);
//		g.move(1, 1);
		
//		//check draw
//		g.move(1, 0);
//		g.move(2, 0);
//		g.move(0, 2);
//		g.move(0, 1);
//		g.move(2, 2);
//		g.move(1, 2);
//		g.move(2, 1);
//		g.move(0, 0);
//		g.move(1, 1);
		}
}
