package com.labotech.lims.service;

import com.labotech.lims.domain.Tbc_instituicao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tbc_instituicao.
 */
public interface Tbc_instituicaoService {

    /**
     * Save a tbc_instituicao.
     *
     * @param tbc_instituicao the entity to save
     * @return the persisted entity
     */
    Tbc_instituicao save(Tbc_instituicao tbc_instituicao);

    /**
     *  Get all the tbc_instituicaos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_instituicao> findAll(Pageable pageable);

    /**
     *  Get the "id" tbc_instituicao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tbc_instituicao findOne(Long id);

    /**
     *  Delete the "id" tbc_instituicao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tbc_instituicao corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tbc_instituicao> search(String query, Boolean removido, Pageable pageable);
}
