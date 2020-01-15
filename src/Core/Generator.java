package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Generator {
	private static Random ladyLuck = new Random();
	private ArrayList<String> content = new ArrayList<>();
	
	private static HashMap<Character,Integer>
	generalMap 	= new HashMap<>();
	private static HashMap<Character,Integer>
	startMap 	= new HashMap<>();
	private static HashMap<Character,HashMap<Character,Integer>>
	flowMap 	= new HashMap<>();
	
	public static void generate(int noGen) {
		reevaluate();
		for(int i=0; i<noGen;i++) generate();
	}
	
	public/*private/**/ static void reevaluate() {
		HashMap<Character,Integer> tempG = Evaluator.generalMap;
		HashMap<Character,Integer> tempS = Evaluator.startMap;
		HashMap<Character, HashMap<Character, Integer>> tempF = Evaluator.flowMap; flowMap = tempF;
		
		if(tempG==null||tempS==null||tempF==null) {
			communicate("One of the Evaluator maps was null, unable to comply");
			return;
		}
		else {
			int temp = 0;
			for(Character c: tempG.keySet()) {
				temp+=tempG.get(c);
				generalMap.put(c,temp);
			}
			
			temp = 0;
			for(Character c: tempS.keySet()) {
				temp+=tempS.get(c);
				startMap.put(c,temp);
			}
			
			temp = 0;
			for(Character c: tempF.keySet()) {
				HashMap<Character,Integer> tempM = new HashMap<>();
				for(Character s: tempF.get(c).keySet()) {
					temp+= tempF.get(c).get(s);
					tempM.put(s, temp);
				}
				flowMap.put(c,tempM);
			}
		}
		toConsole();
	}
	
	private static void generate() {
		
	}
	
	public static void communicate(String message) {
		Program.write("GEN",message);
	}
	
	public static void toConsole() {
		communicate("START MAP:\n"+startMap.toString());
		communicate("FLOW MAP:\n"+flowMap.toString());
		communicate("GENERAL MAP:\n"+generalMap.toString());
	}
}
