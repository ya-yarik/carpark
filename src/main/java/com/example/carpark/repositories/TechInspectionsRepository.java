package com.example.carpark.repositories;
import com.example.carpark.models.TechInspectionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
    public interface TechInspectionsRepository extends JpaRepository<TechInspectionsModel, Integer> {
}
