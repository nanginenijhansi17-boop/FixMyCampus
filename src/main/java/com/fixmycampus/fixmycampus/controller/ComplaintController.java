
package com.fixmycampus.controller;

import com.fixmycampus.model.Complaint;
import com.fixmycampus.repository.ComplaintRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintRepository repository;

    public ComplaintController(ComplaintRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Complaint> getComplaints() {
        return repository.findAll();
    }

    @PostMapping
    public Complaint addComplaint(@RequestBody Complaint complaint) {
        complaint.setStatus("Pending");
        return repository.save(complaint);
    }
}
