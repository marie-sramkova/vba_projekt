package com.example.library.model;

import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import lombok.Data;

@Data
public class BookAndAuthor {
    public BookEntity book;
    public AuthorEntity author;

    public BookAndAuthor() {
    }

    public BookAndAuthor(BookEntity book, AuthorEntity author) {
        this.book = book;
        this.author = author;
    }

    public BookEntity getBook() {
        return book;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "BookAndAuthor{" +
                "book=" + book +
                ", author=" + author +
                '}';
    }
}
