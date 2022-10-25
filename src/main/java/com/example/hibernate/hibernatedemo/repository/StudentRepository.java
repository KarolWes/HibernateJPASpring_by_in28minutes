package com.example.hibernate.hibernatedemo.repository;

import com.example.hibernate.hibernatedemo.entities.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;


@Repository
@Transactional
public class CourseRepository {
    @Autowired
    EntityManager em;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public Course findById(long id){
        return em.find(Course.class, id);
    }
    public void saveCourse(Course c){
        if(c.getId()==null){
            em.persist(c);
        }
        else{
            em.merge(c);
        }

    }
    public void deleteById(long id){
        Course tmp = findById(id);
        em.remove(tmp);
    }
    public void playWithEM(){
        Course c = new Course("C/C++");
        em.persist(c);
        Course c2 = new Course("prolog");
        em.persist(c2);

        em.flush();

        c.setName("C++");
        c2.setName("prolog (.pl)");

        em.refresh(c);
        em.flush();

        Course c3 = findById(10001);
        c3.setName("Learn Spring and Java");
        em.flush();
    }
}
