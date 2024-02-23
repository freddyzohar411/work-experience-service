package com.avensys.rts.workexperienceservice.payloadnewresponse;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceResponseDTO {
	private Integer id;
	private Integer formId;
	private String submissionData;
	private Integer formSubmissionId;
	private String entityType;
	private Integer entityId;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
