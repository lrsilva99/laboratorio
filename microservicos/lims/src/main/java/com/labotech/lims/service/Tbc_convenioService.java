package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_convenio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_convenio.
 */
public interface Tbc_convenioService {

    /**
     * Save a tbc_convenio.
     *
     * @param tbc_convenio the entity to save
     * @return the persisted entity
     */
    Tbc_convenio save(Tbc_convenio tbc_convenio);

    /**
     *  Get all the tbc_convenios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_convenio> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_convenio.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_convenio findOne(Long id);

    /**
     *  Delete the "id" tbc_convenio.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_convenio corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_convenio> search(String query, Pageable pageable);
}
