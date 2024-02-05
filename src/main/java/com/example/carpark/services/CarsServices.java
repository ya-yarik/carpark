package com.example.carpark.services;
import com.example.carpark.models.CarsModel;
import com.example.carpark.repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class CarsServices {
private final CarsRepository carsRepository;
@Autowired
public CarsServices(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }
public List<CarsModel> getAllCars(){
    return carsRepository.findAll();
}
@Transactional
public void addCar(CarsModel car){
        carsRepository.save(car);
    }

@Transactional
public void editCar(int id, CarsModel car){
    car.setId(id);
    carsRepository.save(car);
}
@Transactional
public void deleteCar(int id){
        carsRepository.deleteById(id);
    }
public CarsModel getCarId(int id){
        Optional<CarsModel> thatCar = carsRepository.findById(id);
        return thatCar.orElse(null);
    }
//    public int carsYear(String productionDate) {
//        int nowYear = Year.now().getValue();
//        int oldYear = Integer.parseInt(productionDate);
//        int yearResult=nowYear-oldYear;
//        int carOlder=0;
//        int carYounger=0;
//        if (yearResult > 3) {
//            carOlder++;
//            return carOlder;
//        }
//        else {
//            carYounger++;
//            return carYounger;
//        }


//    public int carsYear(String productionDate) {
//        int nowYear = Year.now().getValue();
//        int oldYear = Integer.parseInt(productionDate);
//        int yearResult = nowYear - oldYear;
//        int carOlder = 0;
//        int carYounger = 0;
//        if (yearResult > 3) {
//            carOlder++;
//            return carOlder;
//        } else {
//            carYounger++;
//            return carYounger;
//        }
//    }

//        public int carsYear(CarsModel carsModel) {
//            int nowYear = Year.now().getValue();
//            int oldYear = Integer.parseInt(carsModel.getProductionDate());
//            int yearResult = nowYear - oldYear;
//            int carOlder = 0;
//            int carYounger = 0;
//            if (yearResult > 3) {
//                carOlder++;
//                return carOlder;
//            } else {
//                carYounger++;
//                return carYounger;
//            }
//        }

}