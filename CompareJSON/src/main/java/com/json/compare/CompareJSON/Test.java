package com.json.compare.CompareJSON;

public class Test {
	public static void main(String[] args) {
		String s = "Prasad";
		String r = "";
		/*int count = s.length();
		for (int i=0;i<s.length();i++) {
			count--; 
			r += s.charAt(count);
		}*/
		
		int count = s.length();
		while (count>0) {
			count--;
			r += s.charAt(count);
		}
		
		System.out.println(r);
	}
}
