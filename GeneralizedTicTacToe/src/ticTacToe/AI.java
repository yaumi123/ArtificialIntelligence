package ticTacToe;

import java.util.List;

public class AI {
	static Point computermove = null;
	//Calculate score depends on the result of the game.
	static int score(Game game) {
		if(game.checkGame()==0) {
			return 10;//X wins.
		}else if(game.checkGame()==1) {
			return -10;//O wins.
		}else if(game.checkGame()==2) {
			return 0;//Draw.
		}else {
			return 0;
		}
	}
	
	static void alphabeta(Game game, int depth) {
		minmax(game, depth);
		System.out.println(game.getPlayer() + " chooses" + computermove);
		game.move(computermove.x, computermove.y);
	
	}
	
	//choose the best move.
	static int minmax(Game game,int depth) {
		
		if(game.checkGame()==0) {
			return 10;//X wins.
		}else if(game.checkGame()==1) {
			return -10;//O wins.
		}else if(game.checkGame()==2) {
			return 0;//Draw.
		}
		List<Point> availableMoves = game.availableMoves();
		int min = 10000;
		int max = -10000;
		for(int i =0;i<availableMoves.size();i++) {
			Point point = availableMoves.get(i);
			if(game.current_player=='X') {
				game.smove(point);
				int currentScore = minmax(game, depth-1);
				max = Math.max(currentScore, max);
				if(depth==0)
					//computermove = point;
					//System.out.println("For " + game.getPlayer() + " position: " + point +", score is "+currentScore);
				if(currentScore==10&&depth==0) {
					game.board[point.x][point.y]='-';
					computermove = point;
					break;
				}
					
			}else if(game.current_player=='O') {
				game.smove(point);
				int currentScore = minmax(game, depth-1);
				min = Math.min(currentScore, min);
				if(min==-10) {
					game.board[point.x][point.y]='-';
					computermove = point;
					break;
				}
			}
			game.board[point.x][point.y]='-';
		}
		return game.current_player=='X'?max:min;
	}
}
