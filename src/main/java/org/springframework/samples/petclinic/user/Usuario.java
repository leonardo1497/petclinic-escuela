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
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.samples.petclinic.model.BaseEntity;

@Entity
@Table(name = "auth_user")
public class Usuario /*extends BaseEntity*/{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_user_id")
    private int id;

    @Column(name = "first_name")
    @NotEmpty
    private String name;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Column(name = "email")
    @NotEmpty
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;
    
    @Column(name = "codigo")
    @NotEmpty
    private String codigo;
    
    @Column(name="municipio")
    @NotEmpty
    private String municipio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    /*@Column(name = "mobile")
    private String mobile;
*/
    @Column(name = "activo")
    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public int getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getLastName() {
            return lastName;
    }

    public void setLastName(String lastName) {
            this.lastName = lastName;
    }

    public String getEmail() {
            return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getPassword() {
            return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    /*public String getMobile() {
            return mobile;
    }

    public void setMobile(String mobile) {
            this.mobile = mobile;
    }*/
}