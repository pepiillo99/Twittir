package me.pepe.Twittir.Utils;

import java.util.Locale;
import java.util.Random;

import me.pepe.Twittir.Utils.Dto.ErrorDto;

public class Utils {
	public static Random random = new Random();
	public static Locale spanishLocale = new Locale("es");
	public static ErrorDto unauthorizedError = new ErrorDto("Session Unauthorized");
	public static String getRandomToken() {
	    char startChar = 33;
	    char endChar = 126;	 
	    int length = 25 + random.nextInt(5);
	    StringBuffer randomString = new StringBuffer(length);
	 
	    for (int i = 0; i < length; i++) {
	        int randomIndex = startChar + random.nextInt(endChar - startChar + 1);
	        randomString.append((char) randomIndex);
	    }
	 
	    return randomString.toString();}
}
