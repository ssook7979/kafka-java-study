package com.dataAlliance.hspark.kafka.security.plain;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;

import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainCallbackHandler implements AuthenticateCallbackHandler {
	Map<String, ?> options = new HashMap<String, Object>();
	
	private static final Logger logger = LoggerFactory.getLogger(PlainCallbackHandler.class);

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		logger.info("options: " + options.toString());
		for (Callback callback: callbacks) {
			if(callback instanceof NameCallback) {
				// String username = (String) options.get("username");
				// logger.info("name: " + username);
				((NameCallback) callback).setName("client");
			} else if(callback instanceof PasswordCallback) {
				// String password = (String) options.get("password");
				// logger.info("pw: " + password);
				((PasswordCallback) callback).setPassword("client-secret".toCharArray());
				
			}
		}
	}

	@Override
	public void configure(Map<String, ?> configs, String saslMechanism, List<AppConfigurationEntry> jaasConfigEntries) {
		logger.info("Kafka Configure : " + configs);
		logger.info("Mechanism : " + saslMechanism);
		logger.info("JAAS Configure : " + jaasConfigEntries.get(0).getOptions());
		
		options = jaasConfigEntries.get(0).getOptions();
	}

	@Override
	public void close() {
		
	}

}
