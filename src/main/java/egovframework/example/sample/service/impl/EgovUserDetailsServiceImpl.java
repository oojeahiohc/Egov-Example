package egovframework.example.sample.service.impl;

import java.util.List;

import org.egovframe.boot.access.service.EgovUserDetailsHelper;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.EgovUserDetailsService;

@Service
public class EgovUserDetailsServiceImpl extends EgovAbstractServiceImpl implements EgovUserDetailsService {

	@Override
	public Object getAuthenticatedUser() {
		if (EgovUserDetailsHelper.isAuthenticated()) {
			return EgovUserDetailsHelper.getAuthenticatedUser();
		}
		return null;
	}

	@Override
	public List<String> getAuthorities() {
		return EgovUserDetailsHelper.getAuthorities();
	}

	@Override
	public Boolean isAuthenticated() {
		return EgovUserDetailsHelper.isAuthenticated();
	}

}
