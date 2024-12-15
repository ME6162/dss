package com.rapidapi.core.dss.entity.dto;

import com.rapidapi.core.dss.entity.model.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EntityDTO {
    private long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    public EntityDTO(){}

    public EntityDTO(Entity entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.createdDate = entity.getCreatedDate();
        this.lastUpdatedDate =entity.getLastUpdatedDate();
    }

    public Entity generateEntity(){
        Entity entity = new Entity();
        entity.setName(this.name);
        entity.setId(this.id);
        return entity;
    }
}
