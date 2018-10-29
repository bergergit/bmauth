package com.bergermobile.rest.services;


public class EncodingUtils {
	
	public static String normalizeSearch(String normalizedSearch) {
		/*
		if (normalizedSearch != null && !normalizedSearch.equals("")) {
			try {
				normalizedSearch = new String(normalizedSearch.getBytes("iso-8859-1"));
			} catch (UnsupportedEncodingException e) {}
		}
		*/
		return normalizedSearch;
	}

}
