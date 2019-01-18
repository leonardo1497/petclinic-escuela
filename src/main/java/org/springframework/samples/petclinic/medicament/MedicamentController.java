/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.medicament;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Isaac
 */

@Controller
class MedicamentController {
    private static final String VIEWS_MEDICAMENT_CREATE_OR_UPDATE_FORM = "medicaments/createOrUpdateMedicamentForm";
    private final MedicamentRepository medicaments;

    public MedicamentController(MedicamentRepository clinicService) {
        this.medicaments = clinicService;
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    
    @GetMapping("/medicament/new")
    public String initCreationForm(Map<String, Object> model) {
        Medicament medicament = new Medicament();
        model.put("medicament", medicament);
        return VIEWS_MEDICAMENT_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/medicament/new")
    public String processCreationForm(@Valid Medicament medicament, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_MEDICAMENT_CREATE_OR_UPDATE_FORM;
        } else {
            this.medicaments.save(medicament);
            return "redirect:/medicaments/";
        }
    }
    
    @GetMapping("/medicament/find")
    public String initFindForm(Map<String, Object> model) {
        Medicament medicament = new Medicament();
        model.put("medicament", medicament);
        return "medicaments/findMedicaments";
    }
    
    @GetMapping("/medicaments")
    public String processFindForm(Medicament medicament, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (medicament.getNombre() == null) {
            medicament.setNombre(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Medicament> results = this.medicaments.findByName(medicament.getNombre());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("nombre", "notFound", "not found");
            return "medicaments/findMedicaments";
        } else if (results.size() == 1) {
            // 1 owner found
            medicament = results.iterator().next();
            return "redirect:/medicament/" + medicament.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "medicaments/medicamentsList";
        }
    }
    
    @GetMapping("/medicament/{medId}")
    public ModelAndView showOwner(@PathVariable("medId") int medId) {
        ModelAndView mav = new ModelAndView("medicaments/medicamentDetails");
        mav.addObject(this.medicaments.findById(medId));
        return mav;
    }
    
    @GetMapping("/medicament/delete/{medId}")
    public String deleteMedicament(Medicament medicament, BindingResult result, Map<String, Object> model,@PathVariable("medId") int medId) {
        this.medicaments.delete(this.medicaments.findById(medId));
        Collection<Medicament> results = this.medicaments.findByName("");
        model.put("selections", results);
        return "redirect:/medicaments";
    }
    
    @GetMapping("/medicament/edit/{medId}")
    public String initUpdateMedicamentForm(@PathVariable("medId") int medId, Model model) {
        Medicament medicament = this.medicaments.findById(medId);
        model.addAttribute(medicament);
        return VIEWS_MEDICAMENT_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/medicament/edit/{medId}")
    public String processUpdateOwnerForm(@Valid Medicament medicament, BindingResult result, @PathVariable("medId") int medId) {
        if (result.hasErrors()) {
            return VIEWS_MEDICAMENT_CREATE_OR_UPDATE_FORM;
        } else {
            medicament.setId(medId);
            this.medicaments.save(medicament);
            return "redirect:/medicament/{medId}";
        }
    }
}
