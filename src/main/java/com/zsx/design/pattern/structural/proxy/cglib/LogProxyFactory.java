package com.zsx.design.pattern.structural.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class LogProxyFactory implements MethodInterceptor {

    private Object target;

    public LogProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        //指定父类，注意：cglib的原理是子类增强父类，所以使用cglib动态代理时，要求目标类不能是final
        enhancer.setSuperclass(target.getClass());
        // 设置回调接口对象
        enhancer.setCallback(this);
        // 创建子类，也就是代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
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
