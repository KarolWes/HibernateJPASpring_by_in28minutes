package com.example.hibernate.hibernatedemo.repository;

import com.example.hibernate.hibernatedemo.entities.Course;
import com.example.hibernate.hibernatedemo.entities.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;


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
//    Review r1 = new Review("3", "Tak srednio bym powiedzial, tak srednio");
//    Review r2 = new Review("4", "Good job");
    public void addReviewsForCourse(Long id, List<Review> reviews){
        Course c = findById(id);
        for(var r: reviews){
            c.addReview(r);
            r.setCourse(c);
            em.persist(r);
        }
//        c.addReview(r1);
//        r1.setCourse(c);
//        c.addReview(r2);
//        r2.setCourse(c);
//        em.persist(r1);
//        em.persist(r2);

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
