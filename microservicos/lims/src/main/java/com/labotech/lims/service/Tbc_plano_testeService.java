package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_plano_teste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service Interface for managing Tbc_plano_teste.
 */
public interface Tbc_plano_testeService {

    /**
     * Save a tbc_plano_teste.
     *
     * @param tbc_plano_teste the entity to save
     * @return the persisted entity
     */
    Tbc_plano_teste save(Tbc_plano_teste tbc_plano_teste);

    /**
     *  Get all the tbc_plano_testes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_plano_teste> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_plano_teste.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_plano_teste findOne(Long id);

    /**
     *  Delete the "id" tbc_plano_teste.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_plano_teste corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_plano_teste> search(String query, Boolean removido, Pageable pageable);
}
