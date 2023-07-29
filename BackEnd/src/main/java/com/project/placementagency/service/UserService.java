package com.project.placementagency.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.project.placementagency.model.Admin;
import com.project.placementagency.model.AdminStatus;
import com.project.placementagency.model.AppliedJobs;
import com.project.placementagency.model.Employer;
import com.project.placementagency.model.Job;
import com.project.placementagency.model.User;
import com.project.placementagency.model.UserDTO;
import com.project.placementagency.model.UserStatus;

@Service
public interface UserService extends UserDetailsService {

	UserDTO addUser(UserDTO userDetails);

	UserDTO getUser(UserDTO userDetails);

	UserStatus updateUser(User userDetails);

	List<AppliedJobs> getAppliedJobs(int uid);

	List<AppliedJobs> getAllJobs();

    boolean existByEmail(String email);
	
//	UserStatus addUserJob(int uid, int jid);
		
}
