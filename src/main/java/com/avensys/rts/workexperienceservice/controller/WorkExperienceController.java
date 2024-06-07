package com.avensys.rts.workexperienceservice.controller;

import com.avensys.rts.workexperienceservice.payloadnewrequest.WorkExperienceListRequestDTO;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.avensys.rts.workexperienceservice.constant.MessageConstants;
import com.avensys.rts.workexperienceservice.payloadnewrequest.WorkExperienceRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewresponse.WorkExperienceResponseDTO;
import com.avensys.rts.workexperienceservice.service.WorkExperienceServiceImpl;
import com.avensys.rts.workexperienceservice.util.JwtUtil;
import com.avensys.rts.workexperienceservice.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/work-experience")
public class WorkExperienceController {
	private final Logger log = LoggerFactory.getLogger(WorkExperienceController.class);
	private final WorkExperienceServiceImpl workExperienceService;
	private final MessageSource messageSource;

	@Autowired
	private JwtUtil jwtUtil;

	public WorkExperienceController(WorkExperienceServiceImpl workExperienceService, MessageSource messageSource) {
		this.workExperienceService = workExperienceService;
		this.messageSource = messageSource;
	}

	@PostMapping("/add")
	public ResponseEntity<Object> createWorkExperience( @ModelAttribute WorkExperienceRequestDTO workExperienceRequestDTO,
			@RequestHeader(name = "Authorization") String token) {
		log.info("Create a workExperience : Controller ");
		Long userId = jwtUtil.getUserId(token);
		workExperienceRequestDTO.setCreatedBy(userId);
		workExperienceRequestDTO.setUpdatedBy(userId);
		WorkExperienceResponseDTO createdWorkExperience = workExperienceService.createWorkExperience(workExperienceRequestDTO);
		return ResponseUtil.generateSuccessResponse(createdWorkExperience, HttpStatus.CREATED,
				messageSource.getMessage(MessageConstants.MESSAGE_CREATED, null, LocaleContextHolder.getLocale()));
	}

	@PostMapping("/add/list")
	public ResponseEntity<Object> createWorkExperienceList(@Valid @ModelAttribute WorkExperienceListRequestDTO workExperienceListRequestDTO,
			@RequestHeader
			(name = "Authorization") String token) {
		log.info("Create a workExperience : Controller ");
		Long userId = jwtUtil.getUserId(token);
		for (WorkExperienceRequestDTO workExperienceRequestDTO : workExperienceListRequestDTO.getWorkExperienceList()) {
			workExperienceRequestDTO.setCreatedBy(userId);
			workExperienceRequestDTO.setUpdatedBy(userId);
		}
		workExperienceService.createWorkExperienceList(workExperienceListRequestDTO);
		return ResponseUtil.generateSuccessResponse(null, HttpStatus.CREATED,
				messageSource.getMessage(MessageConstants.MESSAGE_CREATED, null, LocaleContextHolder.getLocale()));
	}

	@GetMapping("/entity/{entityType}/{entityId}")
	public ResponseEntity<Object> getWorkExperienceByEntityTypeAndEntityId(@PathVariable String entityType,
			@PathVariable Integer entityId) {
		log.info("Get workExperience by entity type and entity id : Controller ");
		return ResponseUtil.generateSuccessResponse(
				workExperienceService.getWorkExperienceByEntityTypeAndEntityId(entityType, entityId), HttpStatus.OK,
				messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteWorkExperience(@PathVariable Integer id) {
		log.info("Delete workExperience : Controller ");
		workExperienceService.deleteWorkExperience(id);
		return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK,
				messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateWorkExperience(@PathVariable Integer id,
			@Valid @ModelAttribute WorkExperienceRequestDTO workExperienceRequestDTO,
			@RequestHeader(name = "Authorization") String token) {
		log.info("Update workExperience : Controller ");
		Long userId = jwtUtil.getUserId(token);
		workExperienceRequestDTO.setUpdatedBy(userId);
		WorkExperienceResponseDTO workExperienceResponseDTO = workExperienceService.updateWorkExperience(id, workExperienceRequestDTO);
		return ResponseUtil.generateSuccessResponse(workExperienceResponseDTO, HttpStatus.OK,
				messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
	}

	/**
	 * This endpoint is to delete workExperience by entity type and entity id
	 * 
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	@DeleteMapping("/entity/{entityType}/{entityId}")
	public ResponseEntity<Object> deleteWorkExperienceByEntityTypeAndEntityId(@PathVariable String entityType,
			@PathVariable Integer entityId) {
		log.info("Delete workExperience by entity type and entity id : Controller ");
		workExperienceService.deleteWorkExperienceByEntityTypeAndEntityId(entityType, entityId);
		return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK,
				messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
	}
}
