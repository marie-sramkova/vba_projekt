package com.example.library.controller;

import com.example.library.model.BookAndAuthor;
import com.example.library.entity.*;
import com.example.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    //region getAllBooks
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<BookEntity> getListOfBooks() {
        List<BookEntity> books = bookRepository.findAll();
        if (books == null || books.isEmpty()) {
            return null;
        } else {
            return books;
        }
    }
    //endregion

    //region getAllBooksForUser
    @RequestMapping(value = "/user/books", method = RequestMethod.GET)
    public List<BookEntity> getListOfBooksForUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()) {
            List<EnrollmentEntity> enrollments = enrollmentRepository.findByAppuserName(username);
            if (!enrollments.isEmpty()) {
                List<BookEntity> books = new ArrayList<>();
                for (EnrollmentEntity enrollment : enrollments) {
                    BookEntity book = bookRepository.findById(enrollment.getBook().getIsbn()).stream().findFirst().orElse(null);
                    if (book != null) {
                        books.add(book);
                    }
                }
                return books;
            }
        }
        return null;
    }
    //endregion

    //region enrollBook
    @RequestMapping(value = "/enrollBook/{bookISBN}",
            method = RequestMethod.POST)
    public ResponseEntity<BookEntity> enroll(@PathVariable Integer bookISBN) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()) {
            Optional<BookEntity> persistedBook = bookRepository.findById(bookISBN);
            if (persistedBook.isPresent()) {
                EnrollmentEntity enrollment = new EnrollmentEntity(appuser.stream().findFirst().orElse(null), persistedBook.stream().findFirst().orElse(null));
                EnrollmentEntity enr = enrollmentRepository.findByAppuserNameAndBookISBN(appuser.stream().findFirst().orElse(null).getName(), persistedBook.stream().findFirst().orElse(null).getIsbn());
                if (enr == null) {
                    EnrollmentEntity persistedEnrollment = enrollmentRepository.save(enrollment);
                    if (persistedEnrollment == null) {
                        return ResponseEntity.notFound().build();
                    } else {
                        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/book/{bookISBN}")
                                .buildAndExpand(persistedBook.stream().findFirst().orElse(null).getIsbn())
                                .toUri();

                        return ResponseEntity.created(uri)
                                .body(persistedBook.stream().findFirst().orElse(null));
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body(null);
    }
    //endregion

    //region create
    @RequestMapping(value = "/createBook",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookEntity> create(@RequestBody BookEntity book) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()) {
            if (book != null && !bookRepository.findById(book.getIsbn()).isPresent()){
                setNewAuthorsFromDBOrCreateAndSet(book);
                BookEntity persistedBook = bookRepository.save(book);
                if (persistedBook == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    return ResponseEntity.ok(book);
                }
            }
        }
        return ResponseEntity.badRequest().body(book);
    }
    //endregion

    //region writeOffBook
    @RequestMapping(value = "/writeOffBook/{bookISBN}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> remove(@PathVariable Integer bookISBN) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()) {
            Optional<BookEntity> persistedBook = bookRepository.findById(bookISBN);
            if (persistedBook.isPresent()) {
                EnrollmentEntity enr = enrollmentRepository.findByAppuserNameAndBookISBN(appuser.stream().findFirst().orElse(null).getName(), persistedBook.stream().findFirst().orElse(null).getIsbn());
                if (enr != null) {
                    enrollmentRepository.delete(enr);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body("Book with isbn " + bookISBN + " successfully wrote off.");
                }
            }
        }
        return ResponseEntity.badRequest()
                .body("Failed to write off book with isbn " + bookISBN);
    }
    //endregion

    //region updateBook
    @RequestMapping(value = "/updateBook",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookEntity> update(@RequestBody BookEntity book) {
        if (book == null)
            return ResponseEntity.badRequest().body(book);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()) {
            if (book != null && bookRepository.findById(book.getIsbn()).isPresent()) {
                setNewAuthorsFromDBOrCreateAndSet(book);
                BookEntity persistedBook = bookRepository.save(book);
                if (persistedBook == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    return ResponseEntity.ok(book);
                }
            }
        }
        return ResponseEntity.badRequest().body(book);
    }

    private void setNewAuthorsFromDBOrCreateAndSet(@RequestBody BookEntity book) {
        List<AuthorEntity> newAuthors = new ArrayList<>();
        for (AuthorEntity author : book.getAuthors()) {
            if (authorRepository.findByNameSurnameBirthDay(
                    author.getFirstName(),
                    author.getSurname(),
                    author.getBirthDay()) == null) {
                AuthorEntity persistedAuthor = authorRepository.save(author);
                newAuthors.add(persistedAuthor);
            } else {
                AuthorEntity authorFromDB = authorRepository.findByNameSurnameBirthDay(
                        author.getFirstName(),
                        author.getSurname(),
                        author.getBirthDay());
                newAuthors.add(authorFromDB);
            }
        }
        book.setAuthors(newAuthors);
    }
    //endregion

    @GetMapping("/book/{bookISBN}")
    public ResponseEntity<Optional<BookEntity>> getBook(@PathVariable int bookISBN) {
        if (bookRepository.findById(bookISBN).isPresent()) {
            return ResponseEntity.ok(bookRepository.findById(bookISBN));
        }
        return ResponseEntity.badRequest().body(null);
    }
}
