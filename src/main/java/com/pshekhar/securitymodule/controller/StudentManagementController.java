package com.pshekhar.securitymodule.controller;

import com.pshekhar.securitymodule.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Tushar Karan"),
            new Student(2, "Mohan Bhargav"),
            new Student(3, "Anand Gaurav")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('course:write')")
    public void registerStudent(@RequestBody Student student, @RequestAttribute(name = "client") String client) {
        System.out.println("Logged-in client: " + client);
        System.out.println("Registering student: " + student);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('course:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("Deleting student with id: " + studentId);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('course:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("Updating student with id: " + studentId + " and data : " + student);
    }

}
