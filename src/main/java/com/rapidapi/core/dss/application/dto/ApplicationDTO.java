package com.rapidapi.core.dss.application.dto;

import com.rapidapi.core.dss.application.model.Application;
import com.rapidapi.core.dss.entity.dto.EntityDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApplicationDTO {
    private long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private List<EntityDTO> entities;

    public ApplicationDTO(){}

    public ApplicationDTO(Application application) {
        this.id = application.getId();
        this.name =application.getName();
        this.createdDate = application.getCreatedDate();
        this.lastUpdatedDate = application.getLastUpdatedDate();
        this.entities = application.getEntities().stream().map(EntityDTO::new).toList();
    }

    public Application generateEntity(){
        Application application = new Application();
        application.setName(this.getName());
        application.setEntities(this.entities.stream().map(EntityDTO::generateEntity).toList());
        return application;
    }
}

