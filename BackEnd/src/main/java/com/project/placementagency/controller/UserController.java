package com.project.placementagency.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.project.placementagency.model.AuthenticationRequest;
import com.project.placementagency.model.AuthenticationResponse;
import com.project.placementagency.model.Employer;
import com.project.placementagency.model.Job;
import com.project.placementagency.model.ResponseString;
import com.project.placementagency.model.User;
import com.project.placementagency.model.UserDTO;
import com.project.placementagency.model.UserStatus;
import com.project.placementagency.service.EmailSenderService;
import com.project.placementagency.service.UserService;
import com.project.placementagency.util.JwtUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserService service;

	@Autowired
	private EmailSenderService mailSender;

	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Integer otpGenerator(HttpSession session) {
		Random random = new Random();
		Integer OTP = random.nextInt(899999) + 100000;
		System.out.println("Session ID:: " + session.getId());
		System.out.println(OTP);
		session.setAttribute("OTP", passwordEncoder.encode(OTP.toString()));
		return OTP;
	}

	@PostMapping("login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrent username or password",e);
        }
        final UserDetails userDetails = service.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

	@GetMapping("/sendOTP")
	public ResponseEntity<?> sendEmail(@RequestParam(name = "mailId") String mailId, HttpSession session)
			throws MessagingException {
				String email = session.getAttribute("mailId").toString();
		if (email!=null&&email.equals(mailId)) {
			Integer OTP = this.otpGenerator(session);
			mailSender.sendEmail(mailId, "OTP to register to the portal",
					"OTP to register to the portal is " + String.valueOf(OTP));
			// return new ResponseEntity<ResponseString>(new
			// ResponseString(HttpStatus.OK.value(), "OTP sent"), HttpStatus.OK);
			return new ResponseEntity<String>("OTP sent", HttpStatus.OK);
		} else
			return new ResponseEntity<String>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestParam(name = "mailId") String mailId, HttpSession session)
	{
		if (service.existByEmail(mailId))
		{
			session.setAttribute("mailId", mailId);
			try {
				return this.sendEmail(mailId, session);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
			return new ResponseEntity<String>("Email not registered", HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/resetPassword/{OTP}")
	public ResponseEntity<String> resetPassword(@PathVariable String OTP,@RequestBody AuthenticationRequest user,HttpSession session)
	{
		String email = session.getAttribute("mailId").toString();
		String storedOTP = session.getAttribute("OTP").toString();
		if (email!=null&&email.equals(user.getUsername())&&passwordEncoder.matches(OTP, storedOTP))
		{
			if(service.resetPassword(user.getUsername(),user.getPassword()))
				return new ResponseEntity<String>("Password changed successfully, Please Login again",HttpStatus.OK);
			else
				return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else
			return new ResponseEntity<String>("Invalid OTP",HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/check/{email}")
	public ResponseEntity<?> checkEmail(@PathVariable String email, HttpSession session) {
		if (service.existByEmail(email))
			// return new ResponseEntity<ResponseString>(new
			// ResponseString(HttpStatus.NOT_ACCEPTABLE.value(), "Email already
			// registered"), HttpStatus.NOT_ACCEPTABLE);
			return new ResponseEntity<String>("Email already registered", HttpStatus.NOT_ACCEPTABLE);

		else {
			try {
				session.setAttribute("mailId", email);
				ResponseEntity<?> re = this.sendEmail(email, session);
				if(re.getStatusCode()==HttpStatus.OK)
				// return new ResponseEntity<ResponseString>(new
				// ResponseString(HttpStatus.OK.value(), "OTP sent"), HttpStatus.OK);
				return new ResponseEntity<String>("OTP sent", HttpStatus.OK);

				else
				return new ResponseEntity<String>("Invalid Email", HttpStatus.INTERNAL_SERVER_ERROR);

			} catch (Exception e) {
				// return new ResponseEntity<ResponseString>(new
				// ResponseString(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Email"),
				// HttpStatus.INTERNAL_SERVER_ERROR);
				System.out.println(e.getMessage());
				return new ResponseEntity<String>("Invalid Email", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@PostMapping("/add/{OTP}")
	public ResponseEntity<?> addUser(@RequestBody UserDTO userDetails, @PathVariable String OTP, HttpSession session) {
		if (passwordEncoder.matches(OTP, session.getAttribute("OTP").toString())) {
			userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
			UserDTO userDTO = service.addUser(userDetails);
			if (userDTO == null)
				// return new ResponseEntity<ResponseString>(new
				// ResponseString(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went
				// wrong"),HttpStatus.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<String>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			else
				// return new ResponseEntity<ResponseString>(new
				// ResponseString(HttpStatus.OK.value(),"Registration successfull, Kindly login
				// again"),HttpStatus.OK);
				return new ResponseEntity<String>("Registration successfull, Kindly login again", HttpStatus.OK);
		} else
			// return new ResponseEntity<ResponseString>(new
			// ResponseString(HttpStatus.BAD_REQUEST.value(),"Invalid
			// OTP"),HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>("Invalid OTP", HttpStatus.BAD_REQUEST);
	}

	// @GetMapping("/login")
	// public ResponseEntity<?> login(@RequestBody UserDTO userDetails) {
	// 	System.out.println("update controller function got input as " + userDetails.getEmail() + "and password");
		
	// 	UserDTO userDTO = service.getUser(userDetails);
	// 	if (userDTO == null)
	// 		return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
	// 	else
	// 		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.FOUND);
	// }

	@GetMapping("/getAppliedJobs/{uid}")
	public ResponseEntity<List<AppliedJobs>> getAppliedJobs(@PathVariable("uid") int uid) {
		System.out.println("update controller function got input as " + uid);

		ResponseEntity<List<AppliedJobs>> re = null;
		List<AppliedJobs> x = service.getAppliedJobs(uid);
		if (x == null)
			re = new ResponseEntity<List<AppliedJobs>>(x, HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<List<AppliedJobs>>(x, HttpStatus.OK);
		return re;
		// List<Employer> employers = repo.getEmployers(uid);
		// List<Job> jobs = repo.getJobs(uid);

		// List<AppliedJobs> appliedJobs = repo.getAppliedJobs(uid);
		// AppliedJobs appliedJobs = new AppliedJobs();
		// appliedJobs.setEmployers(employers);
		// appliedJobs.setJobs(jobs);

		// List<AppliedJobs> appliedJobs = new ArrayList<AppliedJobs>();
		// for (int i=0;i<employers.size();i++)
		// {
		// appliedJobs.add(new AppliedJobs(employers.get(i),jobs.get(i)));
		// }

	}

	@GetMapping("/getAllJobs")
	public ResponseEntity<List<AppliedJobs>> getAllJobs() {
		ResponseEntity<List<AppliedJobs>> re = null;
		List<AppliedJobs> x = service.getAllJobs();
		if (x == null)
			re = new ResponseEntity<List<AppliedJobs>>(x, HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<List<AppliedJobs>>(x, HttpStatus.OK);
		return re;
	}

	@PutMapping("/update")
	public ResponseEntity<UserStatus> updateUser(@RequestBody User userDetails) {
		System.out.println("update controller function got input as " + userDetails);

		ResponseEntity<UserStatus> re = null;
		UserStatus x = service.updateUser(userDetails);
		if (x.getStatuscode() == 0)
			re = new ResponseEntity<UserStatus>(x, HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<UserStatus>(x, HttpStatus.OK);
		return re;
	}

	@PutMapping("/applyJob/{uid}/{eid}")
	public List<AppliedJobs> applyJob(@PathVariable("uid") int uid, @PathVariable("eid") int eid,
			@RequestBody Job jobDetails) {

		if (repo.isUserNull(jobDetails.getJobId()) == null)
			repo.updateApply(uid, jobDetails.getJobId());
		else {

		}
		return repo.getAppliedJobs(uid);
	}

	@GetMapping("print")
	public String print()
	{
		return "Authentication Successfull";
	}

	// @PostMapping("/addUserJob/{uid}/{jid}")
	// public ResponseEntity<UserStatus> addUserJob(@PathVariable("uid") int
	// uid,@PathVariable("jid") int jid)
	// {
	// System.out.println("update controller function got input as " + uid + "," +
	// jid);
	//
	// ResponseEntity<UserStatus> re=null;
	// UserStatus x = service.addUserJob(uid,jid);
	// if(x.getStatuscode() == 0)
	// re = new ResponseEntity<UserStatus>(x,HttpStatus.NOT_FOUND);
	// else
	// re = new ResponseEntity<UserStatus>(x,HttpStatus.OK);
	// return re;
	// }

	// @PostMapping("/addUser")
	// public List<Job> addUserJob()
	// {
	// List<Job> job = repo.selectJob();
	//// ResponseEntity<Integer> re = new
	// ResponseEntity<Integer>(user,HttpStatus.NOT_FOUND);
	//// return re;
	// return job;
	// }
}
