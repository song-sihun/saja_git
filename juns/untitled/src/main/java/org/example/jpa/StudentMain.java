package org.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.example.jpa.entity.School;
import org.example.jpa.entity.Student;

@Slf4j
public class StudentMain {
    public static void find(){
        try (EntityManager entityManager = JPAUtil.getEntityManager()) {
            School school = entityManager.find(School.class, 1);
            log.info("School: {}", school.getName());

            Student student = entityManager.find(Student.class, 1);
            School school2 = student.getSchool();
            log.info("School2: {}", school2.getName());

        } catch (Exception ex) {
            log.info("Exception: {}", ex.getMessage());
        }
    }

    public static void create(String schoolName){
        EntityManager entityManager = JPAUtil.getEntityManager();

        try (entityManager) {
            entityManager.getTransaction().begin();
            School school = new School();
            school.setName(schoolName);
            entityManager.persist(school);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    //    수정
    public static void updateSchoolName(Long id, String SchoolName){
        EntityManager entityManager = JPAUtil.getEntityManager();

        try (entityManager) {
            entityManager.getTransaction().begin();
            School school = entityManager.find(School.class, id);
            school.setName(SchoolName);
            entityManager.persist(school);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public static void updateStudentName(Long id, String name){
        EntityManager entityManager = JPAUtil.getEntityManager();

        try (entityManager) {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, id);
            student.setName(name);
            entityManager.persist(student);
            entityManager.getTransaction().commit();

        } catch (Exception e){
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }


    //    삭제
    public static void deleteSchool(Long id){
        EntityManager entityManager = JPAUtil.getEntityManager();
        try (entityManager) {
            entityManager.getTransaction().begin();
            School school = entityManager.find(School.class, id);
            entityManager.remove(school);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public static void main(String[] args) {
//        create("Test School");
//        updateStudentName(1L, "TEST");
//        updateSchoolName(3L, "TEST School TEST");
        deleteSchool(1L);

    }
}
