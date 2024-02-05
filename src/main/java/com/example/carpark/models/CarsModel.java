package com.example.carpark.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;
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
//    private int carOlder;
//    private int carYounger;
//    private int yearResult;
//    int carYear;

    public CarsModel(int id, String productionDate, String model, String stateNumber, String registerDate, List<TechInspectionsModel> techInspectionsModels) {
        this.id = id;
        this.productionDate = productionDate;
        this.model = model;
        this.stateNumber = stateNumber;
        this.registerDate = registerDate;
        this.techInspectionsModels = techInspectionsModels;
    }

    public CarsModel() {

    }

//    public CarsModel() {
//        carYear = productionDate.getYear();
//    }

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

    //    public void CarsYear(int carYear) {
//        int nowYear = Year.now().getValue();
//        yearResult=nowYear-carYear;
//        if (yearResult > 3) {
//            carOlder=carOlder++;
//            }
//        else {
//            carYounger=carYounger++;
//        }
//
//    }

}