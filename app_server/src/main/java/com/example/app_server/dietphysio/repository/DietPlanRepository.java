package com.example.app_server.dietphysio.repository;

import com.example.app_server.dietphysio.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
}
