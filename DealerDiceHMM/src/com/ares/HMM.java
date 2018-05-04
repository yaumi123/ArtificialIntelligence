package com.ares;

public class HMM {
	double probability = 1;
	public int[] viterbi(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emit_p)
	{
		double[][] value = new double[obs.length][states.length];
		int[][] path = new int[states.length][obs.length];
		probability = 1;
		//
		for (int n : states)
		{
			value[0][n] = start_p[n] * emit_p[n][obs[0]];
			path[n][0] = n;
		}
		for (int m = 1; m < obs.length; ++m)
		{
			int[][] newpath = new int[states.length][obs.length];
			for (int i : states)
			{
				double prob = -1;
				int state;
				for (int n0 : states)
				{
					double nprob = value[m - 1][n0] * trans_p[n0][i] * emit_p[i][obs[m]];
					//find max prob
					if (nprob > prob)
					{
						prob = nprob;
						state = n0;
						value[m][i] = prob;
						System.arraycopy(path[state], 0, newpath[i], 0, m);
						newpath[i][m] = i;
					}
				}
				//probability *= value[m][i];
			}
			path = newpath;
		}
		double prob = -1;
		int state = 0;
		for (int i : states)
		{
			if (value[obs.length - 1][i] > prob)
			{
				prob = value[obs.length - 1][i];
				state = i;
				probability *= value[obs.length - 1][i];
			}
		}
		return path[state];
	}
}
