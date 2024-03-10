package com.chernickij.cvservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate_result")
@EqualsAndHashCode(callSuper = true)
public class CandidateResult extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "candidate_result_id_seq", sequenceName = "candidate_result_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "candidate_result_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "ts_created", nullable = false)
    private Date date;

    @NotNull
    @Column(name = "name", nullable = false)
    private int mark;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;
}
