package com.dataAlliance.hspark.kafka.security.oauth2;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;

import org.apache.http.client.ClientProtocolException;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerTokenCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataAlliance.hspark.kafka.security.oauth2.HttpOAuthClient.UserConfig;

public class OAuthAuthenticateCallbackHandler implements AuthenticateCallbackHandler {
	
	private final Logger logger = LoggerFactory.getLogger(OAuthAuthenticateCallbackHandler.class);
	private Map<String, String> moduleOptions = null;
	private boolean configured = false;
	
	@Override
	public void configure(Map<String, ?> configs, String saslMechanism, List<AppConfigurationEntry> jaasConfigEntries) {

		if (!OAuthBearerLoginModule.OAUTHBEARER_MECHANISM.equals(saslMechanism)) {		
			throw new IllegalArgumentException(String.format("Unexpected SASL mechanism: %s", saslMechanism));
		}
		
		if (Objects.requireNonNull(jaasConfigEntries).size() != 1 || jaasConfigEntries.get(0) == null) {
			throw new IllegalArgumentException(
					String.format(
							"Must supply exactly 1 non-null JAAS mechanism configuration (size was %d)", 
							jaasConfigEntries.size()
					));
		}
		
		moduleOptions = Collections.unmodifiableMap((Map<String, String>) jaasConfigEntries.get(0).getOptions());
		configured = true;
	}

    public boolean isConfigured(){
        return this.configured;
    }
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		if (!isConfigured()) {
			throw new IllegalStateException("Callback handler not configured");
		}
		
		for (Callback callback: callbacks) {
			if (callback instanceof OAuthBearerTokenCallback) {
				handleCallback((OAuthBearerTokenCallback) callback);
			}

		}
		
		(new HttpOAuthClient(moduleOptions)).create().value();
	}

	private void handleCallback(OAuthBearerTokenCallback callback) throws ClientProtocolException, IOException {
		if (callback.token() == null) {
			HttpOAuthClient client = new HttpOAuthClient(moduleOptions);
			callback.token(client.create());
			
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
