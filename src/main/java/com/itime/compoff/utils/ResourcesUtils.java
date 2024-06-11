package com.itime.compoff.utils;

import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourcesUtils {

	private ResourcesUtils() {
        super();
    }

    public static String getResourceContent(Resource resource) throws IOException
    {
        InputStream inputStream = resource.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder fileContent= new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
        	
            fileContent.append(line);
        }
        bufferedReader.close();
        return fileContent.toString();
    }
    
    public static String stringFormater(String message,Object value) {
		return String.format(message, value);
    	
    }
}
