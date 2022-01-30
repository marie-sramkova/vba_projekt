package com.example.library.repository;

import com.example.library.entity.AppuserEntity;
import com.example.library.entity.BookEntity;
import com.example.library.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends CrudRepository<EnrollmentEntity, Integer> {

    @Query("select e from EnrollmentEntity e where e.appuser.name = :userName and e.book.isbn = :bookISBN")
    EnrollmentEntity findByAppuserNameAndBookISBN(@Param("userName") String userName, @Param("bookISBN") int bookISBN);

    @Query("select e from EnrollmentEntity e where e.appuser.name = :userName")
    List<EnrollmentEntity> findByAppuserName(@Param("userName") String userName);

}
