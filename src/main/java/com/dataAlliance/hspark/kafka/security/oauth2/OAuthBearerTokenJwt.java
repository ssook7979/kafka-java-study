package com.dataAlliance.hspark.kafka.security.oauth2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerToken;

public class OAuthBearerTokenJwt implements OAuthBearerToken{
	private String ACCESS_TOKEN_CONFIG = "access_token";
	private String TOKEN_TYPE_CONFIG = "token_type";
	private String EXPIRES_IN_CONFIG = "expires_in";
	private String REFRESH_TOKEN_CONFIG = "refresh_token";
	private String SCOPE_CONFIG = "scope";
	
	private String accessToken;
	private String tokenType;
	private int expiresIn;
	private String refreshToken;
	private Set<String> scope;
	private String principalName;
	
	public OAuthBearerTokenJwt(Map<String, Object> OAuthBearerTokenMap) {
		accessToken = (String) OAuthBearerTokenMap.get(ACCESS_TOKEN_CONFIG);
		tokenType = (String) OAuthBearerTokenMap.get(TOKEN_TYPE_CONFIG);
		expiresIn = (Integer) OAuthBearerTokenMap.get(EXPIRES_IN_CONFIG);
		refreshToken = (String) OAuthBearerTokenMap.get(REFRESH_TOKEN_CONFIG);
		scope = toScopeSet(OAuthBearerTokenMap.get(SCOPE_CONFIG));
	}
	
	private static Set<String> toScopeSet(Object scopeObj) {
		Set<String> scopeSet = new HashSet<String>();
		
		if (scopeObj instanceof ArrayList<?>) {
			List<?> scopeObjList = (ArrayList<?>) scopeObj;
			for (Object scope: scopeObjList) {
				scopeSet.add(scope.toString());
			}
		} else {
			throw new ConfigException("Invalid token: wrong type of scope");
		}
		
		return scopeSet;
	}

	@Override
	public String value() {
		return accessToken;
	}

	@Override
	public Set<String> scope() {
		return scope;
	}

	@Override
	public long lifetimeMs() {
		return expiresIn * 1000;
	}

	@Override
	public String principalName() {
		return principalName;
	}

	@Override
	public Long startTimeMs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String refreshTopen() {
		return refreshToken;
	}
	
	public String tokenType() {
		return tokenType;
	}
}
