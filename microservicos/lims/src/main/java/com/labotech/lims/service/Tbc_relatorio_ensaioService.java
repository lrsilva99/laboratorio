package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_relatorio_ensaio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tbc_relatorio_ensaio.
 */
public interface Tbc_relatorio_ensaioService {

    /**
     * Save a tbc_relatorio_ensaio.
     *
     * @param tbc_relatorio_ensaio the entity to save
     * @return the persisted entity
     */
    Tbc_relatorio_ensaio save(Tbc_relatorio_ensaio tbc_relatorio_ensaio);

    /**
     *  Get all the tbc_relatorio_ensaios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_relatorio_ensaio> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_relatorio_ensaio.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_relatorio_ensaio findOne(Long id);

    /**
     *  Delete the "id" tbc_relatorio_ensaio.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_relatorio_ensaio corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_relatorio_ensaio> search(String query, Pageable pageable);
}
