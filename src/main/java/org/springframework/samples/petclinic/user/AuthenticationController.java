/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

/**
 *
 * @author ACER
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class AuthenticationController {
    private static final String VIEWS_USUARIOS_FORM = "/register";
    private final UserRepository usuarios;
    private static final String VIEWS_USER_UPDATE_FORM = "users/update";
    
    public AuthenticationController(UserRepository user) {
        this.usuarios = user;
    }


        @RequestMapping(value = {"/", "/login" }, method = RequestMethod.GET)
        public ModelAndView login() {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("login"); 
                return modelAndView;
        }



        @RequestMapping(value = "/welcome", method = RequestMethod.GET)
        public ModelAndView home() {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("welcome"); 
                return modelAndView;
        }
        @RequestMapping(value = "/logout", method = RequestMethod.GET)
            public ModelAndView logout() {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("/login"); 
                return modelAndView;
        }

        @GetMapping("/register")
    public String initCreationForm(Map<String, Object> model) {
        Usuario usuario = new Usuario();
        model.put("user",usuario);
        return VIEWS_USUARIOS_FORM;
    }    

    @PostMapping("/register")
    public String processCreationForm(@Valid Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_USUARIOS_FORM;
        } else {
            usuario.setPassword(getMD5(usuario.getPassword()));
            usuario.setActivo(true);
            
            this.usuarios.save(usuario);
            return "redirect:/login";
        }
    }
    
     @GetMapping("/usuarios.html")
    public String showVetList(Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        Usuarios usuarios = new Usuarios();                
        usuarios.getUserList().addAll(this.usuarios.findAll());
        model.put("usuarios", usuarios);
        return "users/userList";
    }

    @GetMapping({ "/users" })
    public @ResponseBody Usuarios showResourcesVetList() {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for JSon/Object mapping
        Usuarios usuarios = new Usuarios();
        usuarios.getUserList().addAll(this.usuarios.findAll());
        return usuarios;
    }
    
    @GetMapping("/users/edit/{usuarioId}")
    public String initUpdateVetForm(@PathVariable("usuarioId") int usuarioId, Model model) {
         Usuario usuario = this.usuarios.findById(usuarioId);
        model.addAttribute(usuario);
        return VIEWS_USER_UPDATE_FORM;
    }
    
    @PostMapping("/users/edit/{usuarioId}")
    public String processUpdateOwnerForm(@Valid Usuario usuario, BindingResult result, @PathVariable("usuarioId") int usuarioId) {
        if (result.hasErrors()) {
            return VIEWS_USER_UPDATE_FORM;
        } else {
            usuario.setId(usuarioId);
            usuario.setPassword(getMD5(usuario.getPassword()));
            this.usuarios.save(usuario);
            return "redirect:/usuarios.html";
        }
    }
    
    @GetMapping("/users/delete/{usuarioId}")
    public String deleteVet(Usuario vet, BindingResult result, Map<String, Object> model,@PathVariable("usuarioId") int usuarioId) {
        this.usuarios.delete(this.usuarios.findById(usuarioId));
        Collection<Usuario> results = this.usuarios.findByName("");
        model.put("selections", result);
        return "redirect:/usuarios.html";       
    }
    
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }   
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}