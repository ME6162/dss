package com.rapidapi.core.dss.field.controller;

import com.rapidapi.core.dss.field.model.Field;
import com.rapidapi.core.dss.field.services.FieldService;
import com.rapidapi.core.dss.common.services.IResponseFormater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/v1/field")
public class FieldController {

    @Autowired
    private FieldService service;

    @Autowired
    private IResponseFormater responseFormater;

    @GetMapping
    public ResponseEntity<Page<Field>> getFilteredFields(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String entity,
                                                         Pageable pageable) {

        Page<Field> Entities = service.getFilteredFields(name, entity, pageable);

        return ResponseEntity.ok(Entities);
    }

    @GetMapping("/{dssId}")
    public ResponseEntity<Object> getField(@PathVariable String dssId) {
        Field Field = service.getFieldByDssId(dssId);
        if (Field == null) {
            return responseFormater.getNotFound();
        }
        return ResponseEntity.ok(Field);
    }

    @PostMapping
    public ResponseEntity<Object> createField(@RequestBody Field Field) {
        try {
            Field createdField = service.addField(Field);
            return ResponseEntity.ok(createdField);
        } catch (RuntimeException e) {
            return responseFormater.getBadRequest(e.getMessage());
        }
    }

    @PutMapping("/{dssId}")
    public ResponseEntity<Object> updateField(@PathVariable String dssId, @RequestBody Map<String, Object> Field) {
        Field updatedField = service.updateField(dssId, Field);
        if (updatedField == null) {
            return responseFormater.getNotFound();
        }
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/{dssId}")
    public ResponseEntity<Void> deleteField(@PathVariable String dssId) {
        service.deleteField(dssId);
        return ResponseEntity.noContent().build();
    }
}
