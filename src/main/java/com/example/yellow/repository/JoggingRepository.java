package com.example.yellow.repository;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.model.WeekStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoggingRepository extends PagingAndSortingRepository<JoggingEntity, Long> {

    Page<JoggingEntity> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT new com.example.yellow.model.WeekStatistics( " +
            "MIN(je.dateTime), MAX(je.dateTime), CAST(SUM(je.distance) AS double) / SUM(je.time), AVG(CAST(je.time AS double)), SUM(je.distance)) " +
            "FROM JoggingEntity je " +
            "WHERE je.userId=:id " +
            "GROUP BY WEEK(je.dateTime)")
    Page<WeekStatistics> getWeekStatisticsByUserId(Long id, Pageable pageable);
}
