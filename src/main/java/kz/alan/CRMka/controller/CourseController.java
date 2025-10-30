package kz.alan.CRMka.controller;

import kz.alan.CRMka.dto.CoursesDTO;
import kz.alan.CRMka.entities.Courses;
import kz.alan.CRMka.mappers.CourseMapper;
import kz.alan.CRMka.repositories.CoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private final CoursesRepository coursesRepository;

    @GetMapping("")
    public List<Courses> getCourses(){
        return coursesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourses(@PathVariable Long id){
        Optional<Courses> courses = coursesRepository.findById(id);
        return courses.isPresent() ? new ResponseEntity<>(courses.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public Courses createCourses(@RequestBody CoursesDTO courses){
        return coursesRepository.save(CourseMapper.toCourses(courses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourses(@PathVariable Long id, @RequestBody CoursesDTO courses){
        courses.setId(id);
        coursesRepository.save(CourseMapper.toCourses(courses));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourses(@PathVariable Long id){
        coursesRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
