/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.product;

import org.springframework.samples.petclinic.medicament.*;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
public interface ProductRepository extends Repository<Product, Integer>{
    
    /**
     * Retrieve {@link Product}s from the data store by name, returning all medicaments
     * whose last name <i>starts</i> with the given name.
     * @param nombre Value to search for
     * @return a Collection of matching {@link Product} (or an empty Collection if none
     * found)
     */
    @Query("SELECT DISTINCT product FROM Product product WHERE product.nombre LIKE :nombre%")
    @Transactional(readOnly = true)
    Collection<Product> findByName(@Param("nombre") String nombre);
    
    /**
     * Retrieve an {@link Medicamenr} from the data store by id.
     * @param id the id to search for
     * @return the {@link Product} if found
     */
    @Query("SELECT product FROM Product product WHERE product.id =:id")
    @Transactional(readOnly = true)
    Product findById(@Param("id") Integer id);
    
    /**
     * Save an {@link Product} to the data store, either inserting or updating it.
     * @param medicament the {@link Product} to save
     */
    void save(Product medicament);
    
    /**
     * Delete an {@link Product} to the data store
     * @param medicament the {@link Product} to delete
     */
    void delete(Product medicament);
}
