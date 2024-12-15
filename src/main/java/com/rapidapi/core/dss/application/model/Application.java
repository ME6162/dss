package com.rapidapi.core.dss.application.model;

import com.rapidapi.core.dss.common.annotations.CanUpdate;
import com.rapidapi.core.dss.entity.model.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@jakarta.persistence.Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Indexed
    @CanUpdate
    private String name;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    @ManyToMany
    @JoinTable(
            name = "application_entity",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "entity_id")
    )
    private List<Entity> entities;
}
