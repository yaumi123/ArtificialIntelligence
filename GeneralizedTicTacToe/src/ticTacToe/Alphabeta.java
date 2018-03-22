package ticTacToe;

import java.util.ArrayList;
import java.util.List;

public class Alphabeta {
	static int score(Game game){
		if(game.checkGame() ==0){
			return 10;
		}else if(game.checkGame() == 1){
			return -10;
		}else{
			return 0;
		}	
	}
    //return the minimum number of the list
	static int minScore(List<Integer> list){
		int min = list.get(0);
		for(int n : list){
			if(n<min){
				min = n;
			}
		}
		return min;
	}
    //return the minimum number of the list
	static int maxScore(List<Integer> list){
		int max = list.get(0);
		for(int n : list){
			if(n>max){
				max = n;
			}
		}
		return max;
	}
	
	static int count;
    //encapsulation of the minimax method and display the result
	static void minimax(Game game){
		count = 0;
		System.out.println("Game Result:" +minimaxHelper(game));
		System.out.println("Moves considered without alpha-beta pruning: "+count);
		
	}
    //minimax method which increment the count and calculate the winner of the game
	static int minimaxHelper(Game game){
		if(game.checkGame() !=3){
			count++;
			return score(game);
		}
		int[][] children = new int[game.SIZE][game.SIZE] ;//=game.availableMoves();
		List<Integer> scores = new ArrayList<Integer>();
		char[][] tempBoard = game.board;
		for(int i =0;i<children.length;i++) {
			for(int j = 0; j <children[0].length;j++) {
				if(children[i][j]==1) {
					scores.add(minimaxHelper(game.newMove(game,i,j)));
					game.board = tempBoard;
					}
			}
		}
			
		if(game.getPlayer() ==0){
			return maxScore(scores);
		}else{
			return minScore(scores);
		}
	}	
	static int alphaCount;
	static int betaCount;
    //encapsulation method for the minimax with alpha beta pruning
	static void minimaxAlphaBeta(Game game){
		count=0;
		alphaCount=0;
		betaCount=0;
		System.out.println("Game Result:" +minimaxAlphaBeta(game, -10000, 10000));
		System.out.println("Moves considered with alpha-beta pruning: "+count);
		System.out.println("Alpha cuts: " + alphaCount);
		System.out.println("Beta cuts: " + betaCount);
	}
    //minimax with alpha beta pruning implemented to optimize the speed of the algorithm
	static int minimaxAlphaBeta(Game game, int alpha, int beta){
		int winner = game.checkGame();
		if(winner !=3){
			count++;
			return score(game);
		}
		int[][] children = new int[game.SIZE][game.SIZE] ;//=game.availableMoves();
		char[][] tempBoard = game.board;
		if(game.getPlayer() ==0){
			for(int i =0;i<children.length;i++) {
				for(int j = 0; j <children[0].length;j++) {
					if(children[i][j]==1) {
						int score = minimaxAlphaBeta(game.newMove(game,i,j),alpha,beta);
						game.board = tempBoard;
						//update alpha
						if(score > alpha){
							alpha= score;
						}
						//cutoff
						if(alpha >= beta){
							alphaCount ++;
							break;
						}
					}
				}
			}
			return alpha;
		}else{
			for(int i =0;i<children.length;i++) {
				for(int j = 0; j <children[0].length;j++) {
					if(children[i][j]==1) {
						int score = minimaxAlphaBeta(game.newMove(game,i,j),alpha,beta);
						game.board = tempBoard;
						//update beta
						if(score < beta){
							beta=score;
						}
						//cutoff
						if(alpha >=beta){
							betaCount++;
							break;
						}
					}
				}
			}
			return beta;
			
		}
	}
}
