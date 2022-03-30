package net.falcon;

import net.falcon.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("net.falcon.*");
		UserService userService = context.getBean(UserService.class);
		System.out.println(userService.test());
	}
}
