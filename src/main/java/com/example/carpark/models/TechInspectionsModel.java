package com.example.carpark.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity(name="tech_inspections")
public class TechInspectionsModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "card_number")
    @NotEmpty(message = "Поле 'Номер карты' не может быть пустым")
    @Size(min = 15, max = 15)
    private Date cardNumber;
    @NotEmpty(message = "Поле 'Дата техосмотра' не может быть пустым")
    @Column(name = "date_inspections")
    @Size(min = 8, max = 10)
    private String dateInspections;
    @Column(name = "description")
    private String comments;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private CarsModel carsModel;

    public TechInspectionsModel(int id, Date cardNumber, String dateInspections, String comments, CarsModel carsModel) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.dateInspections = dateInspections;
        this.comments = comments;
        this.carsModel = carsModel;
    }

    public TechInspectionsModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Date cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDateInspections() {
        return dateInspections;
    }

    public void setDateInspections(String dateInspections) {
        this.dateInspections = dateInspections;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CarsModel getCarsModel() {
        return carsModel;
    }

    public void setCarsModel(CarsModel carsModel) {
        this.carsModel = carsModel;
    }
}