package com.shixin.mybatis;

import cn.hutool.core.lang.ClassScanner;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {
    private final Map<Class<?>, MapperProxyFactory<?>> registryMappers = new HashMap<>();

    public <T> void addMapper(Class<T> mapperClass) {
        // 接口才可以注册
        if (!mapperClass.isInterface()) {
            return;
        }
        if (registryMappers.containsKey(mapperClass)) {
            throw new RuntimeException("Duplicate registry mapper interface " + mapperClass.getName());
        }
        MapperProxyFactory<T> mapperProxyFactory = new MapperProxyFactory<>(mapperClass);
        registryMappers.put(mapperClass, mapperProxyFactory);
    }

    public void addMappers(String packageName) {
        ClassScanner classScanner = new ClassScanner(packageName);
        classScanner.scan().forEach(this::addMapper);
    }

    public <T> T getMapper(Class<T> mapperClass) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) registryMappers.get(mapperClass);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("No mapper registered for " + mapperClass.getName());
        }
        return mapperProxyFactory.newInstance();
    }
}
