package com.example.library.repositories;

import com.example.library.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {

    @Override
    List<BookEntity> findAll();
}
