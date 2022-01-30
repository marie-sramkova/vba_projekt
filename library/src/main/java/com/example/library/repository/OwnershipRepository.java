package com.example.library.repository;

import com.example.library.entity.AuthorEntity;
import com.example.library.entity.OwnershipEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnershipRepository extends CrudRepository<OwnershipEntity, Integer> {

    @Query("select o.author.authorId from OwnershipEntity o where o.book.isbn = :bookISBN")
    List<Integer> findAuthorIdsByBookISBN(@Param("bookISBN") int isbn);
}
