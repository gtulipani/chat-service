package com.chat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "users")
@JsonIgnoreProperties(value = {"id"})
public class User implements Serializable {
	private static final long serialVersionUID = -6507963547063710509L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "created_on", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
	private LocalDateTime createdOn;

	@Column(name = "last_modified", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
	private LocalDateTime lastModified;

	@Column(name = "email", nullable = false)
	@NotNull
	@NotEmpty
	private String username;

	@Column(name = "email", nullable = false)
	@NotNull
	@NotEmpty
	@Convert(converter = SensitiveFieldConverter.class)
	private String password;

	@PrePersist
	protected void onCreate() {
		createdOn = LocalDateTime.now();
		lastModified = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		lastModified = LocalDateTime.now();
	}
}
