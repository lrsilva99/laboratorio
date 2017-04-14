package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_qualidade_amostra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_qualidade_amostra.
 */
public interface Tbc_qualidade_amostraService {

    /**
     * Save a tbc_qualidade_amostra.
     *
     * @param tbc_qualidade_amostra the entity to save
     * @return the persisted entity
     */
    Tbc_qualidade_amostra save(Tbc_qualidade_amostra tbc_qualidade_amostra);

    /**
     *  Get all the tbc_qualidade_amostras.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_qualidade_amostra> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_qualidade_amostra.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_qualidade_amostra findOne(Long id);

    /**
     *  Delete the "id" tbc_qualidade_amostra.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_qualidade_amostra corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_qualidade_amostra> search(String query, Pageable pageable);
}
