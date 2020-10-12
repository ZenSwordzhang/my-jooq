package com.zsx.design.pattern.structural.proxy.dynamical;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class LogInvocationHandler implements InvocationHandler {

    // 被代理的对象，实际的方法执行者
    private Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(target, args);
        after();
        return invoke;
    }

    private void before() {
        System.out.println("Start Time：" + LocalDateTime.now());
    }

    private void after() {
        System.out.println("End Time：" + LocalDateTime.now());
    }
}
