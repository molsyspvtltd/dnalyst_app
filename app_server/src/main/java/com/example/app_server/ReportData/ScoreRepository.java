package com.example.app_server.ReportData;

import com.example.app_server.ReportData.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findBySubcategory(Subcategory subcategory);
}