package org.example.iocexam.book.service;

import org.example.iocexam.book.entity.Book;
import org.example.iocexam.book.repository.BookRepository;
import org.example.iocexam.book.repository.BookRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteById(int id) {
        bookRepository.deleteById(id);

    }

    @Override
    public void deleteByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }

    @Override
    public void update(Book book) {
        bookRepository.save(book);
    }
}
