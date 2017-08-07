package com.logicmonitor.miniurl.util;

public class EncodeDecodeURL {
	
	private static char base62Elements[]={'0','1','2','3','4','5','6','7','8','9',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	public static String createShortURL(long counter) {
        StringBuilder sb = new StringBuilder();
        while (counter != 0) {
            sb.insert(0,String.valueOf(base62Elements[(int) (counter%62)]));
            counter /= 62;
        }
        while (sb.length() != 6) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }
	
	public static long convertShortToLongURL(String s) {
        long counter = 0;
        for (int i = 0; i < s.length(); i++) {
        	int n = -1;
        	char c = s.charAt(i);
        	if (c >= '0' && c <= '9')
                n = c - '0';
            if (c >= 'a' && c <= 'z') {
                n = c - 'a' + 10;
            }
            if (c >= 'A' && c <= 'Z') {
                n = c - 'A' + 36;
            }
            counter = counter * 62 + n;
        }
        return counter;   
    }

	
	
}
