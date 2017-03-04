package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_fazenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_fazenda.
 */
public interface Tbc_fazendaService {

    /**
     * Save a tbc_fazenda.
     *
     * @param tbc_fazenda the entity to save
     * @return the persisted entity
     */
    Tbc_fazenda save(Tbc_fazenda tbc_fazenda);

    /**
     *  Get all the tbc_fazendas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_fazenda> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_fazenda.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_fazenda findOne(Long id);

    /**
     *  Delete the "id" tbc_fazenda.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_fazenda corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_fazenda> search(String query, Pageable pageable);
}
