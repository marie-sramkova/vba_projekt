package com.example.library.repositories;

import com.example.library.entities.AppuserEntity;
import com.example.library.entities.OwnershipEntity;
import org.springframework.data.repository.CrudRepository;

public interface OwnershipRepository extends CrudRepository<OwnershipEntity, Integer> {

}
