package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_tipo_cadastro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_tipo_cadastro.
 */
public interface Tbc_tipo_cadastroService {

    /**
     * Save a tbc_tipo_cadastro.
     *
     * @param tbc_tipo_cadastro the entity to save
     * @return the persisted entity
     */
    Tbc_tipo_cadastro save(Tbc_tipo_cadastro tbc_tipo_cadastro);

    /**
     *  Get all the tbc_tipo_cadastros.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_tipo_cadastro> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_tipo_cadastro.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_tipo_cadastro findOne(Long id);

    /**
     *  Delete the "id" tbc_tipo_cadastro.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_tipo_cadastro corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_tipo_cadastro> search(String query, Pageable pageable);
}
