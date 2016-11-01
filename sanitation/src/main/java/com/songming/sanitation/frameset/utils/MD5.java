package com.songming.sanitation.frameset.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 *MD5加密工具 
 *
 *@author 
 *@Version 1.0
 */
public class MD5 {
    public static String getMD5(String content) {  
        try {  
            MessageDigest digest = MessageDigest.getInstance("MD5");  
            digest.update(content.getBytes());  
            return getHashString(digest);  
              
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    private static String getHashString(MessageDigest digest) {  
        StringBuilder builder = new StringBuilder();  
        for (byte b : digest.digest()) {  
            builder.append(Integer.toHexString((b >> 4) & 0xf));  
            builder.append(Integer.toHexString(b & 0xf));  
        }  
        return builder.toString();  
    }  
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String encode(String content) {  
        try {  
            MessageDigest digest = MessageDigest.getInstance("MD5");  
            digest.update(content.getBytes());  
            return getHashString(digest);  
              
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }

	public static void main(String[] args) {
		System.err.println(encode("testtest"));
	}

}