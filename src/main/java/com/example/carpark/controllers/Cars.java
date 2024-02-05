package com.example.carpark.controllers;

import com.example.carpark.models.CarsModel;
import com.example.carpark.repositories.CarsRepository;
import com.example.carpark.repositories.TechInspectionsRepository;
import com.example.carpark.services.CarsServices;
import com.example.carpark.services.TechInspectionsServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class Cars {
    private final CarsServices carsServices;
    private final CarsRepository carsRepository;
    private final TechInspectionsServices techInspectionsServices;
    private final TechInspectionsRepository techInspectionsRepository;

    @Autowired
    public Cars(CarsServices carsServices, CarsRepository carsRepository, TechInspectionsServices techInspectionsServices, TechInspectionsRepository techInspectionsRepository) {
        this.carsServices = carsServices;
        this.carsRepository = carsRepository;
        this.techInspectionsServices = techInspectionsServices;
        this.techInspectionsRepository = techInspectionsRepository;
    }

    @GetMapping("")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/cars")
    public String allCars(Model model) {
        model.addAttribute("carForm", carsServices.getAllCars());
        return "cars/cars";
    }

    @GetMapping("cars/add")
    public String addCar(Model model) {
        model.addAttribute("carForm", new CarsModel());
        return "cars/add";
    }

    @PostMapping("cars/add")
    public String addCar(@ModelAttribute("carForm") @Valid CarsModel car, BindingResult scan, Model model) {

        if (scan.hasErrors()) {
            return "cars/add";
        }

        for (CarsModel carsModel :
                carsServices.getAllCars()) {
            if (carsModel.getStateNumber().equals(car.getStateNumber())) {
                model.addAttribute("error", "Машина с таким госномером уже существует");

                ObjectError error = new ObjectError("error", "Машина с таким госномером уже существует");
                scan.addError(error);
                return "cars/add";

            }
        }

        carsServices.addCar(car);
        return "redirect:/cars";
    }

    @GetMapping("cars/{id}")
    public String aboutCar(Model model, @PathVariable("id") int id) {
        model.addAttribute("carForm", carsServices.getCarId(id));
        return "cars/selection";
    }

    @GetMapping("cars/edit/{id}")
    public String editCar(Model model, @PathVariable("id") int id) {
        model.addAttribute("carForm", carsServices.getCarId(id));
        return "cars/edit";
    }
    @PostMapping("cars/edit/{id}")
    public String editCar(@ModelAttribute("carForm") @Valid CarsModel car, BindingResult scanEdit, @PathVariable("id") int id, Model model) {

        if (scanEdit.hasErrors()) {
            return "cars/edit";
        }

        for (CarsModel carsModel :
                carsServices.getAllCars()) {
            if (carsModel.getStateNumber().equals(car.getStateNumber())) {
                model.addAttribute("error", "Машина с таким госномером уже существует");

                ObjectError error = new ObjectError("error", "Машина с таким госномером уже существует");
                scanEdit.addError(error);
                return "cars/edit";

            }
        }
        carsServices.editCar(id, car);
        return "redirect:/cars/" + id;
    }

    @GetMapping("cars/delete/{id}")
    public String deleteCar(@PathVariable("id") int id) {
        carsServices.deleteCar(id);
        return "redirect:/cars";
    }

    @GetMapping("cars/report")
    public String report(Model model) {
        model.addAttribute("reportForm", carsServices.getAllCars());
        return "cars/report";
    }


}