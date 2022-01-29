package com.example.library.repository;

import com.example.library.entity.AppuserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppuserRepository extends CrudRepository<AppuserEntity, String> {

    @Override
    Optional<AppuserEntity> findById(String string);
}
