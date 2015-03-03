package com.flocompany.util;


public class StringUtil {
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

}
