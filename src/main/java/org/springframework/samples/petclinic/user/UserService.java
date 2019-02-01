/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.medicament.MedicamentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 *
 * @author ACER
 */
@Service("UserService")
public class UserService implements UserDetailsService{
    String emailUser="";
    @Autowired
    @Qualifier("sesionesRepository")
    private sesionesRepository init;
    @Autowired
    @Qualifier("UserRepository")
    private UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = null;
        sesionesRepository sesion=null;
        Sesion ss = new Sesion();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        try{
            user = repo.findByEmail(email);
            ss.setCorreo(email);
            ss.setFecha(dateFormat.format(date));
            ss.setHora(hourFormat.format(date));
            ss.setExito("Exitoso");
            this.init.save(ss);
            return new User(user.getEmail(),user.getPassword(),user.isActivo(),user.isActivo(),user.isActivo(), user.isActivo(),buildGranted());
        }catch(Exception e){
            ss.setCorreo(email);
            ss.setFecha(dateFormat.format(date));
            ss.setHora(hourFormat.format(date));
            ss.setExito("Fallido");
            this.init.save(ss);
        }
        return new User(user.getEmail(),user.getPassword(),user.isActivo(),user.isActivo(),user.isActivo(), user.isActivo(),buildGranted());
    }

    public List<GrantedAuthority> buildGranted(){
        List<GrantedAuthority> auths = new ArrayList<>();
        return auths;
    }

    /*@Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }*/
    	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

    public boolean validarEstado(String cp, String state){
        API api = new API();
        boolean response;
        response = api.WS(cp, state);
        return response;
    }
}
