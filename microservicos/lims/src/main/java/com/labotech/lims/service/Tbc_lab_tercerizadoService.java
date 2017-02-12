package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_lab_tercerizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service Interface for managing Tbc_lab_tercerizado.
 */
public interface Tbc_lab_tercerizadoService {

    /**
     * Save a tbc_lab_tercerizado.
     *
     * @param tbc_lab_tercerizado the entity to save
     * @return the persisted entity
     */
    Tbc_lab_tercerizado save(Tbc_lab_tercerizado tbc_lab_tercerizado);

    /**
     *  Get all the tbc_lab_tercerizados.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_lab_tercerizado> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_lab_tercerizado.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_lab_tercerizado findOne(Long id);

    /**
     *  Delete the "id" tbc_lab_tercerizado.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_lab_tercerizado corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_lab_tercerizado> search(String query, Boolean removido, Pageable pageable);
}
