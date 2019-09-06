package com.example.yellow.repository;

import com.example.yellow.entity.JoggingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoggingRepository extends CrudRepository<JoggingEntity, Long> {
}
