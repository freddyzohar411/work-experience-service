package com.avensys.rts.workexperienceservice.payloadnewrequest;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: Koh He Xiang This is the DTO class for the document request
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentRequestDTO {
	private String type;
	private String title;
	private String description;
	private Integer entityId;
	private String entityType;
	MultipartFile file;
}
