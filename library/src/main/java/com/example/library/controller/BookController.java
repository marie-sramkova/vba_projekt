package com.example.library.controller;

import com.example.library.dto.BookAndAuthor;
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
    private OwnershipRepository ownershipRepository;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @RequestMapping(value = "/user/books", method = RequestMethod.GET)
    public List<BookEntity> getListOfBooks() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()) {
            List<EnrollmentEntity> enrollments = enrollmentRepository.findByAppuserName(username);
            if (!enrollments.isEmpty()){
                List<BookEntity> books = new ArrayList<>();
                for (EnrollmentEntity enrollment:enrollments) {
                    BookEntity book = bookRepository.findById(enrollment.getBook().getIsbn()).stream().findFirst().orElse(null);
                    if (book != null){
                        books.add(book);
                    }
                }
                return books;
            }
        }
        return null;
    }

    //region enrollBook
    @RequestMapping(value="/enrollBook",
            method = RequestMethod.POST,
            consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookEntity> create(@RequestBody BookEntity book) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()){
            Optional<BookEntity> persistedBook = bookRepository.findById(book.getIsbn());
            if (persistedBook.isPresent()){
                EnrollmentEntity enrollment = new EnrollmentEntity(appuser.stream().findFirst().orElse(null), persistedBook.stream().findFirst().orElse(null));
                EnrollmentEntity enr = enrollmentRepository.findByAppuserNameAndBookISBN(appuser.stream().findFirst().orElse(null).getName(), persistedBook.stream().findFirst().orElse(null).getIsbn());
                if (enr == null){
                    EnrollmentEntity persistedEnrollment = enrollmentRepository.save(enrollment);
                    if (persistedEnrollment == null) {
                        return ResponseEntity.notFound().build();
                    } else {
                        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(persistedBook.stream().findFirst().orElse(null).getIsbn())
                                .toUri();

                        return ResponseEntity.created(uri)
                                .body(persistedBook.stream().findFirst().orElse(null));
                    }
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
    //endregion

    //region post
    @RequestMapping(value="/createBook",
            method = RequestMethod.POST,
            consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookAndAuthor> create(@RequestBody BookAndAuthor bookAndAuthor) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()){
            if (!bookRepository.findById(bookAndAuthor.getBook().getIsbn()).isPresent() &&
                authorRepository.findByNameSurnameBirthDay(
                    bookAndAuthor.getAuthor().getFirstName(),
                    bookAndAuthor.getAuthor().getSurname(),
                    bookAndAuthor.getAuthor().getBirthDay()) == null){
                BookEntity persistedBook = bookRepository.save(bookAndAuthor.getBook());
                AuthorEntity persistedAuthor = authorRepository.save(bookAndAuthor.getAuthor());
                OwnershipEntity ownershipEntity = new OwnershipEntity(persistedBook, persistedAuthor);
                OwnershipEntity persistedOwnership = ownershipRepository.save(ownershipEntity);
                EnrollmentEntity enrollment = new EnrollmentEntity(appuser.stream().findFirst().orElse(null), persistedBook);
                EnrollmentEntity enr = enrollmentRepository.findByAppuserNameAndBookISBN(appuser.stream().findFirst().orElse(null).getName(), persistedBook.getIsbn());
                if (enr == null){
                    EnrollmentEntity persistedEnrollment = enrollmentRepository.save(enrollment);
                }
                if (persistedBook == null || persistedAuthor == null || persistedOwnership == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(persistedBook.getIsbn())
                            .toUri();

                    return ResponseEntity.created(uri)
                            .body(bookAndAuthor);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
    //endregion

    //region writeOffBook
    @RequestMapping(value="/writeOffBook/{bookISBN}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> remove(@PathVariable Integer bookISBN) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()){
            Optional<BookEntity> persistedBook = bookRepository.findById(bookISBN);
            if (persistedBook.isPresent()){
                EnrollmentEntity enr = enrollmentRepository.findByAppuserNameAndBookISBN(appuser.stream().findFirst().orElse(null).getName(), persistedBook.stream().findFirst().orElse(null).getIsbn());
                if (enr != null){
                    enrollmentRepository.delete(enr);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body("Book with isbn " + bookISBN + " successfully removed.");
                    }
                }
            }
        return ResponseEntity.badRequest()
                .body("Failed to unenroll book with isbn " + bookISBN);
    }
    //endregion

    @GetMapping("/{id}")
    public Optional<BookEntity> read(@PathVariable int id) {
        return bookRepository.findById(id);
    }



}
