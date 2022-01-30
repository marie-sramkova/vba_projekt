package com.example.library.dto;

import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import lombok.Data;

@Data
public class BookAndAuthor {
    public BookEntity book;
    public AuthorEntity author;

    public BookEntity getBook() {
        return book;
    }

    public AuthorEntity getAuthor() {
        return author;
    }
}
