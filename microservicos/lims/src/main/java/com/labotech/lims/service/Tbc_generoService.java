package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_genero.
 */
public interface Tbc_generoService {

    /**
     * Save a tbc_genero.
     *
     * @param tbc_genero the entity to save
     * @return the persisted entity
     */
    Tbc_genero save(Tbc_genero tbc_genero);

    /**
     *  Get all the tbc_generos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_genero> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_genero.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_genero findOne(Long id);

    /**
     *  Delete the "id" tbc_genero.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_genero corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_genero> search(String query, Pageable pageable);
}
