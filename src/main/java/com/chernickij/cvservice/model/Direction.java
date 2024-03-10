package com.chernickij.cvservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "direction")
@EqualsAndHashCode(callSuper = true)
public class Direction extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "direction_id_seq", sequenceName = "direction_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "direction_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;
}
