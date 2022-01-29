package com.example.library.repository;

import com.example.library.model.AppuserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppuserRepository extends CrudRepository<AppuserEntity, Integer> {

}
