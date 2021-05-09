package com.tong.javaagent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class NewPreMain {

    /**
     * jvm参数形式启动，运行此方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
        process(agentArgs, inst);
    }


    /**
     * 动态attach方式启动，运行此方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain");
        process(agentArgs, inst);
    }

    private static void process(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new MyTransformer(), true);

        //使用attach方式，添加下列代码
        try {
            inst.retransformClasses(Person.class);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Person person = new Person();
        while (true) {
            person.eat();
            Thread.sleep(2 * 1000);
        }
    }


}
