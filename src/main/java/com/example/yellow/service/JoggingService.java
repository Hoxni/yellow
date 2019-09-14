package com.example.yellow.service;

import com.example.yellow.entity.JoggingEntity;
import com.example.yellow.enumeration.Role;
import com.example.yellow.exception.JoggingNotFoundException;
import com.example.yellow.model.JoggingModel;
import com.example.yellow.model.WeekStatistics;
import com.example.yellow.repository.JoggingRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JoggingService {

    @Autowired
    private JoggingRepository joggingRepository;

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

    public void updateJogging(JoggingModel jogging) {

        JoggingEntity joggingEntity = joggingRepository.findById(jogging.getId())
                .orElseThrow(()->new JoggingNotFoundException(jogging.getId()));

        joggingRepository.save(
                JoggingConverter.updateEntity(joggingEntity, jogging));
    }

    public Page<WeekStatistics> getWeekStatistics(Long userId, int page, int size){

        Pageable pageable = PageRequest.of(page - 1, size);

        return joggingRepository.getWeekStatisticsByUserId(userId, pageable);
    }

    public boolean isOwner(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        return claims.get("id").toString().equals(userId.toString());
    }

    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.name()));
    }

    public boolean hasJogging(Long joggingId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(((Claims)authentication.getPrincipal()).get("id").toString());
        return joggingRepository.findByIdAndUserId(joggingId, userId).isPresent();
    }

}
