package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_tipo_campo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_tipo_campo.
 */
public interface Tbc_tipo_campoService {

    /**
     * Save a tbc_tipo_campo.
     *
     * @param tbc_tipo_campo the entity to save
     * @return the persisted entity
     */
    Tbc_tipo_campo save(Tbc_tipo_campo tbc_tipo_campo);

    /**
     *  Get all the tbc_tipo_campos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_tipo_campo> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_tipo_campo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_tipo_campo findOne(Long id);

    /**
     *  Delete the "id" tbc_tipo_campo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_tipo_campo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_tipo_campo> search(String query, Pageable pageable);
}
