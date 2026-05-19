package org.example.iocexam.book.service;

import org.example.iocexam.book.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(int id);
    void save(Book book);
    void deleteById(int id);
    void deleteByTitle(String title);
    void update(Book book);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByPublisher(String publisher);

}
