package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_especie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_especie.
 */
public interface Tbc_especieService {

    /**
     * Save a tbc_especie.
     *
     * @param tbc_especie the entity to save
     * @return the persisted entity
     */
    Tbc_especie save(Tbc_especie tbc_especie);

    /**
     *  Get all the tbc_especies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_especie> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_especie.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_especie findOne(Long id);

    /**
     *  Delete the "id" tbc_especie.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_especie corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_especie> search(String query, Pageable pageable);
}
