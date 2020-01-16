package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Generator {
	private static Random ladyLuck = new Random();
	private ArrayList<String> content = new ArrayList<>();
	
	public static void generate(int noGen) {
		for(int i=0; i<noGen;i++) generate();
	}
	
	private static void generate() {
		
	}
	
	public static void communicate(String message) {
		Program.write("GEN",message);
	}

}
