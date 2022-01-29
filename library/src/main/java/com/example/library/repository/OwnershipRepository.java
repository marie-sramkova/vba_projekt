package com.example.library.repository;

import com.example.library.model.OwnershipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnershipRepository extends CrudRepository<OwnershipEntity, Integer> {

}
