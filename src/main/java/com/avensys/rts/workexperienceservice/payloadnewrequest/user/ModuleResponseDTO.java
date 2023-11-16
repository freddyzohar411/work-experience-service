package com.avensys.rts.workexperienceservice.payloadnewrequest.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponseDTO {

	private Long id;
	private String moduleName;
	private List<String> permissions;
}
