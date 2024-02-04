package com.example.carpark.services;
import com.example.carpark.models.CarsModel;
import com.example.carpark.models.TechInspectionsModel;
import com.example.carpark.repositories.TechInspectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TechInspectionsServices {
    private final TechInspectionsRepository techInspectionsRepository;
    @Autowired
    public TechInspectionsServices(TechInspectionsRepository techInspectionsRepository) {
        this.techInspectionsRepository = techInspectionsRepository;
    }

    public List<TechInspectionsModel> getAllTechInspections(){
        return techInspectionsRepository.findAll();
    }
    @Transactional
    public void addTechInspection(TechInspectionsModel techInspection){
        techInspectionsRepository.save(techInspection);
    }

    @Transactional
    public void editTechInspection(int id, TechInspectionsModel techInspection){
        techInspection.setId(id);
        techInspectionsRepository.save(techInspection);
    }
    @Transactional
    public void deleteTechInspection(int id){
        techInspectionsRepository.deleteById(id);
    }
    public TechInspectionsModel getTechInspectionId(int id){
        Optional<TechInspectionsModel> thatTechIns = techInspectionsRepository.findById(id);
        return thatTechIns.orElse(null);
    }
}
