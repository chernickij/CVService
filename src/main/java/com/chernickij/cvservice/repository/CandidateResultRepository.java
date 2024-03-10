package com.chernickij.cvservice.repository;

import com.chernickij.cvservice.model.CandidateResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateResultRepository extends JpaRepository<CandidateResult, Long> {
}
