package com.rapidapi.core.dss.application.services;

import com.rapidapi.core.dss.application.dto.ApplicationDTO;
import com.rapidapi.core.dss.application.model.Application;
import com.rapidapi.core.dss.application.repositories.IApplicationRepository;
import com.rapidapi.core.dss.common.util.DTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private IApplicationRepository repository;


    private Application getApplicationByIdAsEntity(String dssId) {
        long id = DTOHelper.getIdFromDssId(dssId, Application.class);
        return repository.findById(id).orElse(null);
    }

    public ApplicationDTO getApplicationById(String dssId) {
        Application application = getApplicationByIdAsEntity(dssId);
        if (application != null) {
            return new ApplicationDTO(application);
        }
        return null;
    }

    public ApplicationDTO addApplication(ApplicationDTO applicationDTO) {
        Application application = applicationDTO.generateEntity();

        application.setCreatedDate(LocalDateTime.now());
        application.setLastUpdatedDate(LocalDateTime.now());

        Application savedApplication = repository.save(application);
        return new ApplicationDTO(savedApplication);
    }

    public ApplicationDTO updateApplication(String dssId, Map<String, Object> newValues) {
        Application application = getApplicationByIdAsEntity(dssId); // Helper method to fetch the entity
        if (application == null) {
            return null;
        }

        DTOHelper.updateModel(application, newValues);
        application.setLastUpdatedDate(LocalDateTime.now());

        Application updatedApplication = repository.save(application);
        return new ApplicationDTO(updatedApplication);
    }

    public void deleteApplication(String dssId) {
        long id = DTOHelper.getIdFromDssId(dssId, Application.class);
        repository.deleteById(id);
    }

    public Page<ApplicationDTO> getFilteredApplications(String name, Pageable pageable) {
        Page<Application> applications =
                (name != null)
                        ? repository.findByNameContainingIgnoreCase(name, pageable)
                        : repository.findAll(pageable);

        List<ApplicationDTO> applicationDTOs = applications.getContent()
                .stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(applicationDTOs, pageable, applications.getTotalElements());
    }

}
