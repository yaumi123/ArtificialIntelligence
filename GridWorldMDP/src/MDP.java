package src;

import java.awt.GridBagConstraints;
import java.util.List;

public class MDP 
{
	// General settings
	private static double Ra = -0.01;            // reward in non-terminal states (used to initialise r[][])
	private static double gamma = 0.9;          // discount factor
	private static double pGood = 0.8;        // probability of taking intended action
	private static double pBad = (1-pGood)/2; // 2 bad actions, split prob between them
	private static double pN = 0.6;
	private static double pE = 0.15;
	private static double pW = 0.1;
	private static double pS = 0.15;
	private static int N = 10000;             // max number of iterations of Value Iteration
	private static double deltaMin = 1e-9;    // convergence criterion for iteration

	// Main data structures
	private static double U[][];  // long-term utility
	private static double Up[][]; // UPrime, used in updates
	private static double R[][];  // instantaneous reward
	private static char  Pi[][];  // policy
	
	private static int rMax, cMax;
	
	public static void main(String[] args)
	{
		List<String> uList = ReadFile.readCsv("res/grid.csv");
		rMax = Integer.parseInt(uList.get(uList.size()-2));
		cMax = Integer.parseInt(uList.get(uList.size()-1));
        int count=0;
        for(int i=0;i<uList.size()-2;i++) {
        	if(uList.get(i).equals("0"))
        		uList.set(i, Double.toString(Ra));
        	if(uList.get(i).equals("-"))
        		uList.set(i, "0");
        }
        uList.remove(uList.size()-2);
        uList.remove(uList.size()-1);
     // initialise R: set everything to Ra and then override the terminal states
        R = new double[rMax][cMax];
        for(int i=0;i<rMax;i++) {
        	for(int j=0;j<cMax;j++) {
        		R[i][j]=Double.parseDouble(uList.get(count));
        		count++;
        		System.out.printf("% 6.2f\t",R[i][j]);
        	}
        	System.out.println();
        }
		double delta = 0;
		int r,c;
		// policy: initially null
		Pi = new char[rMax][cMax]; 
		
		// initialise U'
		Up = new double[rMax][cMax]; // row, col
		for (r=0; r<rMax; r++) {
			for (c=0; c<cMax; c++) {
				Up[r][c] = 0;
			}
		}
		// Don't initialise U: will set U=Uprime in iterations
		U = new double[rMax][cMax];
		

		// Now perform Value Iteration.
		int n = 0;
		do 
		{
			// Simultaneous updates: set U = Up, then compute changes in Up using prev value of U.
			duplicate(Up, U); // src, dest
			n++;
			delta = 0;
			for (r=0; r<rMax; r++) {
				for (c=0; c<cMax; c++) {
					if(R[r][c]!=0&&R[r][c]!=1000&&R[r][c]!=-800)
						updateUPrime(r, c);
					else
						Up[r][c]=R[r][c];
					double diff = Math.abs(Up[r][c] - U[r][c]);
					if (diff > delta)
						delta = diff;
				}
			}
		} while (delta > deltaMin && n < N);
		
		// Display final matrix
		System.out.println("After " + n + " iterations:");
		for (r=0; r<rMax; r++) {
			for (c=0; c<cMax; c++) {
				System.out.printf("%6.2f\t", U[r][c]);
			}
			System.out.print("\n");
		}

		// Before displaying the best policy, insert chars in the sinks and the non-moving block
		//Pi[0][3] = '+'; Pi[1][3] = '-'; Pi[1][1] = '#';
		
		System.out.println("\nBest policy:");
		for (r=0; r<rMax; r++) {
			for (c=0; c<cMax; c++) {
				System.out.print(Pi[r][c] + "   ");
			}
			System.out.print("\n");
		}
	}
	
	public static void updateUPrime(int r, int c)
	{
		// IMPORTANT: this modifies the value of Up, using values in U.
		
		double a[] = new double[4]; // 4 actions
	
		// If at a sink state or unreachable state, use that value
	

//		a[0] = aNorth(r,c)*pGood + aWest(r,c)*pBad + aEast(r,c)*pBad;
//		a[1] = aWest(r,c)*pGood + aSouth(r,c)*pBad + aNorth(r,c)*pBad;
//		a[2] = aEast(r,c)*pGood + aSouth(r,c)*pBad + aNorth(r,c)*pBad;
//		a[3] = aSouth(r,c)*pGood + aWest(r,c)*pBad + aEast(r,c)*pBad;
		a[0] = aNorth(r, c);
		a[1] = aWest(r, c);
		a[2] = aEast(r, c);
		a[3] = aSouth(r, c);

			int best = maxindex(a);
			double nextReward = aNorth(r, c)*pN + aWest(r, c)*pW + aEast(r, c)*pE + aSouth(r, c)*pS;
			Up[r][c] = R[r][c] + gamma * nextReward;
			
			// update policy
			Pi[r][c] = (best==0 ? 'N' : (best==1 ? 'W' : (best==2 ? 'E': 'S')));
		
	}
	
	public static int maxindex(double a[]) 
	{
		int b=0;
		for (int i=1; i<a.length; i++)
			b = (a[b] > a[i]) ? b : i;
		return b;
	}
	
	public static double aNorth(int r, int c)
	{
		// can't go north if at row 0 or if in cell (2,1)
		if ((r==0) || R[r-1][c]==0)
			return U[r][c];
		return U[r-1][c];
	}

	public static double aSouth(int r, int c)
	{
		// can't go south if at row 2 or if in cell (0,1)
		if ((r==rMax-1) || R[r+1][c]==0)
			return U[r][c];
		return U[r+1][c];
	}

	public static double aWest(int r, int c)
	{
		// can't go west if at col 0 or if in cell (1,2)
		if ((c==0) || R[r][c-1]==0)
			return U[r][c];
		return U[r][c-1];
	}

	public static double aEast(int r, int c)
	{
		// can't go east if at col 3 or if in cell (1,0)
		if ((c==cMax-1) || R[r][c+1]==0)
			return U[r][c];
		return U[r][c+1];
	}
	
	public static void duplicate(double[][]src, double[][]dst)
	{
		// Copy data from src to dst
		for (int x=0; x<src.length; x++) {
			for (int y=0; y<src[x].length; y++) {
				dst[x][y] = src[x][y];
			}
		}
	}
}
