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
public class sesiones extends BaseEntity {
    @Column(name = "correo")
    @NotEmpty
    private String correo;

    @Column(name = "fecha")
    @NotEmpty
    private String fecha;
    
    @Column(name = "exito")
    @NotEmpty
    private String exito;
    
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
