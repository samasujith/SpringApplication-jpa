package com.sujith.project.service;

import com.sujith.project.dao.*;
import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import jakarta.transaction.*;
import org.apache.commons.lang.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;
    Logger logger=LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = courseDao.getAll();
        if (courses.isEmpty()) {
            throw (new ApiRequestException("No Courses were found"));
        } else {
            return courses;
        }
    }

    @Override
    @Transactional
    public Course insertCourse(Course theCourse) {
        try {
            return courseDao.insertCourse(theCourse);
        } catch (InvalidDataAccessApiUsageException ex) {
            throw new InvalidDataAccessApiUsageException("courseName should not be null");
        } catch (NullPointerException e) {
            throw new ApiRequestException("courseName should not be null");
        }
    }

    @Override
    @Transactional
    public List<Course> insertMany(List<Course> courses) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
                if (!StringUtils.isEmpty(course.getCourseName())) {
                    result.add(course);
                }else{
                    logger.error("Invalid coursename");
                }



        }
        return courseDao.insertMany(result);
    }
}
