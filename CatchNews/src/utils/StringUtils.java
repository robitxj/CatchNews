package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


public class StringUtils {
	public static String getSubString(String source, String label, String start, String end) {
		
		String result = "";
		int labelIndex = source.indexOf(label);
		
		int startIndex = source.indexOf(start, labelIndex);
		int endIndex = source.indexOf(end, startIndex);

		if(labelIndex != -1 &&
			startIndex != -1 &&
			endIndex != -1)
		result = source.substring(startIndex + start.length(), endIndex);
		
		return result;
	}
	
	public static String getSubString(String source, String label, String start) {
		
		String result = "";
		int labelIndex = source.indexOf(label);
		
		int startIndex = source.indexOf(start, labelIndex);

		if(labelIndex != -1 &&
			startIndex != -1)
		result = source.substring(startIndex + start.length());
		return result;
	}
}
