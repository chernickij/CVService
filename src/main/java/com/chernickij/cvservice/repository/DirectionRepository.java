package com.chernickij.cvservice.repository;

import com.chernickij.cvservice.model.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

    Optional<Direction> findByName(String name);
}
