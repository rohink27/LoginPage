package com.rohin.HelperClasses;

import java.util.UUID;

public class TokenGenerator {
	
	public static String generate()
	{
		UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
	}

}
