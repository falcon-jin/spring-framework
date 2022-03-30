/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory;

import org.springframework.lang.Nullable;

/**
 * FactoryBean的特殊之处在于它可以向容器中注册两个Bean，一个是它本身，一个是FactoryBean.getObject()方法返回值所代表的Bean。
 *
 * 由BeanFactory中使用的对象实现的接口，这些对象本身就是各个对象的工厂。如果一个 bean 实现了这个接口，它被用作一个对象的工厂来暴露，而不是直接作为一个将自己暴露的 bean 实例。
 * 注意：实现此接口的 bean 不能用作普通 bean。 FactoryBean 以 bean 样式定义，但为 bean 引用 ( getObject() ) 公开的对象始终是它创建的对象。
 * FactoryBeans 可以支持单例和原型，并且可以按需懒惰地创建对象，也可以在启动时急切地创建对象。 SmartFactoryBean接口允许公开更细粒度的行为元数据。
 * 该接口在框架本身中被大量使用，例如用于 AOP org.springframework.aop.framework.ProxyFactoryBean或org.springframework.jndi.JndiObjectFactoryBean 。它也可以用于自定义组件；但是，这仅适用于基础设施代码。
 * FactoryBean是一种程序化契约。实现不应该依赖注释驱动的注入或其他反射设施。 getObjectType() getObject()调用可能会在引导过程的早期到达，甚至在任何后处理器设置之前。如果您需要访问其他 bean，请实现BeanFactoryAware并以编程方式获取它们。
 * 容器只负责管理FactoryBean 实例的生命周期，而不是FactoryBean 创建的对象的生命周期。因此，暴露的 bean 对象（例如java.io.Closeable.close()上的销毁方法不会被自动调用。相反，FactoryBean 应该实现DisposableBean并将任何此类关闭调用委托给底层对象。
 * 最后，FactoryBean 对象参与包含 BeanFactory 的 bean 创建同步。除了在 FactoryBean 本身（或类似的）中进行延迟初始化之外，通常不需要内部同步
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 08.03.2003
 * @param <T> the bean type
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.aop.framework.ProxyFactoryBean
 * @see org.springframework.jndi.JndiObjectFactoryBean
 */
public interface FactoryBean<T> {

	/**
	 * 可以在org.springframework.beans.factory.config.BeanDefinition上set的属性的名称，
	 * 以便工厂 bean 可以在无法从工厂 bean 类中推断出它们的对象类型时发出信号。
	 * @since 5.2
	 */
	String OBJECT_TYPE_ATTRIBUTE = "factoryBeanObjectType";


	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。
	 * 与BeanFactory一样，这允许支持 Singleton 和 Prototype 设计模式。
	 * 如果在调用时此 FactoryBean 尚未完全初始化（例如，因为它涉及循环引用），则抛出相应的FactoryBeanNotInitializedException 。
	 * 从 Spring 2.0 开始，FactoryBeans 被允许返回null对象。工厂将此视为正常值使用；在这种情况下，它不会再抛出 FactoryBeanNotInitializedException 了。
	 * 鼓励 FactoryBean 实现现在酌情自行抛出 FactoryBeanNotInitializedException。
	 * @return an instance of the bean (can be {@code null})
	 * @throws Exception in case of creation errors
	 * @see FactoryBeanNotInitializedException
	 */
	@Nullable
	T getObject() throws Exception;

	/**
	 * 返回此 FactoryBean 创建的对象的类型，如果事先不知道，则返回null 。
	 * 这允许人们在不实例化对象的情况下检查特定类型的 bean，例如在自动装配时。
	 * 在创建单例对象的实现的情况下，此方法应尽量避免创建单例；它应该提前估计类型。对于原型，在这里返回一个有意义的类型也是可取的。
	 * 可以在此 FactoryBean 完全初始化之前调用此方法。它不能依赖于初始化期间创建的状态；当然，如果可用，它仍然可以使用这种状态。
	 * 注意：自动装配将简单地忽略在此处返回null的 FactoryBean。因此，强烈建议使用 FactoryBean 的当前状态正确实现此方法。
	 * @return the type of object that this FactoryBean creates,
	 * or {@code null} if not known at the time of the call
	 * @see ListableBeanFactory#getBeansOfType
	 */
	@Nullable
	Class<?> getObjectType();

	/**
	 * 这个工厂管理的对象是单例吗？也就是说， getObject()是否总是返回相同的对象（可以缓存的引用）？
	 * 注意：如果 FactoryBean 指示持有一个单例对象，则从getObject()返回的对象可能会被拥有的 BeanFactory 缓存。因此，除非 FactoryBean 始终公开相同的引用，否则不要返回true 。
	 * FactoryBean 本身的单例状态一般会由拥有的 BeanFactory 提供；通常，它必须在那里定义为单例。
	 * 注意：此方法返回false并不一定表示返回的对象是独立的实例。扩展SmartFactoryBean接口的实现可以通过其SmartFactoryBean.isPrototype()方法显式指示独立实例。
	 * 如果isSingleton()实现返回false ，则简单地假定未实现此扩展接口的普通FactoryBean实现始终返回独立实例。
	 * 默认实现返回true ，因为FactoryBean通常管理一个单例实例。
	 * @return whether the exposed object is a singleton
	 * @see #getObject()
	 * @see SmartFactoryBean#isPrototype()
	 */
	default boolean isSingleton() {
		return true;
	}

}
