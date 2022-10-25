package com.example.hibernate.hibernatedemo.repository;

import com.example.hibernate.hibernatedemo.HibernateDemoApplication;
import com.example.hibernate.hibernatedemo.entities.Course;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = HibernateDemoApplication.class)
public class JPQLTest {
    @Autowired
    EntityManager em;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
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
        List queryResult = em.createQuery("Select c from Course c where name like 'Learn%'", Course.class).getResultList();
        logger.info("Select c from Course c -> {}", queryResult);
    }
    @Test
    void findById_named() {
        List queryResult = em.createNamedQuery("get_all_courses").getResultList();
        logger.info("Select c from Course c -> {}", queryResult);
    }
}
