package com.example.library.repository;

import com.example.library.entity.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Integer> {

    @Query("select a from AuthorEntity a where a.firstName = :name and a.surname = :surname and a.birthDay = :birthDay")
    AuthorEntity findByNameSurnameBirthDay(@Param("name") String firstName, @Param("surname") String surname, @Param("birthDay") Date birthDay);
}
