package com.pixel.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name="USER")
public class User {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private long id;
	@Column(name="USERNAME", nullable = false, unique = true)
	private String username;
	@Column(name="PASSWORD")
	private String password;
	private boolean active;
	private String roles;
	
}
