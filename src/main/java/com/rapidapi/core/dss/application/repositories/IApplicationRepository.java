package com.rapidapi.core.dss.application.repositories;

import com.rapidapi.core.dss.application.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IApplicationRepository extends JpaRepository<Application, Long> {

    Page<Application> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
