package com.example.yellow.service;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.entity.UserEntity;
import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.UserModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.repository.JoggingRepository;
import com.example.yellow.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UserJoggingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoggingRepository joggingRepository;

    public void createJogging(Long userId, JoggingModel jogging) {

        JoggingEntity joggingEntity = joggingRepository.save(
                JoggingUtils.convertModelToEntity(jogging, userId));
        //user.getJoggings().put(joggingEntity.getId(), joggingEntity);

        //userRepository.save(user);

    }

    public JoggingModel getJogging(Long id){
        JoggingEntity joggingEntity = joggingRepository.findById(id)
                .orElseThrow(()-> new EntityExistsException("No jogging with id: " + id));
        return JoggingUtils.convertEntityToModel(joggingEntity);
    }

    public void deleteJogging(Long joggingId) {

        /*UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        JoggingEntity jogging = joggingRepository.findById(joggingId)
                .orElseThrow(()-> new EntityExistsException("No jogging with id: " + joggingId));

        if (!user.getJoggings().containsKey(joggingId))
            throw new EntityExistsException("User has no jogging with id: " + joggingId);

        log.debug("Number of user's joggings: " + user.getJoggings().size());
        user.getJoggings().remove(jogging.getId(), jogging);
        log.debug("Number of user's joggings after deleting one: " + user.getJoggings().size());*/

        joggingRepository.deleteById(joggingId);

    }

    public Iterable<JoggingModel> getUserJoggings(Long userId, int page, int size) throws UsernameNotFoundException {

        Pageable pageable = PageRequest.of(page, size);

        List<JoggingEntity> joggingEntities;

        if (userId == null){
            joggingEntities = joggingRepository.findAll(pageable).getContent();
        } else {
            joggingEntities = joggingRepository.findAllByUserId(userId, pageable);
        }

        return joggingEntities.stream()
                .map(JoggingUtils::convertEntityToModel)
                .collect(Collectors.toList());
    }

    public void updateJogging(JoggingModel jogging) {

        JoggingEntity joggingEntity = joggingRepository.findById(jogging.getId())
                .orElseThrow(EntityExistsException::new);

        joggingRepository.save(
                JoggingUtils.updateEntity(joggingEntity, jogging));
    }

    public UserModel getUser(Long id){

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        return UserUtils.convertEntityToModel(userEntity);
    }

    /*public Iterable<WeekStatistics> getWeekStatistics(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Collection<JoggingEntity> joggings = user.getJoggings();

        Map<Integer, List<JoggingEntity>> weeks = new HashMap<>();
        List<WeekStatistics> weekStatistics = new ArrayList<>();

        for (JoggingEntity jogging : joggings) {
            int weekNum = jogging.getDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            if (weeks.containsKey(weekNum)) {
                weeks.get(weekNum).add(jogging);
            } else {
                List<JoggingEntity> list = new ArrayList<>();
                list.add(jogging);
                weeks.put(weekNum, list);
            }
        }

        for (List<JoggingEntity> list : weeks.values()) {
            weekStatistics.add(createWeekStatistics(list));
        }

        return weekStatistics;

    }

    private WeekStatistics createWeekStatistics(List<JoggingEntity> list){
        double averageTime = 0;
        double averageSpeed;
        int totalDistance = 0;
        for (JoggingEntity jogging: list){
            averageTime += jogging.getTime();
            totalDistance += jogging.getDistance();
        }

        averageSpeed = totalDistance / averageTime;
        averageTime /= list.size();

        LocalDate startDate = Collections.min(list, Comparator.comparing(JoggingEntity::getDateTime))
                .getDateTime().toLocalDate();
        LocalDate endDate = Collections.max(list, Comparator.comparing(JoggingEntity::getDateTime))
                .getDateTime().toLocalDate();

        return new WeekStatistics(startDate, endDate, averageSpeed, averageTime, totalDistance);
    }*/



    /*public Iterable<JoggingModel> getAllJoggins(){
        Pageable pageable = PageRequest.of(page, size);
        return joggingRepository.findAll();
    }*/

    /*public Iterable<UserModel> getAllUsers(){
        ArrayList<UserEntity> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);
        return list.stream().map(v->UserModel.builder().username(v.getUsername()).password(v.getPassword()).build()).collect(Collectors.toList());
    }*/
}
