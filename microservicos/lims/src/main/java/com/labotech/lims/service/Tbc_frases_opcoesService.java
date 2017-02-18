package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_frases_opcoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Service Interface for managing Tbc_frases_opcoes.
 */
public interface Tbc_frases_opcoesService {

    /**
     * Save a tbc_frases_opcoes.
     *
     * @param tbc_frases_opcoes the entity to save
     * @return the persisted entity
     */
    Tbc_frases_opcoes save(Tbc_frases_opcoes tbc_frases_opcoes);

    /**
     *  Get all the tbc_frases_opcoes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_frases_opcoes> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_frases_opcoes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_frases_opcoes findOne(Long id);

    /**
     *  Delete the "id" tbc_frases_opcoes.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_frases_opcoes corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_frases_opcoes> search(String query, Pageable pageable);



    /**
     *  Get all the tbc_frases_opcoes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_frases_opcoes> findAll(Long idFrases, Pageable pageable);

}
