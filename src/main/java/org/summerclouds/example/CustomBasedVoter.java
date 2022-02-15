package org.summerclouds.example;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

public class CustomBasedVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		System.out.println("### SUPPORT " + attribute);
		return true;
	}

	@Override
	public boolean supports(Class clazz) {
		System.out.println("### SUPPORT CLASS: " + clazz);
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection attributes) {
		// TODO Auto-generated method stub
		System.out.println("### VOTE: " + authentication + "\n    " + object + "\n    " + attributes);
		return ACCESS_ABSTAIN;
	}

}
