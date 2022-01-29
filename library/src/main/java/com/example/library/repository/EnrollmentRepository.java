package com.example.library.repository;

import com.example.library.model.EnrollmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CrudRepository<EnrollmentEntity, Integer> {

}
