package com.example.carpark.controllers;
import com.example.carpark.models.CarsModel;
import com.example.carpark.models.TechInspectionsModel;
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

@Controller
@RequestMapping("/techInspections")
public class TechInspections {

    private final CarsServices carsServices;
    private final CarsRepository carsRepository;
    private final TechInspectionsServices techInspectionsServices;
    private final TechInspectionsRepository techInspectionsRepository;

    @Autowired
    public TechInspections(CarsServices carsServices, CarsRepository carsRepository, TechInspectionsServices techInspectionsServices, TechInspectionsRepository techInspectionsRepository) {
        this.carsServices = carsServices;
        this.carsRepository = carsRepository;
        this.techInspectionsServices = techInspectionsServices;
        this.techInspectionsRepository = techInspectionsRepository;
    }

    @GetMapping("")
    public String allTechInspections(Model model) {
        model.addAttribute("techInsForm", techInspectionsServices.getAllTechInspections());
        return "techIns";
    }

    @GetMapping("techInspections/add")
    public String addTechInspection(Model model) {
        model.addAttribute("techInsForm", new TechInspectionsModel());
        return "addTechIns";
    }

    @PostMapping("techInspections/add")
    public String addTechInspection(@ModelAttribute("techInsForm") @Valid TechInspectionsModel techIns, BindingResult scan, Model model) {

        if (scan.hasErrors()) {
            return "addTechIns";
        }

        for (TechInspectionsModel techInspectionsModel :
                techInspectionsServices.getAllTechInspections()) {
            if (techInspectionsModel.getCardNumber().equals(techIns.getCardNumber())) {
                model.addAttribute("error", "Данные техосмотра с таким номером карты уже существуют");

                ObjectError error = new ObjectError("error", "Данные техосмотра с таким номером карты уже существуют");
                scan.addError(error);
                return "addTechIns";

            }
        }
        techInspectionsServices.addTechInspection(techIns);
        return "redirect:/techInspections";
    }

    @GetMapping("techInspections/{id}")
    public String aboutTechInspection(Model model, @PathVariable("id") int id) {
        model.addAttribute("techInsForm", techInspectionsServices.getTechInspectionId(id));
        return "selectTechIns";
    }

    @GetMapping("techInspections/edit/{id}")
    public String editTechInspection(Model model, @PathVariable("id") int id) {
        model.addAttribute("techInsForm", techInspectionsServices.getTechInspectionId(id));
        return "editTechIns";
    }
    @PostMapping("techInspections/edit/{id}")
    public String editTechInspection(@ModelAttribute("techInsForm") @Valid TechInspectionsModel techIns, BindingResult scanEdit, @PathVariable("id") int id, Model model) {

        if (scanEdit.hasErrors()) {
            return "editTechIns";
        }

        for (TechInspectionsModel techInspectionsModel :
                techInspectionsServices.getAllTechInspections()) {
            if (techInspectionsModel.getCardNumber().equals(techIns.getCardNumber())) {
                model.addAttribute("error", "Машина с таким госномером уже существует");

                ObjectError error = new ObjectError("error", "Машина с таким госномером уже существует");
                scanEdit.addError(error);
                return "editTechIns";

            }
        }
        techInspectionsServices.editTechInspection(id, techIns);
        return "redirect:/techInspections/" + id;
    }

    @GetMapping("techInspections/delete/{id}")
    public String deleteTechInspection(@PathVariable("id") int id) {
        techInspectionsServices.deleteTechInspection(id);
        return "redirect:/techInspections";
    }

}