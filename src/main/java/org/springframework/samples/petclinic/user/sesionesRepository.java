/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author ACER
 */
public interface sesionesRepository extends Repository<Sesion, Integer>{
    
    @Transactional(readOnly = true)   
    Collection<Sesion> findAll() throws DataAccessException;
    
    void save(Sesion sesion);
   
}
