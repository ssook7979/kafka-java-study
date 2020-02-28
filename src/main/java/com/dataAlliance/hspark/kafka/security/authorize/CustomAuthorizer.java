package com.dataAlliance.hspark.kafka.security.authorize;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import org.apache.kafka.common.Endpoint;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.server.authorizer.AclCreateResult;
import org.apache.kafka.server.authorizer.AclDeleteResult;
import org.apache.kafka.server.authorizer.Action;
import org.apache.kafka.server.authorizer.AuthorizableRequestContext;
import org.apache.kafka.server.authorizer.AuthorizationResult;
import org.apache.kafka.server.authorizer.Authorizer;
import org.apache.kafka.server.authorizer.AuthorizerServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAuthorizer implements Authorizer {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizer.class);

	@Override
	public void configure(Map<String, ?> configs) {
		logger.info(configs.toString());
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Endpoint, ? extends CompletionStage<Void>> start(AuthorizerServerInfo serverInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthorizationResult> authorize(AuthorizableRequestContext requestContext, List<Action> actions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends CompletionStage<AclCreateResult>> createAcls(AuthorizableRequestContext requestContext,
			List<AclBinding> aclBindings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends CompletionStage<AclDeleteResult>> deleteAcls(AuthorizableRequestContext requestContext,
			List<AclBindingFilter> aclBindingFilters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<AclBinding> acls(AclBindingFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
