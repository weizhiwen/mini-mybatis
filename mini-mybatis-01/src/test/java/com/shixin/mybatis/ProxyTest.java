package com.shixin.mybatis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyTest {

    @Test
    @DisplayName("测试JDK代理")
    public void testJDKProxy() {
        AtomicBoolean isProxy = new AtomicBoolean(false);
        // JDK Proxy基于接口生成动态代理对象
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{UserMapper.class}, (proxy, method, args) -> {
                    isProxy.set(true);
                    System.out.println(method.getName() + " is called with args: " + Arrays.toString(args));
                    return proxy;
                });
        userMapper.deleteUser(1L);
        assertTrue(isProxy.get());
    }

    @Test
    @DisplayName("测试JDK代理只能代理接口，不能代理类")
    public void testProxyException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AtomicBoolean isProxy = new AtomicBoolean(false);
            UserDao userDao = (UserDao) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                    new Class[]{UserDao.class}, (proxy, method, args) -> {
                        isProxy.set(true);
                        System.out.println(method.getName() + " is called with args: " + Arrays.toString(args));
                        return proxy;
                    });
            userDao.deleteUser(1L);
            assertTrue(isProxy.get());
        }, "JDK proxy 只能生成接口的代理对象，不能生成类的代理对象");
        assertEquals("com.shixin.mybatis.UserDao is not an interface", exception.getMessage());
    }
}
