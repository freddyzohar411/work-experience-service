package com.avensys.rts.workexperienceservice.payloadnewrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: Koh He Xiang This is the DTO class for the document delete request
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDeleteRequestDTO {
	private String entityType;
	private Integer entityId;
}
