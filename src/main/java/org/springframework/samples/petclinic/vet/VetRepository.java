/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.medicament.Medicament;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Vet</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface VetRepository extends Repository<Vet, Integer> {

    /**
     * Retrieve all <code>Vet</code>s from the data store.
     *
     * @return a <code>Collection</code> of <code>Vet</code>s
     */
    @Transactional(readOnly = true)   
    Collection<Vet> findAll() throws DataAccessException;
    
    @Query("SELECT vet FROM Vet vet WHERE vet.id =:id")
    @Transactional(readOnly = true)
    Vet findById(@Param("id") Integer id);
    
    @Query("SELECT DISTINCT vet FROM Vet vet WHERE vet.firstName LIKE :firstName%")
    @Transactional(readOnly = true)
    Collection<Vet> findByName(@Param("firstName") String nombre);
    
    @Query("SELECT specialties FROM Specialty specialties ORDER BY specialties.name")
    @Transactional(readOnly = true)
    List<Specialty> findSpecialityTypes();
    
    @Query("Select vet FROM Vet vet")
    @Transactional (readOnly= true)
    Collection <Vet> findAll2();
    
//    @Query("select distinct a.first_name, a.last_name from vets a, specialties b, vet_specialties c where c.vet_id=a.id")
//    @Transactional(readOnly=true)
//    Collection<Vet> findTodo();
   
   void save(Vet vet);
   
   
   void delete(Vet vet);
      
}
