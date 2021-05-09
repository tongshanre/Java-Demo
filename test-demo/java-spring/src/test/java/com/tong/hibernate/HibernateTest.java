package com.tong.hibernate;

import com.tong.hibernate.beans.Student;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

public class HibernateTest {

    @Test
    public void testHibernate() {
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springApplication.xml");
        HibernateTemplate hibernateTemplate = (HibernateTemplate) xmlApplicationContext.getBean("hibernateTemplate");
        Student student = hibernateTemplate.get(Student.class, new Integer(1));
        System.out.println(student);
    }
}
