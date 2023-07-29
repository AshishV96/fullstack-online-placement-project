package com.project.placementagency.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.placementagency.dao.AdminRepository;
import com.project.placementagency.dao.UserRepository;
import com.project.placementagency.model.Admin;
import com.project.placementagency.model.AppliedJobs;
import com.project.placementagency.model.Employer;
import com.project.placementagency.model.Job;
import com.project.placementagency.model.User;
import com.project.placementagency.model.UserDTO;
import com.project.placementagency.model.UserStatus;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repo;

	public UserDTO addUser(UserDTO userDetails) {

		if (repo.existsByEmail(userDetails.getEmail()))
			return null;

		else
			return new UserDTO(repo.save(userDetails.get()));
	}

	@Override
	public UserDTO getUser(UserDTO userDetails) {

		Optional<User> x = repo.findOneByEmailIgnoreCase(userDetails.getEmail());
		if (x.isPresent()) {
			if(passwordEncoder.matches(userDetails.getPassword(), x.get().getPassword()))
			return new UserDTO(x.get());
			else
			return null;
		}
		else
			return null;

	}

	@Override
	public boolean existByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.existsByEmail(email);
	}

	// @Override
	// public UserStatus addUserJob(int uid, int jid) {
	//
	// UserStatus userStatus =new UserStatus(0,new User(),"Error");
	// Optional<User> x = repo.findById(uid);
	//
	// if(x.isPresent())
	// {
	// repo.insertUser_Jobs(uid, jid);
	// User user = repo.findById(uid).get();
	// userStatus.setUser(user);
	// userStatus.setStatuscode(1);
	// userStatus.setStatusmessage("Job details added");
	// }
	// return userStatus;
	// }

	@Override
	public UserStatus updateUser(User userDetails) {

		UserStatus userStatus = new UserStatus(0, new User(), "User details not updated");
		int id = userDetails.getUserId();
		if (repo.findById(id).isPresent()) {
			repo.save(userDetails);
			userStatus.setUser(userDetails);
			userStatus.setStatuscode(1);
			userStatus.setStatusmessage("User details updated");
		}
		return userStatus;
	}

	@Override
	public List<AppliedJobs> getAppliedJobs(int uid) {

		List<AppliedJobs> x = repo.getAppliedJobs(uid);
		if (x.isEmpty())
			x = null;

		return x;
	}

	@Override
	public List<AppliedJobs> getAllJobs() {

		List<AppliedJobs> x = repo.getAllJobs();
		if (x.isEmpty())
			x = null;

		return x;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
	}

}
