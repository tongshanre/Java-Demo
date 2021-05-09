package com.tong.dynamicproxy;

import com.tong.base.Dog;
import com.tong.inter.Animal;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDynamicProxy {
    public void testProxy() {
        final Dog dog = new Dog();
        Animal animal = (Animal) Proxy.newProxyInstance(dog.getClass().getClassLoader(), dog.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before");
                Object invoke = method.invoke(dog, args);
                System.out.println("after");
                return invoke;
            }
        });
        animal.speack();
        System.out.println(dog.getClass());
        System.out.println(animal.getClass());
    }

    public void testCGLib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dog.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before ...");
                Object result = methodProxy.invokeSuper(o, objects);
                System.out.println("after ...");
                return result;
            }
        });
        Dog dog = (Dog) enhancer.create();
        dog.speack();
    }

    @Test
    public void bench() {
        while (true) {

        }
    }
}
