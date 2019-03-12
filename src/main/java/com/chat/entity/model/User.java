package com.chat.entity.model;

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
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

import com.chat.entity.converter.SensitiveFieldConverter;
import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = -6507963547063710509L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(name = "created_on", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
	private LocalDateTime createdOn;

	@Column(name = "last_modified", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
	private LocalDateTime lastModified;

	@Column(name = "username", nullable = false)
	@NotBlank
	private String username;

	@Column(name = "password", nullable = false)
	@NotBlank
	@Convert(converter = SensitiveFieldConverter.class)
	private String password;

	@Column(name = "token")
	@Convert(converter = SensitiveFieldConverter.class)
	private String token;

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
