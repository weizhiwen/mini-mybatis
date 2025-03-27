package com.shixin.mybatis;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = 1L;
    private final Class<T> mapperInterface;

    public MapperProxy(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        return String.format("代理[%s]的[%s]方法成功，参数[%s]", mapperInterface.getName(), method.getName(), Arrays.toString(args));
    }
}
