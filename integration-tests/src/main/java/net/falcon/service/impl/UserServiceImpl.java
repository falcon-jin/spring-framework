package net.falcon.service.impl;

import net.falcon.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public String test() {
		System.out.println("123123123123");
		return null;
	}
}
