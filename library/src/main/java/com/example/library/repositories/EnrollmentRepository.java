package com.example.library.repositories;

import com.example.library.entities.EnrollmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository<EnrollmentEntity, Integer> {

}
