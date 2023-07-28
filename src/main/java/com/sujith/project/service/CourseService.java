package com.sujith.project.service;

import com.sujith.project.entity.*;

import java.util.*;

public interface CourseService {
    List<Course> getAll();

    Course insertCourse(Course theCourse);

    List<Course> insertMany(List<Course> courses);
}
