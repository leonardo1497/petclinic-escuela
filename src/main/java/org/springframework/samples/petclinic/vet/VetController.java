/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import java.util.Map;
import org.springframework.samples.petclinic.medicament.Medicament;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.web.bind.annotation.ModelAttribute;



/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
class VetController {
    
    private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM = "vets/createorupdateVetForm";
    private final VetRepository vets;

    public VetController(VetRepository clinicService) {
        this.vets = clinicService;
    }
            
    @GetMapping("/vets.html")
    public String showVetList(Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        Vets vets = new Vets();                
        vets.getVetList().addAll(this.vets.findAll());
        model.put("vets", vets);
        return "vets/vetList";
    }

    @GetMapping({ "/vets" })
    public @ResponseBody Vets showResourcesVetList() {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for JSon/Object mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        return vets;
    }
    
    @GetMapping("/vets/new")
    public String initCreationForm(Map<String, Object> model) {
        Vet vet = new Vet();        
        model.put("vet", vet);        
        return VIEWS_VET_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/vets/new")
    public String processCreationForm(@Valid Vet vet, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_VET_CREATE_OR_UPDATE_FORM;
        } else {
            this.vets.save(vet);            
            return "redirect:/vets.html";
        }
    }           
    
    @GetMapping("/vets/edit/{vetId}")
    public String initUpdateVetForm(@PathVariable("vetId") int vetId, Model model) {
        Vet vet = this.vets.findById(vetId);
        model.addAttribute(vet);
        return VIEWS_VET_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/vets/edit/{vetId}")
    public String processUpdateOwnerForm(@Valid Vet vet, BindingResult result, @PathVariable("vetId") int vetId) {
        if (result.hasErrors()) {
            return VIEWS_VET_CREATE_OR_UPDATE_FORM;
        } else {
            vet.setId(vetId);
            this.vets.save(vet);
            return "redirect:/vets.html";
        }
    }
    
    @GetMapping("/vet/delete/{vetId}")
    public String deleteVet(Vet vet, BindingResult result, Map<String, Object> model,@PathVariable("vetId") int vetId) {
        this.vets.delete(this.vets.findById(vetId));
        Collection<Vet> results = this.vets.findByName("");
        model.put("selections", result);
        return "redirect:/vets.html";       
    }
    
    @ModelAttribute("specialties")
    public Collection<Specialty> populatSpecialty() {
        return this.vets.findSpecialityTypes();
    }
    
    @GetMapping("/reporte")
    public String showVets(Vet vet, BindingResult result, Map<String, Object> model){
          Collection<Vet> results =this.vets.findAll();
          model.put("selections", results);
        return "/vets/ReporteVets";
    }
//     @GetMapping({ "/vets/new" })
//    public @ResponseBody Vets showList() {
//        Vets vets = new Vets();
//        vets.getVetList().addAll(this.vets.findAll());
//        return vets;
//    }

}
