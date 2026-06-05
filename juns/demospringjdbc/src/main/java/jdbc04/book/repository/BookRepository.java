package jdbc04.book.repository;

import jdbc04.book.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findBookByTitle(String title);
    Book findBookById(Long id);
    Book findBookByIsbn(String isbn);
    Book findBookByAuthor(String author);
}
