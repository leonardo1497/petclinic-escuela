/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ACER
 */
@Repository("UserRepository")
public interface UserRepository extends JpaRepository<Usuario, Serializable>{
    public abstract Usuario findByEmail(String email);
    
    /**
     *
     * @return
     * @throws DataAccessException
     */
    @Transactional(readOnly = true)   
    @Override
    List<Usuario> findAll() throws DataAccessException;
    
    @Query("SELECT usuario FROM Usuario usuario WHERE usuario.id =:id")
    @Transactional(readOnly = true)
    Usuario findById(@Param("id") Integer id);
    
    @Query("SELECT DISTINCT usuario FROM Usuario usuario WHERE usuario.name LIKE :firstName%")
    @Transactional(readOnly = true)
    Collection<Usuario> findByName(@Param("firstName") String nombre);
    
 
}
