package com.avensys.rts.workexperienceservice.service;

import java.util.List;

import com.avensys.rts.workexperienceservice.payloadnewrequest.WorkExperienceListRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewrequest.WorkExperienceRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewresponse.WorkExperienceResponseDTO;

public interface WorkExperienceService {

    WorkExperienceResponseDTO createWorkExperience(WorkExperienceRequestDTO contactNewRequestDTO);

    void createWorkExperienceList(WorkExperienceListRequestDTO workExperienceListRequestDTO);

    WorkExperienceResponseDTO getWorkExperienceById(Integer id);

    WorkExperienceResponseDTO updateWorkExperience(Integer id, WorkExperienceRequestDTO contactNewRequestDTO);

    void deleteWorkExperience(Integer id);

    List<WorkExperienceResponseDTO> getWorkExperienceByEntityTypeAndEntityId(String entityType, Integer entityId);

    void deleteWorkExperienceByEntityTypeAndEntityId(String entityType, Integer entityId);
}
