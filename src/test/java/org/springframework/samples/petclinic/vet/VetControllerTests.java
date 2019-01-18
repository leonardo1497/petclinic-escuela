package org.springframework.samples.petclinic.vet;

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
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetController;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
/**
 * Test class for the {@link VetController}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(VetController.class)
public class VetControllerTests {
    
    private static final int TEST_VET_ID = 1;    
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VetRepository vets;
    
    private Vet vet;
    
    @Before
    public void setup() {
        vet = new Vet();
        vet.setFirstName("James");
        vet.setLastName("Carter");
        vet.setTelephone("9612594528");
        vet.setBusiness_hours("12:13");
        vet.setId(1);
        given(this.vets.findById(TEST_VET_ID)).willReturn(vet);
    } 
    /**
     * paso la prueba
     */
    @Test
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/vets/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("vet"))
            .andExpect(view().name("vets/createorupdateVetForm"));
    }
    
    
    
//    @Test
//    public void testProcessCreationFormHasErrors() throws Exception {
//        mockMvc.perform(post("/vets/new")
//            .param("first_name", "James")
//            .param("last_name", "Carter")
//            .param("telephone", "9612594528")
//            
//        )
//            .andExpect(status().isOk())
//            .andExpect(model().attributeHasErrors("vet"))
//            .andExpect(model().attributeHasFieldErrors("vet", "business_hours"))
//            .andExpect(view().name("vets/createorupdateVetForm"));
//    }           
    /*
    *Paso la prueba pero incompleta
    */
    @Test
    public void testInitUpdateVetForm() throws Exception {
        mockMvc.perform(get("/vets/edit/{vetId}", TEST_VET_ID))
            .andExpect(status().isOk())
            //.andExpect(model().attributeExists("vet"))                
            .andExpect(model().attributeExists("vet"))            
            .andExpect(model().attribute("vet", hasProperty("telephone", is("9612594528"))))
            .andExpect(model().attribute("vet", hasProperty("business_hours", is("12:13"))))
            .andExpect(view().name("vets/createorupdateVetForm"));
    }
    
    /**
     * Paso la prueba
     */
    @Test
    public void testDeleteVet() throws Exception {
        mockMvc.perform(get("/vet/delete/{vetId}", TEST_VET_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/vets.html"));
    }
    
    //PruebA rAUL
    @Test
    public void testMostarVets() throws Exception{
        mockMvc.perform(get("/reporte"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vet"))
                .andExpect(view().name("/vets/ReporteVets"));
    }
}
