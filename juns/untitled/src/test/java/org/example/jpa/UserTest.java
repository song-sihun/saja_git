package org.example.jpa;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class UserTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    @BeforeAll
    public static void setup(){
        System.out.println("BeforeAll 실행");
        entityManagerFactory = Persistence.createEntityManagerFactory("lionPU");
    }
    @AfterAll
    public static void tearDown(){
        System.out.println("AfterAll 실행");
        if(entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    public void before(){
        System.out.println("BeforeEach 실행");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
    }
    @AfterEach
    public void after(){
        System.out.println("AfterEach 실행");
        if(entityTransaction != null &&  entityTransaction.isActive()){
            entityTransaction.rollback();
        }

        if(entityManager != null){
            entityManager.close();
        }
    }

    @Test
    void test1(){
        System.out.println("test1실행!!");
    }

    @Test
    void test2(){
        System.out.println("test2실행!!");
    }

    @Test
    @DisplayName("insert test :: 성공하면 id 존재")
    void insertUser(){
        User user = new User("admin", "admin@admin.com");

        entityManager.persist(user);
        entityTransaction.commit();

        assertNotNull(user.getId(), "test");
    }
}