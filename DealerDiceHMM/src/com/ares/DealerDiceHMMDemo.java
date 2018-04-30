package com.ares;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DealerDiceHMMDemo {
	
	static List<String> diceSequences = new ArrayList<>();
	
	public static List<String> readSequence(String path) {
		try { 
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = null; 
            List<String> dstring = new ArrayList<>();

            while((line=reader.readLine())!=null){ 
            	if(!line.equals("")) {
            		if(line.charAt(0)=='[')
            			dstring.add(line);
            	}
            }
            List<String> dse = new ArrayList<>();
    		String ds = "";
    		int seqNum=0;
    		for(String content : dstring) {
    			System.out.println("#" + (seqNum+1) + " : " + content);
    			for(int i=0;i<content.length();i++) {
    				if(content.charAt(i)!='['&&content.charAt(i)!=','&&content.charAt(i)!=' '&&content.charAt(i)!=']')
    					ds = ds + String.valueOf(content.charAt(i));
    			}
    			dse.add(ds);
    			ds = "";
    			seqNum++;
    		}
            return dse;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
		return null;
		
	}
	public static void main(String[] args) {
		diceSequences = readSequence("res/diceSequences.txt");
		
	}
}
