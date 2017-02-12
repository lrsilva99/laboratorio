package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_frases;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service Interface for managing Tbc_frases.
 */
public interface Tbc_frasesService {

    /**
     * Save a tbc_frases.
     *
     * @param tbc_frases the entity to save
     * @return the persisted entity
     */
    Tbc_frases save(Tbc_frases tbc_frases);

    /**
     *  Get all the tbc_frases.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_frases> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_frases.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_frases findOne(Long id);

    /**
     *  Delete the "id" tbc_frases.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_frases corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_frases> search(String query, Boolean removido, Pageable pageable);
}
