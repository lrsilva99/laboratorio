package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_proprietario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_proprietario.
 */
public interface Tbc_proprietarioService {

    /**
     * Save a tbc_proprietario.
     *
     * @param tbc_proprietario the entity to save
     * @return the persisted entity
     */
    Tbc_proprietario save(Tbc_proprietario tbc_proprietario);

    /**
     *  Get all the tbc_proprietarios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_proprietario> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_proprietario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_proprietario findOne(Long id);

    /**
     *  Delete the "id" tbc_proprietario.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_proprietario corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_proprietario> search(String query, Pageable pageable);
}
