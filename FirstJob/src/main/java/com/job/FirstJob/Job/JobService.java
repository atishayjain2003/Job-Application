package com.job.FirstJob.Job;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    public final JobRepository jobRepository;

    private final static Logger jobServiceLogger = LoggerFactory.getLogger(JobService.class);
    List<Job> jobs = new ArrayList<>();

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findAllJobs(){
        jobServiceLogger.info("JobService->Request received at service to find all jobs");
        return jobRepository.findAll();
    }

    public Boolean addJob(Job job){
        jobServiceLogger.info("JobService->Request received at service to add a new job");
        jobServiceLogger.info("JobService->Job Details to be added are Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
        Optional<Job> existingJob = jobRepository.findById(job.getId());
        if(existingJob.isPresent()){
            jobServiceLogger.warn("JobService->Job with id {} already exists", job.getId());
            return false;
        }
        jobRepository.save(job);
        jobServiceLogger.info("JobService->Job added successfully with details Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
        return true;
    }

    public Job findJobById(long id){
        jobServiceLogger.info("JobService->Request received at service to find with id {}", id);
        Job resultJob = jobRepository.findById(id).orElse(null);
        if(resultJob!=null){
            jobServiceLogger.info("JobService->Job found with following details Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", resultJob.getTitle(), resultJob.getDescription(), resultJob.getMinSalary(), resultJob.getMaxSalary(), resultJob.getLocation());
            return resultJob;
        } else {
            jobServiceLogger.warn("JobService->Job with id {} not found", id);
            return null;
        }

    }

    public Boolean deleteJobById(long id){
        jobServiceLogger.info("JobService->Request received at service to delete job with id {}", id);
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            jobRepository.deleteById(id);
            jobServiceLogger.info("JobService->Job with id {} deleted successfully", id);
            return true;
        } else {
            jobServiceLogger.warn("JobService->Job with id {} not found", id);
            return false;
        }
    }

    public Boolean updateJobById(long id, Job job) {
        jobServiceLogger.info("JobService->Request received at service to update job with id {}", id);
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job existingJob = jobOptional.get();
            jobServiceLogger.info("JobService->Existing Job Details are Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", existingJob.getTitle(), existingJob.getDescription(), existingJob.getMinSalary(), existingJob.getMaxSalary(), existingJob.getLocation());
            existingJob.setTitle(job.getTitle());
            existingJob.setDescription(job.getDescription());
            existingJob.setMinSalary(job.getMinSalary());
            existingJob.setMaxSalary(job.getMaxSalary());
            existingJob.setLocation(job.getLocation());
            jobRepository.save(existingJob);
            jobServiceLogger.info("JobService->Job updated successfully with following details Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", existingJob.getTitle(), existingJob.getDescription(), existingJob.getMinSalary(), existingJob.getMaxSalary(), existingJob.getLocation());
            return true;
        } else {
            jobServiceLogger.warn("JobService->Job with id {} not found", id);
            return false;
        }
    }
}
