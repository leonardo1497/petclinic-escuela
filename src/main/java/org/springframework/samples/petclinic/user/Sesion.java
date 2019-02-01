/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.samples.petclinic.model.BaseEntity;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "sesiones")
public class Sesion extends BaseEntity {
    @Column(name = "correo")
    private String correo;

    @Column(name = "fecha")
    private String fecha;
    
    @Column(name = "hora")
    private String hora;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    @Column(name = "exito")
    private String exito;

    public String getExito() {
        return exito;
    }

    public void setExito(String exito) {
        this.exito = exito;
    }


    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
