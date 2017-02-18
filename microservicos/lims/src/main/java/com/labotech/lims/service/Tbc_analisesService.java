package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_analises;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tbc_analises.
 */
public interface Tbc_analisesService {

    /**
     * Save a tbc_analises.
     *
     * @param tbc_analises the entity to save
     * @return the persisted entity
     */
    Tbc_analises save(Tbc_analises tbc_analises);

    /**
     * Get all the tbc_analises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Tbc_analises> findAll(Pageable pageable);

    /**
     * Get the "id" tbc_analises.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Tbc_analises findOne(Long id);

    /**
     * Delete the "id" tbc_analises.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_analises corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Tbc_analises> search(String query, Boolean removido, Pageable pageable);
}
