package me.test.tools;

import java.util.Random;

public class RandomUtility {
	
	private static final String [] ALPHABET = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9".split(",");
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	public static String generateSalt() {
		return generateSalt(5);
	}
	
	public static String generateSalt(int length) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			sb.append(ALPHABET[RANDOM.nextInt(ALPHABET.length)]);
		}
		
		return sb.toString();
	}
	
	public static Long generateSID() {
		return generateSID(12);
	}
	
	public static Long generateSID(int length) {
		long ret = (long)(RANDOM.nextInt(9) + 1);
		for (int i = 1; i < length; i++) {
			ret *= 10 + (long)(RANDOM.nextInt(10));
		}
		return ret;
	}

}
