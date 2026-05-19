package org.example.iocexam.book.controller;

import org.example.iocexam.book.entity.Book;
import org.example.iocexam.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// RestController = 순수 데이터(Data)를 HTTP 응답 본문(Body)에 직접 반환
// RequestMapping = 클라이언트(사용자)의 요청(URL)을 특정 컨트롤러나 메서드에 연결(매핑)해 주는 어노테이션
@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    // {id} 값을 자바 변수 int id로 매핑하려면 파라미터 앞에 @PathVariable 어노테이션을 반드시 붙여야 합니다.
    public Book findById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @PostMapping("/books")
    public void save(@RequestBody Book book) {
        bookService.save(book);
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable int id) {
        bookService.deleteById(id);
    }

}
