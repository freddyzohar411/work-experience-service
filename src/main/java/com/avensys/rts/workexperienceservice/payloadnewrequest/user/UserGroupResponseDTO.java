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
public class UserGroupResponseDTO {

	private String groupName;
	private String groupDescription;
	private List<RoleResponseDTO> roles;
}
