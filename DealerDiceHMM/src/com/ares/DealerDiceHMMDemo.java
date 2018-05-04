package com.ares;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
 
 
public class DealerDiceHMMDemo {
	
	static List<String> diceSequences = new ArrayList<>();

	
	enum Die
	{
		D1,
		D2,
		D3,
	}
	
	enum Dice{
		One,
		Two,
		Three,
	}
	static int[] states = new int[]{Die.D1.ordinal(), Die.D2.ordinal(),Die.D3.ordinal()};

    static int[] observations;

    static double[] start_probability = new double[]{0.33, 0.33, 0.33};

    static double[][] transititon_probability = new double[][]{

            {0.5, 0.25, 0.25},

            {0.25, 0.5, 0.25},
            
            {0.25, 0.25, 0.5},

    };

    static double[][] emission_probability = new double[][]{

            {0.6, 0.2, 0.2},

            {0.2, 0.6, 0.2},
            {0.2, 0.2, 0.6},

    };

	public static void main(String[] args) {
		diceSequences = ReadFile.readSequence("res/diceSequences.txt");
		//System.out.println(diceSequences);
		HMM h = new HMM();
		for(int i = 0; i<diceSequences.size();i++) {
			String temS = diceSequences.get(i);
			observations = new int[temS.length()];
			for(int j=0;j<temS.length();j++) {
				if(temS.charAt(j)=='1')
					observations[j]=Dice.One.ordinal();
				else if(temS.charAt(j)=='2')
					observations[j]=Dice.Two.ordinal();
				else
					observations[j]=Dice.Three.ordinal();
			}
			int[] result = h.viterbi(observations, states, start_probability, transititon_probability, emission_probability);
			System.out.print("#"+i+" possible sequence : ");
	        for (int r : result)

	        {

	            System.out.print(Die.values()[r] + " ");

	        }
	        System.out.print("------Prob : " + h.probability);
	        System.out.println();
	        observations = null;
		}
		
	}
}
