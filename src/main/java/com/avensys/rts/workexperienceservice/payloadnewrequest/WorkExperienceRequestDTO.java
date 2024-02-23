package com.avensys.rts.workexperienceservice.payloadnewrequest;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceRequestDTO {

	private String entityType;
	private Integer entityId;

	// Form Submission
	private String formData;
	private Integer formId;

	private Long createdBy;
	private Long updatedBy;

	// Added 22112023
	MultipartFile[] multiFiles;
}
