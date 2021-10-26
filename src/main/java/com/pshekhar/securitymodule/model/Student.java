package com.pshekhar.securitymodule.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {
    private final Integer id;
    private final String name;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
