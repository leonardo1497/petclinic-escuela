/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import java.util.Collection;
import java.util.Map;
import org.springframework.samples.petclinic.vet.Vets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ACER
 */
@Controller
public class sesionesController {
    private final sesionesRepository sesions;
    public sesionesController(sesionesRepository clinicService) {
        this.sesions = clinicService;
    }

    
    @GetMapping("/iniciosSesion.html")
    public String showVetList(Map<String, Object> model) {          
        Collection<Sesion> sesiones = this.sesions.findAll();
        model.put("sesiones", sesiones);
        return "iniciosSesion";
    }
  
    
}
