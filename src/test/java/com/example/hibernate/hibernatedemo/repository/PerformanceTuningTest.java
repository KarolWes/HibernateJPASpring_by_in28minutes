package com.example.hibernate.hibernatedemo.repository;

import com.example.hibernate.hibernatedemo.HibernateDemoApplication;
import com.example.hibernate.hibernatedemo.entities.Course;
import com.example.hibernate.hibernatedemo.entities.Review;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HibernateDemoApplication.class)
class CourseRepositoryTest {

    @Autowired
    CourseRepository repository;
    @Autowired
    EntityManager em;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    void findById() {
        Course course = repository.findById(10001);
        assertEquals("Learn Spring", course.getName(), "Wrong name of the gotten record");
    }
    @Test
    @Transactional
    void findById_firstLevelCache(){
        Course course = repository.findById(10001);
        logger.info("First course: {}", course);
        Course course1 = repository.findById(10001);
        logger.info("First course again: {}", course1);
        assertEquals("Learn Spring", course.getName(), "Wrong name of the gotten record");
        assertEquals("Learn Spring", course1.getName(), "Wrong name of the gotten record");
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
    @Test
    @Transactional
    void retrieveReviewsFromCourse() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Course c = repository.findById(10001L);
        logger.info("{}", c.getReviews());
    }
    @Test
    @Transactional
    void retrieveCourseFromReview() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Review r = em.find(Review.class, 50002L);
        logger.info("{}", r.getCourse());
    }
}