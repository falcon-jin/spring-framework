package net.falcon.service.impl;

import net.falcon.service.UserService;
import net.falcon.service.UserServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserServiceA userServiceA;
	@Override
	public String test() {
		System.out.println("UserService"+userServiceA.toString());
		return null;
	}
}
