package com.shixin.mybatis;

import java.lang.reflect.Proxy;

public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance() {
        MapperProxy<T> userMapperProxy = new MapperProxy<>(mapperInterface);
        T mapper = (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{mapperInterface},
                userMapperProxy);
        return mapper;
    }
}
