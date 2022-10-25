package com.example.hibernate.hibernatedemo.repository;

import com.example.hibernate.hibernatedemo.HibernateDemoApplication;
import com.example.hibernate.hibernatedemo.entities.Course;
import com.example.hibernate.hibernatedemo.entities.Student;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = HibernateDemoApplication.class)
public class JPQLTest {
    @Autowired
    EntityManager em;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    void findById() {
        List queryResult = em.createQuery("Select c from Course c").getResultList();
        logger.info("Select c from Course c -> {}", queryResult);
    }
    @Test
    void findById_typed() {
        List queryResult = em.createQuery("Select c from Course c", Course.class).getResultList();
        logger.info("Select c from Course c -> {}", queryResult);
    }
    @Test
    void findById_where() {
        List queryResult = em.createQuery("Select c from Course c where c.name like 'Learn%'", Course.class).getResultList();
        logger.info("Select c from Course c -> {}", queryResult);
    }
    @Test
    void findById_named() {
        List queryResult = em.createNamedQuery("get_all_courses").getResultList();
        logger.info("Select c from Course c -> {}", queryResult);
    }
    @Test
    void coursesWithoutStudents(){
        List<Course> resultList = em.createQuery("select c from Course c where c.students is empty ", Course.class).getResultList();
        logger.info("Courses without students: {}", resultList);
    }
    @Test
    void coursesWithAtLeast2Students(){
        List<Course> resultList = em.createQuery("select c from Course c where size(c.students) >= 2 ", Course.class).getResultList();
        logger.info("Courses with 2+ students: {}", resultList);
    }
    @Test
    void coursesOrderByStudents(){
        List<Course> resultList = em.createQuery("select c from Course c order by size(c.students) desc", Course.class).getResultList();
        logger.info("Courses ordered by students: {}", resultList);
    }
    @Test
    void studentWithPassportsInPattern(){
        List<Student> resultList = em.createQuery("select s from Student s where s.passport.number like '%234%'", Student.class).getResultList();
        logger.info("Courses ordered by students: {}", resultList);
    }

    @Test
    void join(){
        Query q=em.createQuery("select s, c from Student s join s.courses c");
        List<Object[]> resultList = q.getResultList();
        logger.info("size: {}", resultList.size());
        for (Object[] res:resultList) {
            logger.info("Student : {}", res[0]);
            logger.info("Course : {}", res[1]);
        }

    }
    @Test
    void leftJoin(){
        Query q=em.createQuery("select c, s from Course c left join c.students s");
        List<Object[]> resultList = q.getResultList();
        logger.info("size: {}", resultList.size());
        for (Object[] res:resultList) {
            logger.info("Course : {}", res[0]);
            logger.info("Student : {}", res[1]);
        }
    }
    @Test
    void crossJoin(){
        Query q=em.createQuery("select c, s from Course c, Student s");
        List<Object[]> resultList = q.getResultList();
        logger.info("size: {}", resultList.size());
        for (Object[] res:resultList) {
            logger.info("Course : {}", res[0]);
            logger.info("Student : {}", res[1]);
        }

    }
}
