package com.rapidapi.core.dss.application.controller;

import com.rapidapi.core.dss.application.dto.ApplicationDTO;
import com.rapidapi.core.dss.application.services.ApplicationService;
import com.rapidapi.core.dss.common.services.IResponseFormater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {

    @Autowired
    private ApplicationService service;


    @Autowired
    private IResponseFormater responseFormater;

    @GetMapping
    public ResponseEntity<Page<ApplicationDTO>> getFilteredApplications(
            @RequestParam(required = false) String name,
            Pageable pageable) {

        // Fetch filtered and paginated applications
        Page<ApplicationDTO> applications = service.getFilteredApplications(name, pageable);

        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{dssId}")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable String dssId) {
        ApplicationDTO applicationDTO = service.getApplicationById(dssId);
        if (applicationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicationDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createApplication(@RequestBody ApplicationDTO applicationDTO) {
        try {
            ApplicationDTO createdApp = service.addApplication(applicationDTO);
            return ResponseEntity.ok(createdApp);
        } catch (RuntimeException e) {
            return responseFormater.getBadRequest(e.getMessage());
        }
    }

    @PutMapping("/{dssId}")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable String dssId, @RequestBody Map<String,Object> application) {
        ApplicationDTO updatedApp = service.updateApplication(dssId, application);
        if (updatedApp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedApp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        service.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
