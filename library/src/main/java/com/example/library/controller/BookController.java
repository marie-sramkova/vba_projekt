package com.example.library.controller;

import com.example.library.model.BookEntity;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book/list")
    public String getListOfBooks(Model model) {
        List<BookEntity> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "/book/list";
    }

    @GetMapping("/book/create")
    public String createBook() {
        return "/book/create";
    }

    @PostMapping("/book/create")
    public RedirectView createBook(
            @RequestParam("isbn") int isbn,
            @RequestParam("title") String title,
            @RequestParam("numberOfPages") int numberOfPages,
            @RequestParam("genre") String genre,
            @RequestParam("content") String content,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("binding") String binding
    ) {
        BookEntity book = new BookEntity(isbn, title, numberOfPages, genre, content, imageUrl, binding);
        bookRepository.save(book);
        return new RedirectView("/book/list");
    }
}
