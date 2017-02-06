package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_grupo_analise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_grupo_analise.
 */
public interface Tbc_grupo_analiseService {

    /**
     * Save a tbc_grupo_analise.
     *
     * @param tbc_grupo_analise the entity to save
     * @return the persisted entity
     */
    Tbc_grupo_analise save(Tbc_grupo_analise tbc_grupo_analise);

    /**
     *  Get all the tbc_grupo_analises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_grupo_analise> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_grupo_analise.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_grupo_analise findOne(Long id);

    /**
     *  Delete the "id" tbc_grupo_analise.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_grupo_analise corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_grupo_analise> search(String query, Pageable pageable);
}
