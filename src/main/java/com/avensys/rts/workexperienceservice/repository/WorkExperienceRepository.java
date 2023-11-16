package com.avensys.rts.workexperienceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avensys.rts.workexperienceservice.entity.WorkExperienceEntity;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperienceEntity, Integer> {
    List<WorkExperienceEntity> findByEntityTypeAndEntityId(String entityType, Integer entityId);
}
