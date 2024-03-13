package com.avensys.rts.workexperienceservice.payloadnewrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkExperienceListRequestDTO {
	List<WorkExperienceRequestDTO> workExperienceList;
}
