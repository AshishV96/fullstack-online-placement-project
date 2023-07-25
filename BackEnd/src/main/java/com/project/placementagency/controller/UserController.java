package com.project.placementagency.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.placementagency.JobportalApplication;
import com.project.placementagency.dao.UserRepository;
import com.project.placementagency.model.AppliedJobs;
import com.project.placementagency.model.Employer;
import com.project.placementagency.model.Job;
import com.project.placementagency.model.User;
import com.project.placementagency.model.UserDTO;
import com.project.placementagency.model.UserStatus;
import com.project.placementagency.service.EmailSenderService;
import com.project.placementagency.service.UserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private EmailSenderService mailSender;
	
	@Autowired
	private UserRepository repo;
	
	@GetMapping("/use")
	public void use()
	{
		repo.findAll();
	}

	@PostMapping("/send")
	public ResponseEntity<String> sendEmail(@RequestParam(name = "mailId") String mailId,@RequestBody Long OTP) throws MessagingException
	{
		mailSender.sendEmail(mailId, "OTP to register to the portal", String.valueOf(OTP));
		return new ResponseEntity<String>("OTP sent", HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addUser(@RequestBody UserDTO userDetails) 
	{
		System.out.println("update controller function got input as " + userDetails.getUserId());
		
		UserDTO userDTO = service.addUser(userDetails);
		if(userDTO==null)
			return new ResponseEntity<String>("EmailId already registered",HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<String>("User registered with ID:: "+userDTO.getUserId(),HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{email}/{password}")
	public ResponseEntity<UserStatus> getUser(@PathVariable("email") String email,@PathVariable("password") String password) 
	{
		System.out.println("update controller function got input as " + email + password);
		
		ResponseEntity<UserStatus> re=null;
		UserStatus x = service.getUser(email,password);
		if(x.getStatuscode() == 0)
			re = new ResponseEntity<UserStatus>(x,HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<UserStatus>(x,HttpStatus.OK);
		return re;
	}
	
	@GetMapping("/getAppliedJobs/{uid}")
	public ResponseEntity<List<AppliedJobs>> getAppliedJobs(@PathVariable("uid") int uid) 
	{
		System.out.println("update controller function got input as " + uid);
		
		ResponseEntity<List<AppliedJobs>> re = null;
		List<AppliedJobs> x = service.getAppliedJobs(uid);
		if(x == null)
			re = new ResponseEntity<List<AppliedJobs>>(x,HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<List<AppliedJobs>>(x,HttpStatus.OK);
		return re;
//		List<Employer> employers = repo.getEmployers(uid);
//		List<Job> jobs = repo.getJobs(uid);
		
//		List<AppliedJobs> appliedJobs = repo.getAppliedJobs(uid);
//		AppliedJobs appliedJobs = new AppliedJobs();
//		appliedJobs.setEmployers(employers);
//		appliedJobs.setJobs(jobs);
		
//		List<AppliedJobs> appliedJobs = new ArrayList<AppliedJobs>();
//		for (int i=0;i<employers.size();i++)
//		{
//			appliedJobs.add(new AppliedJobs(employers.get(i),jobs.get(i)));
//		}
		
	}
	
	@GetMapping("/getAllJobs")
	public ResponseEntity<List<AppliedJobs>> getAllJobs() 
	{		
		ResponseEntity<List<AppliedJobs>> re = null;
		List<AppliedJobs> x = service.getAllJobs();
		if(x == null)
			re = new ResponseEntity<List<AppliedJobs>>(x,HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<List<AppliedJobs>>(x,HttpStatus.OK);
		return re;
	}
	
	@PutMapping("/update")
	public ResponseEntity<UserStatus> updateUser(@RequestBody User userDetails) 
	{
		System.out.println("update controller function got input as " + userDetails);
		
		ResponseEntity<UserStatus> re=null;
		UserStatus x = service.updateUser(userDetails);
		if(x.getStatuscode() == 0)
			re = new ResponseEntity<UserStatus>(x,HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<UserStatus>(x,HttpStatus.OK);
		return re;
	}
	
	@PutMapping("/applyJob/{uid}/{eid}")
	public List<AppliedJobs> applyJob(@PathVariable("uid") int uid, @PathVariable("eid") int eid, @RequestBody Job jobDetails ) 
	{
		
		if(repo.isUserNull(jobDetails.getJobId()) == null)
			repo.updateApply(uid, jobDetails.getJobId());
		else
		{
			
		}
		return repo.getAppliedJobs(uid);
	}

	
	
//	@PostMapping("/addUserJob/{uid}/{jid}")
//	public ResponseEntity<UserStatus> addUserJob(@PathVariable("uid") int uid,@PathVariable("jid") int jid) 
//	{
//		System.out.println("update controller function got input as " + uid + "," + jid);
//		
//		ResponseEntity<UserStatus> re=null;
//		UserStatus x = service.addUserJob(uid,jid);
//		if(x.getStatuscode() == 0)
//			re = new ResponseEntity<UserStatus>(x,HttpStatus.NOT_FOUND);
//		else
//			re = new ResponseEntity<UserStatus>(x,HttpStatus.OK);
//		return re;
//	}
	
//	@PostMapping("/addUser")
//	public List<Job> addUserJob() 
//	{
//		List<Job> job = repo.selectJob();
////		ResponseEntity<Integer> re = new ResponseEntity<Integer>(user,HttpStatus.NOT_FOUND);
////		return re;
//		return job;
//	}
}
