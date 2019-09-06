package com.example.yellow.repository;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoggingRepository extends PagingAndSortingRepository<JoggingEntity, Long> {


    List<JoggingEntity> findAllByUserId(Long userId, Pageable pageable);
}
