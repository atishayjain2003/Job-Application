package com.job.FirstJob.Job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        jobLogger.info("Request received at controller to find all jobs");
        return jobService.findAllJobs();
    }

    @PostMapping("/addJob")
    public ResponseEntity<String> addJob(@RequestBody Job job){
        jobLogger.info("Request received to add a new job with details ");
        return jobService.addJob(job);
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable long id){
        jobLogger.info("Request received at controller to find job with id {}", id);
        return jobService.findJobById(id);
    }

    @GetMapping("/deleteJob/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable long id) {
        jobLogger.info("Request received at controller to delete job with id {}", id);
        return jobService.deleteJobById(id);
    }

    @PutMapping("updateJob/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable long id, @RequestBody Job job) {
        jobLogger.info("Request received at controller to update job with id {}", id);
        return jobService.updateJobById(id, job);
    }
}
