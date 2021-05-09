package com.tong.base;

public class Teacher {
    private Integer id;
    private String name;

    private Student student;

    public Teacher(String name, Student student) {
        this.name = name;
        this.student = student;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
