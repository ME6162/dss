package com.rapidapi.core.dss.field.model;

import com.rapidapi.core.dss.common.annotations.CanUpdate;
import com.rapidapi.core.dss.entity.model.Entity;
import com.rapidapi.core.dss.field.enums.FieldType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@jakarta.persistence.Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @NotNull
    @Indexed
    @CanUpdate
    private String name;

    @CanUpdate
    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)  // Maps enum to a string column in the DB
    private FieldType type;


    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "entity_id", nullable = false)
    private Entity entity;
}
