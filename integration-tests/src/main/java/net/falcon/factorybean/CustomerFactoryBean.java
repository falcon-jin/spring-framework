package net.falcon.factorybean;

import net.falcon.service.UserService;
import net.falcon.service.impl.UserServiceImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

//@Component
public class CustomerFactoryBean implements FactoryBean<UserService> {

	/**
	 * 这种方式 通过userService和customerFactoryBean 都能获取到UserService对象 通过&customerFactoryBean 获取的是CustomerFactoryBean对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserService getObject() throws Exception {
		//return new UserServiceImpl();
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return UserService.class;
	}
}
