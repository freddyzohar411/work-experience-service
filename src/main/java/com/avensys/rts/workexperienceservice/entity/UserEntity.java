package com.avensys.rts.workexperienceservice.entity;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class UserEntity extends BaseEntity {

	private static final long serialVersionUID = -4259261306081521415L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "KeycloackId cannot be empty")
	@Column(name = "keycloack_id", unique = true)
	private String keycloackId;

	@NotNull(message = "First Name cannot be empty")
	@Column(name = "first_name")
	private String firstName;

	@NotNull(message = "Last Name cannot be empty")
	@Column(name = "last_name")
	private String lastName;

	@NotNull(message = "Username cannot be empty")
	@Column(name = "username", unique = true)
	private String username;

	@NotNull(message = "Email cannot be empty")
	@Email(message = "Please enter a valid email address")
	@Column(name = "email", unique = true)
	private String email;

	@NotNull(message = "Password cannot be empty")
	@Length(min = 7, message = "Password should be atleast 7 characters long")
	@Column(name = "password")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Column(name = "mobile")
	@Length(min = 10, message = "Password should be atleast 10 number long")
	private String mobile;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "locked")
	private Boolean locked = false;

	@Column(name = "enabled")
	private Boolean enabled = true;

	@ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<UserGroupEntity> groupEntities;

}