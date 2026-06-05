package jdbc04.book.service;

import jdbc04.Jdbc04App;
import jdbc04.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Jdbc04App.class)
@Transactional
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findBookByIsbn() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        book.setIsbn("123456789");
        book.setPublishYear(2020);

        bookService.saveBook(book);

        book = bookService.findBookByIsbn("123456789");
        assertEquals("Test Title", book.getTitle());

    }

    @Test
    void findBookByAuthor() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        book.setIsbn("123456789");
        book.setPublishYear(2020);

        bookService.saveBook(book);

        book = bookService.findBookByAuthor("Test Author");
        assertEquals("Test Author", book.getAuthor());
    }

    @Test
    void findBookByTitle() {
    }

    @Test
    void findAll() {
    }

    @Test
    void saveBook() {
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        book.setIsbn("123456789");
        book.setPublishYear(2020);
        bookService.saveBook(book);
        book = bookService.findBookByIsbn("123456789");
        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");
        book.setPublishYear(2020);
        bookService.updateBook(book.getId(), book);
    }

}