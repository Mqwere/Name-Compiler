package Core;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
	private static Random ladyLuck = new Random();
	private static ArrayList<String> content = new ArrayList<>();
	private static int fuckups = 0;
	
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
		try { /// For some unknown reason, it throws NullPointers, and it needs to have like... one restart? Weird, I know
			for(int i = 0; i<size; i++) {
				tar = ladyLuck.nextInt(Evaluator.flow.get(bef).getMax()+1);
				bef = Evaluator.flow.get(bef).get(tar);
				output += bef;
			}
		
			fuckups = 0;
			content.add(output);
		}
		catch(Exception e) {
			if(fuckups++>9) Program.error("Generator.generate(int,int) - 10 attempts failed, ditching the output..."); 
			else 			Generator.generate(minLen, maxLen);
		}
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
