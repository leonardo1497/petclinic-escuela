/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.medicament;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.medicament.Medicament;
import org.springframework.samples.petclinic.medicament.MedicamentController;
import org.springframework.samples.petclinic.medicament.MedicamentRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
/**
 *
 * @author Jose Pablo
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MedicamentController.class)
public class MedicamentControllerTest {
    private static final int TEST_OWNER_ID = 10;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicamentRepository medicaments;

    private Medicament med;
    
    @Before
    public void setup() {
        med = new Medicament();
        med.setNombre("Amoxcin");
        med.setPresentacion("Tabletas");
        med.setIngredientes("taquitos :3");
        med.setId(10);
        given(this.medicaments.findById(TEST_OWNER_ID)).willReturn(med);
    } 
    
    @Test
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/medicament/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("medicament"))
            .andExpect(view().name("medicaments/createOrUpdateMedicamentForm"));
    }
    
    @Test
    public void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/medicament/new")
            .param("nombre", "Parace")
            .param("ingredientes", "huevitos")
            .param("presentacion", "tabletas")
        )
            .andExpect(status().is3xxRedirection());
    }
    
    @Test
    public void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post("/medicament/new")
            .param("nombre", "Petximo")
            .param("presentacion", "Azucar")
        )
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("medicament"))
            .andExpect(model().attributeHasFieldErrors("medicament", "ingredientes"))
            .andExpect(view().name("medicaments/createOrUpdateMedicamentForm"));
    }
    
    @Test
    public void testInitFindForm() throws Exception {
        mockMvc.perform(get("/medicament/find"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("medicament"))
            .andExpect(view().name("medicaments/findMedicaments"));
    }
    
    @Test
    public void testProcessFindFormSuccess() throws Exception {
        given(this.medicaments.findByName("")).willReturn(Lists.newArrayList(med, new Medicament()));
        mockMvc.perform(get("/medicaments"))
            .andExpect(status().isOk())
            .andExpect(view().name("medicaments/medicamentsList"));
    }
    
    @Test
    public void testInitUpdateMedicamentForm() throws Exception {
        mockMvc.perform(get("/medicament/edit/{medId}", TEST_OWNER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("medicament"))
            .andExpect(model().attribute("medicament", hasProperty("nombre", is("Amoxcin"))))
            .andExpect(model().attribute("medicament", hasProperty("ingredientes", is("taquitos :3"))))
            .andExpect(model().attribute("medicament", hasProperty("presentacion", is("Tabletas"))))
            .andExpect(view().name("medicaments/createOrUpdateMedicamentForm"));
    }
    
    @Test
    public void testProcessUpdateMedicamentFormSuccess() throws Exception {
        mockMvc.perform(post("/medicament/edit/{medId}", TEST_OWNER_ID)
            .param("nombre", "Petzin")
            .param("ingredientes", "Huevito :3")
            .param("presentacion", "en bolita")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/medicament/{medId}"));
    }
    
    @Test
    public void testDeleteMedicament() throws Exception {
        mockMvc.perform(get("/medicament/delete/{medId}", TEST_OWNER_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/medicaments"));
    }
}

