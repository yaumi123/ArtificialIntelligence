package ticTacToe;

import java.util.ArrayList;
import java.util.List;

public class Game {
	char current_player;
	char[][] board;
	int SIZE;
	int WIN_NUMBER;
	Player p1 = new Player();
	Player p2 = new Player();
	
	public Game() {
		
	}
	/**
	 * Initiate game/board
	 * @param player1, player2 </code>string</code> The two players in this game
	 * @param n </code>int</code> The size of this board(n*n)
	 * @param m </code>int</code> The number of same markers in a row/column/Oblique to win
	 */
	public Game(String player1, String player2, int n, int m) {
		setSIZE(n);
		setWIN_NUMBER(m);
		board = new char[SIZE][SIZE];
		p1.setMark('X');
		p1.setName(player1);
		p2.setMark('O');
		p2.setName(player2);
		current_player = p1.getMark();
		//Create board
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				board[i][j]='-';
			}
		}
		displaying();
		System.out.println("Game Initiated.   "+p1.getName()+ ": X    " +p2.getName()+": O");

	}
	
	public void nextPlayer() {
		if(current_player==p1.getMark()) {
			current_player=p2.getMark();
		}else if(current_player==p2.getMark()) {
			current_player=p1.getMark();
		}
	}
	
	/*public boolean isWon() {
		if(((board[0][0]!='-'&&board[0][0]==board[0][1]&&board[0][1]==board[0][2])||
				(board[1][0]!='-'&&board[1][0]==board[1][1]&&board[1][1]==board[1][2])||
				(board[2][0]!='-'&&board[2][0]==board[2][1]&&board[2][1]==board[2][2])||
				(board[0][0]!='-'&&board[0][0]==board[1][1]&&board[1][1]==board[2][2])||
				(board[0][2]!='-'&&board[0][2]==board[1][1]&&board[1][1]==board[2][0])||
				(board[0][0]!='-'&&board[0][0]==board[1][0]&&board[1][0]==board[2][0])||
				(board[1][0]!='-'&&board[1][0]==board[1][1]&&board[1][1]==board[1][2])||
				(board[2][0]!='-'&&board[2][0]==board[2][1]&&board[2][1]==board[2][2]))
				) {
		return true;}else {
			return false;
		}
	}*/
	
	public boolean isWon() {
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<=SIZE-1;j++) {
				if(checkRow(i, j)||checkCol(i, j)||checkObl(i, j)||checkRObl(i, j)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean checkRow(int i, int j) {
		int count= 1;
		if(j<=SIZE-WIN_NUMBER) {
			for(int a = 0;a<WIN_NUMBER-1;a++) {
				if((board[i][j+a]==board[i][j+a+1]&&board[i][j+a]==p1.getMark())
							||(board[i][j+a]==board[i][j+a+1]&&board[i][j+a]==p2.getMark()))
					count++;
				else
					count=1;
				if(count==WIN_NUMBER)
					return true;
			}
		}
		return false;
	}
	
	public boolean checkCol(int i, int j) {
		int count= 1;
		if(i<=SIZE-WIN_NUMBER) {
			for(int a = 0;a<WIN_NUMBER-1;a++) {
				if((board[i+a][j]==board[i+a+1][j]&&board[i+a][j]==p1.getMark())
							||(board[i+a][j]==board[i+a+1][j]&&board[i+a][j]==p2.getMark()))
					count++;
				else
					count=1;
				if(count==WIN_NUMBER)
					return true;
			}
		}
		return false;
	}
	
	public boolean checkObl(int i, int j) {
		int count = 1;
		if(i<=SIZE-WIN_NUMBER&&j<=SIZE-WIN_NUMBER) {
			int b = 0;
			for(int a = 0;a<WIN_NUMBER-1;a++) {
				if((board[i+a][j+b]==board[i+a+1][j+b+1]&&board[i+a][j+b]==p1.getMark())
							||(board[i+a][j+b]==board[i+a+1][j+b+1]&&board[i+a][j+b]==p2.getMark()))
					count++;
				else
					count=1;
				if(count==WIN_NUMBER)
					return true;
				b++;
			}
		}
		return false;
	}
	
	public boolean checkRObl(int i, int j) {
		int count= 1;
		if(i<=SIZE-WIN_NUMBER&&j>=WIN_NUMBER-1) {
			int b = 0;
			for(int a = 0;a<WIN_NUMBER-1;a++) {
				if((board[i+a][j-b]==board[i+a+1][j-b-1]&&board[i+a][j-b]==p1.getMark())
						||(board[i+a][j-b]==board[i+a+1][j-b-1]&&board[i+a][j-b]==p2.getMark()))
					count++;
				else
					count=1;
				if(count==WIN_NUMBER)
					return true;
			b++;
			}
		}
		return false;
		
	}
	
	public boolean isFull() {
		boolean isFilled = true;
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				if(board[i][j]=='-')
					isFilled = false;
			}
		}
		return isFilled;
	}
	
	public void displaying() {
		//System.out.println("+++++++++++++");
		for (int i=0;i<SIZE;i++) {
		System.out.print("| ");
		for (int j=0;j<SIZE;j++) {
		System.out.print(board[i][j] + " | ");
		}
		System.out.println();
		
		}
		System.out.println("+++++++++++++");
	}
	
	public void move(int r, int c) {
		if(!isWon()) {
		if ((r >= 0) && (r < SIZE)) {
			if ((c >= 0) && (c < SIZE)) {
				if (board[r][c] == '-') {
					board[r][c] = current_player;
					displaying();
					checkResult();
					nextPlayer();
				}
				else {
					System.out.println("Action not allowed. This cell has been occupied.");
				}
			}
		}
		}else {
			System.out.println("Game has been already over.");}
	}
	
	public void smove(Point point) {
		if(!isWon()) {
			if ((point.x >= 0) && (point.x < SIZE)) {
				if ((point.y >= 0) && (point.y < SIZE)) {
					if (board[point.x][point.y] == '-') {
						board[point.x][point.y] = current_player;
						//displaying();
						checkGame();
						nextPlayer();
					}
					else {
						//System.out.println("Action not allowed. This cell has been occupied.");
					}
				}
			}
			}else {
				//System.out.println("Game has been already over.");
				}
	}

	public int checkResult() {
		if (isWon()) {
			if(current_player=='X') {
				System.out.println("Our winner is " + p1.getName() + ", COngrats her/him ");
				return 0;
				}
			else if(current_player == 'O') {
				System.out.println("Our winner is " + p2.getName() + ", COngrats her/him ");
				return 1;
				}	
			}
			else if (isFull()) {
			System.out.println("Game Drawed ");
			return 2;
			}
		return 3;
	}
	
	public int checkGame() {
		if (isWon()) {
			if(current_player=='X') {
				return 0;
				}
			else if(current_player == 'O') {
				return 1;
				}	
			}
			else if (isFull()) {
			return 2;
			}
		return 3;
	}
	
	
	public int getWIN_NUMBER() {
		return WIN_NUMBER;
	}

	public void setWIN_NUMBER(int wIN_NUMBER) {
		WIN_NUMBER = wIN_NUMBER;
	}

	public int getSIZE() {
		return SIZE;
	}

	public void setSIZE(int sIZE) {
		SIZE = sIZE;
	}

	public List<Point> availableMoves() {
		List<Point> list = new ArrayList<>();
		for(int i = 0 ; i < SIZE;i++){
			for(int j = 0;j<SIZE;j++) {
				if(board[i][j]=='-'){
					list.add(new Point(i, j));
				}
			}
		}
		return list;
	}

	public Game newMove(Game game,int i,int j) {
		if(current_player=='X') {
			char[][] newBoard = new char[SIZE][SIZE];
			for(int a =0;a<SIZE;a++) {
				for(int b =0;b<SIZE;b++) {
					newBoard[a][b]=board[a][b];
				}
			}
			newBoard[i][j]='X';
			game.board = newBoard;
			return game;
		}else {
			char[][] newBoard = new char[SIZE][SIZE];
			for(int a =0;a<SIZE;a++) {
				for(int b =0;b<SIZE;b++) {
					newBoard[a][b]=board[a][b];
				}
			}
			newBoard[i][j]='O';
			game.board = newBoard;
			return game;
		}
	}

	public int getPlayer() {
		if(current_player=='X')
			return 0;
		else return 1;
	}
}
