package com.chat.entity.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "messages")
public class Message implements Serializable {
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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender")
	private User sender;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recipient")
	private User recipient;
}
