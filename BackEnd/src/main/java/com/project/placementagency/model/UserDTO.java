package com.project.placementagency.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements UserDetails {
    
    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    @JsonIgnore
    private String password;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
	private String lastName;

    @JsonProperty("age")
	private Integer age;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("address")
	private String address;

    @JsonProperty("qualification")
	private String qualification;

    @JsonProperty("contactNo")
    private Double contactNo;

    @JsonProperty("jobs")
    private List<JobDTO> jobs = new ArrayList<>();

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
        if(user.getJobs()!=null)
        user.getJobs().stream().forEach(job->this.jobs.add(new JobDTO(job)));
    }

    public User get()
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
        if(this.jobs!=null)this.jobs.stream().forEach(job->user.getJobs().add(job.get()));

        return user;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
