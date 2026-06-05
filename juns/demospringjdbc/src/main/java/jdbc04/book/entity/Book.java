package jdbc04.book.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "title")
    private String title;

    @Column(value = "author")
    private String author;

    @Column(value = "isbn")
    private String isbn;

    @Column(value = "publish_year")
    private Integer publishYear;

    @Column(value = "created_at")
    private LocalDateTime createdAt;

    public void updateDetails(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
