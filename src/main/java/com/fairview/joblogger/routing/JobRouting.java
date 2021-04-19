package com.fairview.joblogger.routing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobRouting {
    @GetMapping("")
    public ResponseEntity getJobs(){
        System.out.println("Hello world");
        return new ResponseEntity<>(null, HttpStatus.OK);
    };
}
