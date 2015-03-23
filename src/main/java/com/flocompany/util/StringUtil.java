package com.flocompany.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtil {
	
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM HH:mm");
	
	public static boolean isEmpty(String str) {

		return str == null || str.length() == 0;

	}

	public static boolean isNotEmpty(String str) {

		return !isEmpty(str);

	}

	public static boolean isBlank(String str) {

		int strLen;

		if (str == null || (strLen = str.length()) == 0) {

			return true;

		}

		for (int i = 0; i < strLen; i++) {

			if ((Character.isWhitespace(str.charAt(i)) == false)) {

				return false;

			}

		}

		return true;

	}

	public static boolean isNotBlank(String str) {

		return !isBlank(str);

	}

	
	
	public static boolean isValidMail(String email)
	{
		if( email!=null && email.trim().length()>0 )
			return email.matches("^[a-zA-Z0-9\\.\\-\\_]+@([a-zA-Z0-9\\-\\_\\.]+\\.)+([a-zA-Z]{2,4})$");
		return false;
	}
	
	public static String formatDateMessage(Date date){
		return dateFormat.format(date);		
	}

}
