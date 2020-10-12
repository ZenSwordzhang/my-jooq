package com.zsx.design.pattern.structural.proxy.dynamical;

import java.lang.reflect.Proxy;

public class LogProxyFactory {

    public Object getProxyInstance(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new LogInvocationHandler(target));
    }
}
