package com.example.carpark.controllers;
import com.example.carpark.models.CarsModel;
import com.example.carpark.models.TechInspectionsModel;
import com.example.carpark.repositories.CarsRepository;
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
@RequestMapping("/techins")
public class TechInspections {

    private final CarsServices carsServices;
    private final CarsRepository carsRepository;
    private final TechInspectionsServices techInspectionsServices;

    @Autowired
    public TechInspections(CarsServices carsServices, CarsRepository carsRepository, TechInspectionsServices techInspectionsServices) {
        this.carsServices = carsServices;
        this.carsRepository = carsRepository;
        this.techInspectionsServices = techInspectionsServices;
    }

    @GetMapping("")
    public String allTechInspections(Model model) {
        model.addAttribute("techInsForm", techInspectionsServices.getAllTechInspections());
        return "techins/inspections";
    }

    @GetMapping("/add")
    public String addTechInspection(Model model) {
        model.addAttribute("techInsForm", new TechInspectionsModel());
        model.addAttribute("carsModel", carsRepository.findAll());
        return "techins/add";
    }

    @PostMapping("/add")
    public String addTechInspection(@ModelAttribute("techInsForm") @Valid TechInspectionsModel techIns, BindingResult scan, Model model,  @RequestParam("carsModel") int carsModel) {
        CarsModel carsModel1 = (CarsModel) carsRepository.findById(carsModel).orElseThrow();

        if (scan.hasErrors()) {
            model.addAttribute("carsModel", carsRepository.findAll());
            return "techins/add";
        }

        for (TechInspectionsModel techInspectionsModel :
                techInspectionsServices.getAllTechInspections()) {
            if (techInspectionsModel.getCardNumber().equals(techIns.getCardNumber())) {
                model.addAttribute("error", "Данные техосмотра с таким номером карты уже существуют");

                ObjectError error = new ObjectError("error", "Данные техосмотра с таким номером карты уже существуют");
                scan.addError(error);
                return "techins/add";

            }
        }
        techInspectionsServices.addTechInspection(techIns, carsModel1);
        return "redirect:/techins";
    }

    @GetMapping("/{id}")
    public String aboutTechInspection(Model model, @PathVariable("id") int id) {
        model.addAttribute("carForm", carsServices.getCarId(id));
        model.addAttribute("techInsForm", techInspectionsServices.getAllTechInspections());
        return "techins/selection";
    }

    @GetMapping("/edit/{id}")
    public String editTechInspection(Model model, @PathVariable("id") int id) {
        model.addAttribute("techInsForm", techInspectionsServices.getTechInspectionId(id));
        model.addAttribute("carsModel", carsRepository.findAll());
        return "techins/edit";
    }

    @PostMapping("/edit/{id}")
    public String editTechInspection(@ModelAttribute("techInsForm") @Valid TechInspectionsModel techIns, BindingResult scanEdit, @PathVariable("id") int id, Model model) {

        if (scanEdit.hasErrors()) {
            model.addAttribute("carsModel", carsRepository.findAll());
            return "techins/edit";
        }

        for (TechInspectionsModel techInspectionsModel :
                techInspectionsServices.getAllTechInspections()) {
            if (techInspectionsModel.getCardNumber().equals(techIns.getCardNumber())) {
                model.addAttribute("error", "Машина с таким госномером уже существует");

                ObjectError error = new ObjectError("error", "Машина с таким госномером уже существует");
                scanEdit.addError(error);
                return "techins/edit";

            }
        }
        techInspectionsServices.editTechInspection(id, techIns);
        return "redirect:/techins/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteTechInspection(@PathVariable("id") int id) {
        techInspectionsServices.deleteTechInspection(id);
        return "redirect:/techins";
    }
}