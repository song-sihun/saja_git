package org.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.jpa.entity.Employee;
import org.example.jpa.entity.Project;

@Slf4j
public class EmployeeMain {

    public static void create(Object e) {
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        } catch (Exception err) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public static void addProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            em.getTransaction().begin();

            Employee e = em.find(Employee.class, employeeId);
            Project p = em.find(Project.class, projectId);

//            e.getProjects().add(p);
//            p.getEmployees().add(e);
            e.addProject(p);

            em.persist(e);
            em.persist(p);

            em.getTransaction().commit();

        }  catch (Exception err) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public static void find(Long id, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            em.getTransaction().begin();
            Employee e = em.find(Employee.class, id);
            Project p = em.find(Project.class, projectId);
            em.getTransaction().commit();

            log.info("Employee found with id {} projects {}", e.getId(), e.getProjects());
            log.info("Project found with id {} employees {}", p.getId(), p.getEmployees());
        } catch (Exception err) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public static void update(Long id, String employeeName) {
        EntityManager em = JPAUtil.getEntityManager();
        try(em){
            em.getTransaction().begin();
            Employee e = em.find(Employee.class, id);
            e.setName(employeeName);
            em.getTransaction().commit();
            Employee updated = em.find(Employee.class, id);
            log.info("Employee updated with name {}", updated.getName());

        } catch(Exception err) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }


    public static void main(String[] args) {

//        Employee e = new Employee("John Doe");
//        create(e);

//        Project p = new Project("Project 1");
//        create(p);

//        addProject(3L, 3L);

//        find(1L, 1L);

        update(1L, "Updated Doe");


    }
}
