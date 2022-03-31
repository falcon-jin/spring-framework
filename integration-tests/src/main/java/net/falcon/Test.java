package net.falcon;

import net.falcon.factorybean.CustomerFactoryBean;
import net.falcon.service.UserService;
import net.falcon.service.impl.UserServiceImpl;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.util.Objects;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestApplication.class);
		Object userService = context.getBean("userServiceImpl");

			if (userService instanceof UserService registry) {
				System.out.println(registry);


			}




	}
}
