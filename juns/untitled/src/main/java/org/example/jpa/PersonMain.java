package org.example.jpa;


import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.jpa.entity.Passport;
import org.example.jpa.entity.Person;

@Slf4j
public class PersonMain {

    public static void create(Object o){
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            log.error(e.getMessage());
        }
    }

    public static void update(Long id, String personName){
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            person.setName(personName);
            em.getTransaction().commit();
        }  catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            log.error(e.getMessage());
        }
    }

    public static void find(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            Person person = em.find(Person.class, id);
            if(person != null){
                log.info(person.toString());
            } else {
                log.info("Person not found");
            }
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            log.error(e.getMessage());
        }
    }

    public static void delete(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
//        Person person = new Person("John Doe");
//        Passport passport = new Passport("12345");
//        person.setPassport(passport);
//        create(person);
//        update(1L, "Jane Doe");
//        find(1L);
//        delete(1L);
        find(1L);

    }
}
