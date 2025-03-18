package com.shixin.mybatis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyTest {

    @Test
    @DisplayName("测试JDK代理")
    public void testJDKProxy() {
        // JDK Proxy基于接口生成动态代理对象
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{UserMapper.class},
                (proxy, method, args) -> String.format("%s is called with args: %s", method.getName(), Arrays.toString(args)));
        String result = userMapper.deleteUser(1L);
        assertEquals("deleteUser is called with args: [1]", result);
    }

    @Test
    @DisplayName("测试JDK代理只能代理接口，不能代理类")
    public void testProxyException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UserDao userDao = (UserDao) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                    new Class[]{UserDao.class},
                    (proxy, method, args) -> String.format("%s is called with args: %s", method.getName(), Arrays.toString(args)));
            String result = userDao.deleteUser(1L);
            assertEquals("deleteUser is called with args: [1]", result);
        }, "JDK proxy 只能生成接口的代理对象，不能生成类的代理对象");
        assertEquals("com.shixin.mybatis.UserDao is not an interface", exception.getMessage());
    }

    @Test
    @DisplayName("测试将代理调用封装到MapperProxy")
    public void testMapperProxy() {
        // 将代理调用封装到MapperProxy
        MapperProxy<UserMapper> userMapperProxy = new MapperProxy<>(UserMapper.class);
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(UserMapper.class.getClassLoader(),
                new Class[]{UserMapper.class},
                userMapperProxy);
        String result = userMapper.deleteUser(1L);
        assertEquals("代理[com.shixin.mybatis.UserMapper]的[deleteUser]方法成功，参数[[1]]", result);
        assertTrue(userMapper.toString().startsWith("com.shixin.mybatis.MapperProxy"));
    }
}
