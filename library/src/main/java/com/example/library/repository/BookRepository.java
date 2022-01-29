package com.example.library.repository;

import com.example.library.model.BookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {

    @Override
    List<BookEntity> findAll();
}
