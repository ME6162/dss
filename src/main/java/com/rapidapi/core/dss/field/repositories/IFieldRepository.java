package com.rapidapi.core.dss.field.repositories;

import com.rapidapi.core.dss.field.model.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IFieldRepository extends JpaRepository<Field, Long> {

    Page<Field> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    Page<Field> findByEntityId(long entityId, Pageable pageable);

    Page<Field> findByEntityIdAndNameContainingIgnoreCase(long entityId, String name, Pageable pageable);
}
