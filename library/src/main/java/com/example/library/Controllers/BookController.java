package com.example.library.Controllers;

import com.example.library.entities.BookEntity;
import com.example.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/list")
    public String getListOfBooks(Model model) {
        List<BookEntity> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return Arrays.toString(books.toArray());
    }
}
