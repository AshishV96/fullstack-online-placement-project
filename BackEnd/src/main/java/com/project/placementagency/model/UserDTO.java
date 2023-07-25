package com.project.placementagency.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Integer userId;
    private String email;
    private String password;
    private String firstName;
	private String lastName;
	private Integer age;
    private String gender;
	private String address;
	private String qualification;
    private Double contactNo;
    private List<Job> jobs;

    public UserDTO(User user)
    {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.password = null;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.qualification = user.getQualification();
        this.contactNo = user.getContactNo();
        this.jobs = user.getJobs();
    }

    public User getUser()
    {
        User user = new User();
        user.setUserId(this.userId);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setAge(this.age);
        user.setGender(this.gender);
        user.setAddress(this.address);
        user.setQualification(this.qualification);
        user.setContactNo(this.contactNo);
        user.setJobs(this.jobs);

        return user;
    }
}
