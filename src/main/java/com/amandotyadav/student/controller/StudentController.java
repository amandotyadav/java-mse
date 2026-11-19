
package com.amandotyadav.student.controller;

import com.amandotyadav.student.model.Student;
import com.amandotyadav.student.repository.StudentRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // 1. Add student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    // 2. Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // 3. Search student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update scores and skills
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable int id,
            @RequestBody Student updatedStudent) {

        return repository.findById(id).map(student -> {

            student.setProgrammingScore(
                    updatedStudent.getProgrammingScore());

            student.setMathematicsScore(
                    updatedStudent.getMathematicsScore());

            student.setTechnicalSkills(
                    updatedStudent.getTechnicalSkills());

            return ResponseEntity.ok(repository.save(student));

        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.ok("Student deleted successfully");
    }

    // 6. Display merit list
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {

        return repository.findAll()
                .stream()
                .sorted((s1, s2) ->
                        Double.compare(
                                s2.getAverage(),
                                s1.getAverage()
                        ))
                .toList();
    }
}
