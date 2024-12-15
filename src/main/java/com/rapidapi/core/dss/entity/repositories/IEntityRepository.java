package com.rapidapi.core.dss.entity.repositories;

import com.rapidapi.core.dss.entity.model.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface IEntityRepository extends JpaRepository<Entity, Long> {

    Page<Entity> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    Page<Entity> findByApplicationsId(long applicationId, Pageable pageable);

    Page<Entity> findByApplicationsIdAndNameContainingIgnoreCase(long applicationId, String name, Pageable pageable);
}
