package com.example.yellow.service;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.exception.JoggingNotFoundException;
import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.repository.JoggingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JoggingService {

    private final JoggingRepository joggingRepository;

    public JoggingService(JoggingRepository joggingRepository) {
        this.joggingRepository = joggingRepository;
    }

    public void createJogging(Long userId, JoggingModel jogging) {
        joggingRepository.save(JoggingConverter.convertModelToEntity(jogging, userId));
    }

    public JoggingModel getJogging(Long id){
        JoggingEntity joggingEntity = joggingRepository.findById(id)
                .orElseThrow(()->new JoggingNotFoundException(id));
        return JoggingConverter.convertEntityToModel(joggingEntity);
    }

    public void deleteJogging(Long joggingId) {
        joggingRepository.deleteById(joggingId);
    }

    public Page<JoggingModel> getUserJoggings(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<JoggingEntity> joggingEntities = joggingRepository.findAllByUserId(userId, pageable);

        return joggingEntities.map(JoggingConverter::convertEntityToModel);
    }

    public void updateJogging(Long joggingId, JoggingModel jogging) {

        JoggingEntity joggingEntity = joggingRepository.findById(joggingId)
                .orElseThrow(()->new JoggingNotFoundException(joggingId));

        joggingRepository.save(
                JoggingConverter.updateEntity(joggingEntity, jogging));
    }

    public Page<WeekStatistics> getWeekStatistics(Long userId, int page, int size){

        Pageable pageable = PageRequest.of(page - 1, size);

        return joggingRepository.getWeekStatisticsByUserId(userId, pageable);
    }

    public boolean isOwnerOfJogging(Long userId, Long joggingId){
        return joggingRepository.findByIdAndUserId(joggingId, userId).isPresent();
    }
}
