package com.avensys.rts.workexperienceservice.payloadnewresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: Koh He Xiang This is the DTO class for the document response
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponseDTO {
	private Integer id;
	private String type;
	private String title;
	private String documentName;
	private String description;
	private Integer entityId;
	private String entityType;
	private String documentKey;

}
