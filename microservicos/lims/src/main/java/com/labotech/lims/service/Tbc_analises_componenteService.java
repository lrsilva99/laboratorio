package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_analises_componente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tbc_analises_componente.
 */
public interface Tbc_analises_componenteService {

    /**
     * Save a tbc_analises_componente.
     *
     * @param tbc_analises_componente the entity to save
     * @return the persisted entity
     */
    Tbc_analises_componente save(Tbc_analises_componente tbc_analises_componente);

    /**
     *  Get all the tbc_analises_componentes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_analises_componente> findAll(Long idAnalise, Pageable pageable);

    /**
     *  Get the "id" tbc_analises_componente.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_analises_componente findOne(Long id);

    /**
     *  Delete the "id" tbc_analises_componente.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_analises_componente corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_analises_componente> search(String query, Pageable pageable);
}
