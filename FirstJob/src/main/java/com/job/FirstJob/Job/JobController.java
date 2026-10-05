package com.job.FirstJob.Job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    private static final Logger jobLogger = LoggerFactory.getLogger(JobController.class);

    @GetMapping("/allJobs")
    public ResponseEntity<List<Job>> findAllJobs(){
        jobLogger.info("JobController->Request received at controller to find all jobs");
        List<Job> allJobs = jobService.findAllJobs();
        for(int i=0; i<allJobs.size(); i++){
            jobLogger.info("JobController->Fetched Job details are Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", allJobs.get(i).getTitle(), allJobs.get(i).getDescription(), allJobs.get(i).getMinSalary(), allJobs.get(i).getMaxSalary(), allJobs.get(i).getLocation());
        }
        return ResponseEntity.ok(allJobs);
    }

    @PostMapping("/addJob")
    public ResponseEntity<String> addJob(@RequestBody Job job){
        jobLogger.info("JobController->Request received to add a new job with details Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
        boolean result = jobService.addJob(job);
        if(result){
            return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
        } else {
            jobLogger.info("JobController->Job with id {} already exists", job.getId());
            return ResponseEntity.badRequest().body("Job with id " + job.getId() + " already exists");
        }
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<?> getJobById(@PathVariable long id){
        jobLogger.info("JobController->Request received at controller to find job with id {}", id);
        Job resultJob = jobService.findJobById(id);
        if(resultJob!=null){
            jobLogger.info("JobController->Job found with id {}", id);
            return new ResponseEntity<>(resultJob, HttpStatus.OK);
        } else {
            jobLogger.info("JobController->Job with id {} not found", id);
            return new ResponseEntity<>("Job with id "+ id + "not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deleteJob/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable long id) {
        jobLogger.info("JobController->Request received at controller to delete job with id {}", id);
        boolean result = jobService.deleteJobById(id);
        if(result){
            jobLogger.info("JobController->Job deleted successfully with id {}", id);
            return ResponseEntity.ok("Job deleted successfully with id " + id);
        } else {
            jobLogger.info("JobController->Job with id {} not found", id);
            return new ResponseEntity<>("Job with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateJob/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable long id, @RequestBody Job job) {
        jobLogger.info("JobController->Request received at controller to update job with id {}", id);
        jobLogger.info("JobController->Job details to be updated are Title: {}, Description: {}, Min Salary: {}, Max Salary: {}, Location: {}", job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
        boolean result = jobService.updateJobById(id, job);
        if (result){
            jobLogger.info("JobController->Job updated successfully with id {}", id);
            return ResponseEntity.ok("Job updated successfully with id " + id);
        } else {
            jobLogger.info("JobController->Job with id {} not found", id);
            return new ResponseEntity<>("Job with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
