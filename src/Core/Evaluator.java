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
			if(!((c>=0 && c<=31)||(c>=33 && c<=64)||(c>=91 && c<=96)||c>=123)) {
				if(c>='A' && c<='Z') c = (char)(c + 32);
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
						if(flowMap.get(chr)==null) temp.put(word.charAt(j-1), 1);
						else {
							if(flowMap.get(chr).get(word.charAt(j-1))==null) temp.put(word.charAt(j-1), 1);
							else {
								temp = flowMap.get(chr);
								temp.put(word.charAt(j-1), temp.get(word.charAt(j-1))+1);
							}
						}
						flowMap.put(chr, temp);
					}
					if(generalMap.get(chr)==null) 
							generalMap.put(chr, 1);
					else 	generalMap.put(chr, generalMap.get(chr)+1);
				}
			}
			else {
				
			}
		}
	}
}
