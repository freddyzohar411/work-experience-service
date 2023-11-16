package com.avensys.rts.workexperienceservice.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "module", uniqueConstraints = { @UniqueConstraint(columnNames = { "module_name" }) })
public class ModuleEntity extends BaseEntity {

	private static final long serialVersionUID = 281577856522059273L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "module_description")
	private String moduleDescription;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "module_id", referencedColumnName = "id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<RoleModulePermissionsEntity> modulePermissions;

}
