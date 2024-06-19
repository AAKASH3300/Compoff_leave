package com.itime.compoff.utils;

public class ResourcesUtils {

	private ResourcesUtils() {
        super();
    }

    public static String stringFormater(String message,Object value) {
		return String.format(message, value);
    	
    }
}
