package jdbc04.book.service;

import jdbc04.book.entity.Book;
import jdbc04.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    public Book findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    public Book findBookByAuthor(String author) {
        return bookRepository.findBookByAuthor(author);
    }

    public List<Book> findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }


    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Long id, Book updateBook) {
        Book sourseBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다. id=" + id));
        sourseBook.updateDetails(updateBook.getTitle(), updateBook.getAuthor());

    }


}
