package org.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UserDaoTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static UserDao userDao;

    @BeforeAll
    public static void setup(){
        System.out.println("BeforeAll 실행");
        entityManagerFactory = Persistence.createEntityManagerFactory("lionPU");
        userDao = new UserDao();
    }
    @AfterAll
    public static void tearDown(){
        System.out.println("AfterAll 실행");
        if(entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }

    @Test
    void create() {
        User user = new User("jun" ,"jus22@email.com");
        userDao.create(user);
        assertNotNull(user.getId());
    }

    @Test
    void update() {
        User user = userDao.getUserById(1L);
        user.setEmail("test@test.com");
        userDao.update(user);
        log.info(user.getEmail());

        User user2 = userDao.getUserById(1L);
        log.info(user2.getEmail());

        assertEquals("test@test.com", user2.getEmail());

    }

    @Test
    void deleteById() {
        User user = new User("jun44" ,"jus44@email.com");
        userDao.create(user);
        userDao.deleteById(5L);
        User user1 = userDao.getUserById(5L);
        assertNull(user1);
    }

    @Test
    void getUserById() {
        User user = userDao.getUserById(1L);
        assertNotNull(user);
    }
}