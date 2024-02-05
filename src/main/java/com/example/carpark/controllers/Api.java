package com.example.carpark.controllers;
import com.example.carpark.models.*;
import com.example.carpark.repositories.CarsRepository;
import com.example.carpark.repositories.TechInspectionsRepository;
import com.example.carpark.util.CarErrorResponse;
import com.example.carpark.util.TechInspectionErrorResponse;
import com.example.carpark.util.CarNotFoundException;
import com.example.carpark.util.TechInspectionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Api {

    private final CarsRepository carsRepository;
    private final TechInspectionsRepository techInspectionsRepository;

    public Api(CarsRepository carsRepository, TechInspectionsRepository techInspectionsRepository) {
        this.carsRepository = carsRepository;
        this.techInspectionsRepository = techInspectionsRepository;
    }

    @GetMapping("/")
    public String getInfoApi(){
        return "Информация об автопарке (API)";
    }

    @GetMapping("cars")
    public List<CarsModel> getCars(){
        System.out.println(carsRepository.findAll());
        return carsRepository.findAll();
    }

    @GetMapping("techins")
    public List<TechInspectionsModel> getTechInspections(){
        System.out.println(techInspectionsRepository.findAll());
        return techInspectionsRepository.findAll();
    }
    @GetMapping("cars/{id}")
    public CarsModel getCarId(@PathVariable("id") int id){
        Optional<CarsModel> carsModelOptional = carsRepository.findById(id);
        return carsModelOptional.orElseThrow(CarNotFoundException::new);
    }
    @GetMapping("techins/{id}")
    public TechInspectionsModel getTechInspectionId(@PathVariable("id") int id){
        Optional<TechInspectionsModel> techInspectionsModel = techInspectionsRepository.findById(id);
        return techInspectionsModel.orElseThrow(CarNotFoundException::new);
    }

    @GetMapping("cars/delete/{id}")
    public String deleteCarId(@PathVariable("id") int id){
        carsRepository.deleteById(id);
        return "Автомобиль удалён";
    }

    @GetMapping("techins/delete/{id}")
    public String deleteTechInspectionId(@PathVariable("id") int id){
        techInspectionsRepository.deleteById(id);
        return "Данные тех. осмотра удалены";
    }

    @GetMapping("cars/add")
    public String addCar (@RequestParam("productionDate") String productionDate, @RequestParam("model") String model, @RequestParam("stateNumber") String stateNumber, @RequestParam("registerDate") String registerDate){

        CarsModel newCar = new CarsModel(productionDate, model, stateNumber, registerDate);
        carsRepository.save(newCar);
        return "Автомобиль добавлен";
    }

    @GetMapping("techins/add")
    public String addTechInspection (@RequestParam("cardNumber") String cardNumber, @RequestParam("dateInspections") String dateInspections, @RequestParam("comments") String comments, CarsModel carsModel){
        TechInspectionsModel newTechInspection = new TechInspectionsModel(cardNumber, dateInspections, comments, carsModel);
        techInspectionsRepository.save(newTechInspection);
        return "Данные тех. осмотра добавлены";
    }


    @ExceptionHandler
    public ResponseEntity<CarErrorResponse> handlerException(CarNotFoundException carNotFoundException){
        CarErrorResponse response = new CarErrorResponse("Не удалось найти автомобиль по данному id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<TechInspectionErrorResponse> handlerException(TechInspectionNotFoundException techInspectionNotFoundException){
        TechInspectionErrorResponse response = new TechInspectionErrorResponse("Не удалось найти данные тех. осмотра по данному id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/cars/list")
    public List<CarsModel> listCars() {
        return carsRepository.findAll();
    }
    @GetMapping("/techins/list")
    public List<TechInspectionsModel> listTechins() {
        return techInspectionsRepository.findAll();
    }
}