package com.tong.jdbc;

import com.tong.hibernate.beans.Student;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplateTest {

    ClassPathXmlApplicationContext xmlApplicationContext;

    @Before
    public void before() {
        xmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springApplication.xml");
    }


    public void testJdbcTemplate() {

        JdbcTemplate jdbcTemplate = (JdbcTemplate) xmlApplicationContext.getBean("jdbcTemplate");
        List<Student> query = jdbcTemplate.query("select * from student", new RowMapper<Student>() {

            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                return student;
            }
        });
        System.out.println(query);
    }

    public void testTransaction() {
        JdbcTemplate jdbcTemplate = (JdbcTemplate) xmlApplicationContext.getBean("jdbcTemplate");
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) xmlApplicationContext.getBean("transactionManager");
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
        try {
            System.out.println(jdbcTemplate.queryForList("select * from student"));
            int update = jdbcTemplate.update("insert into student(name)values(?)", new String[]{"小李"});
            System.out.println("update:" + update);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            e.printStackTrace();
        }
        System.out.println(jdbcTemplate.queryForList("select * from student"));
    }
}
