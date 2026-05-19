package org.example.iocexam.book.repository;

import org.example.iocexam.book.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository{
    List<Book> findAll();
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByPublisher(String publisher);
    Book findById(int id);
    void deleteByTitle(String title);
    void deleteById(int id);
    void save(Book book);
}
