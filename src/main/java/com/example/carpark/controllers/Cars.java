package com.example.carpark.controllers;
import com.example.carpark.models.CarsModel;
import com.example.carpark.services.CarsServices;
import com.example.carpark.services.TechInspectionsServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.time.Year;

@Controller
@RequestMapping("/")
public class Cars {
    private final CarsServices carsServices;
    private final TechInspectionsServices techInspectionsServices;

    @Autowired
    public Cars(CarsServices carsServices, TechInspectionsServices techInspectionsServices) {
        this.carsServices = carsServices;
        this.techInspectionsServices = techInspectionsServices;
    }

    @GetMapping("")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/cars")
    public String allCars(Model model) {
        model.addAttribute("carForm", carsServices.getAllCars());
        model.addAttribute("techInsForm", techInspectionsServices.getAllTechInspections());
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
                model.addAttribute("error", "Машина с таким гос.номером уже существует");

                ObjectError error = new ObjectError("error", "Машина с таким гос.номером уже существует");
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
            model.addAttribute("error", "Ошибка при заполнении");
            ObjectError error = new ObjectError("error", "Ошибка при заполнении");
            scanEdit.addError(error);
            return "cars/edit";
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
        int carOlder = 0;
        int carYounger=0;
        for (CarsModel cars : carsServices.getAllCars()) {

            int yearResult = Year.now().getValue() - Integer.parseInt(cars.getProductionDate());

            if (yearResult > 3) {
                carOlder++;
            }
            if (yearResult < 3){
                carYounger++;
            }
        }
        model.addAttribute("reportForm", carOlder);
        model.addAttribute("reportFormTwo", carYounger);
        return "cars/report";
    }

    @GetMapping("/cars/list")
    public String carsPage() {
        return "cars/api-cars";
    }

    @GetMapping("/techins/list")
    public String techinsPage() {
        return "techins/api-techins";
    }
}
