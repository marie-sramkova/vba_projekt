package com.example.library.controller;

import com.example.library.dto.BookAndAuthor;
import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import com.example.library.entity.OwnershipEntity;
import com.example.library.model.AuthRequestModel;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.OwnershipRepository;
import com.example.library.service.JwtService;
import com.example.library.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private OwnershipRepository ownershipRepository;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<BookEntity> getListOfBooks() {
        List<BookEntity> books = bookRepository.findAll();
        return books;
    }

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String hello(){
        return "Hello PXP!";
    }


    @RequestMapping(value="/create",
            method = RequestMethod.POST,
            consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookAndAuthor> create(@RequestBody BookAndAuthor bookAndAuthor) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        BookEntity book = bookAndAuthor.getBook();
        BookEntity persistedBook = bookRepository.save(book);
        AuthorEntity author = bookAndAuthor.getAuthor();
        AuthorEntity persistedAuthor = authorRepository.save(author);
        OwnershipEntity ownershipEntity = new OwnershipEntity(book, author);
        OwnershipEntity persistedOwnership = ownershipRepository.save(ownershipEntity);
        if (persistedBook == null || persistedAuthor == null || persistedOwnership == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(book.getIsbn())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(bookAndAuthor);
        }
    }

    @GetMapping("/{id}")
    public Optional<BookEntity> read(@PathVariable int id) {
        return bookRepository.findById(id);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getJwtAuthToken(@RequestBody AuthRequestModel authRequestModel) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestModel.getUserName(), authRequestModel.getPassword()));
        }catch (BadCredentialsException ex){
            throw new Exception("Incorrect userName and password", ex);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequestModel.getUserName());
        final String jwt = jwtService.getJwt(userDetails);
        return jwt;
    }

}
