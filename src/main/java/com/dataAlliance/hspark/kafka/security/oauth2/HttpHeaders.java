package com.dataAlliance.hspark.kafka.security.oauth2;

public class HttpHeaders {
	public static String KEY_CONTENT_TYPE = "Content-Type";
	public static String KEY_ACCEPT = "Accept";
	public static String KEY_AUTHORIZATION = "Authorization";
	
	static class ContentType {
		public static String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
		public static String JSON = "application/json";
	}
	
	static class Accept {
		public static String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
		public static String JSON = "application/json";		
	}
}
