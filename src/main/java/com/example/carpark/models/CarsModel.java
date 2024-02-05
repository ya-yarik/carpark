package com.example.carpark.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.Year;
import java.util.List;

@Entity
@Table(name="cars")
public class CarsModel {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="production_date")
    @NotEmpty(message="Поле 'Год выпуска' не может быть пустым")
    @Size(min=4, max=4)
    private String productionDate;
    @NotEmpty(message="Поле 'Модель' не может быть пустым")
    @Column(name="model")
    @Size(max=50)
    private String model;
    @NotEmpty(message="Поле 'Госномер' не может быть пустым")
    @Column(name="state_number")
    @Size(min = 8, max=8)
    private String stateNumber;
    @NotEmpty(message="Поле 'Дата постановки на учёт' не может быть пустым")
    @Column(name="register_date")
//    @Size(min=8, max=8)
    private String registerDate;
    @OneToMany(mappedBy = "carsModel", fetch = FetchType.EAGER)
    private List<TechInspectionsModel> techInspectionsModels;

    @Transient
    int carOlder;
    @Transient
    int carYounger;
    @Transient
    int yearResult;


    public CarsModel(int id, String productionDate, String model, String stateNumber, String registerDate, List<TechInspectionsModel> techInspectionsModels) {
        this.id = id;
        this.productionDate = productionDate;
        this.model = model;
        this.stateNumber = stateNumber;
        this.registerDate = registerDate;
        this.techInspectionsModels = techInspectionsModels;
    }

    public CarsModel(String productionDate, String model, String stateNumber, String registerDate) {
        this.productionDate = productionDate;
        this.model = model;
        this.stateNumber = stateNumber;
        this.registerDate = registerDate;
    }

    public CarsModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public List<TechInspectionsModel> getTechInspectionsModels() {
        return techInspectionsModels;
    }

    public void setTechInspectionsModels(List<TechInspectionsModel> techInspectionsModels) {
        this.techInspectionsModels = techInspectionsModels;
    }

    @Override
    public String toString() {
        return stateNumber;
    }

    public int CarsOld() {
        yearResult = Year.now().getValue() - Integer.parseInt(productionDate);
        carOlder=0;
        if (yearResult > 3){
            carOlder++;
        }
        return carOlder;
    }

    public int CarsYoung() {
        carYounger=0;
        yearResult = Year.now().getValue() - Integer.parseInt(productionDate);
        if (yearResult < 3){
            carYounger++;
        }
        return carYounger;
    }












//+carYounger
//carYounger++;
//        return carYounger;

//    for (String car : countries) {
//        System.out.println(country);
//    }

    public int getCarOlder() {
        return carOlder;
    }

    public void setCarOlder(int carOlder) {
        this.carOlder = carOlder;
    }

    public int getCarYounger() {
        return carYounger;
    }

    public void setCarYounger(int carYounger) {
        this.carYounger = carYounger;
    }

    public int getYearResult() {
        return yearResult;
    }

    public void setYearResult(int yearResult) {
        this.yearResult = yearResult;
    }
}