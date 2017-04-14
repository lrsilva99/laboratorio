package com.labotech.lims.service;

import com.labotech.lims.domain.Tbr_amostra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbr_amostra.
 */
public interface Tbr_amostraService {

    /**
     * Save a tbr_amostra.
     *
     * @param tbr_amostra the entity to save
     * @return the persisted entity
     */
    Tbr_amostra save(Tbr_amostra tbr_amostra);

    /**
     *  Get all the tbr_amostras.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbr_amostra> findAll(Pageable pageable);

    /**
     *  Get the "id" tbr_amostra.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbr_amostra findOne(Long id);

    /**
     *  Delete the "id" tbr_amostra.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbr_amostra corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbr_amostra> search(String query, Pageable pageable);
}
