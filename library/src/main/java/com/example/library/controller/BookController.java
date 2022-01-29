package com.example.library.controller;

import com.example.library.model.BookEntity;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.rmi.ServerException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/list")
    public List<BookEntity> getListOfBooks() {
        List<BookEntity> books = bookRepository.findAll();
        return books;
    }

    @PostMapping(
            value="/create",
            consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookEntity> create(
            @RequestBody BookEntity book
    ) {
        BookEntity persistedBook = bookRepository.save(book);
        if (persistedBook == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(persistedBook.getIsbn())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(persistedBook);
        }
    }

    @GetMapping("/{id}")
    public Optional<BookEntity> read(@PathVariable int id) {
        return bookRepository.findById(id);
    }
}
