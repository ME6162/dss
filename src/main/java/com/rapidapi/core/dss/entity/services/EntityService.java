package com.rapidapi.core.dss.entity.services;

import com.rapidapi.core.dss.entity.dto.EntityDTO;
import com.rapidapi.core.dss.entity.model.Entity;
import com.rapidapi.core.dss.entity.repositories.IEntityRepository;
import com.rapidapi.core.dss.common.util.DTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EntityService {

    @Autowired
    private IEntityRepository repository;


    private Entity _getEntityByDssId(String dssId) {
        long id = DTOHelper.getIdFromDssId(dssId, Entity.class);
        return getEntityById(id);
    }

    private Entity getEntityById(long id) {
        return repository.findById(id).orElse(null);
    }

    public EntityDTO addEntity(EntityDTO entityDTO) {
        Entity entity = entityDTO.generateEntity();
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        return new EntityDTO(repository.save(entity));
    }

    public void deleteEntity(String dssId) {
        long id = DTOHelper.getIdFromDssId(dssId, Entity.class);
        repository.deleteById(id);
    }
    
    public Page<EntityDTO> getFilteredEntities(String name, String dssApplicationId, Pageable pageable) {
        Long applicationId = null;
        if(dssApplicationId != null){
            applicationId = DTOHelper.getIdFromDssId(dssApplicationId, Entity.class);
        }

        Page<Entity> entities;
        if (applicationId != null && name != null) {
            entities = repository.findByApplicationsIdAndNameContainingIgnoreCase(applicationId, name, pageable);
        } else if (applicationId != null) {
            entities = repository.findByApplicationsId(applicationId, pageable);
        } else if (name != null) {
            entities = repository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            entities = repository.findAll(pageable);
        }

        List<EntityDTO> EntityDTOs = entities.getContent().stream().map(EntityDTO::new).toList();
        return new PageImpl<>(EntityDTOs, pageable, entities.getTotalElements());

    }

    public EntityDTO getEntityByDssId(String dssId) {
        Entity entity = _getEntityByDssId(dssId);
        if (entity != null){
            return new EntityDTO(entity);
        }
        return null;
    }

    public EntityDTO updateEntity(String dssId, Map<String, Object> newValues) {
        Entity entity = _getEntityByDssId(dssId);
        if(entity == null){
            return null;
        }

        DTOHelper.updateModel(entity, newValues);
        entity.setLastUpdatedDate(LocalDateTime.now());
        return new EntityDTO(repository.save(entity));
    }
}
