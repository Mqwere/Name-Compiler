package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Generator {
	private static Random ladyLuck = new Random();
	private ArrayList<String> content = new ArrayList<>();
	
	public static void generate(int noGen, int minLen, int maxLen) {
		for(int i=0; i<noGen;i++) generate(minLen, maxLen);
	}
	
	private static void generate(int minLen, int maxLen) {
		
	}
	
	public static void ranGen(int noGen, int minLen, int maxLen) {
		Evaluator.randomMap();
		Generator.generate(noGen, minLen, maxLen);
	}
	
	public static void communicate(String message) {
		Program.write("GEN",message);
	}

}
