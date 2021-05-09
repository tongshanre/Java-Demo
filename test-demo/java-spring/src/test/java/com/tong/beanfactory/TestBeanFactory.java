package com.tong.beanfactory;


import com.tong.base.Student;
import com.tong.base.Teacher;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

public class TestBeanFactory {

    // 1. 通过代码配置Bean
    public void beanFactoryRegistryByCode() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 构造方法
        ConstructorArgumentValues values = new ConstructorArgumentValues();
        values.addGenericArgumentValue("hello world");
        RootBeanDefinition rootBeanDefinition1 = new RootBeanDefinition(String.class, values, null);
        beanFactory.registerBeanDefinition("haha", rootBeanDefinition1);
        // set方法
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("name", "小明");
        mutablePropertyValues.add("age", 10);
        RootBeanDefinition rootBeanDefinition2 = new RootBeanDefinition(Student.class, null, mutablePropertyValues);
        beanFactory.registerBeanDefinition("student", rootBeanDefinition2);


        Assert.assertTrue(beanFactory.containsBeanDefinition("haha"));
        Object haha = beanFactory.getBean("haha");
        System.out.println(haha);
        Assert.assertTrue(beanFactory.containsBeanDefinition("student"));
        System.out.println(beanFactory.getBean("student"));
    }

    // 2. 通过properties文件配置Bean
    public void beanFactoryRegistryByProp() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new DefaultResourceLoader().getResource("classpath:student.properties"));
        Assert.assertTrue(beanFactory.containsBean("student"));
        System.out.println(beanFactory.getBean("student"));
    }

    // 3. 通过xml文件配置Bean
    public void beanFactoryRegistryByXml() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new DefaultResourceLoader().getResource("classpath:student.xml"));
        Assert.assertTrue(beanFactory.containsBean("student"));
        System.out.println(beanFactory.getBean("student"));
    }

    //4. 使用注解配置Bean
    @Test
    public void beanFactoryRegistryByAnno() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:springApplication.xml");
        Assert.assertTrue(ctx.containsBean("student"));
        Student student = (Student) ctx.getBean("student");
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        System.out.println(student.getName() + "-" + student.getTeacher().getName());
        System.out.println(teacher.getName() + "-" + teacher.getStudent().getName());
    }

}
