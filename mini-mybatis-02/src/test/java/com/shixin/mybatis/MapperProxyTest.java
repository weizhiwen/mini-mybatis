package com.shixin.mybatis;

import com.shixin.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapperProxyTest {

    @Test
    @DisplayName("使用工厂模式创建Mapper的代理对象")
    public void testMapperProxy() {
        MapperProxyFactory<UserMapper> factory = new MapperProxyFactory<>(UserMapper.class);
        UserMapper userMapper = factory.newInstance();
        String result = userMapper.deleteUser(1L);
        assertEquals("代理[com.shixin.mybatis.mapper.UserMapper]的[deleteUser]方法成功，参数[[1]]", result);
        assertTrue(userMapper.toString().startsWith("com.shixin.mybatis.MapperProxy"));
    }

    @Test
    @DisplayName("使用MapperRegistry创建代理对象")
    public void testMapperRegistry() {
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("com.shixin.mybatis.mapper");
        UserMapper userMapper = registry.getMapper(UserMapper.class);
        String result = userMapper.deleteUser(1L);
        assertEquals("代理[com.shixin.mybatis.mapper.UserMapper]的[deleteUser]方法成功，参数[[1]]", result);
        assertTrue(userMapper.toString().startsWith("com.shixin.mybatis.MapperProxy"));
    }
}