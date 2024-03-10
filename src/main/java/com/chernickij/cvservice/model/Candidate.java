package com.chernickij.cvservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate")
@EqualsAndHashCode(callSuper = true)
public class Candidate extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "candidate_id_seq", sequenceName = "candidate_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "candidate_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Column(name = "patronymic", nullable = false)
    private String patronymic;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "direction_candidate",
            joinColumns = {@JoinColumn(name = "candidate_id")},
            inverseJoinColumns = {@JoinColumn(name = "direction_id")})
    private List<Direction> directions;
}
