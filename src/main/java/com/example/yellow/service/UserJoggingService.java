package com.example.yellow.service;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.entity.UserEntity;
import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.repository.JoggingRepository;
import com.example.yellow.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserJoggingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoggingRepository joggingRepository;

    public void createJogging(String username, JoggingModel jogging) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.getJoggings().add(
                joggingRepository.save(convertToJoggingEntity(jogging, user))
        );
        userRepository.save(user);

    }

    public void deleteJogging(String username, Long joggingId) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        Optional<JoggingEntity> jogging = joggingRepository.findById(joggingId);
        boolean hasJogging = false;
        for (JoggingEntity j: user.getJoggings()){
            if (j.getId().equals(joggingId)){
                hasJogging = true;
                break;
            }
        }
        if (!hasJogging) throw new EntityExistsException("User has no jogging with id: " + joggingId);
        if (jogging.isPresent()){
            user.getJoggings().remove(jogging.get());
            userRepository.save(user);
            //joggingRepository.deleteById(joggingId);
        }
    }

    public Iterable<JoggingModel> getUserJoggings(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //System.out.println(user.getJoggings().size());
        log.debug("Number of user's joggings: " + user.getJoggings().size());

        return createJoggingModelList(user.getJoggings());

    }

    public void updateUserJogging(String username, JoggingModel jogging) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        boolean hasJogging = false;
        for (JoggingEntity j: user.getJoggings()){
            if (j.getId().equals(jogging.getId())){
                hasJogging = true;
                break;
            }
        }
        if (!hasJogging) throw new NoSuchElementException("User has no jogging with id: " + jogging.getId());

        JoggingEntity joggingEntity = joggingRepository.findById(jogging.getId())
                .orElseThrow(EntityExistsException::new);

        joggingEntity.setDistance(jogging.getDistance());
        joggingEntity.setTime(jogging.getTime());
        joggingEntity.setDateTime(jogging.getDateTime());

        joggingRepository.save(joggingEntity);
    }

    public Iterable<WeekStatistics> getWeekStatistics(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<JoggingEntity> joggings = user.getJoggings();

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
    }

    private JoggingEntity convertToJoggingEntity(JoggingModel joggingModel, UserEntity user){
        return JoggingEntity.builder()
                .id(joggingModel.getId())
                .distance(joggingModel.getDistance())
                .time(joggingModel.getTime())
                .dateTime(joggingModel.getDateTime())
                .user(user)
                .build();
    }

    private JoggingModel convertToJoggingModel(JoggingEntity joggingEntity){
        return JoggingModel.builder()
                .id(joggingEntity.getId())
                .distance(joggingEntity.getDistance())
                .time(joggingEntity.getTime())
                .dateTime(joggingEntity.getDateTime())
                .userId(joggingEntity.getUser().getId())
                .build();
    }

    private Iterable<JoggingModel> createJoggingModelList(Collection<JoggingEntity> joggings) {
        return joggings.stream()
                .map(this::convertToJoggingModel)
                .collect(Collectors.toList());
    }

    public Iterable<JoggingModel> getAll(){
        ArrayList<JoggingEntity> list = new ArrayList<>();
        joggingRepository.findAll().forEach(list::add);
        return createJoggingModelList(list);
    }
}
