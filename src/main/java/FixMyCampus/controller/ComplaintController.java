
package FixMyCampus.controller;

import FixMyCampus.model.Complaint;
import FixMyCampus.repository.ComplaintRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ComplaintController {

    private final ComplaintRepository repository;

    public ComplaintController(ComplaintRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("complaints",
                repository.findAll());
        model.addAttribute("complaint", new Complaint());
        return "index";
    }

    @PostMapping("/submit")
    public String submitComplaint(
            @ModelAttribute Complaint complaint) {

        complaint.setStatus("Pending");
        repository.save(complaint);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateStatus(@PathVariable Long id) {
        Complaint complaint = repository.findById(id)
                .orElseThrow();

        complaint.setStatus("Resolved");
        repository.save(complaint);

        return "redirect:/";
    }
}
