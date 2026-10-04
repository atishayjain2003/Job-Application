package com.job.FirstJob.Job;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    private final static Logger jobServiceLogger = LoggerFactory.getLogger(JobService.class);
    List<Job> jobs = new ArrayList<>();

    public ResponseEntity<List<Job>> findAllJobs(){
        jobServiceLogger.info("Request received at service to find all jobs");
        return ResponseEntity.ok(jobs);
    }

    public ResponseEntity<String> addJob(Job job){
        jobServiceLogger.info("Request received at service to add a new job");
        jobServiceLogger.info("Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
        int idPresent=0;
        for (Job value : jobs) {
            if (value.getId() == job.getId()) {
                idPresent = 1;
                break;
            }
        }
        if(idPresent==1){
            jobServiceLogger.warn("Id already exists");
        } else {
            jobs.add(job);
            jobServiceLogger.info("Job added successfully!");
            return new ResponseEntity<>("Job added successfully!", HttpStatus.CREATED);

        }
        return new ResponseEntity<>("Id already exists", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Job> findJobById(long id){
        jobServiceLogger.info("Request received at service to find with id {}", id);
        for(Job job : jobs){
            if(job.getId() == id){
                jobServiceLogger.info("Job Details are Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
                return ResponseEntity.ok(job);

            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteJobById(long id){
        jobServiceLogger.info("Request received at service to delete job with id {}", id);
        int idPresent=0;
        for(Job job : jobs){
            if(job.getId()==id){
                jobServiceLogger.info("Job removed with following details Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
                jobs.remove(job);
                idPresent=1;
                break;
            }
        }
        if(idPresent==1){
            return new ResponseEntity<>("Job deleted successfully!", HttpStatus.OK);
        } else {
            jobServiceLogger.warn("Job with id {} not found", id);
            return new ResponseEntity<>("Job with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> updateJobById(long id, Job job){
        jobServiceLogger.info("Request received at service to update job with id {}", id);
        int idPresent=0;
        for(Job existingJob : jobs){
            if(existingJob.getId()==id){
                jobServiceLogger.info("Existing Job Details are Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", existingJob.getTitle(), existingJob.getDescription(), existingJob.getMinSalary(), existingJob.getMaxSalary(), existingJob.getLocation());
                existingJob.setTitle(job.getTitle());
                existingJob.setDescription(job.getDescription());
                existingJob.setMinSalary(job.getMinSalary());
                existingJob.setMaxSalary(job.getMaxSalary());
                existingJob.setLocation(job.getLocation());
                idPresent=1;
                jobServiceLogger.info("Job updated successfully with following details Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", existingJob.getTitle(), existingJob.getDescription(), existingJob.getMinSalary(), existingJob.getMaxSalary(), existingJob.getLocation());
                break;
            }
        }
        if(idPresent==1){
            return new ResponseEntity<>("Job updated successfully!", HttpStatus.OK);
        } else {
            jobServiceLogger.warn("Job with id {} not found", id);
            return new ResponseEntity<>("Job with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
