package Core;

import java.util.HashMap;

public class Evaluator {
	public static String content = new String("");
	public static CharRegister
		general	= new CharRegister();
	public static CharRegister
		start  	= new CharRegister();
	public static HashMap<Character,CharRegister>
		flow 	= new HashMap<>();
	
	public static void randomMap() {
		communicate("Initiating random evaluation.");
		set("Jiirkiip Rypimidio Pyrojiip");
		evaluate();
	}
	
	public static void set(String str) {
		clear();
		String output = new String();
		for(char c: str.toCharArray()) {
			if(!((c>=0 && c<=31)||(c>=33 && c<=64)||(c>=91 && c<=96)||c>=123) || c=='\n') {
				if(c>='A' && c<='Z') c = (char)(c + 32);
				else if(c=='\n') c = ' ';
				output += c;
			}
		}
		Evaluator.content = output;
	}
	
	public static void evaluate() {
		String pieces[] = Evaluator.content.split(" ");
		for(int i=0; i<pieces.length;i++) {
			String word = pieces[i];
			if(word.length()>2) {
				for(int j=0; j<word.length();j++) {
					char chr = word.charAt(j);
					if(j==0) {
						if(start.map.get(chr)==null) 
								start.map.put(chr, 1);
						else 	start.map.put(chr, start.map.get(chr)+1);
					}
					else {
						CharRegister temp = new CharRegister();
						if(flow.get(word.charAt(j-1))==null) temp.map.put(chr, 1);
						else {
							temp = flow.get(word.charAt(j-1));
							if(flow.get(word.charAt(j-1)).map.get(chr)==null) temp.map.put(chr, 1);
							else {
								temp.map.put(chr, temp.map.get(chr)+1);
							}
						}
						flow.put(word.charAt(j-1), temp);
					}
					if(start.map.get(chr)==null) 
							start.map.put(chr, 1);
					else 	start.map.put(chr, start.map.get(chr)+1);
				}
			}
			else continue;
		}
		reevaluate();
	}
	
//	private static void enrich() {
//		
//	}
	
	private/**/ static void reevaluate() {
		CharRegister tempG = new CharRegister(start.map);
		CharRegister tempS = new CharRegister(start.map);
		HashMap<Character, CharRegister> tempF = new HashMap<Character,CharRegister>(flow);
		clearMaps();
		
		if(tempG==null||tempS==null||tempF==null) {
			communicate("One of the evaluated maps was null, unable to comply");
			return;
		}
		else {
			int temp = 0;
			for(Character c: tempG.map.keySet()) {
				temp+=tempG.map.get(c);
				start.map.put(c,temp);
			}
			
			temp = 0;
			for(Character c: tempS.map.keySet()) {
				temp+=tempS.map.get(c);
				start.map.put(c,temp);
			}
			
			for(Character c: tempF.keySet()) {
				temp = 0;
				CharRegister tempM = new CharRegister();
				for(Character s: tempF.get(c).map.keySet()) {
					temp+= tempF.get(c).map.get(s);
					tempM.map.put(s, temp);
				}
				flow.put(c,tempM);
			}
		}
		communicate("Full evaluation completed.");
		//toConsole();
	}
	
	public static void clearMaps() {
		start.map.clear();
		start.map.clear();
		flow.clear();		
	}

	public static void clear() {
		clearMaps();
		content = new String();
	}
	
	public static void communicate(Object message) {
		Program.write("EVAL",message);
	}
	
	public static void toConsole() {
		communicate("START MAP:\n"+start.getMax());
		communicate("GENERAL MAP:\n"+general.getMax());
		String output = new String("FLOW MAP:");
		for(Character c: flow.keySet()) {output+="\n"+c+" "+flow.get(c).getMax();}
		communicate(output);
	}
}
