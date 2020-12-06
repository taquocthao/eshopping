package com.tathao.eshopping.ultils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePasswordEncodeUtils {

	public static String encode(String rawPassword) {
		String encode = new BCryptPasswordEncoder().encode(rawPassword);
		return encode;
	}
	
//	public static void main(String...strings) {
//		String passwordEncode = encode("123456");
//		System.out.println("###Password generated: " + passwordEncode);
//	}
	
}
