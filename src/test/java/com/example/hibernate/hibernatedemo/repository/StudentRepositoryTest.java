package com.example.hibernate.hibernatedemo.repository;

import com.example.hibernate.hibernatedemo.HibernateDemoApplication;
import com.example.hibernate.hibernatedemo.entities.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HibernateDemoApplication.class)
class CourseRepositoryTest {

    @Autowired
    CourseRepository repository;
    @Test
    void findById() {
        Course course = repository.findById(10001);
        assertEquals("Learn Spring", course.getName(), "Wrong name of the gotten record");
    }

    @Test
    @DirtiesContext
    void deleteById() {
        repository.deleteById(10002);
        assertNull(repository.findById(10002));
    }

    @Test
    @DirtiesContext
    void saveCourse() {
        Course course = repository.findById(10001);
        assertEquals("Learn Spring", course.getName(), "Wrong name of the gotten record");
        course.setName("PHP");
        repository.saveCourse(course);
        Course course1 = repository.findById(10001);
        assertEquals("PHP", course1.getName(), "Wrong name of the gotten record");
    }

    @Test
    void playWithEM() {
        repository.playWithEM();
    }
}