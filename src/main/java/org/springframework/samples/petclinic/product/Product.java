/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.product;

import org.springframework.samples.petclinic.medicament.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.samples.petclinic.model.BaseEntity;

/**
 *
 * @author Daniel
 */

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    
    @Column(name = "nombre")
    @NotEmpty
    private String nombre;

    @Column(name = "descripcion")
    @NotEmpty
    private String descripcion;
    
    @Column(name = "precio")
    @NotNull
    private float precio;
    
    @Column(name = "existencia")
    @NotNull
    private int existencia;
    
    @Column(name = "fotografia")
    private String fotografia;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    public int getExistencia() {
        return existencia;
    }
    
    public void setExistencia(int existencia){
        this.existencia = existencia;
    }
    
    public String getFotografia(){
        return fotografia;
    }
    
    public void setFotografia(String fotogafia){
        this.fotografia = fotografia;
    }

}
