package com.dataAlliance.hspark.kafka.security.oauth2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.kafka.common.config.ConfigException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpOAuthClient {
	private String OAUTH_SERVER;
	private String OAUTH_LOGIN_ENDPOINT;
	private String OAUTH_AUTHORIZATION;
	private String USERNAME;
	private String PASSWORD;
	private String GRANT_TYPE;
	
	public static class ServerConfig {
		public static final String OAUTH_SERVER_CONFIG = "OAUTH_SERVER";
		public static final String OAUTH_LOGIN_ENDPOINT_CONFIG = "OAUTH_LOGIN_ENDPOINT";
		public static final String OAUTH_AUTHORIZATION_CONFIG = "OAUTH_AUTHORIZATION";	
	}
	
	public static class UserConfig {
		public static final String USERNAME_CONFIG = "username";
		public static final String PASSWORD_CONFIG = "password";
		public static final String GRANT_TYPE_CONFIG = "grant_type";		
	}
	
	public static class UserConfigValue {
		public static final String GRANT_TYPE_PASSWORD = "password";
		public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	}
	
	public HttpOAuthClient(Map<String, String> moduleOptions) throws ConfigException {
		setOAuthServer(moduleOptions.get(ServerConfig.OAUTH_SERVER_CONFIG));
		setOAuthLoginEndpoint(moduleOptions.get(ServerConfig.OAUTH_LOGIN_ENDPOINT_CONFIG));
		setOAuthAuthorization(moduleOptions.get(ServerConfig.OAUTH_AUTHORIZATION_CONFIG));
		setUsername(moduleOptions.get(UserConfig.USERNAME_CONFIG));
		setPassword(moduleOptions.get(UserConfig.PASSWORD_CONFIG));
		setGrantType(moduleOptions.get(UserConfig.GRANT_TYPE_CONFIG));
	}
	/*
	public static void main(String[] args) throws ConfigException, ClientProtocolException, IOException {
		Map<String, String> moduleOptions = new HashMap<String, String>();
		moduleOptions.put(ServerConfig.OAUTH_SERVER_CONFIG, "https://auth.data-alliance.com:3005");
		moduleOptions.put(ServerConfig.OAUTH_LOGIN_ENDPOINT_CONFIG, "/oauth2/token");
		moduleOptions.put(ServerConfig.OAUTH_AUTHORIZATION_CONFIG, "Basic MTY4YWM4NzMtNzdjMi00NDE3LWExMzItYjIwMmY4ODIzOWFhOjVjMjM1NzlkLWYwMGEtNDU3Yi04OTM4LTQzYTIxYWRhMjUzMQ==");
		moduleOptions.put(UserConfig.USERNAME_CONFIG, "admin@test.com");
		moduleOptions.put(UserConfig.PASSWORD_CONFIG, "test");
		moduleOptions.put(UserConfig.GRANT_TYPE_CONFIG, UserConfigValue.GRANT_TYPE_PASSWORD);
		
		System.out.println((new HttpOAuthClient(moduleOptions)).create().value());
	}*/
	
	public OAuthBearerTokenJwt create() throws ClientProtocolException, IOException {		
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(OAUTH_SERVER + OAUTH_LOGIN_ENDPOINT);
		
		postRequest.setHeader(HttpHeaders.KEY_CONTENT_TYPE, HttpHeaders.ContentType.X_WWW_FORM_URLENCODED);
		postRequest.setHeader(HttpHeaders.KEY_ACCEPT, HttpHeaders.Accept.JSON);
		postRequest.setHeader(HttpHeaders.KEY_AUTHORIZATION, OAUTH_AUTHORIZATION);
		
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		
		namevaluepairs.add(new BasicNameValuePair(UserConfig.USERNAME_CONFIG, USERNAME));
		namevaluepairs.add(new BasicNameValuePair(UserConfig.PASSWORD_CONFIG, PASSWORD));
		namevaluepairs.add(new BasicNameValuePair(UserConfig.GRANT_TYPE_CONFIG, GRANT_TYPE));
		
		postRequest.setEntity(new UrlEncodedFormEntity(namevaluepairs));
		HttpResponse response = client.execute(postRequest);
		
		if (!isValid(response)) {
			throw new ConfigException("Invalid user credential.");
		}
		
		return jsonStringToOAuthBearerTokenJwt((new BasicResponseHandler()).handleResponse(response));
	}
	
	private boolean isValid(HttpResponse response) {
		if (response.getStatusLine().getStatusCode() == 200) {
			return true;
		}
		return false;
	}
	
	private void setOAuthServer(String oauthServer) throws ConfigException {
		if (isNullOrBlank(oauthServer)) {
			throw new ConfigException(ServerConfig.OAUTH_SERVER_CONFIG + " can't be null or blank.");
		}
		OAUTH_SERVER = oauthServer;
	}
	
	private void setOAuthLoginEndpoint(String oauthLoginEndpoint) throws ConfigException {
		if (isNullOrBlank(oauthLoginEndpoint)) {
			throw new ConfigException(ServerConfig.OAUTH_LOGIN_ENDPOINT_CONFIG + " can't be null or blank.");
		}
		OAUTH_LOGIN_ENDPOINT = oauthLoginEndpoint;
	}

	private void setOAuthAuthorization(String oauthAuthorization) throws ConfigException {
		if (isNullOrBlank(oauthAuthorization)) {
			throw new ConfigException(ServerConfig.OAUTH_AUTHORIZATION_CONFIG + " can't be null or blank.");
		}
		OAUTH_AUTHORIZATION = oauthAuthorization;
	}
	
	private void setUsername(String username) throws ConfigException {
		// should be changed to email validation
		if (isNullOrBlank(username)) {
			throw new ConfigException(UserConfig.USERNAME_CONFIG + " can't be null or blank.");
		}
		USERNAME = username;
	}
	
	private void setPassword(String password) {
		if (isNullOrBlank(password)) {
			throw new ConfigException(UserConfig.PASSWORD_CONFIG + " can't be null or blank.");			
		}
		PASSWORD = password;
	}
	
	private void setGrantType(String grantType) {
		if (isNullOrBlank(grantType)) {
			throw new ConfigException(UserConfig.GRANT_TYPE_CONFIG + " can't be null or blank.");			
		}
		GRANT_TYPE = grantType;
	}
	
	private boolean isNullOrBlank(String target) {
		Pattern p = Pattern.compile("^\\s*$");
		Matcher m;
		try {
			 m = p.matcher(target);
			
		} catch(NullPointerException e) {
			return true;
		}
		
		if (m.find() | m == null) {
			return true;
		}
		return false;
	}
	
	private OAuthBearerTokenJwt jsonStringToOAuthBearerTokenJwt(String json) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> OAuthBearerTokenMap = mapper.readValue(json, Map.class);

		return new OAuthBearerTokenJwt(OAuthBearerTokenMap);
	}
}
