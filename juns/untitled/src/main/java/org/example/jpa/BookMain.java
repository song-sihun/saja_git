package org.example.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.example.jpa.entity.Author;
import org.example.jpa.entity.Book;

@Slf4j
public class BookMain {
    public static void main(String[] args) {
//        create();
//        find();
//        update();
        delete();

    }

    private static void find(){
        //        em.getTransaction().begin();
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Author author = em.find(Author.class, 1L);
            log.info("지은이 이름 : {}", author.getName());

            for (Book book : author.getBooks()) {
                log.info("책제목 : {}", book.getTitle());
            }
            Book book = em.find(Book.class, 1L);
            log.info("Book title : {}", book.getTitle());
            log.info("Author name : {}", book.getAuthor().getName());

        }
    }
    private static void create(){
        EntityManager em = JPAUtil.getEntityManager();

        try (em) {
            em.getTransaction().begin();
            Author author = new Author();
            author.setName("강경미");

            Book book = new Book();
            book.setTitle("모두의 자바");
            book.setAuthor(author);

            author.getBooks().add(book);

            em.persist(author);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
    private static void update(){
        EntityManager em = JPAUtil.getEntityManager();
        try (em) {
            em.getTransaction().begin();
            Author author = em.find(Author.class, 1L);
            author.setName("carami");
            em.getTransaction().commit();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
    private static void delete(){
        EntityManager em = JPAUtil.getEntityManager();
        try (em) {
            em.getTransaction().begin();
            Author author = em.find(Author.class, 1L);
            log.info(author.getName());
            em.remove(author);
            em.getTransaction().commit();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

}