package com.project.placementagency.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	@Column(unique = true, length = 50)
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Integer age;
	@Column(length = 10)
	private String gender;
	private String address;
	private String qualification;
//	@Column(unique = true)
	private Double contactNo;
	
//	@OneToOne
//	@JoinColumn(name = "jid")
//	private Job job;
//	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<Job> jobs;
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_jobs",
//    joinColumns = @JoinColumn(name = "user_id"),
//    inverseJoinColumns = @JoinColumn(name = "job_id"))
//	private List<Job> jobs;
	
//	@OneToMany(mappedBy = "users",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	private List<Job_User> userJob;
	
}
