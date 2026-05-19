package org.example.iocexam.book.entity;

public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String category;
    private String isbn;

    public Book() {};

    public Book(int id, String title, String author, String publisher, String description, String category, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.category = category;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
