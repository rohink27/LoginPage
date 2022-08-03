package com.rohin.HelperClasses;

import java.util.regex.*;

public class PasswordCheck {
	
	public static String validPassword(String password)
	{
		String error="";
		  String regex = "^(?=.*[0-9])"
                  + "(?=.*[a-z])(?=.*[A-Z])"
                  + "(?=.*[@#$%^&+=])"
                  + "(?=\\S+$).{8,20}$";

   
   Pattern p = Pattern.compile(regex);

  
   if (password == null) {
       error= "Empty Password";
       return error;
   }

 
   Matcher m = p.matcher(password);

 
   boolean check= m.matches();
   if(check)
	   return error;
   else
   {
	   error= "Password must be 8-20 characters long, must contain atleast 1 lowercase letter, an uppercase letter, a digit and a special character";
	   return error;
   }
		
	}

}
