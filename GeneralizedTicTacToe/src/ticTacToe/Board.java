package ticTacToe;

public class Board {
	char[][] board;
	int SIZE;
	public Board(int size) {
		this.SIZE = size;
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				board[i][j]='-';
			}
		}
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
}
