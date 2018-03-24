package ticTacToe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    int SIZE;
	int WIN_NUMBER;
    int[][] board;

    public Board(int n, int m) {
    	SIZE = n;
		WIN_NUMBER = m;
		board = new int[SIZE][SIZE];
    }

    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }

    public boolean hasXWon() {
    	for(int i=0;i<SIZE;i++) {
			for(int j=0;j<=SIZE-1;j++) {
				if((checkRow(i, j)||checkCol(i, j)||checkObl(i, j)||checkRObl(i, j))&&(board[i][j]==1)) {
					return true;
				}
			}
		}
		return false;
    }
    
    public boolean hasOWon() {
    	for(int i=0;i<SIZE;i++) {
			for(int j=0;j<=SIZE-1;j++) {
				if((checkRow(i, j)||checkCol(i, j)||checkObl(i, j)||checkRObl(i, j))&&(board[i][j]==2)) {
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
				if((board[i][j+a]==board[i][j+a+1])
							||(board[i][j+a]==board[i][j+a+1]))
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
				if((board[i+a][j]==board[i+a+1][j])
							||(board[i+a][j]==board[i+a+1][j]))
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
				if((board[i+a][j+b]==board[i+a+1][j+b+1])
							||(board[i+a][j+b]==board[i+a+1][j+b+1]))
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
				if((board[i+a][j-b]==board[i+a+1][j-b-1])
						||(board[i+a][j-b]==board[i+a+1][j-b-1]))
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



    public List<Point> getAvailableStates() {
    	 availablePoints = new ArrayList<>();
         for (int i = 0; i < SIZE; ++i) {
             for (int j = 0; j < SIZE; ++j) {
                 if (board[i][j] == 0) {
                     availablePoints.add(new Point(i, j));
                 }
             }
         }
         return availablePoints;
    }

    public boolean placeAMove(Point point, int player) {
			if (board[point.x][point.y] == 0) {
				 board[point.x][point.y] = player; 
				return true;
			}
			else {
				System.out.println("Action not allowed. ");
				return false;
			}
    }

    void takeHumanInput() {
        System.out.println("Your move: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        Point point = new Point(x, y);
        placeAMove(point, 2); 
    }

    public void displayBoard() {
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
    Point XMove; 
    Point OMove;
    
    public int minimax(int depth, int turn) {  
        if (hasXWon()) return +1; 
        if (hasOWon()) return -1;

        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0; 
 
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
         
        for (int i = 0; i < pointsAvailable.size(); ++i) {  
            Point point = pointsAvailable.get(i);   
            if (turn == 1) { 
                placeAMove(point, 1); 
                int currentScore = minimax(depth + 1, 2);
                max = Math.max(currentScore, max);
                
                if(depth == 0)System.out.println("Score for position "+(i+1)+" = "+currentScore);
                if(currentScore >= 0){ if(depth == 0) XMove = point;} 
                if(currentScore == 1){board[point.x][point.y] = 0; break;} 
                if(i == pointsAvailable.size()-1 && max < 0){if(depth == 0)XMove = point;}
            } else if (turn == 2) {
                placeAMove(point, 2); 
                int currentScore = minimax(depth + 1, 1);
                min = Math.min(currentScore, min); 
                if(depth == 0)System.out.println("Score for position "+(i+1)+" = "+currentScore);
                if(currentScore >= 0){ if(depth == 0) XMove = point;} 
                if(min == -1){board[point.x][point.y] = 0; break;}
                if(i == pointsAvailable.size()-1 && min > 0){if(depth == 0)XMove = point;}
            }
            board[point.x][point.y] = 0; //Reset this point
        } 
        return turn == 1?max:min;
    } 
    public int minimax2(int depth, int turn) {  
        if (hasXWon()) return +1; 
        if (hasOWon()) return -1;

        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0; 
 
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
         
        for (int i = 0; i < pointsAvailable.size(); ++i) {  
            Point point = pointsAvailable.get(i);   
            if (turn == 1) { 
                placeAMove(point, 1); 
                int currentScore = minimax(depth + 1, 2);
                max = Math.max(currentScore, max);
                
                if(depth == 0)System.out.println("Score for position "+(i+1)+" = "+currentScore);
                if(currentScore >= 0){ if(depth == 0) OMove = point;} 
                if(currentScore == 1){board[point.x][point.y] = 0; break;} 
                if(i == pointsAvailable.size()-1 && max < 0){if(depth == 0)OMove = point;}
            } else if (turn == 2) {
                placeAMove(point, 2); 
                int currentScore = minimax(depth + 1, 1);
                min = Math.min(currentScore, min); 
                if(depth == 0)System.out.println("Score for position "+(i+1)+" = "+currentScore);
                if(currentScore >= 0){ if(depth == 0) OMove = point;} 
                if(min == -1){board[point.x][point.y] = 0; break;}
                if(i == pointsAvailable.size()-1 && min > 0){if(depth == 0)OMove = point;}
            }
            board[point.x][point.y] = 0; //Reset this point
        } 
        return turn == 2?max:min;
    }
}
