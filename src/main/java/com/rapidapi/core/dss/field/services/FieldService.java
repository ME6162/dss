package com.rapidapi.core.dss.field.services;

import com.rapidapi.core.dss.entity.model.Entity;
import com.rapidapi.core.dss.field.model.Field;
import com.rapidapi.core.dss.field.repositories.IFieldRepository;
import com.rapidapi.core.dss.common.util.DTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class FieldService {

    @Autowired
    private IFieldRepository repository;


    public Field getFieldById(long id) {
        return repository.findById(id).orElse(null);
    }

    public Field addField(Field Field) {
        Field.setCreatedDate(LocalDateTime.now());
        Field.setLastUpdatedDate(LocalDateTime.now());
        return repository.save(Field);
    }

    public void deleteField(String dssId) {
        long id = DTOHelper.getIdFromDssId(dssId, Field.class);
        repository.deleteById(id);
    }

    public Page<Field> getFilteredFields(String name, String dssEntityId, Pageable pageable) {
        Long entityId = null;
        if(dssEntityId != null){
            entityId = DTOHelper.getIdFromDssId(dssEntityId, Entity.class);
        }
        if (entityId != null && name != null) {
            return repository.findByEntityIdAndNameContainingIgnoreCase(entityId, name, pageable);
        } else if (entityId != null) {
            return repository.findByEntityId(entityId, pageable);
        } else if (name != null) {
            return repository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            return repository.findAll(pageable);
        }
    }


    public Field getFieldByDssId(String dssId) {
        long id = DTOHelper.getIdFromDssId(dssId, Field.class);

        return getFieldById(id);
    }

    public Field updateField(String dssId, Map<String, Object> newValues) {
        Field Field = getFieldByDssId(dssId);
        if(Field == null){
            return null;
        }

        DTOHelper.updateModel(Field, newValues);
        Field.setLastUpdatedDate(LocalDateTime.now());
        return repository.save(Field);
    }
}
