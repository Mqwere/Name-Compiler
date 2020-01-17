package Core;

import java.util.HashMap;

public class CharRegister {
	public HashMap<Character,Integer> map;
	
	public CharRegister() { 
		this.map = new HashMap<Character,Integer>();
	}
	
	public CharRegister(HashMap<Character,Integer> map) {
		this.map = new HashMap<Character,Integer>(map);
	}
	
	public int getMax() {
		int temp = 0;
		for(Character c: map.keySet()) {
			temp = map.get(c);
		}
		return temp;
	}
	
	public char get(int index) {
		for(Character c: map.keySet()) {
			if(map.get(c) >= index) {
				return c;
			}
		}
		return 'i';
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}
