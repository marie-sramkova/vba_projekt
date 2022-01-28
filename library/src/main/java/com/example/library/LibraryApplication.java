package com.example.library;

import com.example.library.entities.BookEntity;
import com.example.library.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    private static final Logger log = LoggerFactory.getLogger(LibraryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            AppuserRepository appuserRepository, AuthorRepository authorRepository,
            BookRepository bookRepository, EnrollmentRepository enrollmentRepository,
            OwnershipRepository ownershipRepository) {
        return (args) -> {

            BookEntity book = new BookEntity();
            book.setIsbn(1);
            book.setName("Kniha 1");
            book.setBinding("pevná");
            book.setContent("Obsah první knihy");
            book.setGenre("Thriller");
            book.setImageUrl("url první knihy");
            book.setNumberOfPages(100);

            bookRepository.save(book);

            List<BookEntity> books = bookRepository.findAll();

            log.info("Pocet najdenych " + books.size()) ;
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (BookEntity book1 : books) {
                System.out.println(book1);
                log.info(book1.toString());
            }

//            RestTbl table1 = new RestTbl("Stul 1");
//            restTblRepository.save(table1);
//
//            coffeeRepository.save(new Coffee("Late", table1));
//            coffeeRepository.save(new Coffee("Turek", table1));
//            coffeeRepository.save(new Coffee("Moca", table1));
//
//            List<Coffee> coffees = coffeeRepository.findByName("Moca");
//            log.info("Pocet najdenych " + coffees.size()) ;
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (Coffee coffee : coffeeRepository.findAll()) {
//                log.info(coffee.toString());
//            }

        };
    }

}
