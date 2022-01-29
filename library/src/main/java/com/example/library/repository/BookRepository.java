package com.example.library.repository;

import com.example.library.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Integer> {

    @Override
    List<BookEntity> findAll();

    @Override
    Optional<BookEntity> findById(Integer integer);
}
