/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.medicament;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.samples.petclinic.model.BaseEntity;

/**
 *
 * @author Isaac
 */

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {
    
    @Column(name = "nombre")
    @NotEmpty
    private String nombre;

    @Column(name = "ingredientes")
    @NotEmpty
    private String ingredientes;
    
    @Column(name = "presentacion")
    @NotEmpty
    private String presentacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

}
