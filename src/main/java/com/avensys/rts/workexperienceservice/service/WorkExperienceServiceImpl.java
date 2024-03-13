package com.avensys.rts.workexperienceservice.service;

import java.io.File;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import com.avensys.rts.workexperienceservice.APIClient.DocumentAPIClient;
import com.avensys.rts.workexperienceservice.payloadnewrequest.DocumentRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewrequest.WorkExperienceListRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewresponse.DocumentResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avensys.rts.workexperienceservice.APIClient.FormSubmissionAPIClient;
import com.avensys.rts.workexperienceservice.APIClient.UserAPIClient;
import com.avensys.rts.workexperienceservice.customresponse.HttpResponse;
import com.avensys.rts.workexperienceservice.entity.WorkExperienceEntity;
import com.avensys.rts.workexperienceservice.entity.UserEntity;
import com.avensys.rts.workexperienceservice.payloadnewrequest.WorkExperienceRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewrequest.FormSubmissionsRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewresponse.WorkExperienceResponseDTO;
import com.avensys.rts.workexperienceservice.payloadnewresponse.FormSubmissionsResponseDTO;
import com.avensys.rts.workexperienceservice.repository.WorkExperienceRepository;
import com.avensys.rts.workexperienceservice.repository.UserRepository;
import com.avensys.rts.workexperienceservice.util.MappingUtil;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

	private final String CANDIDATE_WORKEXPERIENCE_TYPE = "candidate_workexperience";

	private final Logger log = LoggerFactory.getLogger(WorkExperienceServiceImpl.class);
	private final WorkExperienceRepository workExperienceRepository;

	@Autowired
	private DocumentAPIClient documentAPIClient;

	@Autowired
	private UserAPIClient userAPIClient;

	@Autowired
	private FormSubmissionAPIClient formSubmissionAPIClient;

	@Autowired
	private UserRepository userRepository;

	public WorkExperienceServiceImpl(WorkExperienceRepository workExperienceRepository, UserAPIClient userAPIClient,
			FormSubmissionAPIClient formSubmissionAPIClient) {
		this.workExperienceRepository = workExperienceRepository;
		this.userAPIClient = userAPIClient;
		this.formSubmissionAPIClient = formSubmissionAPIClient;
	}

	@Override
//	@Transactional
	public WorkExperienceResponseDTO createWorkExperience(WorkExperienceRequestDTO workExperienceRequestDTO) {
		WorkExperienceEntity savedWorkExperienceEntity = workExperienceRequestDTOToWorkExperienceEntity(workExperienceRequestDTO);
		// Save documents to document microservice
		if (workExperienceRequestDTO.getMultiFiles() != null) {
			for (MultipartFile file : workExperienceRequestDTO.getMultiFiles()) {
				addDocuments(file, savedWorkExperienceEntity.getId(), workExperienceRequestDTO.getEntityType());
			}
		}
		// Save form data to form submission microservice
		FormSubmissionsRequestDTO formSubmissionsRequestDTO = new FormSubmissionsRequestDTO();
		formSubmissionsRequestDTO.setUserId(workExperienceRequestDTO.getCreatedBy());
		formSubmissionsRequestDTO.setFormId(workExperienceRequestDTO.getFormId());
		formSubmissionsRequestDTO
				.setSubmissionData(MappingUtil.convertJSONStringToJsonNode(workExperienceRequestDTO.getFormData()));
		formSubmissionsRequestDTO.setEntityId(savedWorkExperienceEntity.getId());
		formSubmissionsRequestDTO.setEntityType(workExperienceRequestDTO.getEntityType());
		HttpResponse formSubmissionResponse = formSubmissionAPIClient.addFormSubmission(formSubmissionsRequestDTO);
		FormSubmissionsResponseDTO formSubmissionData = MappingUtil
				.mapClientBodyToClass(formSubmissionResponse.getData(), FormSubmissionsResponseDTO.class);

		savedWorkExperienceEntity.setFormSubmissionId(formSubmissionData.getId());
		workExperienceRepository.save(savedWorkExperienceEntity);
		return workExperienceEntityToWorkExperienceResponseDTO(savedWorkExperienceEntity);
	}

	@Override
	public void createWorkExperienceList(WorkExperienceListRequestDTO workExperienceListRequestDTO) {
		workExperienceListRequestDTO.getWorkExperienceList().forEach(this::createWorkExperience);
	}

	@Override
	public WorkExperienceResponseDTO getWorkExperienceById(Integer id) {
		WorkExperienceEntity workExperienceFound = workExperienceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Work Experience is  not found"));
		return workExperienceEntityToWorkExperienceResponseDTO(workExperienceFound);
	}

	@Override
	@Transactional
	public WorkExperienceResponseDTO updateWorkExperience(Integer id,
			WorkExperienceRequestDTO workExperienceRequestDTO) {
		WorkExperienceEntity workExperienceFound = workExperienceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Work Experience is not found"));
		WorkExperienceEntity updatedWorkExperienceEntity = updateWorkExperienceRequestDTOToWorkExperienceEntity(
				workExperienceFound, workExperienceRequestDTO);

		// Save documents to document microservice
		if (workExperienceRequestDTO.getMultiFiles() != null) {
			for (MultipartFile file : workExperienceRequestDTO.getMultiFiles()) {
				addDocuments(file, updatedWorkExperienceEntity.getId(), workExperienceRequestDTO.getEntityType());
			}
		}

		// Update form submission
		FormSubmissionsRequestDTO formSubmissionsRequestDTO = new FormSubmissionsRequestDTO();
		formSubmissionsRequestDTO.setUserId(workExperienceRequestDTO.getUpdatedBy());
		formSubmissionsRequestDTO.setFormId(workExperienceRequestDTO.getFormId());
		formSubmissionsRequestDTO
				.setSubmissionData(MappingUtil.convertJSONStringToJsonNode(workExperienceRequestDTO.getFormData()));
		formSubmissionsRequestDTO.setEntityId(updatedWorkExperienceEntity.getId());
		formSubmissionsRequestDTO.setEntityType(workExperienceRequestDTO.getEntityType());
		HttpResponse formSubmissionResponse = formSubmissionAPIClient
				.updateFormSubmission(updatedWorkExperienceEntity.getFormSubmissionId(), formSubmissionsRequestDTO);
		FormSubmissionsResponseDTO formSubmissionData = MappingUtil
				.mapClientBodyToClass(formSubmissionResponse.getData(), FormSubmissionsResponseDTO.class);

		updatedWorkExperienceEntity.setFormSubmissionId(formSubmissionData.getId());
		return workExperienceEntityToWorkExperienceResponseDTO(updatedWorkExperienceEntity);
	}

	@Override
	@Transactional
	public void deleteWorkExperience(Integer id) {
		WorkExperienceEntity workExperienceEntityFound = workExperienceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Work Experience is not found"));
		workExperienceRepository.delete(workExperienceEntityFound);
	}

	@Override
	public List<WorkExperienceResponseDTO> getWorkExperienceByEntityTypeAndEntityId(String entityType,
			Integer entityId) {
		List<WorkExperienceEntity> workExperienceEntityList = workExperienceRepository
				.findByEntityTypeAndEntityId(entityType, entityId);
		List<WorkExperienceResponseDTO> workExperienceResponseDTOList = workExperienceEntityList.stream()
				.map(this::workExperienceEntityToWorkExperienceResponseDTO).toList();
		return workExperienceResponseDTOList;
	}

	@Override
	@Transactional
	public void deleteWorkExperienceByEntityTypeAndEntityId(String entityType, Integer entityId) {
		List<WorkExperienceEntity> workExperienceEntityList = workExperienceRepository
				.findByEntityTypeAndEntityId(entityType, entityId);
		if (!workExperienceEntityList.isEmpty()) {
			// Delete each Work Experience form submission before deleting
			workExperienceEntityList.forEach(workExperienceEntity -> {
				formSubmissionAPIClient.deleteFormSubmission(workExperienceEntity.getFormSubmissionId());
				workExperienceRepository.delete(workExperienceEntity);
			});
		}
	}

	private WorkExperienceResponseDTO workExperienceEntityToWorkExperienceResponseDTO(
			WorkExperienceEntity workExperienceEntity) {
		WorkExperienceResponseDTO workExperienceResponseDTO = new WorkExperienceResponseDTO();
		workExperienceResponseDTO.setId(workExperienceEntity.getId());
		workExperienceResponseDTO.setCreatedAt(workExperienceEntity.getCreatedAt());
		workExperienceResponseDTO.setUpdatedAt(workExperienceEntity.getUpdatedAt());
		workExperienceResponseDTO.setEntityType(workExperienceEntity.getEntityType());
		workExperienceResponseDTO.setEntityId(workExperienceEntity.getEntityId());
		workExperienceResponseDTO.setFormId(workExperienceEntity.getFormId());
		workExperienceResponseDTO.setFormSubmissionId(workExperienceEntity.getFormSubmissionId());

		// Get created by User data from user microservice
		Optional<UserEntity> userEntity = userRepository.findById(workExperienceEntity.getCreatedBy());
		UserEntity userData = userEntity.get();
		workExperienceResponseDTO.setCreatedBy(userData.getFirstName() + " " + userData.getLastName());

		// Get updated by user data from user microservice
		if (workExperienceEntity.getUpdatedBy() == workExperienceEntity.getCreatedBy()) {
			workExperienceResponseDTO.setUpdatedBy(userData.getFirstName() + " " + userData.getLastName());
		} else {
			userEntity = userRepository.findById(workExperienceEntity.getUpdatedBy());
			userData = userEntity.get();
			workExperienceResponseDTO.setUpdatedBy(userData.getFirstName() + " " + userData.getLastName());
		}

		// Get form submission data
		HttpResponse formSubmissionResponse = formSubmissionAPIClient
				.getFormSubmission(workExperienceEntity.getFormSubmissionId());
		FormSubmissionsResponseDTO formSubmissionData = MappingUtil
				.mapClientBodyToClass(formSubmissionResponse.getData(), FormSubmissionsResponseDTO.class);
		workExperienceResponseDTO
				.setSubmissionData(MappingUtil.convertJsonNodeToJSONString(formSubmissionData.getSubmissionData()));
		return workExperienceResponseDTO;
	}

	private WorkExperienceEntity updateWorkExperienceRequestDTOToWorkExperienceEntity(
			WorkExperienceEntity workExperienceEntity, WorkExperienceRequestDTO workExperienceRequestDTO) {
		workExperienceEntity.setEntityType(workExperienceRequestDTO.getEntityType());
		workExperienceEntity.setEntityId(workExperienceRequestDTO.getEntityId());
		workExperienceEntity.setUpdatedBy(workExperienceRequestDTO.getUpdatedBy());
		workExperienceEntity.setFormId(workExperienceRequestDTO.getFormId());
		return workExperienceRepository.save(workExperienceEntity);
	}

	private WorkExperienceEntity workExperienceRequestDTOToWorkExperienceEntity(
			WorkExperienceRequestDTO workExperienceRequestDTO) {
		WorkExperienceEntity workExperienceEntity = new WorkExperienceEntity();
		workExperienceEntity.setEntityType(workExperienceRequestDTO.getEntityType());
		workExperienceEntity.setEntityId(workExperienceRequestDTO.getEntityId());
		workExperienceEntity.setCreatedBy(workExperienceRequestDTO.getCreatedBy());
		workExperienceEntity.setUpdatedBy(workExperienceRequestDTO.getUpdatedBy());
		workExperienceEntity.setFormId(workExperienceRequestDTO.getFormId());
		return workExperienceRepository.save(workExperienceEntity);
	}

	private void addDocuments(MultipartFile file, Integer entityId, String entityType) {
		DocumentRequestDTO documentRequestDTO = new DocumentRequestDTO();
		// Save document and tag to account entity
		documentRequestDTO.setEntityId(entityId);
		documentRequestDTO.setEntityType(entityType);
		documentRequestDTO.setTitle(file.getOriginalFilename());
		documentRequestDTO.setFile(file);
		HttpResponse documentResponse = documentAPIClient.createDocument(documentRequestDTO);
		DocumentResponseDTO documentData = MappingUtil.mapClientBodyToClass(documentResponse.getData(), DocumentResponseDTO.class);
	}

}
