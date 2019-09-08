package com.example.yellow.repository;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.model.WeekStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Repository
public interface JoggingRepository extends PagingAndSortingRepository<JoggingEntity, Long> {

    Page<JoggingEntity> findAllByUserId(Long userId, Pageable pageable);

    /*@SqlResultSetMapping(
            name = "WeekStatisticsMapping",
            classes = @ConstructorResult(
                    targetClass = WeekStatistics.class,
                    columns = {
                            @ColumnResult(name = "id", type = Long.class),
                            @ColumnResult(name = "title"),
                            @ColumnResult(name = "version", type = Long.class),
                            @ColumnResult(name = "authorName")}))
    @Query(value =
            "select jog.date_time as startDate, jog.date_time as endDate, jog.distance as totalDistance, jog.distance as averageTime, jog.distance as averageSpeed " +
            "from JOGGINGS jog " +
            "where jog.user_id = ?1 ", nativeQuery = true)*/

    @Query("SELECT new com.example.yellow.model.WeekStatistics(je.dateTime, je.dateTime, je.distance, je.distance, je.distance) FROM JoggingEntity je WHERE je.userId=?1")
    Page<WeekStatistics> getStatistics(Long userId, Pageable pageable);


}
