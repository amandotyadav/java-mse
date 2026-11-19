package com.amandotyadav.student.repository;

import com.amandotyadav.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    List<Student> findAllByOrderByProgrammingScoreDesc();
}
