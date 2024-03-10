package com.chernickij.cvservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractEntity {

    @NotNull
    @Column(name = "ts_created", updatable = false, nullable = false)
    private Date created;

    @Column(name = "ts_updated")
    private Date updated;
}
