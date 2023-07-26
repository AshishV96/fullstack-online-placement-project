package com.project.placementagency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private int jobId;	
	private String jobCat;
	private String jobDescp;
	private String jobLocation;
	private double jobSalary;

    public JobDTO(Job job)
    {
        this.jobId = job.getJobId();
        this.jobCat = job.getJobCat();
        this.jobDescp = job.getJobDescp();
        this.jobLocation = job.getJobLocation();
        this.jobSalary = job.getJobSalary();
    }

    public Job getJob()
    {
        Job job = new Job();
        job.setJobId(this.jobId);
        job.setJobCat(this.jobCat);
        job.setJobDescp(this.jobDescp);
        job.setJobLocation(this.jobLocation);
        job.setJobSalary(this.jobSalary);

        return job;
    }

}
