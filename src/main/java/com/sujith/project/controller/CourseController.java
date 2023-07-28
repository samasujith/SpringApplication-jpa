package com.sujith.project.controller;

import com.sujith.project.entity.*;
import com.sujith.project.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {
    CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseService.getAll();
    }

    @PostMapping("/courses")
    public Course insertCourse(@RequestBody Course theCourse) {
        return courseService.insertCourse(theCourse);
    }

    @PostMapping("/courses/insertMany")
    public List<Course> insertCourses(@RequestBody List<Course> courses) {
        return courseService.insertMany(courses);
    }


}
