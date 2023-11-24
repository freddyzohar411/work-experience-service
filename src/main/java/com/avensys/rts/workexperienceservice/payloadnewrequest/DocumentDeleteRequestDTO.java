package com.avensys.rts.workexperienceservice.payloadnewrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: Koh He Xiang
 * This is the DTO class for the document delete request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDeleteRequestDTO {
    private String entityType;
    private Integer entityId;
}
