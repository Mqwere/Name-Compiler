package Core;

import java.util.HashMap;

public class Evaluator {
	public static String content = new String("");
	public static HashMap<Character,Integer>
		generalMap 	= new HashMap<>();
	public static HashMap<Character,Integer>
		startMap 	= new HashMap<>();
	public static HashMap<Character,HashMap<Character,Integer>>
		flowMap 	= new HashMap<>();
	
	public static void set(String str) {
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
						if(startMap.get(chr)==null) 
								startMap.put(chr, 1);
						else 	startMap.put(chr, startMap.get(chr)+1);
					}
					else {
						HashMap<Character,Integer> temp = new HashMap<Character,Integer>();
						if(flowMap.get(word.charAt(j-1))==null) temp.put(chr, 1);
						else {
							temp = flowMap.get(word.charAt(j-1));
							if(flowMap.get(word.charAt(j-1)).get(chr)==null) temp.put(chr, 1);
							else {
								temp.put(chr, temp.get(chr)+1);
							}
						}
						flowMap.put(word.charAt(j-1), temp);
					}
					if(generalMap.get(chr)==null) 
							generalMap.put(chr, 1);
					else 	generalMap.put(chr, generalMap.get(chr)+1);
				}
			}
			else continue;
		}
		communicate("Evaluation completed.");
	}

	public static void clear() {
		generalMap.clear();
		startMap.clear();
		flowMap.clear();
		content = new String();
	}
	
	public static void communicate(String message) {
		Program.write("EVAL",message);
	}
	
	public static void toConsole() {
		communicate("START MAP:\n"+startMap.toString());
		communicate("FLOW MAP:\n"+flowMap.toString());
		communicate("GENERAL MAP:\n"+generalMap.toString());
	}
}
