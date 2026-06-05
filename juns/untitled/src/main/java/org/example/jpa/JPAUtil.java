package org.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;
import lombok.Setter;

public class JPAUtil {

    @Setter
    @Getter
    private static EntityManagerFactory emf;
    private static EntityManager em;

    // 생성자 대신 static 블록을 사용합니다.
    static {
        try {
            emf = Persistence.createEntityManagerFactory("lionPU");
            em = emf.createEntityManager();
        } catch (Exception e) {
            throw new RuntimeException("EntityManagerFactory 초기화 실패!");
        }
    }

    public static EntityManager getEntityManager(){
        return em;
    }
}
