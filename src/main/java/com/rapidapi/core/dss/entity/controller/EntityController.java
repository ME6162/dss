package com.rapidapi.core.dss.entity.controller;

import com.rapidapi.core.dss.entity.dto.EntityDTO;
import com.rapidapi.core.dss.entity.services.EntityService;
import com.rapidapi.core.dss.common.services.IResponseFormater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/entity")
public class EntityController {

    @Autowired
    private EntityService service;


    @Autowired
    private IResponseFormater responseFormater;

    @GetMapping
    public ResponseEntity<Page<EntityDTO>> getFilteredEntities(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String application,
                                                            Pageable pageable) {

        Page<EntityDTO> Entities = service.getFilteredEntities(name, application, pageable);

        return ResponseEntity.ok(Entities);
    }

    @GetMapping("/{dssId}")
    public ResponseEntity<Object> getEntity(@PathVariable String dssId) {
        EntityDTO entity = service.getEntityByDssId(dssId);
        if (entity == null) {
            return responseFormater.getNotFound();
        }
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity<Object> createEntity(@RequestBody EntityDTO entityDTO) {
        try {
            EntityDTO createdEntity = service.addEntity(entityDTO);
            return ResponseEntity.ok(createdEntity);
        } catch (RuntimeException e) {
            return responseFormater.getBadRequest(e.getMessage());
        }
    }

    @PutMapping("/{dssId}")
    public ResponseEntity<Object> updateEntity(@PathVariable String dssId, @RequestBody Map<String, Object> entity) {
        EntityDTO updatedEntity = service.updateEntity(dssId, entity);
        if (updatedEntity == null) {
            return responseFormater.getNotFound();
        }
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{dssId}")
    public ResponseEntity<Void> deleteEntity(@PathVariable String dssId) {
        service.deleteEntity(dssId);
        return ResponseEntity.noContent().build();
    }
}
