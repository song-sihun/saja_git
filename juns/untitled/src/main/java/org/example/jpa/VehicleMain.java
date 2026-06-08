package org.example.jpa;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.jpa.entity.Truck;

@Slf4j
public class VehicleMain {
    public static void createTruck(Object o) {

        EntityManager em = JPAUtil.getEntityManager();

        try(em){
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTruck(new Truck(5.0));

    }


}
