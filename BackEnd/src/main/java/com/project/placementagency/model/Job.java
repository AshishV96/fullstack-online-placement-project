package com.project.placementagency.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Job {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int jobId;	
	private String jobCat;
	private String jobDescp;
	private String jobLocation;
	private double jobSalary;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "uid",referencedColumnName = "userId")
	private User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "eid",referencedColumnName = "companyId")
	private Employer employer;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private User users;
	
//	@JsonIgnore
//	@ManyToMany(mappedBy = "jobs")
//	private List<User> users; 
	
//	@OneToMany(mappedBy = "jobs",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	private List<Job_User> jobUser;
}
