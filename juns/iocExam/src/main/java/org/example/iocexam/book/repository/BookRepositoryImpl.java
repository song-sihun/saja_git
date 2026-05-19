package org.example.iocexam.book.repository;

import org.example.iocexam.book.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Override
    public List<Book> findByTitle(String title) {
        System.out.println("BookRepositoryImpl.findByTitle");
        System.out.println("Book list");
        return List.of();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        System.out.println("BookRepositoryImpl.findByAuthor");
        return List.of();
    }

    @Override
    public List<Book> findByPublisher(String publisher) {
        System.out.println("BookRepositoryImpl.findByPublisher");
        return List.of();
    }

    @Override
    public void deleteByTitle(String title) {
        System.out.println("BookRepositoryImpl.deleteByTitle");
    }

    @Override
    public List<Book> findAll() {
        System.out.println("BookRepositoryImpl.findAll");
        return List.of();
    }

    @Override
    public Book findById(int id) {
        System.out.println("BookRepositoryImpl.findById");
        return null;
    }

    @Override
    public void deleteById(int id) {
        System.out.println("BookRepositoryImpl.deleteById");
    }

    @Override
    public void save(Book book) {
        System.out.println("BookRepositoryImpl.save");
    }
}
