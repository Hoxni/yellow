package com.example.yellow.repository;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.model.WeekStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoggingRepository extends PagingAndSortingRepository<JoggingEntity, Long> {

    Page<JoggingEntity> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT new com.example.yellow.model.WeekStatistics( " +
            "MIN(je.createdAt), MAX(je.createdAt), CAST(SUM(je.distance) AS double) / SUM(je.duration), AVG(CAST(je.duration AS double)), SUM(je.distance)) " +
            "FROM JoggingEntity je " +
            "WHERE je.userId=:id " +
            "GROUP BY date_part('week', je.createdAt)")
    Page<WeekStatistics> getWeekStatisticsByUserId(Long id, Pageable pageable);

    Optional<JoggingEntity> findByIdAndUserId(Long id, Long userId);
}
