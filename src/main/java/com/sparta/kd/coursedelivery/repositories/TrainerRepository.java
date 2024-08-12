package com.sparta.kd.coursedelivery.repositories;

import com.sparta.kd.coursedelivery.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
}