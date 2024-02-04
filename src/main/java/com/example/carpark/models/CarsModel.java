package com.example.carpark.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Entity(name="cars")
public class CarsModel {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="production_date")
    @NotEmpty(message="Поле 'Дата выпуска' не может быть пустым")
    @Size(min=8, max=10)
    private Date productionDate;
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
    @Size(min=8, max=10)
    private String registerDate;
    @OneToMany(mappedBy = "tech_inspections", fetch = FetchType.EAGER)
    private List<TechInspectionsModel> techInspectionsModels;
    private int carOlder;
    private int carYounger;
    private int yearResult;
    int carYear = productionDate.getYear();

    public CarsModel(int id, Date productionDate, String model, String stateNumber, String registerDate, List<TechInspectionsModel> techInspectionsModels, int carsOlder, int carYear) {
        this.id = id;
        this.productionDate = productionDate;
        this.model = model;
        this.stateNumber = stateNumber;
        this.registerDate = registerDate;
        this.techInspectionsModels = techInspectionsModels;
    }

    public CarsModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
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
    public void CarsYear(int carYear) {
        int nowYear = Year.now().getValue();
        yearResult=nowYear-carYear;
        if (yearResult > 3) {
            carOlder=carOlder++;
            }
        else {
            carYounger=carYounger++;
        }

    }

}