package com.rohin.HelperClasses;

public class UsernameCheck {
	
	public static String checkUsername(String username)
	{
		String error="";
		for(int i=0; i<username.length(); i++)
		{
			char c= username.charAt(i);
			if(Character.isUpperCase(c))
			{
				error="Username must be lowercase only";
				return error;
			}
			if(c=='@')
			{
				error="Username must not contain @ symbols";
				return error;
			}
		}
		return error;
	}

}
