package Core;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
	private static Random ladyLuck = new Random();
	private static ArrayList<String> content = new ArrayList<>();
	
	public static void generate(int noGen, int minLen, int maxLen) {
		for(int i=0; i<noGen;i++) generate(minLen, maxLen);
		toConsole();
		content = new ArrayList<>();
	}
	
	private static void generate(int minLen, int maxLen) {
		String output = new String("");
		int  	tar = ladyLuck.nextInt(Evaluator.start.getMax()+1),
				size = ladyLuck.nextInt(maxLen - minLen + 1) + minLen;
		char 	bef = Evaluator.start.get(tar);
				
		output += (char)((int)bef - 32);
		for(int i = 0; i<size; i++) {
			tar = ladyLuck.nextInt(Evaluator.flow.get(bef).getMax()+1);
			bef = Evaluator.flow.get(bef).get(tar);
			output += bef;
		}
		content.add(output);
	}
	
	public static void ranGen(int noGen, int minLen, int maxLen) {
		Evaluator.randomMap(); 
		Generator.generate(noGen, minLen, maxLen);
	}
	
	public static void toConsole() {
		String output = new String("Generation completed:");
		for(String s: content) {
			output +="\n" + s;
		}
		communicate(output);
	}
	
	public static void communicate(Object message) {Program.write("GEN",message);}
}
