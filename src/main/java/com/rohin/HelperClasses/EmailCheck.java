package com.rohin.HelperClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCheck {
	public static String emailCheck(String email)
	{
		String regex = "^(.+)@(.+)$";
		 
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if(m.matches())
			return "";
		else
			return "Invalid email";
	}

}
