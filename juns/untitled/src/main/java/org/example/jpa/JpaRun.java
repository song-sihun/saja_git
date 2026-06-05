package org.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaRun {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lionPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

//        User user = new User("jun", "jun@email.com");
//        user.setId(2L);

        User user2 = new User("jun2", "jun2@email.com");
        user2.setId(1L);

        log.info("before persist");
        entityManager.persist(user2);
        log.info("after persist");

        log.info("before update");
        entityManager.getTransaction().commit();
        log.info("after update");


        entityManager.close();
        entityManagerFactory.close();


    }
}
