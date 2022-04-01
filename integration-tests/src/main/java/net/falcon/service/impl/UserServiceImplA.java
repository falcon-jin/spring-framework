package net.falcon.service.impl;

import net.falcon.service.UserService;
import net.falcon.service.UserServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplA implements UserServiceA {
	@Autowired
	private UserService userService;
	@Override
	public String test() {
		System.out.println("UserServiceA"+userService.toString());
		return null;
	}
}
