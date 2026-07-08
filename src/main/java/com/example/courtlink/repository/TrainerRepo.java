package com.example.courtlink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courtlink.entity.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer,Long>{
}
