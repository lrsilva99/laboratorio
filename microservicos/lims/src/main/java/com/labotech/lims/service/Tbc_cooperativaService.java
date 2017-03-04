package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_cooperativa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_cooperativa.
 */
public interface Tbc_cooperativaService {

    /**
     * Save a tbc_cooperativa.
     *
     * @param tbc_cooperativa the entity to save
     * @return the persisted entity
     */
    Tbc_cooperativa save(Tbc_cooperativa tbc_cooperativa);

    /**
     *  Get all the tbc_cooperativas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_cooperativa> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_cooperativa.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_cooperativa findOne(Long id);

    /**
     *  Delete the "id" tbc_cooperativa.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_cooperativa corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_cooperativa> search(String query, Pageable pageable);
}
