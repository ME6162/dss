package com.rapidapi.core.dss.entity.model;

import com.rapidapi.core.dss.application.model.Application;
import com.rapidapi.core.dss.common.annotations.CanUpdate;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@jakarta.persistence.Entity
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Indexed
    @CanUpdate
    private String name;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    @ManyToMany(mappedBy = "entities")
    private List<Application> applications;

    //Todo need to add field here
}
