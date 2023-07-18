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
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employer {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int companyId;
	@Column(unique = true, length = 50)
	private String companyemail;
	private String companypassword;
	private String companyName;
//	@Column(unique = true)
	private double contact;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL)
	private List<Job> jobs;
	
//	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_jobs",
//    joinColumns = @JoinColumn(name = "user_id"),
//    inverseJoinColumns = @JoinColumn(name = "job_id"))
//	private List<Job> jobs;
	
//	@OneToMany(mappedBy = "users",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	private List<Job_User> userJob;
	
}

